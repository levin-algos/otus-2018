package ru.otus.algo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartesianTreeTest {

    private final Integer[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private final Integer[] priorities = {1, 2, 1, 2, 1, 2, 1, 2, 2, 1};
    private final List<Pair<Integer, Integer>> pairs = Pair.combine(values, priorities);

    private static final TreeChecker<Pair<Integer, Integer>> checker = new TreeChecker<>();

    @BeforeAll
    static void init() {
            checker.addCheck(TreeInvariants.isBST(), Comparator.comparing(Pair::getLeft), TreeChecker.Invocation.ROOT);
            checker.addCheck(TreeInvariants.isHeap(), Comparator.comparing(Pair::getRight), TreeChecker.Invocation.EACH_NODE);
    }

    @Test
    void buildTest() {
        CartesianTree<Integer, Integer> tree = CartesianTree.of(pairs);

        assertTrue(checker.check(tree));
    }

    @Test
    void buildTestNotSorted() {
        Integer[] values = {1, 3, 2, 4, 5, 6, 7, 8, 9, 10};
        Integer[] priorities = {1, 2, 1, 2, 1, 2, 1, 2, 2, 1};
        CartesianTree<Integer, Integer> tree = CartesianTree.of(Pair.combine(values, priorities));

        assertFalse(checker.check(tree));
    }

    @Test
    void merge() {
        Integer[] values =     {1, 2, 3};
        Integer[] priorities = {1, 2, 1};
        List<Pair<Integer, Integer>> pairs = Pair.combine(values, priorities);
        CartesianTree<Integer, Integer> tree = CartesianTree.of(pairs);

        assertTrue(checker.check(tree));

        for (Pair<Integer, Integer> i: pairs) {
            assertTrue(tree.contains(i));
        }

        Integer[] values1 =     {4, 5, 6};
        Integer[] priorities1 = {1, 2, 1};
        List<Pair<Integer, Integer>> pairs1 = Pair.combine(values1, priorities1);
        CartesianTree<Integer, Integer> tree1 = CartesianTree.of(pairs1);
        assertTrue(checker.check(tree1));

        for (Pair<Integer, Integer> i: pairs1) {
            assertTrue(tree1.contains(i));
        }

        CartesianTree<Integer, Integer> merge = CartesianTree.merge(tree, tree1);

        assertTrue(checker.check(merge));
        for (Pair<Integer, Integer> i: pairs1) {
            assertTrue(merge.contains(i));
        }

        for (Pair<Integer, Integer> i: pairs1) {
            assertTrue(merge.contains(i));
        }
    }

    @Test
    void add() {
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