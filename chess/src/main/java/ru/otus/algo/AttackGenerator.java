package ru.otus.algo;

import java.util.Set;

final class AttackGenerator {

    private static final MagicTable magic = new MagicTable();
    private static final Direction[] KING_ATTACKS = {
            Direction.NORTH_WEST, Direction.NORTH, Direction.NORTH_EAST,
                    Direction.WEST, Direction.EAST,
                    Direction.SOUTH_WEST, Direction.SOUTH, Direction.SOUTH_EAST};

    private long generateAttackForSlidingPieces(Piece piece, Position pos) {
        long attacks;
        final long blockers = pos.getBlockers();
        if (piece.getFigure() == Figure.ROOK)
            attacks = magic.getRookAttacks(piece.getSquare(), blockers);
        else if (piece.getFigure() == Figure.BISHOP)
            attacks = magic.getBishopAttacks(piece.getSquare(), blockers);
        else {
            attacks = magic.getBishopAttacks(piece.getSquare(), blockers);
            attacks |= magic.getRookAttacks(piece.getSquare(), blockers);
        }
        return attacks;
    }

    long generateAttackMap(Piece piece, Position position) {
        Figure figure = piece.getFigure();
        long res;
        if (figure == Figure.KING) {
            res = generateAttackForKing(piece);
        } else if (figure == Figure.PAWN) {
            res = generateAttackMapForPawn(piece);
        } else if (figure == Figure.KNIGHT) {
            res = generateAttackForKnight(piece);
        } else {
            res = generateAttackForSlidingPieces(piece, position);
        }
        final long pieceMap = piece.getSquare().getPieceMap();
        res &=  ~pieceMap & ~position.getBlockers(piece.getSide());
        return res;
    }

    private long generateAttackForKnight(Piece piece) {
        final long pieceMap = piece.getSquare().getPieceMap();
        return BitManipulation.fillKnight(pieceMap);
    }

    private long generateAttackForKing(Piece piece) {
        long val = piece.getSquare().getPieceMap();
        return BitManipulation.fillOnce(val, KING_ATTACKS);
    }

    private long generateAttackMapForPawn(Piece piece) {
        final Side side = piece.getSide();
        final long pieceMap = piece.getSquare().getPieceMap();
        if (Side.WHITE == side) {
            return BitManipulation.fillOnce(pieceMap, new Direction[]{Direction.NORTH_EAST, Direction.NORTH_WEST});
        } else {
            return BitManipulation.fillOnce(pieceMap, new Direction[]{Direction.SOUTH_EAST, Direction.SOUTH_WEST});

        }
    }

    long generateMovesForPawn(Piece piece, Position pos) {
        final Side side = pos.getSideToMove();
        boolean hasDouble = side == Side.WHITE ? Square.Rank.isOn(piece.getSquare(), Square.Rank.SECOND) :
                Square.Rank.isOn(piece.getSquare(), Square.Rank.SEVENTH);
        long blockers = pos.getBlockers();
        final long pieceMap = piece.getSquare().getPieceMap();
        long obs = blockers & ~pieceMap;
        final Direction[] dir = {side == Side.WHITE ? Direction.NORTH : Direction.SOUTH};
        final long l = BitManipulation.fillOnce(pieceMap, dir);
        long res = l & ~obs;
        if (hasDouble)
            res |= BitManipulation.fillOnce(res, dir) & ~obs;

        return res;
    }

    void generateMovesFromLong(long bits, Piece piece, Set<Move> moves) {
        int pos = 0;
        while ((bits != 0)) {
            int delta = Long.numberOfTrailingZeros(bits);
            pos += delta;
            bits = bits >>> (delta + 1);
            Move move = Move.of(piece, Square.of(pos++));

            if (!moves.add(move))
                throw new IllegalStateException("move has already added!");
            if (delta == 63) break;
        }
    }
}