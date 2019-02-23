package ru.otus.algo;

import java.util.function.Consumer;

public interface TreeNode<T> {

    T getValue();

    TreeNode<T> getLeft();

    TreeNode<T> getRight();

    TreeNode<T> getParent();

    default TreeNode<T> getLeftMost(TreeNode<T> node) {
        TreeNode<T> res = node;
        while (res.getLeft() != null) {
            res = res.getLeft();
        }
        return res;
    }

    default void traverse(TreeNode<T> root, Consumer<TreeNode<T>> action) {
        for (TreeNode<T> it = getLeftMost(root); it != null; it = successor(it)) {
            action.accept(it);
        }
    }

    default TreeNode<T> successor(TreeNode<T> it) {
        if (it == null)
            return null;

        else if (it.getRight() != null) {
            TreeNode<T> p = it.getRight();
            while (p.getRight() != null)
                p = p.getRight();
            return p;
        } else {
            TreeNode<T> p = it.getParent();
            TreeNode<T> ch = it;

            while (p != null && p.getRight() == ch) {
                ch = p;
                p = p.getParent();
            }

            return p;
        }
    }
}
