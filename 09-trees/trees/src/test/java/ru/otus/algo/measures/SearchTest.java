package ru.otus.algo.measures;

import ru.otus.algo.AVLTree;
import ru.otus.algo.BinaryTree;
import ru.otus.algo.RedBlackTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class SearchTest {

    public static void main(String[] args) {

        List<SearchResult> results = new ArrayList<>();

        int low = 1_000_000, measures = 15, high = 5_000_000;

        List<Long> arr = InsertionTest.generateLongData(DataType.RND, high);
        List<Long> data = InsertionTest.generateLongData(DataType.RND, high);


        search(arr, i -> RedBlackTree.of(data.subList(0, i).toArray(new Long[0])), low, measures, high, results);

        search(arr, i -> AVLTree.of(data.subList(0, i).toArray(new Long[0])), low, measures, high, results);

        DataSet set = new DataSet("wiki.train.tokens");
        List<String> searchTokens = set.getData(high);

        search(searchTokens, i -> RedBlackTree.of(set.getData(i).toArray(new String[0])), low, measures, high, results);
        search(searchTokens, i -> AVLTree.of(set.getData(i).toArray(new String[0])), low, measures, high, results);

        System.out.println(SearchResult.header());

        for (SearchResult result : results) {
            System.out.println(result);
        }
    }

    private static <T> void search(List<T> arr, Function<Integer, BinaryTree<T>> fn, int low, int measures, int high, List<SearchResult> results) {
        int step = (high - low) / measures;
        BinaryTree<T> tree = null;
        for (int i = low; i <= high; i += step) {

            System.out.println(i);

            tree = fn.apply(i);

            searchRandomTest(tree, arr.subList(0, i), results);
        }
    }

    private static <T> void searchRandomTest(BinaryTree<T> tree, List<T> arr, List<SearchResult> results) {
        results.add(measureSearch(tree, arr));
    }

    private static <T> void searchCycleTest(BinaryTree<T> tree, List<T> arr, List<SearchResult> results) {
        int counter = 0;
        long delta = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            for (T l : arr) {
                if (tree.contains(l))
                    counter++;
            }
        }
        delta = System.nanoTime() - delta;
        results.add(new SearchResult(tree.getClass(), delta, DataType.CYCLE, tree.size(), counter));
    }

    private static <T> SearchResult measureSearch(BinaryTree<T> tree, List<T> arr) {
        int counter = 0;
        long delta = System.nanoTime();

        for (T t : arr) {
            if (tree.contains(t))
                counter++;

        }
        delta = System.nanoTime() - delta;
        return new SearchResult(tree.getClass(), delta, DataType.RND, tree.size(), counter);
    }
}