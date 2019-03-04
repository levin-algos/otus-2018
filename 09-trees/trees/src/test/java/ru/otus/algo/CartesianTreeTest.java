package ru.otus.algo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CartesianTreeTest {

    private final Integer[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final TreeChecker<Integer> checker = new TreeChecker<>();
    private static Random rnd = new Random();

    @BeforeAll
    static void init() {
        checker.addCheck(TreeInvariants.isBST(), Integer::compareTo, TreeChecker.Invocation.ROOT);
        checker.addCheck(TreeInvariants.isHeap(), Integer::compareTo, TreeChecker.Invocation.EACH_NODE);
    }

    @Test
    void buildTest() {
        CartesianTree<Integer> tree = CartesianTree.of(values, o -> rnd.nextInt(8));

        assertTrue(checker.check(tree));
    }

    @Test
    void buildTestNotSorted() {
        Integer[] values = {1, 3, 2, 4, 5, 6, 7, 8, 9, 10};
        CartesianTree<Integer> tree = CartesianTree.of(values, o -> rnd.nextInt(8));

        assertFalse(checker.check(tree));
    }

    @Test
    void merge() {
        Integer[] values = {1, 2, 3};
        CartesianTree<Integer> tree = CartesianTree.of(values, o -> rnd.nextInt(8));

        assertTrue(checker.check(tree));

        for (Integer i : values) {
            assertTrue(tree.contains(i));
        }

        Integer[] values1 = {4, 5, 6};
        CartesianTree<Integer> tree1 = CartesianTree.of(values1, o -> rnd.nextInt(8));
        assertTrue(checker.check(tree1));

        for (Integer i : values1) {
            assertTrue(tree1.contains(i));
        }

        CartesianTree<Integer> merge = CartesianTree.merge(tree, tree1);

        assertTrue(checker.check(merge));
        for (Integer i : values) {
            assertTrue(merge.contains(i));
        }

        for (Integer i : values1) {
            assertTrue(merge.contains(i));
        }

        List<Integer> merged = new ArrayList<>();
        merge.traverse(Traversal.IN_ORDER, merged::add);

        assertEquals(new Integer[]{1, 2, 3, 4, 5, 6}, merged.toArray(new Integer[0]));
    }
//
//    @Test
//    void add() {
//        Integer[] values =     {1, 2, 3,  4,  5, 6, 7,  8, 10, 11, 14};
//        Integer[] priorities = {3, 1, 2,  4,  5, 3, 2,  4,  2,  3,  2};
//        CartesianTree<Integer, Integer> tree = CartesianTree.of(Pair.combine(values, priorities));
//
//        assertTrue(checker.check(tree));
//        Pair<CartesianTree<Integer, Integer>, CartesianTree<Integer, Integer>> split = tree.split(5);
//        assertTrue(checker.check(split.getLeft()));
//        assertTrue(checker.check(split.getRight()));
//
//        CartesianTree<Integer, Integer> merge = CartesianTree.merge(split.getLeft(), split.getRight());
//        assertTrue(checker.check(merge));
//
//        merge.add(Pair.of(5, 5));
//        assertTrue(checker.check(merge));
//    }
}