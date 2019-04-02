package ru.otus.algo.measures;

import ru.otus.algo.AVLTree;
import ru.otus.algo.BinarySearchTree;
import ru.otus.algo.BinaryTree;
import ru.otus.algo.RedBlackTree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SearchTest {

    public static void main(String[] args) {

        List<SearchResult> results = new ArrayList<>();

        int low = 1_000_000, measures = 15, high = 5_000_000;
        List<Long> arr = DataSet.generateLongData(DataSet.DataType.RND, high);
        List<Long> data = DataSet.generateLongData(DataSet.DataType.RND, high);

        search(arr, DataSet.DataType.RND, ()-> BinarySearchTree.of(data.toArray(new Long[0])), low, measures, high, results);
        search(arr, DataSet.DataType.RND, () -> RedBlackTree.of(data.toArray(new Long[0])), low, measures, high, results);
        search(arr, DataSet.DataType.RND, () -> AVLTree.of(data.toArray(new Long[0])), low, measures, high, results);


        DataSet set = new DataSet("wiki.train.tokens");
        List<String> searchTokens = set.getRandomTokens(high);

        search(searchTokens, DataSet.DataType.TOKENS, () -> BinarySearchTree.of(searchTokens.toArray(new String[0])), low, measures, high, results);
        search(searchTokens, DataSet.DataType.TOKENS, () -> RedBlackTree.of(searchTokens.toArray(new String[0])), low, measures, high, results);
        search(searchTokens, DataSet.DataType.TOKENS, () -> AVLTree.of(searchTokens.toArray(new String[0])), low, measures, high, results);

        searchCycle(BinarySearchTree.of(data.toArray(new Long[0])), arr, 5000, results);
        searchCycle(RedBlackTree.of(data.toArray(new Long[0])), arr, 5000,  results);
        searchCycle(AVLTree.of(data.toArray(new Long[0])), arr, 5000, results);


        System.out.println("Summary:");
        System.out.println(SearchResult.header());

        for (SearchResult result : results) {
            System.out.println(result);
        }
    }

    private static <T> void search(List<T> arr, DataSet.DataType type, Supplier<BinaryTree<T>> fn, int low, int measures, int high, List<SearchResult> results) {
        int step = (high - low) / measures;
        BinaryTree<T> tree;
        tree = fn.get();
        for (int i = low; i <= high; i += step) {
            System.out.println(String.format("Running search test for %s of %s data type. Tree size: %s. i: %s", tree.getClass().getSimpleName(), type, tree.size(), i));

            SearchResult e = measureSearch(tree, arr.subList(0, i), type);

            results.add(e);
            System.out.println(e);
            System.out.println();
        }
    }

    private static <T> void searchCycle(BinaryTree<T> tree, List<T> arr, int size, List<SearchResult> results) {
        int counter = 0;
        System.out.println(String.format("Running cycle (%s searches %s times) search test for %s of %s data type. " +
                "Tree size: %s", size, 1000, tree.getClass().getSimpleName(), DataSet.DataType.RND, tree.size()));
        long delta = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            for (int k =0; k < size; k++) {
                if (tree.contains(arr.get(k)))
                    counter++;
            }
        }
        delta = System.nanoTime() - delta;
        SearchResult e = new SearchResult(tree.getClass(), delta, DataSet.DataType.CYCLE, 1000*size, counter);
        results.add(e);
        System.out.println(e);
        System.out.println();
    }

    private static <T> SearchResult measureSearch(BinaryTree<T> tree, List<T> arr, DataSet.DataType type) {
        int counter = 0;
        long delta = System.nanoTime();

        for (T t : arr) {
            if (tree.contains(t))
                counter++;

        }
        delta = System.nanoTime() - delta;
        return new SearchResult(tree.getClass(), delta, type, arr.size(), counter);
    }
}