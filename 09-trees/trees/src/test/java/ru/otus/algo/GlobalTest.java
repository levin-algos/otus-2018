package ru.otus.algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.LongStream;

public class GlobalTest {

    private static final Random random = new Random();

    public static void main(String[] args) {
        List<BinaryTree<Long>> list = new ArrayList<>();
        list.add(RedBlackTree.of(new Long[0]));
        list.add(AVLTree.of(new Long[0]));

        ArrayList<TestResult.DataType> types = new ArrayList<>();
        types.add(TestResult.DataType.RANDOM_LONG);
        types.add(TestResult.DataType.ORDERED);

        System.out.println(TestResult.header());
        int low = 100, measures = 30, high = 1_000_000;
        int step = (high - low)/ measures;
        for (int i=low; i <= high; i+= step) {
            List<TestResult> testResults = GlobalTest.insertionTest(list, types, i);
            for (TestResult t : testResults) {
                System.out.println(t);
            }

        }
    }

    private static Long[] generateData(TestResult.DataType type, int size) {
        Long[] longs = new Long[size];
        if (TestResult.DataType.RANDOM_LONG == type) {
            for (int i = 0; i < longs.length; i++)
                longs[i] = random.nextLong();
        } else if (TestResult.DataType.ORDERED == type) {
            longs = LongStream.rangeClosed(0, size).boxed().toArray(Long[]::new);
        } else if (TestResult.DataType.FROM_FILE == type){

        } else {
            throw new UnsupportedOperationException();
        }

        return longs;
    }


    private static List<TestResult> insertionTest(List<BinaryTree<Long>> trees, List<TestResult.DataType> types, int size) {
        List<TestResult> result = new ArrayList<>();

        for (TestResult.DataType type: types) {
            Long[] arr = generateData(type, size);

            long delta;
            for (BinaryTree<Long> t : trees) {
                delta = System.nanoTime();
                for (Long l : arr) {
                    t.add(l);
                }
                delta = System.nanoTime() - delta;
                int height = t.getHeight();
                result.add(new TestResult(t.getClass(), delta, height, type, size));
            }
        }

        return result;
    }

    private static class TestResult {

        private final Class<?> cl;
        private final long time;
        private final int height;
        private final DataType type;
        private final int size;

        public static String header() {
            return String.format("%s;%s;%s;%s", "Case", "ms", "height", "size");
        }

        TestResult(Class<?> cl, long time, int height, DataType type, int size) {
            this.cl = cl;
            this.time = time;
            this.height = height;
            this.type = type;
            this.size = size;
        }

        @Override
        public String toString() {
            return String.format("%s %s;%f;%s;%s",
                    cl.getSimpleName(), type, (double)time/1_000_000, height, size);
        }

        private enum DataType {
            RANDOM_LONG,
            ORDERED,
            FROM_FILE
        }
    }
}
