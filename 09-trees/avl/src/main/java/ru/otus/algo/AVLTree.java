package ru.otus.algo;

import java.util.Comparator;

public class AVLTree<T> extends BinarySearchTree<T> {

    public AVLTree(Comparator<T> comparator) {
        super(comparator);
    }

    private int height = 1;

    private int getHeight(BinarySearchTree<T> node) {
        return node == null ? 0 : ((AVLTree<T>) node).height;
    }

    private void getBalance(BinarySearchTree<T> node) {
        if (node != null)
            height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    @Override
    public void add(T element) {
        super.add(element);
        balance(this);
    }

    private void balance(BinarySearchTree<T> node) {
        if (node == null)
            return;

        getBalance(node);
        getBalance(node.left);
        getBalance(node.right);

        if (Math.abs(getHeight(node.left) - getHeight(node.right)) == 2) {
            if (right != null && getHeight(right) >= 0) {
                TreeRotations.left(node);
                balance(node);
                balance(node.left);
            } else if (right != null && getHeight(left) <= 0) {
                TreeRotations.right(this);
                balance(node);
                balance(right);
            } else if (left != null && getHeight(node.left.left) - getHeight(node.left.right) == 2) {
                TreeRotations.left(left);
                balance(node.left);
                balance(node.left.left);
                TreeRotations.right(node);
                balance(node);
                balance(node.right);
            } else if (right != null && getHeight(right.left) - getHeight(right.right) == -2) {
                TreeRotations.right(node.right);
                this.balance(node);
                balance(node.right);
                TreeRotations.left(node);
                balance(node);
                balance(node.left);
            }
        }

        if (node.parent != null) {
            getBalance(node.parent);
            balance(node.parent);
        }
    }
}