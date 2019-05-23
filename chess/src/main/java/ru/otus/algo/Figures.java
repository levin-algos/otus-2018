package ru.otus.algo;

public enum Figures {

    PAWN(0),
    ROOK(1),
    KNIGHT(2),
    BISHOP(3),
    QUEEN(4),
    KING(5);

    private int value;

    Figures(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
