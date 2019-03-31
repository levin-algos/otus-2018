package ru.otus.algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.LongStream;

public class GlobalTest {

    private static final Random random = new Random();

    public static void main(String[] args) {

        ArrayList<TestResult.DataType> types = new ArrayList<>();
        types.add(TestResult.DataType.RND);
        types.add(TestResult.DataType.ASC);

        System.out.println(TestResult.header());
        int low = 1_000_000, measures = 15, high = 5_000_000;
        int step = (high - low) / measures;
        for (int i = low; i < high; i += step) {
            List<Supplier<BinaryTree<Long>>> list = new ArrayList<>();
            list.add(() -> RedBlackTree.of(new Long[0]));
            list.add(() -> AVLTree.of(new Long[0]));
            List<TestResult> testResults = new ArrayList<>();
            GlobalTest.insertionLongTest(list, types, i, testResults);

            List<Supplier<BinaryTree<String>>> list1 = new ArrayList<>();
            list1.add(() -> RedBlackTree.of(new String[0]));
            list1.add(() -> AVLTree.of(new String[0]));
            GlobalTest.insertionTokensTest(list1, i, testResults);

            for (TestResult t : testResults) {
                System.out.println(t);
            }
        }
    }

    private static String[] generateTokens(int size) {
        return DataSetImport.getData("wiki.train.tokens", size);
    }

    private static Long[] generateLongData(TestResult.DataType type, int size) {
        Long[] longs = new Long[size];
        if (TestResult.DataType.RND == type) {
            for (int i = 0; i < longs.length; i++)
                longs[i] = random.nextLong();
        } else if (TestResult.DataType.ASC == type) {
            longs = LongStream.rangeClosed(0, size).boxed().toArray(Long[]::new);
        } else {
            throw new UnsupportedOperationException();
        }

        return longs;
    }

    private static void insertionTokensTest(List<Supplier<BinaryTree<String>>> trees, int size, List<TestResult> result) {
        if (result == null)
            throw new IllegalArgumentException();

        String[] arr = generateTokens(size);

        for (Supplier<BinaryTree<String>> sup : trees)
            result.add(measureInsert(sup.get(), arr, TestResult.DataType.TOKENS));
    }

    private static void insertionLongTest(List<Supplier<BinaryTree<Long>>> trees, List<TestResult.DataType> types, int size, List<TestResult> result) {
        if (result == null)
            throw new IllegalArgumentException();

        for (TestResult.DataType type : types) {
            Long[] arr = generateLongData(type, size);

            for (Supplier<BinaryTree<Long>> sup : trees)
                result.add(measureInsert(sup.get(), arr, type));
        }
    }

    private static <T> TestResult measureInsert(BinaryTree<T> tree, T[] arr, TestResult.DataType type) {
        long delta = System.nanoTime();
        for (T l : arr) {
            tree.add(l);
        }
        delta = System.nanoTime() - delta;
        int height = tree.getHeight();
        return new TestResult(tree.getClass(), delta, height, type, arr.length, tree.getLeftRotationCount(), tree.getRightRotationCount());
    }

    private static class TestResult {

        private final Class<?> cl;
        private final long time;
        private final int height;
        private final DataType type;
        private final int size;
        private final int leftRot;
        private final int rightRot;


        public static String header() {
            return String.format("%s;%s;%s;%s;%s;%s", "Case", "ms", "height", "size", "l_rot", "r_rot");
        }

        TestResult(Class<?> cl, long time, int height, DataType type, int size, int leftRot, int rightRot) {
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

        private enum DataType {
            RND,
            ASC,
            TOKENS
        }
    }
}
