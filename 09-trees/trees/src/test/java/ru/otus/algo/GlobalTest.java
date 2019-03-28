package ru.otus.algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.LongStream;

public class GlobalTest {


    private static final int MAX = 1_00_000;
    private static final Random random = new Random();

    public static void main(String[] args) {
        List<BinaryTree<Long>> list = new ArrayList<>();
        list.add(RedBlackTree.of(new Long[0]));
        list.add(AVLTree.of(new Long[0]));

        ArrayList<TestResult.DataType> types = new ArrayList<>();
        types.add(TestResult.DataType.RANDOM_LONG);
        types.add(TestResult.DataType.ORDERED);

        List<TestResult> testResults = GlobalTest.insertionTest(list, types);

        System.out.println(TestResult.header());
        for (TestResult t: testResults) {
            System.out.println(t);
        }
    }

    private static Long[] generateData(TestResult.DataType type) {
        Long[] longs = new Long[MAX];
        if (TestResult.DataType.RANDOM_LONG == type) {
            for (int i = 0; i < longs.length; i++)
                longs[i] = random.nextLong();
        } else if (TestResult.DataType.ORDERED == type) {
            longs = LongStream.rangeClosed(0, MAX).boxed().toArray(Long[]::new);
        } else if (TestResult.DataType.FROM_FILE == type){

        } else {
            throw new UnsupportedOperationException();
        }

        return longs;
    }


    private static List<TestResult> insertionTest(List<BinaryTree<Long>> trees, List<TestResult.DataType> types) {
        List<TestResult> result = new ArrayList<>();

        for (TestResult.DataType type: types) {
            Long[] arr = generateData(type);

            long delta;
            for (BinaryTree<Long> t : trees) {
                delta = System.nanoTime();
                for (Long l : arr) {
                    t.add(l);
                }
                delta = System.nanoTime() - delta;
                int height = t.getHeight();
                result.add(new TestResult(t.getClass(), delta, height, type));
            }
        }

        return result;
    }

    private static class TestResult {

        private final Class<?> cl;
        private final long time;
        private final int height;
        private final DataType type;

        public static String header() {
            return String.format("%20s%20s%20s%20s", "Class","Test type", "Delta (ms)", "Height");
        }

        public TestResult(Class<?> cl, long time, int height, DataType type) {
            this.cl = cl;
            this.time = time;
            this.height = height;
            this.type = type;
        }

        @Override
        public String toString() {
            return String.format("%20s%20s%20f%20s",
                    cl.getSimpleName(), type, (double)time/1_000_000, height);
        }

        private enum DataType {
            RANDOM_LONG,
            ORDERED,
            FROM_FILE
        }
    }
}
