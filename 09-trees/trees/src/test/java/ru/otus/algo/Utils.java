package ru.otus.algo;

import java.util.Comparator;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

class Utils {
    private Utils() {}

    /**
     * Checks invariants of red black tree and red-black tree:
     * 1. {@code node} value greater than left child of this {@code node}
     * 2. {@code node} value less than right child of this {@code node}
     * 3. Empty tree is binary search tree by definition.
     * 4. Root always is black
     * 5. Both children of red node are black
     * 6. Every path from a given node to any of its descendant leafs contains the same number of black nodes.
     * To prove the last invariant  method calculates number of black nodes for each subtree.
     * If number of black nodes in left and right subtrees are equal - invariant is proven.
     * @param tree - tree to verify invariants
     * @param <T> - value type
     * @return - true if invariants are satisfied by {@code tree}.
     */
    static <T> boolean verifyTree(RedBlackTree<T> tree, Comparator<T> cmp) {
        if (tree != null) {
            RedBlackTree.RedBlackTreeNode<T>  root = (RedBlackTree.RedBlackTreeNode<T>) tree.root;
            if (root == null)
                return true;
            if (root.color != RedBlackTree.BLACK)
                return false;
            Pair<Boolean, Integer> left = verifyNode((RedBlackTree.RedBlackTreeNode<T> )root.left, cmp);
            Pair<Boolean, Integer> right = verifyNode((RedBlackTree.RedBlackTreeNode<T> )root.right, cmp);

            if (!left.getLeft() || !right.getLeft()) {
                return false;
            }

            return left.getRight().equals(right.getRight());
        }
        return true;
    }

    private static <T> Pair<Boolean, Integer> verifyNode(RedBlackTree.RedBlackTreeNode<T> node, Comparator<T> cmp) {
        if (node == null) return Pair.of(true, 0);
        if (cmp == null)
            throw new IllegalArgumentException();

        T nodeValue = node.value;
        RedBlackTree.RedBlackTreeNode<T> left = (RedBlackTree.RedBlackTreeNode<T>) node.left;
        if (left != null) {
            T leftValue = left.value;
            if (nodeValue == null || leftValue == null || cmp.compare(nodeValue, leftValue) < 0) {
                return Pair.of(false, 0);
            }
        }

        RedBlackTree.RedBlackTreeNode<T> right = (RedBlackTree.RedBlackTreeNode<T>) node.right;
        if (right != null) {
            T rightValue = right.value;

            if (nodeValue == null || rightValue == null || cmp.compare(nodeValue, rightValue) > 0)
                return Pair.of(false, 0);
        }

        if (left != null && right != null &&
                node.color == RedBlackTree.RED && (left.color != RedBlackTree.BLACK || right.color != RedBlackTree.BLACK))
            return Pair.of(false, 0);


        Pair<Boolean, Integer> leftRes = verifyNode(left, cmp);
        Pair<Boolean, Integer> rightRes = verifyNode(right, cmp);

        if (leftRes.getLeft() && rightRes.getLeft()) {
            if (leftRes.getRight().equals(rightRes.getRight())) {
                int inc = node.color == RedBlackTree.BLACK ? 1 : 0;
                return Pair.of(true, leftRes.getRight() + inc);
            } else {
                return Pair.of(false, 0);
            }
        } else {
            return Pair.of(false, 0);
        }
    }

    static Integer[] generateRandom(int size, int max) {
        if (size < 0 || max < 1)
            throw new IllegalArgumentException();

        Random random = new Random();
        return random.ints(size, 0, max).boxed().toArray(Integer[]::new);
    }

    static boolean isSorted(Integer[] list) {
        if (list == null)
            throw new IllegalArgumentException();

        int current = Integer.MIN_VALUE;
        for (int el : list) {
            if (current > el)
                return false;
            current = el;
        }
        return true;
    }
}