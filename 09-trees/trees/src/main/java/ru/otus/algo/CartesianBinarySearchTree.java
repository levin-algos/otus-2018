package ru.otus.algo;

import java.util.Comparator;
import java.util.List;

public class CartesianBinarySearchTree<K, V>  {

//    public CartesianBinarySearchTree(Comparator<Pair<K, V>> cmp) {
//        super(cmp);
//    }

    public CartesianBinarySearchTree<K, V> build(List<Pair<K, V>> values) {
        if (values== null)
            throw new IllegalArgumentException();

        CartesianBinarySearchTree<K, V> last = null;

//        for (Pair<K, V> i: values) {
//            if (last == null) {
//                last = new CartesianBinarySearchTree<>(i, null, null, null);
//                continue;
//            }
//            if (comparator.compare(last.value, i) > 0) {
//                last.right = new CartesianBinarySearchTree<>(i, null, null, last);
//                last = (CartesianBinarySearchTree<K, V>) last.right;
//            } else {
//                CartesianBinarySearchTree<K, V> cur = last;
//
//                while (cur.parent != null && comparator.compare(cur.value,i) <= 0)
//                    cur = (CartesianBinarySearchTree<K, V>) cur.parent;
//                if (comparator.compare(cur.value, i) < 0)
//                    last = new CartesianBinarySearchTree<>(i, cur, null, null);
//                else {
//                    last = new CartesianBinarySearchTree<>(i, (CartesianBinarySearchTree<K, V>) cur.right, null, cur);
//                    cur.right = last;
//                }
//            }
//        }
//
//        while (last != null && last.parent != null)
//            last = (CartesianBinarySearchTree<K, V>) last.parent;

        return last;
    }
}