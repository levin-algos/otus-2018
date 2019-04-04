package ru.otus.algo.measures;

import ru.otus.algo.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class SearchTest {


    private static final int LOW = 1_000_000;
    private static final int STEP = 250_000;
    private static final int HIGH = 5_000_000;

    public static void main(String[] args) {

        List<SearchResult> results = new ArrayList<>();
//        List<Long> arr = DataSet.generateLongData(DataSet.DataType.RND, HIGH);
//        List<Long> data = DataSet.generateLongData(DataSet.DataType.RND, HIGH);
//        List<Long> sortedData = new ArrayList<>(data);
//        Collections.sort(sortedData);

//        search("BST_RND", arr, ()-> BinarySearchTree.of(data.toArray(new Long[0])), results);
//        search("RBT_RND", arr, () -> RedBlackTree.of(data.toArray(new Long[0])), results);
//        search("AVL_RND", arr, () -> AVLTree.of(data.toArray(new Long[0])), results);
//        search("Treap_RND", arr, () -> CartesianTree.of(sortedData.toArray(new Long[0])), results);


        DataSet set = new DataSet("wiki.train.tokens");
        List<String> searchTokens = set.getData();
        List<String> sortedTokens = new ArrayList<>(searchTokens);
        Collections.sort(sortedTokens);

        search("BST_TOKENS", searchTokens, () -> BinarySearchTree.of(searchTokens.toArray(new String[0])), results);
        search("RBT_TOKENS", searchTokens, () -> RedBlackTree.of(searchTokens.toArray(new String[0])), results);
        search("AVL_TOKENS", searchTokens, () -> AVLTree.of(searchTokens.toArray(new String[0])), results);
        search("Treap_TOKENS", searchTokens, () -> CartesianTree.of(sortedTokens.toArray(new String[0])), results);

        List<Pair<String, Integer>> frequencyPairs = set.getFrequencyPairs();
        search("Optimal1_TOKENS", searchTokens, () -> BinarySearchTree.buildOptimal(new ArrayList<>(frequencyPairs)), results);
        search("Optimal2_TOKENS", searchTokens, () -> BinarySearchTree.buildMehlhorn(new ArrayList<>(frequencyPairs)), results);
//
//        searchCycle("BST_CYCLE", BinarySearchTree.of(data.toArray(new Long[0])), arr, 5000, results);
//        searchCycle("RBT_CYCLE", RedBlackTree.of(data.toArray(new Long[0])), arr, 5000,  results);
//        searchCycle("AVL_CYCLE", AVLTree.of(data.toArray(new Long[0])), arr, 5000, results);
//        searchCycle("Treap_CYCLE", CartesianTree.of(sortedData.toArray(new Long[0])), arr, 5000, results);


        System.out.println("Summary:");
        System.out.println(SearchResult.header());

        for (SearchResult result : results) {
            System.out.println(result);
        }
    }

    private static <T> void search(String testName, List<T> arr, Supplier<BinaryTree<T>> fn, List<SearchResult> results) {
        BinaryTree<T> tree;
        tree = fn.get();
        for (int i = LOW; i <= (HIGH > arr.size()? arr.size(): HIGH); i += STEP) {
            System.out.println(String.format("Running search test %s data type. Tree size: %s. i: %s", testName, tree.size(), i));

            SearchResult e = measureSearch(testName, tree, arr.subList(0, i));

            results.add(e);
            System.out.println(e);
            System.out.println();
        }
    }

    private static <T> void searchCycle(String testName, BinaryTree<T> tree, List<T> arr, int size, List<SearchResult> results) {
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
        int height = tree.getHeight();
        SearchResult e = new SearchResult(testName, delta, 1000*size, counter, height);
        results.add(e);
        System.out.println(e);
        System.out.println();
    }

    private static <T> SearchResult measureSearch(String testName, BinaryTree<T> tree, List<T> arr) {
        int counter = 0;
        long delta = System.nanoTime();

        for (T t : arr) {
            if (tree.contains(t))
                counter++;

        }
        delta = System.nanoTime() - delta;
        int height = tree.getHeight();
        return new SearchResult(testName, delta, arr.size(), counter, height);
    }
}