package ru.otus.algo;

import java.util.Comparator;
import java.util.function.BiPredicate;

class TreeInvariants {

    private TreeInvariants() {}

    static <V> BiPredicate<AbstractBinarySearchTree.Node<V>, Comparator<? super V>> isBST() {
        return (n, cmp) -> {
            if (n == null)
                return true;

            AbstractBinarySearchTree.Node<V> left = n.left;
            if (left != null) {

                V nodeValue = n.value;
                V leftValue = left.value;

                if (nodeValue == null || leftValue == null || cmp.compare(nodeValue, leftValue) < 0)
                    return false;
            }

            AbstractBinarySearchTree.Node<V> right = n.right;
            if (right != null) {

                V nodeValue = n.value;
                V rightValue = right.value;

                if (nodeValue == null || rightValue == null || cmp.compare(nodeValue, rightValue) > 0)
                    return false;
            }
            return true;
        };
    }

    public static <K, V> BiPredicate<AbstractBinarySearchTree.Node<Pair<K, V>>, Comparator<? super Pair<K, V>>> isHeap() {
        return (n, cmp) -> {
            if (n == null)
                return true;

            AbstractBinarySearchTree.Node<Pair<K,V>> left = n.left;
            if (left != null) {

                Pair<K, V> nodeValue = n.value;
                Pair<K, V> leftValue = left.value;

                if (nodeValue == null || leftValue == null || cmp.compare(nodeValue, leftValue) < 0)
                    return false;
            }

            AbstractBinarySearchTree.Node<Pair<K,V>> right = n.right;
            if (right != null) {

                Pair<K,V> nodeValue = n.value;
                Pair<K,V> rightValue = right.value;

                if (nodeValue == null || rightValue == null || cmp.compare(nodeValue, rightValue) < 0)
                    return false;
            }
            return true;
        };
    }
}