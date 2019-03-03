package ru.otus.algo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.otus.algo.common.Utils;

import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RedBlackTreeTest {

    private RedBlackTree<Integer> tree;
    private static final int MAX = 1000;

    static Stream<Comparator<Integer>> comparatorProducer() {
        return Stream.of(null, Integer::compareTo);
    }

    private static final TreeChecker<Integer> checker = new TreeChecker<>();

    @BeforeAll
    static void init() {
        checker.addCheck(TreeInvariants.isBST(), Integer::compareTo, TreeChecker.Invocation.ROOT);
        checker.addCheck(TreeInvariants.isRedBlack(), Integer::compareTo, TreeChecker.Invocation.EACH_NODE);
        checker.addCheck(TreeInvariants.countBlackNodes(), Integer::compareTo, TreeChecker.Invocation.ROOT);
    }

    @ParameterizedTest
    @MethodSource("comparatorProducer")
    void case1(Comparator<? super Integer> cmp) {
        tree = RedBlackTree.of(new Integer[]{1}, cmp);

        assertTrue(checker.check(tree));

        
//        Object o = root.get(tree);
//        assertTrue(checkNode(o, 1, BLACK));
    }

    @ParameterizedTest
    @MethodSource("comparatorProducer")
    void case2Left(Comparator<? super Integer> cmp) {
        tree = RedBlackTree.of(new Integer[]{1, 2}, cmp);

        assertTrue(checker.check(tree));

//        Object o = root.get(tree);
//        assertTrue(checkNode(o, 1, BLACK));
//        assertTrue(checkNode(right.get(o), 2, RED));
    }

    @ParameterizedTest
    @MethodSource("comparatorProducer")
    void case2Right(Comparator<? super Integer> cmp) {
        tree = RedBlackTree.of(new Integer[]{2, 1}, cmp);

        assertTrue(checker.check(tree));

//        Object o = root.get(tree);
//        assertTrue(checkNode(o, 2, BLACK));
//        assertTrue(checkNode(left.get(o), 1, RED));
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

        assertTrue(checker.check(tree));

//        Object o = root.get(tree);
//        Object l = left.get(o);
//        assertTrue(checkNode(o, 3, BLACK));
//        assertTrue(checkNode(l, 2, BLACK));
//        assertTrue(checkNode(left.get(l), 1, RED));
//        assertTrue(checkNode(right.get(o), 4, BLACK));
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

        assertTrue(checker.check(tree));

//        Object o = root.get(tree);
//        Object l = left.get(o);
//        assertTrue(checkNode(o, 4, BLACK));
//        assertTrue(checkNode(l, 2, BLACK));
//        assertTrue(checkNode(right.get(l), 3, RED));
//        assertTrue(checkNode(right.get(o), 5, BLACK));
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

        assertTrue(checker.check(tree));

//        Object o = root.get(tree);
//        Object r = right.get(o);
//        assertTrue(checkNode(o, 3, BLACK));
//        assertTrue(checkNode(r, 4, BLACK));
//        assertTrue(checkNode(right.get(r), 5, RED));
//        assertTrue(checkNode(left.get(o), 2, BLACK));
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

        assertTrue(checker.check(tree));

//        Object o = root.get(tree);
//        Object r = right.get(o);
//        assertTrue(checkNode(o, 2, BLACK));
//        assertTrue(checkNode(r, 4, BLACK));
//        assertTrue(checkNode(left.get(r), 3, RED));
//        assertTrue(checkNode(left.get(o), 1, BLACK));
    }

    @ParameterizedTest
    @MethodSource("comparatorProducer")
    void case45Left(Comparator<? super Integer> cmp) {
        tree = RedBlackTree.of(new Integer[]{20, 30, 50, 70, 15, 10, 7, 25, 29, 23}, cmp);

        assertTrue(checker.check(tree));

//        Object o = root.get(tree);
//        Object r = right.get(o);
//        Object l = left.get(o);
//        assertTrue(checkNode(o, 25, BLACK));
//        assertTrue(checkNode(l, 15, RED));
//        assertTrue(checkNode(right.get(o), 30, RED));
//
//        Object ll = left.get(l);
//        Object lr = right.get(l);
//        assertTrue(checkNode(ll, 10, BLACK));
//        assertTrue(checkNode(lr, 20, BLACK));
//
//        assertTrue(checkNode(left.get(r), 29, BLACK));
//        Object rr = right.get(r);
//        assertTrue(checkNode(rr, 50, BLACK));
//
//        assertTrue(checkNode(left.get(ll), 7, RED));
//        assertTrue(checkNode(right.get(lr), 23, RED));
//        assertTrue(checkNode(right.get(rr), 70, RED));

    }

    @ParameterizedTest
    @MethodSource("comparatorProducer")
    void case45Right(Comparator<? super Integer> cmp) {
        tree = RedBlackTree.of(new Integer[]{30, 20, 10, 40, 50, 60, 31, 32, 33}, cmp);

        assertTrue(checker.check(tree));

//        Object o = root.get(tree);
//        Object r = right.get(o);
//        Object l = left.get(o);
//        assertTrue(checkNode(o, 31, BLACK));
//        assertTrue(checkNode(l, 20, RED));
//        assertTrue(checkNode(r, 40, RED));
//
//        Object ll = left.get(l);
//        Object lr = right.get(l);
//        assertTrue(checkNode(ll, 10, BLACK));
//        assertTrue(checkNode(lr, 30, BLACK));
//
//        Object rl = left.get(r);
//        Object rr = right.get(r);
//        assertTrue(checkNode(rl, 32, BLACK));
//        assertTrue(checkNode(rr, 50, BLACK));
//
//        assertTrue(checkNode(right.get(rl), 33, RED));
//        assertTrue(checkNode(right.get(rr), 60, RED));
    }

//    private boolean checkNode(Object o, int i, boolean black) {
//        Object o1 = value.get(o);
//        Object o2 = color.get(o);
//        return o1.equals(i) && o2.equals(black);
//    }


    @ParameterizedTest
    @MethodSource("comparatorProducer")
    void deleteRoot(Comparator<? super Integer> cmp) {
        tree = RedBlackTree.of(new Integer[]{10, 20, 30, 40, 50, 60}, cmp);

        assertTrue(checker.check(tree));

        tree.remove(20);
        assertTrue(checker.check(tree));

        tree.remove(30);
        assertTrue(checker.check(tree));

        tree.remove(40);
        assertTrue(checker.check(tree));

        tree.remove(50);
        assertTrue(checker.check(tree));

        tree.remove(10);
        assertTrue(checker.check(tree));
    }

    @ParameterizedTest
    @MethodSource("comparatorProducer")
    void deleteSiblingRed(Comparator<? super Integer> cmp) {
        tree = RedBlackTree.of(new Integer[]{10, 20, 30, 40, 50, 60}, cmp);

        tree.remove(40);
        assertTrue(checker.check(tree));
    }

    @ParameterizedTest
    @MethodSource("comparatorProducer")
    void deleteRedLeaf(Comparator<? super Integer> cmp) {
        tree = RedBlackTree.of(new Integer[]{10, 20, 30, 40, 50, 60}, cmp);

        tree.remove(60);
        assertTrue(checker.check(tree));
    }

    @ParameterizedTest
    @MethodSource("comparatorProducer")
    void deleteBlackLeaf(Comparator<? super Integer> cmp) {
        tree = RedBlackTree.of(new Integer[]{10, 20, 30, 40, 50}, cmp);

        tree.remove(10);
        assertTrue(checker.check(tree));
    }

    @ParameterizedTest
    @MethodSource("comparatorProducer")
    void deleteBlackWithOneChild(Comparator<? super Integer> cmp) {
        tree = RedBlackTree.of(new Integer[]{10, 20, 30, 40, 50, 60}, cmp);

        tree.remove(50);
        assertTrue(checker.check(tree));
    }

    @ParameterizedTest
    @MethodSource("comparatorProducer")
    void sequentialAddsAndRemoves(Comparator<? super Integer> cmp) {
        RedBlackTree<Integer> tree = RedBlackTree.of(IntStream.range(0, MAX).boxed().toArray(Integer[]::new), cmp);

        assertTrue(checker.check(tree));

        for (int i = 0; i < MAX; i++) {
            tree.remove(i);
            assertTrue(checker.check(tree));
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