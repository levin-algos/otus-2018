package ru.otus.algo;

import java.util.function.Consumer;

public interface BinaryTreeNode<T> {

    T getValue();

    BinaryTreeNode<T> getLeft();

    BinaryTreeNode<T> getRight();

    BinaryTreeNode<T> getParent();

    default BinaryTreeNode<T> getLeftMost(BinaryTreeNode<T> node) {
        BinaryTreeNode<T> res = node;
        while (res.getLeft() != null) {
            res = res.getLeft();
        }
        return res;
    }

    default void traverse(BinaryTreeNode<T> root, Consumer<BinaryTreeNode<T>> action) {
        for (BinaryTreeNode<T> it = getLeftMost(root); it != null; it = successor(it)) {
            action.accept(it);
        }
    }

    default BinaryTreeNode<T> successor(BinaryTreeNode<T> it) {
        if (it == null)
            return null;

        else if (it.getRight() != null) {
            BinaryTreeNode<T> p = it.getRight();
            while (p.getLeft() != null)
                p = p.getLeft();
            return p;
        } else {
            BinaryTreeNode<T> p = it.getParent();
            BinaryTreeNode<T> ch = it;

            while (p != null && p.getRight() == ch) {
                ch = p;
                p = p.getParent();
            }

            return p;
        }
    }
}
