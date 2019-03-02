package ru.otus.algo;

import ru.otus.algo.common.ReflectionEntry;

import java.util.Comparator;
import java.util.function.BiPredicate;

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

            if (Math.abs(leftH - rightH) > 1)
                return false;

            return true;
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

                if (nodeValue == null || rightValue == null || cmp.compare(nodeValue, rightValue) > 0)
                    return false;
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

                if (nodeValue == null || rightValue == null || cmp.compare(nodeValue, rightValue) < 0)
                    return false;
            }
            return true;
        };
    }
}