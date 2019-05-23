package ru.otus.algo;

import java.util.HashSet;
import java.util.Set;

public class King extends AbstractFigure {

    public King(Board board, Position position, Side side) {
        super(board, position, side, Figures.KING);
    }

    @Override
    public Set<Position> getMoves() {
        final HashSet<Object> set = new HashSet<>();

        return null;
    }

}

/*
M:
7 0 0 0 0 0 0 0 0
6 0 0 0 0 0 0 0 0
5 0 0 0 0 0 0 0 0
4 0 0 0 0 0 0 0 0
3 0 0 0 0 0 0 0 0
2 1 1 1 0 0 0 0 0
1 1 0 1 0 0 0 0 0
0 1 1 1 0 0 0 0 0

  0 1 2 3 4 5 6 7

460039

  a1 = M >>>
*/
