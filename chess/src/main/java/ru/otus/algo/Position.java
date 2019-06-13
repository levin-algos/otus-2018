package ru.otus.algo;

import java.util.*;

/*TODO:
    1. generate en passant
    2. add en passant fens for both sides
 */

public class Position {
    private final Map<Square, Piece> whites;
    private final Map<Square, Piece> blacks;

    private final Map<Piece, Long> attacks;

    private long whiteBlockers, blackBlockers;
    private long whiteAttacks, blackAttacks;
    private Side sideToMove;
    private Square enPassant = null;
    private int halfMoveClock;
    private int castleAbility;
    private int movesNum;
    private static final AttackGenerator generator = new AttackGenerator();

    private Position() {
        whites = new HashMap<>();
        blacks = new HashMap<>();
        attacks = new HashMap<>();
    }

    private Position(Position pos, Move move) {
        whites = new HashMap<>(pos.whites);
        blacks = new HashMap<>(pos.blacks);
        attacks = new HashMap<>();
        sideToMove = pos.sideToMove;
        movesNum++;

        if (sideToMove != move.getSide())
            throw new IllegalStateException();

        Map<Square, Piece> map = move.getSide() == Side.WHITE ? whites : blacks;
        Map<Square, Piece> opp = move.getSide() == Side.BLACK ? whites : blacks;

        final Square destination = move.getDestination();
        if (map.containsKey(destination))
            throw new IllegalStateException("Destination square is not empty: " + move);

        final Piece piece = move.getPiece();
        if (!map.remove(move.getFrom(), piece))
            throw new IllegalStateException("Cannot find piece: " + move);

        if (move.getType() == MoveType.EN_PASSANT) {
            if (pos.enPassant == null)
                throw new IllegalStateException("move type in en passant, but en passant square is null");

            Square square;
            if (piece.getSide() == Side.WHITE) {
                square = Square.decreaseRank(pos.enPassant, 1);
            } else {
                square = Square.addRank(pos.enPassant, 1);
            }
            if (opp.remove(square) == null)
                throw new IllegalStateException("en passant pawn is not found!");

            map.put(destination, Piece.of(piece.getSide(), piece.getFigure(), destination));

        } else if (move.getType() == MoveType.PROMOTION) {
            map.put(destination, Piece.of(piece.getSide(), move.getPromotion(), destination));
        } else if (move.getType() == MoveType.CASTLING_KINGSIDE) {
            Square rookSquare = sideToMove == Side.WHITE? Square.H1 : Square.H8;
            Square rookDestination = sideToMove == Side.WHITE? Square.F1: Square.F8;
            final Piece rook = map.get(rookSquare);
            if (rook.getFigure() == Figure.ROOK) {
                map.remove(rookSquare);
                Piece last = map.put(rookDestination, Piece.of(sideToMove, Figure.ROOK, rookDestination));
                if (last != null || opp.containsKey(destination))
                    throw new IllegalStateException("rook destination square is not empty while castling");
            }
        } else {
            map.put(destination, Piece.of(piece.getSide(), piece.getFigure(), destination));
        }

        opp.remove(destination);
        calculateBlockers(Side.WHITE);
        calculateBlockers(Side.BLACK);
        calculateAttacks();
        switchSide();
    }

    boolean isCheckTo(Side side) {
        Piece king = null;
        Map<Square, Piece> map = side == Side.WHITE ? whites : blacks;
        for (Map.Entry<Square, Piece> entry : map.entrySet()) {
            final Piece value = entry.getValue();
            if (value.getFigure() == Figure.KING)
                king = value;
        }

        if (king != null) {
            final long attacks = side == Side.WHITE ? blackAttacks : whiteAttacks;
            final long l = attacks & king.getSquare().getPieceMap();

            return l != 0;
        }
        return false;
    }

    private void calculateAttacks() {
        for (Map.Entry<Square, Piece> entry : whites.entrySet()) {
            final Piece piece = entry.getValue();
            final long v = generator.generateAttackMap(piece, this);
            whiteAttacks |= v;
            attacks.put(piece, v);
        }

        for (Map.Entry<Square, Piece> entry : blacks.entrySet()) {
            final Piece piece = entry.getValue();
            final long v = generator.generateAttackMap(piece, this);
            blackAttacks |= v;
            attacks.put(piece, v);
        }
    }

    private void calculateBlockers(Side sideToMove) {
        long res = 0;
        Map<Square, Piece> map = sideToMove == Side.WHITE ? whites : blacks;
        for (Map.Entry<Square, Piece> entry : map.entrySet()) {
            res |= entry.getKey().getPieceMap();
        }

        if (sideToMove == Side.WHITE)
            whiteBlockers = res;
        else
            blackBlockers = res;
    }

    private void switchSide() {
        sideToMove = sideToMove == Side.WHITE ? Side.BLACK : Side.WHITE;
    }

    public Side getSideToMove() {
        return sideToMove;
    }

    Position move(Move move) {
        Objects.requireNonNull(move);

        Map<Square, Piece> map = move.getSide() == Side.WHITE ? whites : blacks;

        if (!map.get(move.getFrom()).equals(move.getPiece()))
            throw new IllegalStateException("wrong move");

        return new Position(this, move);
    }

    private void add(Piece piece) {
        final Side side = piece.getSide();
        Map<Square, Piece> map = side == Side.WHITE ? whites : blacks;

        if (map.containsKey(piece.getSquare()))
            throw new IllegalStateException();

        map.put(piece.getSquare(), piece);
        final long pieceMap = piece.getSquare().getPieceMap();
        if (side == Side.WHITE) {
            whiteBlockers |= pieceMap;
        } else if (side == Side.BLACK) {
            blackBlockers |= pieceMap;
        }
    }

    public int getHalfMoveClock() {
        return halfMoveClock;
    }

    public Optional<Square> getEnPassant() {
        return Optional.ofNullable(enPassant);
    }

    public Set<Piece> getPieces(Side side) {
        return new HashSet<>((side == Side.WHITE ? whites : blacks).values());
    }

    Set<Move> getAllMoves() {
        final Map<Square, Piece> map = sideToMove == Side.WHITE ? whites : blacks;
        Set<Move> moves = new HashSet<>();
        for (Map.Entry<Square, Piece> entry : map.entrySet()) {
            final Piece piece = entry.getValue();
            Long bits = attacks.get(piece);
            if (bits == null)
                throw new IllegalStateException("attack table not found");

            if (piece.getFigure() == Figure.KING) {
                long attack = (sideToMove == Side.WHITE ? blackAttacks : whiteAttacks);
                castle(piece.getSide(), attack, getBlockers(), moves);
                bits &= ~attack;
            } else if (piece.getFigure() == Figure.PAWN) {
                enPassant(piece, bits, moves);
                final long opponent = sideToMove == Side.BLACK ? whiteBlockers : blackBlockers;
                final long m = generator.generateMovesForPawn(piece, this);
                bits = (bits & opponent) | m;
            }
            generator.generateMovesFromLong(bits, piece, moves);
        }
        return moves;
    }

    private void enPassant(Piece piece, long bits, Set<Move> moves) {
        final long enPassant = getEnPassant().isPresent() ? getEnPassant().get().getPieceMap() : 0;
        if ((bits & enPassant) != 0) {
            moves.add(Move.enPassant(piece, getEnPassant().get()));
        }
    }

    private void castle(Side side, long attack, long blockers, Set<Move> moves) {
        if (canCastle(side, Castle.KING_SIDE)) {
            long kingPath = side == Side.WHITE? 3L << 5 : 3L << 61;

            long canCastle = (attack & kingPath) | (kingPath & blockers);
            if (canCastle == 0)
                moves.add(Move.castle(side, Castle.KING_SIDE));
        }
        if (canCastle(side, Castle.QUEEN_SIDE)) {
            long kingPath = side == Side.WHITE? 7L << 1: 3L << 58;
            long castlePath = side == Side.WHITE? 3L << 2: 3L << 58;
            long canCastle = (attack & castlePath) | (kingPath & blockers);
            if (canCastle == 0)
                moves.add(Move.castle(side, Castle.QUEEN_SIDE));
        }
    }

    long getBlockers(Side side) {
        return side == Side.WHITE ? whiteBlockers : blackBlockers;
    }

    long getBlockers() {
        return blackBlockers | whiteBlockers;
    }

    public long perft(int depth) {
        if (depth < 0)
            throw new IllegalArgumentException();
        if (depth == 0) return 1;

        final Set<Move> allMoves = getAllMoves();

        long res = 0;
        for (Move m: allMoves) {
            res += move(m).perft(depth-1);
        }

        return res;
    }

    public static class Builder implements PositionBuilder {
        private final Position position;

        public Builder() {
            this.position = new Position();
        }

        @Override
        public void add(Side side, Figure figure, Square square) {
            position.add(Piece.of(side, figure, square));
        }

        @Override
        public void setMoveSide(Side side) {
            position.sideToMove = side;
        }

        @Override
        public void setEnPassant(Square enPassant) {
            position.enPassant = enPassant;
        }

        @Override
        public void setHalfMoveClock(int halfMoveClock) {
            position.halfMoveClock = halfMoveClock;
        }

        @Override
        public void setCastle(Side side, Castle castle) {
            position.setCastle(side, castle);
        }

        @Override
        public void setMovesCount(int count) {
            position.movesNum = count;
        }

        @Override
        public Position build() {
            position.calculateBlockers(Side.WHITE);
            position.calculateBlockers(Side.BLACK);
            position.calculateAttacks();
            return position;
        }
    }

    private void setCastle(Side side, Castle castle) {
        int s = side == Side.WHITE ? 0 : 1;
        int c = castle == Castle.KING_SIDE ? 0 : 1;

        castleAbility |= 1L << s * 2 + c;
    }

    private void unsetCastle(Side side, Castle castle) {
        int s = side == Side.WHITE ? 0 : 1;
        int c = castle == Castle.KING_SIDE ? 0 : 1;

        castleAbility &= ~(1L << s * 2 + c);
    }

    public boolean canCastle(Side side, Castle castle) {
        int s = side == Side.WHITE ? 0 : 1;
        int c = castle == Castle.KING_SIDE ? 0 : 1;

        long l = 1L << s * 2 + c;
        return (castleAbility & l) != 0;
    }
}