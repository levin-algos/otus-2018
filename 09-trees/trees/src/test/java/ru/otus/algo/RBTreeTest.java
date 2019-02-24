package ru.otus.algo;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class RBTreeTest {

    private RBTree<Integer> tree;

    private Field root = FieldUtils.getField(RBTree.class, "root", true);
    private Field color = FieldUtils.getField(root.getType(), "color", true);
    private Field value = FieldUtils.getField(root.getType(), "value", true);
    private Field left = FieldUtils.getField(root.getType(), "left", true);
    private Field right = FieldUtils.getField(root.getType(), "right", true);
    private static final boolean BLACK = false;
    private static final boolean RED = true;

    @Nested
    class Insertion {

        @Test
        void case1() throws IllegalAccessException {
            int i = 1;
            tree = RBTree.of(new Integer[]{i});

            Object o = root.get(tree);
            assertTrue(checkNode(o, 1, BLACK));
        }

        @Test
        void case2Left() throws IllegalAccessException {
            tree = RBTree.of(new Integer[]{1, 2});

            Object o = root.get(tree);
            assertTrue(checkNode(o, 1, BLACK));
            assertTrue(checkNode(right.get(o), 2, RED));
        }

        @Test
        void case2Right() throws IllegalAccessException {
            tree = RBTree.of(new Integer[]{2, 1});

            Object o = root.get(tree);
            assertTrue(checkNode(o, 2, BLACK));
            assertTrue(checkNode(left.get(o), 1, RED));
        }

        /*
                    3,Black                                 3,Black

               2,Red        4,Red       ==>         2,Black         4,Black

           1,Red                                1,Red
         */
        @Test
        void case3LeftLeft() throws IllegalAccessException {
            tree = RBTree.of(new Integer[]{3, 4, 2, 1});

            Object o = root.get(tree);
            Object l = left.get(o);
            assertTrue(checkNode(o, 3, BLACK));
            assertTrue(checkNode(l, 2, BLACK));
            assertTrue(checkNode(left.get(l), 1, RED));
            assertTrue(checkNode(right.get(o), 4, BLACK));
        }

        /*
            4,Black                                  4,Black

       2,Red           5,Red       ==>         2,Black         5,Black

            3,Red                                   3,Red
 */
        @Test
        void case3LeftRight() throws IllegalAccessException {
            tree = RBTree.of(new Integer[]{4, 5, 2, 3});

            Object o = root.get(tree);
            Object l = left.get(o);
            assertTrue(checkNode(o, 4, BLACK));
            assertTrue(checkNode(l, 2, BLACK));
            assertTrue(checkNode(right.get(l), 3, RED));
            assertTrue(checkNode(right.get(o), 5, BLACK));
        }


        /*
                    3,Black                                 3,Black

                2,Red        4,Red       ==>         2,Black         4,Black

                                    5,Red                                       5,Red
        */
        @Test
        void case3RightRight() throws IllegalAccessException {
            tree = RBTree.of(new Integer[]{3, 4, 2, 5});

            Object o = root.get(tree);
            Object r = right.get(o);
            assertTrue(checkNode(o, 3, BLACK));
            assertTrue(checkNode(r, 4, BLACK));
            assertTrue(checkNode(right.get(r), 5, RED));
            assertTrue(checkNode(left.get(o), 2, BLACK));
        }

        /*
                        2,Black                                 2,Black

                1,Red           4,Red       ==>         1,Black         4,Black

                            3,Red                                    3,Red
        */
        @Test
        void case3RightLeft() throws IllegalAccessException {
            tree = RBTree.of(new Integer[]{2, 1, 4, 3});

            Object o = root.get(tree);
            Object r = right.get(o);
            assertTrue(checkNode(o, 2, BLACK));
            assertTrue(checkNode(r, 4, BLACK));
            assertTrue(checkNode(left.get(r), 3, RED));
            assertTrue(checkNode(left.get(o), 1, BLACK));
        }

        @Test
        void case45Left() throws IllegalAccessException {
            tree = RBTree.of(new Integer[]{20, 30, 50, 70, 15, 10, 7, 25, 29, 23});

            Object o = root.get(tree);
            Object r = right.get(o);
            Object l = left.get(o);
            assertTrue(checkNode(o, 25, BLACK));
            assertTrue(checkNode(l, 15, RED));
            assertTrue(checkNode(right.get(o), 30, RED));

            Object ll = left.get(l);
            Object lr = right.get(l);
            assertTrue(checkNode(ll, 10, BLACK));
            assertTrue(checkNode(lr, 20, BLACK));

            assertTrue(checkNode(left.get(r), 29, BLACK));
            Object rr = right.get(r);
            assertTrue(checkNode(rr, 50, BLACK));

            assertTrue(checkNode(left.get(ll), 7, RED));
            assertTrue(checkNode(right.get(lr), 23, RED));
            assertTrue(checkNode(right.get(rr), 70, RED));

        }

        @Test
        void case45Right() throws IllegalAccessException {
            tree = RBTree.of(new Integer[]{30, 20, 10, 40, 50, 60, 30, 31, 32, 33});

            Object o = root.get(tree);
            Object r = right.get(o);
            Object l = left.get(o);
            assertTrue(checkNode(o, 31, BLACK));
            assertTrue(checkNode(l, 20, RED));
            assertTrue(checkNode(r, 40, RED));

            Object ll = left.get(l);
            Object lr = right.get(l);
            assertTrue(checkNode(ll, 10, BLACK));
            assertTrue(checkNode(lr, 30, BLACK));

            Object rl = left.get(r);
            Object rr = right.get(r);
            assertTrue(checkNode(rl, 32, BLACK));
            assertTrue(checkNode(rr, 50, BLACK));

            assertTrue(checkNode(right.get(rl), 33, RED));
            assertTrue(checkNode(right.get(rr), 60, RED));

        }

    }

    private boolean checkNode(Object o, int i, boolean black) throws IllegalAccessException {
        return value.get(o).equals(i) && color.get(o).equals(black);
    }

    @Nested
    class Deletion {

        @Test
        void deleteLeaf() throws IllegalAccessException {
            tree = RBTree.of(new Integer[]{1});

            tree.remove(1);
            assertNull(root.get(tree));
        }

        @Test
        void deleteRedWithOneChild() {
            tree = RBTree.of(new Integer[]{10, 20, 30, 40, 50, 60});

            tree.remove(40);

        }
    }
}