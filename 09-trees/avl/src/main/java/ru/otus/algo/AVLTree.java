package ru.otus.algo;

import java.util.Comparator;

public class AVLBinarySearchTree<T> extends BinarySearchTree<T> {

    public AVLBinarySearchTree(Comparator<T> comparator) {
        super(comparator);
    }

    private int height = 1;

    private int getHeight(BinarySearchTree<T> node) {
        return node == null ? 0 : ((AVLBinarySearchTree<T>) node).height;
    }

    private void getBalance(BinarySearchTree<T> node) {
//        if (node != null)
//            height = Math.max(getHeight(node.getLeft()), getHeight(node.getRight())) + 1;
    }

    @Override
    public void add(T element) {
        super.add(element);
        balance(this);
    }

    private void balance(BinarySearchTree<T> node) {
        if (node == null)
            return;

//        BinarySearchTree<T> left = node.getLeft();
//        BinarySearchTree<T> right = node.getRight();
//
//        getBalance(node);
//        getBalance(left);
//        getBalance(right);
//
//        if (Math.abs(getHeight(left) - getHeight(right)) == 2) {
//            if (right != null && getHeight(right) >= 0) {
//                TreeRotations.left(node);
//                balance(node);
//                balance(left);
//            } else if (getRight() != null && getHeight(getParent()) <= 0) {
//                TreeRotations.right(node);
//                balance(node);
//                balance(getRight());
//            } else if (getLeft() != null && getHeight(left.getLeft()) - getHeight(left.getRight()) == 2) {
//                TreeRotations.left(getLeft());
//                balance(left);
//                balance(left.getLeft());
//                TreeRotations.right(node);
//                balance(node);
//                balance(right);
//            } else if (getRight() != null && getHeight(getRight().getLeft()) - getHeight(getRight().getRight()) == -2) {
//                TreeRotations.right(right);
//                this.balance(node);
//                balance(right);
//                TreeRotations.left(node);
//                balance(node);
//                balance(left);
//            }
        }
/*
        if (node.getParent() != null) {
            getBalance(node.getParent());
            balance(node.getParent());
        }*/
//    }
}