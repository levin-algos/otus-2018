package ru.otus.algo.measures;

import ru.otus.algo.AVLTree;
import ru.otus.algo.BinaryTree;
import ru.otus.algo.RedBlackTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class RemoveTest {


    public static void main(String[] args) {
        int high = 5_000_000, low = 1_000_000, measures = 15;
        List<Long> arr = InsertionTest.generateLongData(DataType.RND, high);
        List<Long> data = new ArrayList<>(arr.size());
        data.addAll(arr);
        Collections.shuffle(data);

        List<InsertResult> results = new ArrayList<>();
        remove(data.subList(0, 1_000_000), i -> RedBlackTree.of(arr.subList(0, i).toArray(new Long[0])), low, measures, high, results);
        remove(data.subList(0, 1_000_000), i -> AVLTree.of(arr.subList(0, i).toArray(new Long[0])), low, measures, high, results);

        System.out.println(InsertResult.header());
        for (InsertResult res: results)
            System.out.println(res);
    }

    private static <T> void remove(List<T> arr, Function<Integer, BinaryTree<T>> fn, int low, int measures, int high, List<InsertResult> results) {
        int step = (high - low) / measures;
        BinaryTree<T> tree;
        for (int i = low; i <= high; i += step) {

            System.out.println(i);

            tree = fn.apply(i);

            removeRandomTest(tree, arr, results);
        }
    }

    private static <T> void removeRandomTest(BinaryTree<T> tree, List<T> subList, List<InsertResult> results) {
        results.add(measureRemove(tree, subList, DataType.RND));
    }

    private static <T> InsertResult measureRemove(BinaryTree<T> tree, List<T> arr, DataType type) {
        long delta = System.nanoTime();
        for (T t : arr)
            tree.remove(t);

        delta = System.nanoTime() - delta;
        return new InsertResult(tree.getClass(), delta, tree.getHeight(), type, tree.size(), tree.getLeftRotationCount(), tree.getRightRotationCount());
    }

}