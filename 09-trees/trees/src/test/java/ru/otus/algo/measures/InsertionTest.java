package ru.otus.algo.measures;

import ru.otus.algo.AVLTree;
import ru.otus.algo.BinarySearchTree;
import ru.otus.algo.BinaryTree;
import ru.otus.algo.RedBlackTree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class InsertionTest {

    public static void main(String[] args) {

        ArrayList<DataSet.DataType> types = new ArrayList<>();
        types.add(DataSet.DataType.RND);
        types.add(DataSet.DataType.ASC);
        types.add(DataSet.DataType.TOKENS);

        int low = 1_000_000, measures = 15, high = 5_000_000;
        List<InsertResult> res = new ArrayList<>();
        for (DataSet.DataType type : types) {
            if (DataSet.DataType.TOKENS == type) {
                DataSet set = new DataSet("wiki.train.tokens");
                List<String> tokens = set.getRandomTokens(high);
                insert(tokens, DataSet.DataType.TOKENS, BinarySearchTree::of, low, measures, high, res);
                insert(tokens, DataSet.DataType.TOKENS, RedBlackTree::of, low, measures, high, res);
                insert(tokens, DataSet.DataType.TOKENS, AVLTree::of, low, measures, high, res);
            } else {
                List<Long> data = DataSet.generateLongData(type, high);
                if (DataSet.DataType.ASC != type)
                    insert(data, type, BinarySearchTree::of, low, measures, high, res);
                insert(data, type, RedBlackTree::of, low, measures, high, res);
                insert(data, type, AVLTree::of, low, measures, high, res);
            }
        }

        System.out.println("Summary:");
        System.out.println(InsertResult.header());
        for (InsertResult t : res) {
            System.out.println(t);
        }
    }

    private static <T> void insert(List<T> arr, DataSet.DataType type, Supplier<BinaryTree<T>> fn, int low, int measures, int high, List<InsertResult> results) {
        int step = (high - low) / measures;
        BinaryTree<T> tree;
        for (int i = low; i <= high; i += step) {
            tree = fn.get();
            System.out.println(String.format("Running insert test for %s of %s data type (%s inserts).", tree.getClass().getSimpleName(), type, i));
            InsertResult e = measureInsert(tree, arr, i, type);
            results.add(e);
            System.out.println(e);
            System.out.println();
        }
    }

    private static <T> InsertResult measureInsert(BinaryTree<T> tree, List<T> arr, int size, DataSet.DataType type) {
        if (size > arr.size())
            throw new IllegalArgumentException();

        long delta = System.nanoTime();
        for (int i = 0; i < size; i++) {
            tree.add(arr.get(i));
        }
        delta = System.nanoTime() - delta;
        int height = tree.getHeight();
        return new InsertResult(tree.getClass(), delta, height, type, size, tree.getLeftRotationCount(), tree.getRightRotationCount());
    }
}