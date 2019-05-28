package ru.otus.algo;

public enum Direction {
    NORTH(8),
    NORTH_EAST(9),
    EAST(1),
    SOUTH_EAST(-7),
    SOUTH(-8),
    SOUTH_WEST(-9),
    WEST(-1),
    NORTH_WEST(7),
    NORTH_NORTH_EAST(17),
    NORTH_EAST_EAST(+10),
    SOUTH_EAST_EAST(-6),
    SOUTH_SOUTH_EAST(-15),
    SOUTH_SOUTH_WEST(-17),
    SOUTH_WEST_WEST(-10),
    NORTH_WEST_WEST(6),
    NORTH_NORTH_WEST(+15);

    private final int value;

    Direction(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
