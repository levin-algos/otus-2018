package ru.otus.algo;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartesianTreeTest {

    private final Integer[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private final Integer[] priorities = {1, 2, 1, 2, 1, 2, 1, 2, 2, 1};
    private final List<Pair<Integer, Integer>> pairs = Pair.combine(values, priorities);

    private final TreeChecker<Pair<Integer, Integer>> checker = new TreeChecker<>();

    @Test
    void buildTest() {
        CartesianTree<Integer, Integer> tree = CartesianTree.of(pairs);

//        assertTrue(Utils.isBST(tree, Comparator.comparingInt(Pair::getLeft)));
    }

    @Test
    void merge() {
        Integer[] values =     {1, 2, 3 };
        Integer[] priorities = {1, 2, 1};
        CartesianTree<Integer, Integer> tree = CartesianTree.of(Pair.combine(values, priorities));

        Integer[] values1 =     {4, 5, 6 };
        Integer[] priorities1 = {1, 2, 1};
        CartesianTree<Integer, Integer> tree1 = CartesianTree.of(Pair.combine(values1, priorities1));

        CartesianTree<Integer, Integer> merge = CartesianTree.merge(tree, tree1);
    }

    @Test
    void add() {
//        checker.addCheck(AbstractBinarySearchTree.class, TreeInvariants.isBST(), Comparator.comparing(Pair::getLeft));
//        checker.addCheck(AbstractBinarySearchTree.class, TreeInvariants.isHeap(), Comparator.comparing(Pair::getRight));
        Integer[] values =     {1, 2, 3,  4,  5, 6, 7,  8, 10, 11, 14};
        Integer[] priorities = {3, 1, 2,  4,  5, 3, 2,  4,  2,  3,  2};
        CartesianTree<Integer, Integer> tree = CartesianTree.of(Pair.combine(values, priorities));

        assertTrue(checker.check(tree));
        Pair<CartesianTree<Integer, Integer>, CartesianTree<Integer, Integer>> split = tree.split(5);
        assertTrue(checker.check(split.getLeft()));
        assertTrue(checker.check(split.getRight()));

        CartesianTree<Integer, Integer> merge = CartesianTree.merge(split.getLeft(), split.getRight());
        assertTrue(checker.check(merge));

        merge.add(Pair.of(5, 5));
        assertTrue(checker.check(merge));
    }
}