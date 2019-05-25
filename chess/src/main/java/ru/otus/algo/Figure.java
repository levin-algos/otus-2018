package ru.otus.algo;

public enum Figure {

    PAWN(0),
    ROOK(1),
    KNIGHT(2),
    BISHOP(3),
    QUEEN(4),
    KING(5);

    private int value;

    Figure(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
