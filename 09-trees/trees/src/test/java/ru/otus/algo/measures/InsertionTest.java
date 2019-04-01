package ru.otus.algo.measures;

import ru.otus.algo.AVLTree;
import ru.otus.algo.BinaryTree;
import ru.otus.algo.RedBlackTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class InsertionTest {

    private static final Random random = new Random();

    public static void main(String[] args) {

        ArrayList<DataType> types = new ArrayList<>();
        types.add(DataType.RND);
        types.add(DataType.ASC);

        System.out.println(InsertResult.header());
        int low = 1_000_00, measures = 15, high = 5_00_000;
        int step = (high - low) / measures;
        for (int i = low; i < high; i += step) {
            List<Supplier<BinaryTree<Long>>> list = new ArrayList<>();
            list.add(RedBlackTree::of);
            list.add(AVLTree::of);
            List<InsertResult> insertResults = new ArrayList<>();
            InsertionTest.insertionLongTest(list, types, i, insertResults);

            List<Supplier<BinaryTree<String>>> list1 = new ArrayList<>();
            list1.add(RedBlackTree::of);
            list1.add(AVLTree::of);
            InsertionTest.insertionTokensTest(list1, i, insertResults);

            for (InsertResult t : insertResults) {
                System.out.println(t);
            }
        }
    }

    static List<Long> generateLongData(DataType type, int size) {
        List<Long> longs = new ArrayList<>();
        if (DataType.RND == type) {
            for (int i = 0; i < size; i++)
                longs.add(Math.abs(random.nextLong()));
        } else if (DataType.ASC == type) {
            longs = LongStream.rangeClosed(0, size).boxed().collect(Collectors.toList());
        } else {
            throw new UnsupportedOperationException();
        }

        return longs;
    }

    private static void insertionTokensTest(List<Supplier<BinaryTree<String>>> trees, int size, List<InsertResult> result) {
        if (result == null)
            throw new IllegalArgumentException();

        DataSet set = new DataSet("wiki.train.tokens");

        for (Supplier<BinaryTree<String>> sup : trees)
            result.add(measureInsert(sup.get(), set.getData(size), DataType.TOKENS));
    }

    private static void insertionLongTest(List<Supplier<BinaryTree<Long>>> trees, List<DataType> types, int size, List<InsertResult> result) {
        if (result == null)
            throw new IllegalArgumentException();

        for (DataType type : types) {
            List<Long> arr = generateLongData(type, size);

            for (Supplier<BinaryTree<Long>> sup : trees)
                result.add(measureInsert(sup.get(), arr, type));
        }
    }

    private static <T> InsertResult measureInsert(BinaryTree<T> tree, List<T> arr, DataType type) {
        long delta = System.nanoTime();
        for (T t: arr) {
            tree.add(t);
        }
        delta = System.nanoTime() - delta;
        int height = tree.getHeight();
        return new InsertResult(tree.getClass(), delta, height, type, tree.size(), tree.getLeftRotationCount(), tree.getRightRotationCount());
    }
}
