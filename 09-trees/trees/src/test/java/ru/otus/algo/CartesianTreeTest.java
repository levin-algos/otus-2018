package ru.otus.algo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class CartesianTreeTest {

    private final Integer[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final TreeChecker<Integer> checker = new TreeChecker<>();
    private static Random rnd = new Random();
    private Function<Integer, Integer> priority = o -> rnd.nextInt(4);

    @BeforeAll
    static void init() {
        checker.addCheck(TreeInvariants.isBST(), Integer::compareTo, TreeChecker.Invocation.ROOT);
        checker.addCheck(TreeInvariants.isHeap(), Integer::compareTo, TreeChecker.Invocation.EACH_NODE);
    }

    @Test
    void buildTest() {
        CartesianTree<Integer> tree = CartesianTree.of(values, priority);

        assertTrue(checker.check(tree));
    }

    @Test
    void buildTestNotSorted() {
        Integer[] values = {1, 3, 2, 4, 5, 6, 7, 8, 9, 10};

        CartesianTree<Integer> tree = CartesianTree.of(values, priority);

        assertFalse(checker.check(tree));
    }

    @Test
    void merge() {
        Integer[] values = {1, 2, 3};
        CartesianTree<Integer> tree = CartesianTree.of(values, priority);

        assertTrue(checker.check(tree));

        for (Integer i : values) {
            assertTrue(tree.contains(i));
        }

        Integer[] values1 = {4, 5, 6};
        CartesianTree<Integer> tree1 = CartesianTree.of(values1, priority);
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

        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5, 6}, merged.toArray(new Integer[0]));
    }

    @Test
    void split() {
        Integer[] values = {0, 1, 2, 3, 4, 5, 6, 8, 9, 10, 11};

        CartesianTree<Integer> tree = CartesianTree.of(values, priority);

        Pair<CartesianTree<Integer>, CartesianTree<Integer>> pair = tree.split(7);

        assertTrue(checker.check(pair.getLeft()));
        assertTrue(checker.check(pair.getRight()));

        for (int i = 0; i < 7; i++) {
            assertTrue(pair.getLeft().contains(values[i]));
            assertFalse(pair.getRight().contains(values[i]));
        }
        for (int i = 7; i < values.length; i++) {
            assertFalse(pair.getLeft().contains(values[i]));
            assertTrue(pair.getRight().contains(values[i]));
        }

        List<Integer> l = new ArrayList<>();
        pair.getLeft().traverse(Traversal.IN_ORDER, l::add);
        assertArrayEquals(new Integer[]{0, 1, 2, 3, 4, 5, 6}, l.toArray(new Integer[0]));

        l.clear();
        pair.getRight().traverse(Traversal.IN_ORDER, l::add);
        assertArrayEquals(new Integer[]{8, 9, 10, 11}, l.toArray(new Integer[0]));
    }


    @Test
    void add() {
        int splitPos = 5;
        Integer[] values = {1, 2, 3, 4, 5, 6, 7, 8, 10, 11, 14};
        CartesianTree<Integer> tree = CartesianTree.of(values, priority);
        TreeVisualizer vis = new TreeVisualizer(tree, "init");

        assertTrue(checker.check(tree));
        Pair<CartesianTree<Integer>, CartesianTree<Integer>> split = tree.split(values[splitPos]);
        assertTrue(checker.check(split.getLeft()));
        assertTrue(checker.check(split.getRight()));

        vis.addBottom("split("+values[splitPos]+")");
        TreeVisualizer splitted = new TreeVisualizer(split.getLeft());
        splitted.addRight(split.getRight());
        vis.addBottom(splitted);

        for (int i = 0; i < splitPos; i++) {
            assertTrue(split.getLeft().contains(values[i]));
        }
        assertFalse(split.getLeft().contains(values[splitPos]));
        assertFalse(split.getRight().contains(values[splitPos]));

        for (int i = splitPos+1; i < values.length; i++) {
            assertTrue(split.getRight().contains(values[i]));
        }

        CartesianTree<Integer> merge = CartesianTree.merge(split.getLeft(), split.getRight());
        assertTrue(checker.check(merge));
        vis.addBottom(merge, "merge:");
        assertNotNull(merge);
        assertFalse(merge.contains(values[splitPos]));

        for (int i = 0; i < splitPos; i++) {
            assertTrue(merge.contains(values[i]));
        }

        for (int i = 0; i < splitPos; i++) {
            assertTrue(merge.contains(values[i]));
        }

        merge.add(values[splitPos]);
        vis.addBottom(merge, "add("+values[splitPos]+")");
        assertTrue(checker.check(merge));
        vis.save(Paths.get("add-test.png"));
        for (Integer value : values) {
            assertTrue(merge.contains(value));
        }


    }
}