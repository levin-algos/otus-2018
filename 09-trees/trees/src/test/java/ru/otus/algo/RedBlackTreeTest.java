package ru.otus.algo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.otus.algo.common.Utils;

import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RedBlackTreeTest {

    private RedBlackTree<Integer> tree;
    private static final int MAX = 100;

    static Stream<Comparator<Integer>> comparatorProducer() {
        return Stream.of(null, Integer::compareTo);
    }

    @ParameterizedTest
    @MethodSource("comparatorProducer")
    void case1(Comparator<? super Integer> cmp) {
        tree = RedBlackTree.of(new Integer[]{1}, cmp);

        assertTrue(tree.checkInvariants(tree.root, tree.getComparator()));
    }

    @ParameterizedTest
    @MethodSource("comparatorProducer")
    void case2Left(Comparator<? super Integer> cmp) {
        tree = RedBlackTree.of(new Integer[]{1, 2}, cmp);

        assertTrue(tree.checkInvariants(tree.root, tree.getComparator()));
    }

    @ParameterizedTest
    @MethodSource("comparatorProducer")
    void case2Right(Comparator<? super Integer> cmp) {
        tree = RedBlackTree.of(new Integer[]{2, 1}, cmp);

        assertTrue(tree.checkInvariants(tree.root, tree.getComparator()));

    }

    /*
                3,Black                                 3,Black

           2,Red        4,Red       ==>         2,Black         4,Black

       1,Red                                1,Red
     */
    @ParameterizedTest
    @MethodSource("comparatorProducer")
    void case3LeftLeft(Comparator<? super Integer> cmp) {
        tree = RedBlackTree.of(new Integer[]{3, 4, 2, 1}, cmp);

        assertTrue(tree.checkInvariants(tree.root, tree.getComparator()));
    }

    /*
        4,Black                                  4,Black

   2,Red           5,Red       ==>         2,Black         5,Black

        3,Red                                   3,Red
*/
    @ParameterizedTest
    @MethodSource("comparatorProducer")
    void case3LeftRight(Comparator<? super Integer> cmp) {
        tree = RedBlackTree.of(new Integer[]{4, 5, 2, 3}, cmp);

        assertTrue(tree.checkInvariants(tree.root, tree.getComparator()));
    }


    /*
                3,Black                                 3,Black

            2,Red        4,Red       ==>         2,Black         4,Black

                                5,Red                                       5,Red
    */
    @ParameterizedTest
    @MethodSource("comparatorProducer")
    void case3RightRight(Comparator<? super Integer> cmp) {
        tree = RedBlackTree.of(new Integer[]{3, 4, 2, 5}, cmp);

        assertTrue(tree.checkInvariants(tree.root, tree.getComparator()));
    }

    /*
                    2,Black                                 2,Black

            1,Red           4,Red       ==>         1,Black         4,Black

                        3,Red                                    3,Red
    */
    @ParameterizedTest
    @MethodSource("comparatorProducer")
    void case3RightLeft(Comparator<? super Integer> cmp) {
        tree = RedBlackTree.of(new Integer[]{2, 1, 4, 3}, cmp);

        assertTrue(tree.checkInvariants(tree.root, tree.getComparator()));
    }

    @ParameterizedTest
    @MethodSource("comparatorProducer")
    void case45Left(Comparator<? super Integer> cmp) {
        tree = RedBlackTree.of(new Integer[]{20, 30, 50, 70, 15, 10, 7, 25, 29, 23}, cmp);

        assertTrue(tree.checkInvariants(tree.root, tree.getComparator()));
    }

    @ParameterizedTest
    @MethodSource("comparatorProducer")
    void case45Right(Comparator<? super Integer> cmp) {
        tree = RedBlackTree.of(new Integer[]{30, 20, 10, 40, 50, 60, 31, 32, 33}, cmp);

        assertTrue(tree.checkInvariants(tree.root, tree.getComparator()));
    }

    @ParameterizedTest
    @MethodSource("comparatorProducer")
    void deleteRoot(Comparator<? super Integer> cmp) {
        tree = RedBlackTree.of(new Integer[]{10, 20, 30, 40, 50, 60}, cmp);

        assertTrue(tree.checkInvariants(tree.root, tree.getComparator()));

        tree.remove(20);
        tree.remove(30);
        tree.remove(40);
        tree.remove(50);
        tree.remove(10);
    }

    @ParameterizedTest
    @MethodSource("comparatorProducer")
    void deleteSiblingRed(Comparator<? super Integer> cmp) {
        tree = RedBlackTree.of(new Integer[]{10, 20, 30, 40, 50, 60}, cmp);

        tree.remove(40);
    }

    @ParameterizedTest
    @MethodSource("comparatorProducer")
    void deleteRedLeaf(Comparator<? super Integer> cmp) {
        tree = RedBlackTree.of(new Integer[]{10, 20, 30, 40, 50, 60}, cmp);

        tree.remove(60);
    }

    @ParameterizedTest
    @MethodSource("comparatorProducer")
    void deleteBlackLeaf(Comparator<? super Integer> cmp) {
        tree = RedBlackTree.of(new Integer[]{10, 20, 30, 40, 50}, cmp);

        tree.remove(10);
    }

    @ParameterizedTest
    @MethodSource("comparatorProducer")
    void deleteBlackWithOneChild(Comparator<? super Integer> cmp) {
        tree = RedBlackTree.of(new Integer[]{10, 20, 30, 40, 50, 60}, cmp);

        tree.remove(50);
    }

    @ParameterizedTest
    @MethodSource("comparatorProducer")
    void sequentialAddsAndRemoves(Comparator<? super Integer> cmp) {
        RedBlackTree<Integer> tree = RedBlackTree.of(IntStream.range(0, MAX).boxed().toArray(Integer[]::new), cmp);


        for (int i = 0; i < MAX; i++) {
            tree.remove(i);
        }
    }

    @ParameterizedTest
    @MethodSource("comparatorProducer")
    void contains(Comparator<? super Integer> cmp) {
        Integer[] arr = Utils.generateRandom(MAX, MAX / 2);
        RedBlackTree<Integer> tree = RedBlackTree.of(arr, cmp);

        for (Integer i : arr) {
            assertTrue(tree.contains(i));
        }
    }
}