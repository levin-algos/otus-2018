package ru.otus.algo;

public enum Figure {

    PAWN(""),
    ROOK("R"),
    KNIGHT("N"),
    BISHOP("B"),
    QUEEN("Q"),
    KING("K");

    private String shortName;

    Figure(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }
}