package ru.otus.algo;

import java.util.List;

public class CartesianTree<K, V extends Comparable<V>> extends BinarySearchTree<Pair<K, V>> {


    public CartesianTree(Pair<K, V> value, CartesianTree<K, V> left, CartesianTree<K, V> right, CartesianTree<K, V> parent) {
        super(value, left, right, parent);
    }

    public static <K, V extends Comparable<V>> CartesianTree<K, V> merge(CartesianTree<K, V> left, CartesianTree<K, V> right) {
        if (left == null)
            return right;

        if (right == null)
            return left;

        if (left.value.compareTo(right.value) > 0) {
            CartesianTree<K, V> merge = merge((CartesianTree<K, V>) left.right, right);
            return new CartesianTree<>(left.value, (CartesianTree<K, V>) left.left, merge, null);
        } else {
            CartesianTree<K, V> merge = merge(left, (CartesianTree<K, V>) right.left);
            return new CartesianTree<>(right.value, merge, (CartesianTree<K, V>) right.right, null);
        }
    }


    public static <K, V extends Comparable<V>> CartesianTree<K, V> build(List<Pair<K, V>> values) {
        if (values== null)
            throw new IllegalArgumentException();

        CartesianTree<K, V> last = null;

        for (Pair<K, V> i: values) {
            if (last == null) {
                last = new CartesianTree<>(i, null, null, null);
                continue;
            }
            if (last.value.compareTo(i) > 0) {
                last.right = new CartesianTree<>(i, null, null, last);
                last = (CartesianTree<K, V>) last.right;
            } else {
                CartesianTree<K, V> cur = last;

                while (cur.parent != null && cur.value.compareTo(i) <= 0)
                    cur = (CartesianTree<K, V>) cur.parent;
                if (cur.value.compareTo(i) < 0)
                    last = new CartesianTree<>(i, cur, null, null);
                else {
                    last = new CartesianTree<>(i, (CartesianTree<K, V>) cur.right, null, cur);
                    cur.right = last;
                }
            }
        }

        while (last != null && last.parent != null)
            last = (CartesianTree<K, V>) last.parent;

        return last;
    }
}