package ru.otus.algo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AVLTreeTest {

    static Stream<Comparator<? super Integer>> comparatorSource() {
        return Stream.of(null, Integer::compareTo);
    }

    @Test
    void test1() {
        AVLTree<Integer> tree = AVLTree.of(new Integer[0]);
        int MAX = 1_000_000;
        Integer[] ints = IntStream.rangeClosed(0, MAX).boxed().toArray(Integer[]::new);

        Collections.shuffle(Arrays.asList(ints));

        for (int i : ints) {
            tree.add(i);
        }

        for (int i: ints) {
            tree.remove(i);
        }
    }

    @ParameterizedTest
    @MethodSource("comparatorSource")
    void random(Comparator<? super Integer> cmp) {
        AVLTree<Integer> tree = AVLTree.of(cmp);
        int MAX = 100000;
        for (int i = 0; i < MAX; i++) {
            tree.add(i);
        }

        Random r = new Random();
        for (int i = 0; i < MAX; i++) {
            int element = r.nextInt(MAX);
            tree.remove(element);
        }
    }

    @ParameterizedTest
    @MethodSource("comparatorSource")
    void sequentialAddAndRemove(Comparator<? super Integer> cmp) {
        int MAX = 10;
        tree = AVLTree.of(IntStream.range(0, MAX).boxed().toArray(Integer[]::new), cmp);

        for (int i = 0; i < MAX; i++) {
            tree.remove(i);
        }
    }

    @ParameterizedTest
    @MethodSource("comparatorSource")
    void addAVLTreeRightRotation(Comparator<? super Integer> cmp) {
        Integer[] arr = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

        AVLTree<Integer> tree = AVLTree.of(arr, cmp);


        for (Integer i : arr) {
            assertTrue(tree.contains(i));
        }
    }

    @ParameterizedTest
    @MethodSource("comparatorSource")
    void addAVLTreeLeftRotation(Comparator<? super Integer> cmp) {
        Integer[] integers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        tree = AVLTree.of(integers, cmp);


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
        }
    }

    @ParameterizedTest
    @MethodSource("comparatorSource")
    void deleteLeaf(Comparator<? super Integer> cmp) {
        tree = AVLTree.of(integers, cmp);
        tree.remove(10);
        assertEquals(integers.length - 1, tree.size());

        tree.remove(5);
        assertEquals(integers.length - 2, tree.size());

        tree.remove(4);
        assertEquals(integers.length - 3, tree.size());

        tree.remove(3);
        assertEquals(integers.length - 4, tree.size());

        tree.remove(2);
        assertEquals(integers.length - 5, tree.size());

        tree.remove(1);
        assertEquals(0, tree.size());
    }

    @ParameterizedTest
    @MethodSource("comparatorSource")
    void deleteWithOneChild(Comparator<? super Integer> cmp) {
        Integer[] arr = {2, 4, 3};
        tree = AVLTree.of(arr, cmp);
        tree.remove(4);
        assertFalse(tree.contains(4));
        assertEquals(arr.length - 1, tree.size());
        assertTrue(tree.contains(3));

        arr = new Integer[]{3, 1, 2};
        tree = AVLTree.of(arr, cmp);
        tree.remove(1);
        assertFalse(tree.contains(1));
        assertEquals(arr.length - 1, tree.size());
        assertTrue(tree.contains(2));

        arr = new Integer[]{3, 2, 1};
        tree = AVLTree.of(arr, cmp);
        tree.remove(2);
        assertFalse(tree.contains(2));
        assertEquals(arr.length - 1, tree.size());
        assertTrue(tree.contains(1));

        arr = new Integer[]{1, 2, 3};
        tree = AVLTree.of(arr, cmp);
        tree.remove(2);
        assertFalse(tree.contains(2));
        assertEquals(arr.length - 1, tree.size());
        assertTrue(tree.contains(3));
    }

    @ParameterizedTest
    @MethodSource("comparatorSource")
    void deleteWithTwoChildren(Comparator<? super Integer> cmp) {
        Integer[] arr = {5, 2, 8, 1, 4, 6, 10, 3, 7};

        tree = AVLTree.of(arr, cmp);
        assertEquals(arr.length, tree.size());

        tree.remove(5);
        assertFalse(tree.contains(5));
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