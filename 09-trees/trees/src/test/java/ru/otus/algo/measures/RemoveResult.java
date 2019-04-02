package ru.otus.algo.measures;

public class RemoveResult {

        private final Class<?> cl;
        private final long time;
        private final int height;
        private final DataSet.DataType type;
        private final int size;
        private final int leftRot;
        private final int rightRot;


        public static String header() {
            return String.format("%s;%s;%s;%s;%s;%s", "case", "ms", "height", "removes", "l_rot", "r_rot");
        }

        RemoveResult(Class<?> cl, long time, int height, DataSet.DataType type, int size, int leftRot, int rightRot) {
            this.cl = cl;
            this.time = time;
            this.height = height;
            this.type = type;
            this.size = size;
            this.leftRot = leftRot;
            this.rightRot = rightRot;
        }

        @Override
        public String toString() {
            return String.format("%s %s;%f;%s;%s;%s;%s",
                    cl.getSimpleName(), type, (double) time / 1_000_000, height, size, leftRot, rightRot);
        }

}
