package ru.otus.algo.measures;

import ru.otus.algo.AVLTree;
import ru.otus.algo.BinaryTree;
import ru.otus.algo.RedBlackTree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SearchTest {

    public static void main(String[] args) {

        List<SearchResult> results = new ArrayList<>();

        int low = 100_000, measures = 15, high = 500_000;
        List<Long> arr = InsertionTest.generateLongData(DataType.RND, high);
        List<Long> data = InsertionTest.generateLongData(DataType.RND, high);

        search(arr, DataType.RND,  i -> RedBlackTree.of(data.subList(0, i).toArray(new Long[0])), low, measures, high, results);

        search(arr, DataType.RND, i -> AVLTree.of(data.subList(0, i).toArray(new Long[0])), low, measures, high, results);

        DataSet set = new DataSet("wiki.train.tokens");
        List<String> searchTokens = set.getRandomTokens(high);

        search(searchTokens, DataType.TOKENS, i -> RedBlackTree.of(set.getData(i).toArray(new String[0])), low, measures, high, results);
        search(searchTokens, DataType.TOKENS, i -> AVLTree.of(set.getData(i).toArray(new String[0])), low, measures, high, results);

        System.out.println(SearchResult.header());

        for (SearchResult result : results) {
            System.out.println(result);
        }
    }

    private static <T> void search(List<T> arr, DataType type, Function<Integer, BinaryTree<T>> fn, int low, int measures, int high, List<SearchResult> results) {
        int step = (high - low) / measures;
        BinaryTree<T> tree;
        for (int i = low; i <= high; i += step) {

            System.out.println(i);

            tree = fn.apply(i);

            searchRandomTest(tree, arr.subList(0, i), results, type);
        }
    }

    private static <T> void searchRandomTest(BinaryTree<T> tree, List<T> arr, List<SearchResult> results, DataType type) {
        results.add(measureSearch(tree, arr, type));
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

    private static <T> SearchResult measureSearch(BinaryTree<T> tree, List<T> arr, DataType type) {
        int counter = 0;
        long delta = System.nanoTime();

        for (T t : arr) {
            if (tree.contains(t))
                counter++;

        }
        delta = System.nanoTime() - delta;
        return new SearchResult(tree.getClass(), delta, type, tree.size(), counter);
    }
}