package ru.otus.algo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AVLTreeTest {

    static Stream<Comparator<? super Integer>> comparatorSource() {
        return Stream.of(null, Integer::compareTo);
    }

    private static final TreeChecker<Integer> checker = new TreeChecker<>();

    @BeforeAll
    static void init() {
        checker.addCheck(TreeInvariants.isBST(), Integer::compareTo, TreeChecker.Invocation.ROOT);
        checker.addCheck(TreeInvariants.isAVL(), Integer::compareTo, TreeChecker.Invocation.EACH_NODE);
    }

    @Test
    void writeToFileTest() throws IOException {
        Path path = Paths.get("test.png");
        if (Files.exists(path))
            Files.delete(path);

        AVLTree<Integer> tree = AVLTree.of(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        TreeVisualizer vis = new TreeVisualizer();
        vis.add(tree, "test");
        vis.save(path);
    }


//    @Test
    void test1() {
        AVLTree<Integer> tree = AVLTree.of(new Integer[0]);
        int MAX = 1000;

        for (int i = 0; i < MAX; i++) {
            tree.add(i);
        }

        assertTrue(checker.check(tree));

//        tree.saveToFile(Paths.get("avl-vis", "1000.png"));
        for (int i = 0; i <= 50; i++) {
            tree.remove(500 - i);
//            tree.saveToFile(Paths.get("avl-vis", "1000_rm"+(500-i)+".png"));
            tree.remove(500 + i);
//            tree.saveToFile(Paths.get("avl-vis", "1000_rm"+(500+i)+".png"));
            assertTrue(checker.check(tree));
        }
    }

    @ParameterizedTest
    @MethodSource("comparatorSource")
    void random(Comparator<? super Integer> cmp) {
        AVLTree<Integer> tree = AVLTree.of(cmp);
        int MAX = 1000;
        for (int i = 0; i < MAX; i++) {
            tree.add(i);
            assertTrue(checker.check(tree));
        }

        Random r = new Random();
        for (int i = 0; i < MAX; i++) {
            tree.remove(r.nextInt(MAX));

            assertTrue(checker.check(tree));
        }
    }

    @ParameterizedTest
    @MethodSource("comparatorSource")
    void sequentialAddAndRemove(Comparator<? super Integer> cmp) {
        int MAX = 10;
        tree = AVLTree.of(IntStream.range(0, MAX).boxed().toArray(Integer[]::new), cmp);
        assertTrue(checker.check(tree));

        for (int i = 0; i < MAX; i++) {
            tree.remove(i);
            assertTrue(checker.check(tree));
        }
    }

    @ParameterizedTest
    @MethodSource("comparatorSource")
    void addAVLTreeRightRotation(Comparator<? super Integer> cmp) {
        Integer[] arr = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

        AVLTree<Integer> tree = AVLTree.of(arr, cmp);

        assertTrue(checker.check(tree));

        for (Integer i : arr) {
            assertTrue(tree.contains(i));
        }
    }

    @ParameterizedTest
    @MethodSource("comparatorSource")
    void addAVLTreeLeftRotation(Comparator<? super Integer> cmp) {
        Integer[] integers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        tree = AVLTree.of(integers, cmp);

        assertTrue(checker.check(tree));

        for (Integer i : integers) {
            assertTrue(this.tree.contains(i));
        }
    }

    private AVLTree<Integer> tree;
    private final Integer[] integers = {1, 2, 3, 4, 5, 10};

    @ParameterizedTest
    @MethodSource("comparatorSource")
    void soloAdd(Comparator<? super Integer> cmp) {
        tree = AVLTree.of(integers, cmp);
        assertEquals(integers.length, tree.size());
        tree.add(100);
        assertEquals(integers.length + 1, tree.size());
        assertTrue(tree.contains(100));
    }

    @ParameterizedTest
    @MethodSource("comparatorSource")
    void contains(Comparator<? super Integer> cmp) {
        tree = AVLTree.of(integers, cmp);
        for (Integer i : integers) {
            assertTrue(tree.contains(i));
        }
        assertEquals(integers.length, tree.size());

        assertFalse(tree.contains(11));
    }

    @ParameterizedTest
    @MethodSource("comparatorSource")
    void remove(Comparator<? super Integer> cmp) {
        tree = AVLTree.of(integers, cmp);
        assertEquals(integers.length, tree.size());
        tree.remove(2);
        assertEquals(integers.length - 1, tree.size());
        assertFalse(tree.contains(2));
        for (Integer i : new Integer[]{1, 3, 4, 5, 10}) {
            assertTrue(tree.contains(i));
        }

        assertFalse(tree.contains(11));
    }

    @ParameterizedTest
    @MethodSource("comparatorSource")
    void deleteRoot(Comparator<? super Integer> cmp) {
        tree = AVLTree.of(integers, cmp);

        for (Integer i : integers) {
            tree.remove(i);
            assertTrue(checker.check(tree));
        }
    }

    @ParameterizedTest
    @MethodSource("comparatorSource")
    void deleteLeaf(Comparator<? super Integer> cmp) {
        tree = AVLTree.of(integers, cmp);
        tree.remove(10);
        assertTrue(checker.check(tree));
        assertEquals(integers.length - 1, tree.size());

        tree.remove(5);
        assertTrue(checker.check(tree));
        assertEquals(integers.length - 2, tree.size());

        tree.remove(4);
        assertTrue(checker.check(tree));
        assertEquals(integers.length - 3, tree.size());

        tree.remove(3);
        assertTrue(checker.check(tree));
        assertEquals(integers.length - 4, tree.size());

        tree.remove(2);
        assertTrue(checker.check(tree));
        assertEquals(integers.length - 5, tree.size());

        tree.remove(1);
        assertTrue(checker.check(tree));
        assertEquals(0, tree.size());
    }

    @ParameterizedTest
    @MethodSource("comparatorSource")
    void deleteWithOneChild(Comparator<? super Integer> cmp) {
        Integer[] arr = {2, 4, 3};
        tree = AVLTree.of(arr, cmp);
        assertTrue(checker.check(tree));
        tree.remove(4);
        assertFalse(tree.contains(4));
        assertTrue(checker.check(tree));
        assertEquals(arr.length - 1, tree.size());
        assertTrue(tree.contains(3));

        arr = new Integer[]{3, 1, 2};
        tree = AVLTree.of(arr, cmp);
        assertTrue(checker.check(tree));
        tree.remove(1);
        assertFalse(tree.contains(1));
        assertTrue(checker.check(tree));
        assertEquals(arr.length - 1, tree.size());
        assertTrue(tree.contains(2));

        arr = new Integer[]{3, 2, 1};
        tree = AVLTree.of(arr, cmp);
        assertTrue(checker.check(tree));
        tree.remove(2);
        assertFalse(tree.contains(2));
        assertTrue(checker.check(tree));
        assertEquals(arr.length - 1, tree.size());
        assertTrue(tree.contains(1));

        arr = new Integer[]{1, 2, 3};
        tree = AVLTree.of(arr, cmp);
        tree.remove(2);
        assertFalse(tree.contains(2));
        assertTrue(checker.check(tree));
        assertEquals(arr.length - 1, tree.size());
        assertTrue(tree.contains(3));
    }

    @ParameterizedTest
    @MethodSource("comparatorSource")
    void deleteWithTwoChildren(Comparator<? super Integer> cmp) {
        Integer[] arr = {5, 2, 8, 1, 4, 6, 10, 3, 7};

        tree = AVLTree.of(arr, cmp);
        assertTrue(checker.check(tree));
        assertEquals(arr.length, tree.size());

        tree.remove(5);
        assertFalse(tree.contains(5));
        assertTrue(checker.check(tree));
        assertEquals(arr.length - 1, tree.size());
        for (int i = 1; i < arr.length; i++) {
            assertTrue(tree.contains(arr[i]));
        }
    }

    @ParameterizedTest
    @MethodSource("comparatorSource")
    void deleteOnEmptyTree(Comparator<? super Integer> cmp) {
        tree = AVLTree.of(new Integer[0], cmp);
        tree.remove(0);
    }
}