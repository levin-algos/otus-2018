package ru.otus.algo;

public enum Side {
    WHITE(0),
    BLACK(1);

    private int value;

    Side(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
