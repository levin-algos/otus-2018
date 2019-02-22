package ru.otus.algo;

import java.util.Comparator;
import java.util.List;

public class CartesianTree<K, V> extends BinarySearchTree<Pair<K, V>> {

    public CartesianTree(Comparator<Pair<K, V>> cmp) {
        super(cmp);
    }

    public CartesianTree(Pair<K, V> value, CartesianTree<K, V> left, CartesianTree<K, V> right, CartesianTree<K, V> parent) {
        super(value, left, right, parent);
    }

    public CartesianTree<K, V> build(List<Pair<K, V>> values) {
        if (values== null)
            throw new IllegalArgumentException();

        CartesianTree<K, V> last = null;

//        for (Pair<K, V> i: values) {
//            if (last == null) {
//                last = new CartesianTree<>(i, null, null, null);
//                continue;
//            }
//            if (comparator.compare(last.value, i) > 0) {
//                last.right = new CartesianTree<>(i, null, null, last);
//                last = (CartesianTree<K, V>) last.right;
//            } else {
//                CartesianTree<K, V> cur = last;
//
//                while (cur.parent != null && comparator.compare(cur.value,i) <= 0)
//                    cur = (CartesianTree<K, V>) cur.parent;
//                if (comparator.compare(cur.value, i) < 0)
//                    last = new CartesianTree<>(i, cur, null, null);
//                else {
//                    last = new CartesianTree<>(i, (CartesianTree<K, V>) cur.right, null, cur);
//                    cur.right = last;
//                }
//            }
//        }
//
//        while (last != null && last.parent != null)
//            last = (CartesianTree<K, V>) last.parent;

        return last;
    }
}