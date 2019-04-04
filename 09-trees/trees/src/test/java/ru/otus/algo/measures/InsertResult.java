package ru.otus.algo.measures;

class InsertResult {

    private final String name;
    private final long time;
    private final int height;
    private final int size;
    private final int leftRot;
    private final int rightRot;


    public static String header() {
        return String.format("%s;%s;%s;%s;%s;%s", "Case", "ms", "height", "size", "l_rot", "r_rot");
    }

    InsertResult(String name, long time, int height, int size, int leftRot, int rightRot) {
        this.name = name;
        this.time = time;
        this.height = height;
        this.size = size;
        this.leftRot = leftRot;
        this.rightRot = rightRot;
    }

    @Override
    public String toString() {
        return String.format("%s;%f;%s;%s;%s;%s",
                name, (double) time / 1_000_000, height, size, leftRot, rightRot);
    }
}
