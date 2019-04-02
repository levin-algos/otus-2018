package ru.otus.algo.measures;

import ru.otus.algo.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class RemoveTest {

    public static void main(String[] args) {
        int high = 1_000_000, low = 100_000, measures = 15;
        List<Long> arr = DataSet.generateLongData(DataSet.DataType.RND, 5_000_000);
        List<Long> data = new ArrayList<>(arr);
        Collections.shuffle(data);

        List<RemoveResult> results = new ArrayList<>();
        remove(data.subList(0, high), () -> BinarySearchTree.of(arr.toArray(new Long[0])), low, measures, high, results);
        remove(data.subList(0, high), () -> RedBlackTree.of(arr.toArray(new Long[0])), low, measures, high, results);
        remove(data.subList(0, high), () -> AVLTree.of(arr.toArray(new Long[0])), low, measures, high, results);
        remove(data.subList(0, high), () -> CartesianTree.of(arr.toArray(new Long[0])), low, measures, high, results);

        System.out.println(RemoveResult.header());
        for (RemoveResult res: results)
            System.out.println(res);
    }

    private static <T> void remove(List<T> arr, Supplier<BinaryTree<T>> fn, int low, int measures, int high, List<RemoveResult> results) {
        int step = (high - low) / measures;
        BinaryTree<T> tree;
        for (int i = low; i <= high; i += step) {
            tree = fn.get();
            System.out.println(String.format("Running remove test for %s of %s data type. Tree size: %s. i: %s", tree.getClass().getSimpleName(), DataSet.DataType.RND, tree.size(), i));
            RemoveResult e = measureRemove(tree, arr.subList(0, i));
            results.add(e);
            System.out.println(e);
            System.out.println();
        }
    }

    private static <T> RemoveResult measureRemove(BinaryTree<T> tree, List<T> arr) {
        long delta = System.nanoTime();
        for (T t : arr)
            tree.remove(t);

        delta = System.nanoTime() - delta;
        return new RemoveResult(tree.getClass(), delta, tree.getHeight(), DataSet.DataType.RND, arr.size(), tree.getLeftRotationCount(), tree.getRightRotationCount());
    }
}