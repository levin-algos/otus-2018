package ru.otus.algo;

import ru.otus.algo.common.ReflectionEntry;

import java.util.Comparator;
import java.util.function.BiPredicate;

@SuppressWarnings("unchecked")
class TreeInvariants {

    private TreeInvariants() {
    }

    static <V> BiPredicate<ReflectionEntry, Comparator<? super V>> isAVL() {
        return (n, cmp) -> {
            if (n == null)
                return true;

            ReflectionEntry left = n.getField("left");
            ReflectionEntry right = n.getField("right");

            int leftH = left == null ? 0 : left.getIntFieldValue("height");
            int rightH = right == null ? 0 : right.getIntFieldValue("height");

            return Math.abs(leftH - rightH) <= 1;

        };
    }

    static <V> BiPredicate<ReflectionEntry, Comparator<? super V>> isBST() {
        return (n, cmp) -> {
            if (n == null)
                return true;

            ReflectionEntry left = n.getField("left");
            if (left != null) {

                V nodeValue = (V) n.getValue("value");
                V leftValue = (V) left.getValue("value");

                if (nodeValue == null || leftValue == null || cmp.compare(nodeValue, leftValue) < 0)
                    return false;
            }

            ReflectionEntry right = n.getField("right");
            if (right != null) {

                V nodeValue = (V) n.getValue("value");
                V rightValue = (V) right.getValue("value");

                return nodeValue != null && rightValue != null && cmp.compare(nodeValue, rightValue) <= 0;
            }
            return true;
        };
    }

    static <K, V> BiPredicate<ReflectionEntry, Comparator<? super Pair<K, V>>> isHeap() {
        return (n, cmp) -> {
            if (n == null)
                return true;

            ReflectionEntry left = n.getField("left");
            if (left != null) {

                Pair<K, V> nodeValue = (Pair<K, V>) n.getValue("value");
                Pair<K, V> leftValue = (Pair<K, V>) left.getValue("value");

                if (nodeValue == null || leftValue == null || cmp.compare(nodeValue, leftValue) < 0)
                    return false;
            }

            ReflectionEntry right = n.getField("right");
            if (right != null) {

                Pair<K, V> nodeValue = (Pair<K, V>) n.getValue("value");
                Pair<K, V> rightValue = (Pair<K, V>) right.getValue("value");

                return nodeValue != null && rightValue != null && cmp.compare(nodeValue, rightValue) >= 0;
            }
            return true;
        };
    }

    static <V> BiPredicate<ReflectionEntry, Comparator<? super V>> isRedBlack() {
        return (n, cmp) -> {
                if (n == null) return false;
                if (cmp == null)
                    throw new IllegalArgumentException();

            ReflectionEntry left = n.getField("left");
            ReflectionEntry right = n.getField("right");
            boolean color = n.getBoolFieldValue("color");


            if (left != null && right != null) {
                boolean leftColor = left.getBoolFieldValue("color");
                boolean rightColor = right.getBoolFieldValue("color");
                return color != RedBlackTree.RED || (leftColor == RedBlackTree.BLACK && rightColor == RedBlackTree.BLACK);
            }

            return true;
        };
    }

    static <V> BiPredicate<ReflectionEntry, Comparator<? super V>> countBlackNodes() {
        return (n, cmp) -> {
            Pair<Boolean, Integer> res = verifyNode(n);
            return res.getLeft();
        };
    }

    private static Pair<Boolean, Integer> verifyNode(ReflectionEntry entry) {
        if (entry == null)
            return Pair.of(true, 0);

        ReflectionEntry left = entry.getField("left");
        ReflectionEntry right = entry.getField("right");

        Pair<Boolean, Integer> leftRes = verifyNode(left);
        Pair<Boolean, Integer> rightRes = verifyNode(right);

        if (leftRes.getLeft() && rightRes.getLeft()) {
            if (leftRes.getRight().equals(rightRes.getRight())) {
                int inc = entry.getBoolFieldValue("color") == RedBlackTree.BLACK ? 1 : 0;
                return Pair.of(true, leftRes.getRight() + inc);
            } else {
                return Pair.of(false, 0);
            }
        } else {
            return Pair.of(false, 0);
        }
    }
}