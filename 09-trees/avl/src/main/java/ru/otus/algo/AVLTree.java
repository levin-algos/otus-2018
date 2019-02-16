package ru.otus.algo;

public class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    BinarySearchTree<T> create() {
        return new AVLTree<>();
    }

    private int height = 1;

    public int getHeight(AVLTree<T> node) {
        return node == null ? 0 : node.height;
    }

    public void getBalance() {
        height = Math.max(getHeight((AVLTree<T>)left), getHeight((AVLTree<T>)right)) + 1;
    }

    @Override
    public void add(T element) {
        super.add(element);
        getBalance();
        balance();
    }

    private void balance() {
        if (getHeight((AVLTree<T>) left) - getHeight((AVLTree<T>) right) == 2) {
            if (getHeight((AVLTree<T>) left.left) >= getHeight((AVLTree<T>) left.right))
                littleRight(this);
            else if (getHeight((AVLTree<T>) left.left) < getHeight((AVLTree<T>) left.right)) {
                bigRight(this);
            } else if (getHeight((AVLTree<T>) right.left) > getHeight((AVLTree<T>) right.right)) {
                bigLeft(this);
            }
        }
        if (parent!= null) {
            ((AVLTree<T>) parent).getBalance();
            ((AVLTree<T>) parent).balance();
        }
    }

    private void littleRight(AVLTree<T> node) {
        AVLTree<T> left = (AVLTree<T>) node.left;
        AVLTree<T> left1 = (AVLTree<T>) left.left;

        node.left = left1;
        left1.parent = node;

        left.left = left.right;
        left.right = node.right;
        left.parent = node;

        node.right = left;
        T val = node.value;
        node.value = left.value;
        left.value = val;

        left.getBalance();
        left1.getBalance();
        node.getBalance();
    }

    private void bigRight(AVLTree<T> node) {
        AVLTree<T> c = (AVLTree<T>) node.left.right;
        AVLTree<T> b = (AVLTree<T>) node.left;
        AVLTree<T> a = (AVLTree<T>) node.right;

        b.right = c.left;
        c.left.parent = b;

        c.left = c.right;
        c.right = a.right;
        a.right.parent = c;

        a.right = c;
        c.parent = a;

        T tmp = a.value;
        a.value = c.value;
        c.value = tmp;
    }

    private void bigLeft(AVLTree<T> node) {
        AVLTree<T> b = (AVLTree<T>) node.right;
        AVLTree<T> c = (AVLTree<T>) b.left;

        b.left = c.right;
        c.right.parent = b;

        c.right = c.left;
        c.left = node.left;
        node.left.parent = c;

        node.left = c;
        c.parent = node;

        T tmp = node.value;
        node.value = c.value;
        c.value = tmp;
    }
}