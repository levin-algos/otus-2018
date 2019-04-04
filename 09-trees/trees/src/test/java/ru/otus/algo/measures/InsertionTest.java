package ru.otus.algo.measures;

import ru.otus.algo.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public class InsertionTest {

    private final static int LOW = 1_000_000;
    private final static int HIGH = 5_000_000;
    private final static int STEP = 250_000;

    public static void main(String[] args) {

        ArrayList<DataSet.DataType> types = new ArrayList<>();
//        types.add(DataSet.DataType.RND);
//        types.add(DataSet.DataType.ASC);
        types.add(DataSet.DataType.TOKENS);

        List<InsertResult> res = new ArrayList<>();
        for (DataSet.DataType type : types) {
            if (DataSet.DataType.TOKENS == type) {
                DataSet set = new DataSet("wiki.train.tokens");
                List<String> tokens = set.getData();
                insert("BST_"+type, tokens, BinarySearchTree::of, res);
                insert("RBT_"+type, tokens, RedBlackTree::of, res);
                insert("AVL_"+type, tokens, AVLTree::of, res);
                insert("Treap_"+type, tokens, CartesianTree::of, res);
                List<Pair<String, Integer>> frequencyPairs = set.getFrequencyPairs();
                build("Optimal1", frequencyPairs.size(), () -> BinarySearchTree.buildOptimal (frequencyPairs), res);
                build("Optimal2", frequencyPairs.size(), () -> BinarySearchTree.buildMehlhorn(frequencyPairs), res);

            } else {
                List<Long> data = DataSet.generateLongData(type, HIGH);
                if (DataSet.DataType.ASC != type)
                    insert("BST_"+type, data, BinarySearchTree::of, res);
                insert("RBT_"+type, data, RedBlackTree::of, res);
                insert("AVL_"+type, data, AVLTree::of, res);

                Random random = new Random();
                Function<Long, Integer> priority = i -> random.nextInt();
                for (int i = LOW; i <= HIGH; i += STEP) {
                    CartesianTree<Long> tree;
                    long delta = System.nanoTime();
                    if (DataSet.DataType.ASC != type) {
                        ArrayList<Long> longs = new ArrayList<>(data.subList(0, i));
                        longs.sort(Comparator.naturalOrder());
                        tree = CartesianTree.of(longs.toArray(new Long[0]), priority);
                        delta = System.nanoTime() - delta;
                    } else {
                        tree = CartesianTree.of(data.subList(0, i).toArray(new Long[0]), priority);
                        delta = System.nanoTime() - delta;
                    }
                    res.add(new InsertResult("Treap_"+type, delta, tree.getHeight(), i, 0, 0));
                }

            }
        }

        System.out.println("Summary:");
        System.out.println(InsertResult.header());
        for (InsertResult t : res) {
            System.out.println(t);
        }
    }

    private static <T> void build(String testName, int size, Supplier<BinaryTree<T>> fn, List<InsertResult> results) {
        BinaryTree<T> tree;
            long delta = System.nanoTime();
            tree = fn.get();
            delta = System.nanoTime() - delta;
            InsertResult e = new InsertResult(testName, delta, tree.getHeight(), size, tree.getLeftRotationCount(), tree.getRightRotationCount());
            results.add(e);
            System.out.println(e);
            System.out.println();
    }

    private static <T> void insert(String testName, List<T> arr, Supplier<BinaryTree<T>> fn, List<InsertResult> results) {
        BinaryTree<T> tree;
        for (int i = LOW; i <= (HIGH > arr.size() ? arr.size() : HIGH); i += STEP) {
            tree = fn.get();
            System.out.println(String.format("Running insert test %s (%s inserts).", testName, i));
            InsertResult e = measureInsert(testName, tree, arr, i);
            results.add(e);
            System.out.println(e);
            System.out.println();
        }
    }

    private static <T> InsertResult measureInsert(String testName, BinaryTree<T> tree, List<T> arr, int size) {
        if (size > arr.size())
            throw new IllegalArgumentException();

        long delta = System.nanoTime();
        for (int i = 0; i < size; i++) {
            tree.add(arr.get(i));
        }
        delta = System.nanoTime() - delta;
        int height = tree.getHeight();
        return new InsertResult(testName, delta, height, size, tree.getLeftRotationCount(), tree.getRightRotationCount());
    }
}