package ru.otus.sort;

public enum SizeUnits {
    INTEGER(4);

    private int size;

    SizeUnits(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
