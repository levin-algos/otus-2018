package ru.otus.algo;

import java.util.Comparator;
import java.util.Random;

class Utils {
    private Utils() {}

    /**
     * Recursively checks invariants of binary search tree:
     * 1. {@code node} value greater than left child of this {@code node}
     * 2. {@code node} value less than right child of this {@code node}
     * Empty tree is binary search tree by definition.
     * Comparisons are made by {@code cmp} comparator
     *
     * This method does not allow null {@code node} values.
     * Tree with null values are not considered to be binary search tree.
     *
     * If {@code cmp} is null throws IllegalArgumentException
     *
     * @param node - root element of the binary search tree
     * @param cmp - comparator to compare {@code node} values
     * @param <T> - value type of {@code node}
     * @return - true if binary tree is binary search tree, else false
     */
    static <T> boolean isBST(AbstractBinarySearchTree<T> node, Comparator<? super T> cmp) {
        if (cmp == null)
            throw new IllegalArgumentException();

        if (node == null) return true;

        return isBSTNode(node.root, cmp);
    }

    private static <T> boolean isBSTNode(AbstractBinarySearchTree.Node<T> node, Comparator<? super T> cmp) {
        if (node == null)
            return true;

        AbstractBinarySearchTree.Node<T> left = node.left;
        if (left != null) {

            T nodeValue = node.value;
            T leftValue = left.value;

            if (nodeValue == null || leftValue == null || cmp.compare(nodeValue, leftValue) < 0)
                return false;
        }

        AbstractBinarySearchTree.Node<T> right = node.right;
        if (right != null) {

            T nodeValue = node.value;
            T rightValue = right.value;

            if (nodeValue == null || rightValue == null || cmp.compare(nodeValue, rightValue) > 0)
                return false;
        }

        return isBSTNode(left, cmp) && isBSTNode(right, cmp);
    }

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

            if (!left.getFirst() || !right.getFirst()) {
                return false;
            }

            return left.getSecond().equals(right.getSecond());
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

        if (leftRes.getFirst() && rightRes.getFirst()) {
            if (leftRes.getSecond().equals(rightRes.getSecond())) {
                int inc = node.color == RedBlackTree.BLACK ? 1 : 0;
                return Pair.of(true, leftRes.getSecond() + inc);
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