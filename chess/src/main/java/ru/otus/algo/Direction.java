package ru.otus.algo;

public enum Direction {
    NORTH(8),
    NORTH_EAST(9),
    EAST(1),
    SOUTH_EAST(-7),
    SOUTH(-8),
    SOUTH_WEST(-9),
    WEST(-1),
    NORTH_WEST(7);

    private final int value;

    Direction(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}