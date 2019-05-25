package ru.otus.algo;

import java.util.*;

public class Position {

    private final Direction[][] MOVE_DIRECTIONS = {
            // PAWN
            {Direction.NORTH},
            // ROOK
            {},
            // KNIGHT
            {},
            // BISHOP
            {},
            // QUEEN
            {},
            // KING
            {Direction.NORTH_WEST, Direction.NORTH, Direction.NORTH_EAST,
             Direction.WEST,                        Direction.EAST,
             Direction.SOUTH_WEST, Direction.SOUTH, Direction.SOUTH_EAST  }
    };

    private final Map<Figure, Set<Long>> whites;
    private final Map<Figure, Set<Long>> blacks;
    private long obstacles;

    private Position() {
        whites = new HashMap<>();
        blacks = new HashMap<>();
    }

    private Position(Position pos) {
        whites = new HashMap<>();
        blacks = new HashMap<>();

        pos.whites.forEach(whites::put);
        pos.blacks.forEach(blacks::put);
        pos.obstacles = obstacles;
    }

    public static Position of(Side side, Figure figure, Square sq) {
        Position position = new Position();
        position.add(side, figure, sq);
        return position;
    }

    public Position move(Move move) {
        Objects.requireNonNull(move);

        Map<Figure, Set<Long>> map = move.getSide() == Side.WHITE ? whites : blacks;
        Set<Long> set = map.get(move.getFigure());

        if (set == null || set.size() == 0)
            throw new IllegalStateException("wrong move");

        if (!validateMove(move.getFigure(), set, move.getFrom().getValue(), move.getDestination().getValue()))
            throw new IllegalStateException("wrong move!");

        Position position = new Position(this);
        map = move.getSide() == Side.WHITE ? position.whites : position.blacks;
        set = map.get(move.getFigure());

        if (!set.remove(1L << move.getFrom().getValue()))
            throw new IllegalStateException();
        set.add(1L << move.getDestination().getValue());

        return position;
    }

    private boolean validateMove(Figure figure, Set<Long> set, int from, int to) {
        long bits = 1L << from;
        if (!set.contains(bits))
            return false;

        long res = 0;
        if (figure == Figure.KING) {
            res = BitManipulation.fillOnce(bits, MOVE_DIRECTIONS[Figure.KING.getValue()]);
        }
        long value = 1L << to;
        return (res & value) != 0;
    }

    private void add(Side side, Figure figure, Square sq) {
        Map<Figure, Set<Long>> map = side == Side.WHITE ? whites : blacks;
        Set<Long> longs = map.getOrDefault(figure, new HashSet<>());
        long bits = 1L << sq.getValue();
        longs.add(bits);
        map.put(figure, longs);
        obstacles |= bits;
    }

    public Set<Move> getAllMoves() {
        Set<Move> moves = new HashSet<>();
        generateMoves(Side.WHITE, moves);
        generateMoves(Side.BLACK, moves);
        return moves;
    }

    private void generateMoves(Side side, Set<Move> moves) {
        Map<Figure, Set<Long>> map = side == Side.WHITE ? whites : blacks;
        for (Map.Entry<Figure, Set<Long>> f: map.entrySet()) {
            Figure key = f.getKey();
            if (key == Figure.KING) {
                generateMovesForKing(side, f.getValue(), moves);
            } else if (key == Figure.PAWN) {
                generateMovesForPawn(side, f.getValue(), moves);
            }
        }
    }

    private void generateMovesForPawn(Side side, Set<Long> value, Set<Move> moves) {
        for (Long val: value) {
            int i = Long.numberOfTrailingZeros(val);
            boolean hasDouble = i > 7 && i < 16;
            long obs = obstacles & ~val;
            long res = BitManipulation.fillOnce(val, MOVE_DIRECTIONS[Figure.PAWN.getValue()]) & ~obs;
            if (hasDouble)
                res |= BitManipulation.fillOnce(res, MOVE_DIRECTIONS[Figure.PAWN.getValue()]) & ~obs;
            generateMovesFromLong(res, side, Square.of(i), Figure.PAWN, moves);
        }
    }

    private void generateMovesForKing(Side side, Set<Long> value, Set<Move> moves) {
        for (Long val: value) {
            Square from = Square.of(Long.numberOfTrailingZeros(val));
            long obs = obstacles & ~val;
            long res = BitManipulation.fillOnce(val, MOVE_DIRECTIONS[Figure.KING.getValue()]) & ~obs;
            generateMovesFromLong(res, side, from, Figure.KING, moves);
        }
    }

    private void generateMovesFromLong(long bits, Side side, Square from, Figure figure,  Set<Move> moves) {
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

    public static class Builder {

        private Position position;

        public Builder() {
            this.position = new Position();
        }

        public Builder add(Side white, Figure figure, Square sq) {
            position.add(white, figure, sq);
            return this;
        }

        public Position build() {
            return position;
        }
    }
}