package ru.otus.algo;

import java.util.*;
import java.util.stream.Collectors;

public class Position {
    private static final Direction[][] MOVE_DIRECTIONS = {
            // PAWN
            {Direction.NORTH},
            // ROOK
            {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST},
            // KNIGHT
            {Direction.NORTH_NORTH_EAST, Direction.NORTH_EAST_EAST, Direction.SOUTH_EAST_EAST,
                    Direction.SOUTH_SOUTH_EAST, Direction.SOUTH_SOUTH_WEST, Direction.SOUTH_WEST_WEST,
                    Direction.NORTH_WEST_WEST, Direction.NORTH_NORTH_WEST},
            // BISHOP
            {Direction.SOUTH_WEST, Direction.SOUTH_EAST, Direction.NORTH_WEST, Direction.NORTH_EAST},
            // QUEEN
            {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST,
                    Direction.SOUTH_WEST, Direction.SOUTH_EAST, Direction.NORTH_WEST, Direction.NORTH_EAST},
            // KING
            {Direction.NORTH_WEST, Direction.NORTH, Direction.NORTH_EAST,
                    Direction.WEST, Direction.EAST,
                    Direction.SOUTH_WEST, Direction.SOUTH, Direction.SOUTH_EAST}
    };

    private final Map<Figure, Set<Long>> whites;
    private final Map<Figure, Set<Long>> blacks;

    private long whiteBlockers;
    private long blackBlockers;
    private long blockers;
    private long whiteAttack;
    private long blackAttack;
    private Side sideToMove = Side.WHITE;
    private Square enPassant;
    private int halfMoveClock;
    private int castleAbility;
    private int movesNum;
    private static MagicTable magic = new MagicTable();

    public Position() {
        whites = new HashMap<>();
        blacks = new HashMap<>();
    }

    private Position(Position pos, Move move) {
        whites = new HashMap<>();
        blacks = new HashMap<>();

        pos.whites.forEach((figure, longs) -> {
            Set<Long> res = new HashSet<>(longs);
            whites.put(figure, res);
        });

        pos.blacks.forEach((figure, longs) -> {
            Set<Long> res = new HashSet<>(longs);
            blacks.put(figure, res);
        });

        if (sideToMove != move.getSide())
            throw new IllegalStateException();

        Map<Figure, Set<Long>> map = move.getSide() == Side.WHITE ? whites : blacks;
        Set<Long> set = map.get(move.getFigure());

        if (!set.remove(1L << move.getFrom().getValue()))
            throw new IllegalStateException("Cannot find piece: " + move);

        set.add(1L << move.getDestination().getValue());

        generateBlockers();

        sideToMove = sideToMove == Side.WHITE ? Side.BLACK : Side.WHITE;
        generateAttackMaps();
    }

    private void generateBlockers() {
        for (Map.Entry<Figure, Set<Long>> entry : whites.entrySet()) {
            entry.getValue().forEach(l ->
                    whiteBlockers |= l
            );
        }

        for (Map.Entry<Figure, Set<Long>> entry : blacks.entrySet()) {
            entry.getValue().forEach(l ->
                    blackBlockers |= l);}
        blockers = whiteBlockers | blackBlockers;
    }

    private void generateAttackMaps() {
        for (Map.Entry<Figure, Set<Long>> entry : whites.entrySet()) {
            entry.getValue().forEach(l ->
                    whiteAttack |= generateAttackMap(Side.WHITE, entry.getKey(), l)
            );
        }

        for (Map.Entry<Figure, Set<Long>> entry : blacks.entrySet()) {
            entry.getValue().forEach(l ->
                    blackAttack |= generateAttackMap(Side.BLACK, entry.getKey(), l)
            );
        }
    }

    public Side getSideToMove() {
        return sideToMove;
    }

    public Position move(Move move) {
        Objects.requireNonNull(move);

        Map<Figure, Set<Long>> map = move.getSide() == Side.WHITE ? whites : blacks;
        Set<Long> set = map.get(move.getFigure());
        generateAttackMaps();

        if (set == null || set.size() == 0)
            throw new IllegalStateException("wrong move");

        return new Position(this, move);
    }

    private void add(Side side, Figure figure, Square sq) {
        Map<Figure, Set<Long>> map = side == Side.WHITE ? whites : blacks;
        Set<Long> longs = map.getOrDefault(figure, new HashSet<>());
        long bits = 1L << sq.getValue();
        longs.add(bits);
        map.put(figure, longs);
        if (Side.WHITE == side) {
            whiteBlockers |= bits;
        } else {
            blackBlockers |= bits;
        }
        blockers = whiteBlockers | blackBlockers;
    }

    public Set<Move> getAllMoves() {
        Set<Move> moves = new HashSet<>();
        generateMoves(sideToMove, moves);
        return moves.stream().filter(this::checkPins).collect(Collectors.toSet());
    }

    private long generateAttackMap(Side side, Figure figure, Long pieceMap) {
        if (Figure.PAWN == figure) {
            if (Side.WHITE == side) {
                return BitManipulation.fillOnce(pieceMap, new Direction[]{Direction.NORTH_EAST, Direction.NORTH_WEST}) & ~whiteBlockers;
            } else {
                final long l = BitManipulation.fillOnce(pieceMap, new Direction[]{Direction.SOUTH_EAST, Direction.SOUTH_WEST});
                return l & ~blackBlockers;
            }
        } else if (Figure.KNIGHT == figure) {
            long blocker = side == Side.WHITE ? whiteBlockers : blackBlockers & ~pieceMap;
            return BitManipulation.fillOnce(pieceMap, MOVE_DIRECTIONS[Figure.KNIGHT.getValue()]) & ~blocker;
        } else if (Figure.KING == figure) {
            long blocker = (side == Side.WHITE ? whiteBlockers : blackBlockers) & ~pieceMap;
            long attack = side == Side.WHITE ? blackAttack : whiteAttack;
            final long kng = BitManipulation.fillOnce(pieceMap, MOVE_DIRECTIONS[Figure.KING.getValue()]);
            return ~attack & kng & ~blocker;
        } else {
            int square = Long.numberOfTrailingZeros(pieceMap);
            long attacks;
            if (figure == Figure.ROOK)
                attacks = magic.getRookAttacks(square, blockers);
            else if (figure == Figure.BISHOP)
                attacks = magic.getBishopAttacks(square, blockers);
            else {
                attacks = magic.getBishopAttacks(square, blockers);
                attacks |= magic.getRookAttacks(square, blockers);
            }
            attacks &= ~pieceMap & ~(sideToMove == Side.WHITE ? whiteBlockers : blackBlockers);
            return attacks;
        }
    }

    private void generateMoves(Side side, Set<Move> moves) {
        Map<Figure, Set<Long>> map = side == Side.WHITE ? whites : blacks;
        for (Map.Entry<Figure, Set<Long>> f : map.entrySet()) {
            Figure key = f.getKey();
            if (key == Figure.KING) {
                generateMovesForKing(side, f.getValue(), moves);
            } else if (key == Figure.PAWN) {
                generateMovesForPawn(side, f.getValue(), moves);
            } else if (key == Figure.KNIGHT) {
                generateMovesForKnight(side, f.getValue(), moves);
            } else {
                generateMoveForSlidingPieces(side, key, f.getValue(), moves);
            }
        }
    }

    private void generateMoveForSlidingPieces(Side side, Figure figure, Set<Long> value, Set<Move> moves) {
        for (Long val : value) {
            int square = Long.numberOfTrailingZeros(val);

            long attacks;
            if (figure == Figure.ROOK)
                attacks = magic.getRookAttacks(square, blockers);
            else if (figure == Figure.BISHOP)
                attacks = magic.getBishopAttacks(square, blockers);
            else {
                attacks = magic.getBishopAttacks(square, blockers);
                attacks |= magic.getRookAttacks(square, blockers);
            }
            attacks &= ~val & ~(sideToMove == Side.WHITE ? whiteBlockers : blackBlockers);
            generateMovesFromLong(attacks, side, Square.of(square), figure, moves);
        }
    }

    private void generateMovesForKnight(Side side, Set<Long> value, Set<Move> moves) {
        for (Long val : value) {
            Square from = Square.of(Long.numberOfTrailingZeros(val));
            long blocker = side == Side.WHITE ? whiteBlockers : blackBlockers & ~val;
            final long attack = BitManipulation.fillOnce(val, MOVE_DIRECTIONS[Figure.KNIGHT.getValue()]) & ~blocker;
            generateMovesFromLong(attack, side, from, Figure.KNIGHT, moves);
        }
    }

    private void generateMovesForKing(Side side, Set<Long> value, Set<Move> moves) {
        for (Long val : value) {
            Square from = Square.of(Long.numberOfTrailingZeros(val));
            long blocker = (side == Side.WHITE ? whiteBlockers : blackBlockers) & ~val;
            long attack = side == Side.WHITE ? blackAttack : whiteAttack;
            final long kng = BitManipulation.fillOnce(val, MOVE_DIRECTIONS[Figure.KING.getValue()]);
            long res = ~attack & kng & ~blocker;
            generateMovesFromLong(res, side, from, Figure.KING, moves);
        }
    }

    private void generateMovesForPawn(Side side, Set<Long> value, Set<Move> moves) {
        for (Long val : value) {
            int i = Long.numberOfTrailingZeros(val);
            boolean hasDouble = side == Side.WHITE ? Square.Rank.isOn(Square.of(i), Square.Rank.SECOND) :
                    Square.Rank.isOn(Square.of(i), Square.Rank.SEVENTH);
            long obstacles = whiteBlockers | blackBlockers;
            long partnerObs = Side.WHITE == side ? blackBlockers : whiteBlockers;
            long obs = obstacles & ~val;
            final Direction[] dir = {side == Side.WHITE ? Direction.NORTH : Direction.SOUTH};
            final long l = BitManipulation.fillOnce(val, dir);
            long captures = generateAttackMap(side, Figure.PAWN, val) & partnerObs;
            long res = l & ~obs;
            if (hasDouble)
                res |= BitManipulation.fillOnce(res, dir) & ~obs;

            res |= captures;
            generateMovesFromLong(res, side, Square.of(i), Figure.PAWN, moves);
        }
    }

    private void generateMovesFromLong(long bits, Side side, Square from, Figure figure, Set<Move> moves) {
        int pos = 0;
        while ((bits != 0)) {
            int delta = Long.numberOfTrailingZeros(bits);
            pos += delta;
            bits = bits >>> (delta + 1);
            Move move = side == Side.WHITE ? Move.white(figure, from, Square.of(pos++)) : Move.black(figure, from, Square.of(pos++));

            if (!moves.add(move))
                throw new IllegalStateException("move has already added!");
        }
    }

    private boolean checkPins(Move move) {
//        System.out.println(move);
        final Position position = move(move);
//        System.out.println(BitManipulation.drawLong(position.whiteBlockers) + " white blockers");
        final Side side = position.sideToMove == Side.WHITE ? Side.BLACK : Side.WHITE;
        return !position.isCheck(side);
    }

    private boolean isCheck(Side side) {
        long attacks = Side.WHITE == side ? blackAttack : whiteAttack;
//        System.out.println(BitManipulation.drawLong(attacks) + ": attacks");
        long king = Side.BLACK == side ? blacks.get(Figure.KING).toArray(new Long[0])[0] :
                whites.get(Figure.KING).toArray(new Long[0])[0];
//        System.out.println(BitManipulation.drawLong(king) + ": king");
        final long l = attacks & king;
        return l != 0;
    }

    public Set<Piece> getPieces(Side side) {
        Set<Piece> pieces = new HashSet<>();
        Map<Figure, Set<Long>> map = side == Side.WHITE ? whites : blacks;

        map.forEach((figure, longs) -> {
            longs.forEach(aLong -> {
                int pos = Long.numberOfTrailingZeros(aLong);
                pieces.add(Piece.of(side, figure, Square.of(pos)));
            });
        });
        return pieces;
    }

    public int getHalfMoveClock() {
        return halfMoveClock;
    }

    public Optional<Square> getEnPassant() {
        return Optional.ofNullable(enPassant);
    }

    public static class Builder implements PositionBuilder {

        private Position position;

        public Builder() {
            this.position = new Position();
        }

        @Override
        public Builder add(Piece... piece) {
            for (Piece p : piece)
                position.add(p.getSide(), p.getFigure(), p.getSquare());

            return this;
        }

        @Override
        public PositionBuilder setMoveSide(Side side) {
            position.sideToMove = side;
            return this;
        }

        @Override
        public PositionBuilder setEnPassant(Square enPassant) {
            position.enPassant = enPassant;
            return this;
        }

        @Override
        public PositionBuilder setHalfMoveClock(int halfMoveClock) {
            position.halfMoveClock = halfMoveClock;
            return this;
        }

        @Override
        public PositionBuilder setCaslte(Side side, Castle castle) {
            position.setCastle(side, castle);
            return this;
        }

        @Override
        public PositionBuilder setMovesCount(int count) {
            position.movesNum = count;
            return this;
        }

        @Override
        public Position build() {
            return position;
        }
    }

    private void setCastle(Side side, Castle castle) {
        int s = side == Side.WHITE ? 0 : 1;
        int c = castle == Castle.KINGS_SIDE ? 0 : 1;

        castleAbility |= 1L << s * 2 + c;
    }

    private void unsetCastle(Side side, Castle castle) {
        int s = side == Side.WHITE ? 0 : 1;
        int c = castle == Castle.KINGS_SIDE ? 0 : 1;

        castleAbility &= ~(1L << s * 2 + c);
    }

    public boolean canCastle(Side side, Castle castle) {
        int s = side == Side.WHITE ? 0 : 1;
        int c = castle == Castle.KINGS_SIDE ? 0 : 1;

        long l = 1L << s * 2 + c;
        return (castleAbility & l) != 0;
    }
}