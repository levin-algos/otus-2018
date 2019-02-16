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
        System.out.println(element);
        super.add(element);
        getBalance();
        balance();
    }

    private void balance() {
        if (Math.abs(getHeight((AVLTree<T>) left) - getHeight((AVLTree<T>) right)) == 2) {
            if (right != null && getHeight((AVLTree<T>) right) >=0 )
                rotateLeft(this);
            else if ( right != null && getHeight((AVLTree<T>) left) <=0)
                rotateRight(this);
            else if (left != null && getHeight((AVLTree<T>) left)==2) {
                rotateLeft((AVLTree<T>) left);
                rotateRight(this);
            } else if (right != null && getHeight((AVLTree<T>) right) == -2) {
                rotateRight((AVLTree<T>) right);
                rotateLeft(this);
            }
        }
        if (parent!= null) {
            ((AVLTree<T>) parent).getBalance();
            ((AVLTree<T>) parent).balance();
        }
    }

    private void rotateRight(AVLTree<T> node) {
        AVLTree<T> left = (AVLTree<T>) node.left;
        AVLTree<T> left1 = (AVLTree<T>) left.left;

        node.left = left1;
        if (left1 != null)
            left1.parent = node;

        left.left = left.right;
        left.right = node.right;
        left.parent = node;

        node.right = left;
        T val = node.value;
        node.value = left.value;
        left.value = val;

        left.getBalance();
        if (left1 != null)
        left1.getBalance();
        node.getBalance();
    }

    private void rotateLeft(AVLTree<T> node) {
        AVLTree<T> b = (AVLTree<T>) node.right;

        node.right = b.right;
        if (b.right != null)
            b.right.parent = node;

        b.right = b.left;
        b.left = node.left;
        if (b.left != null)
            b.left.parent = b;

        node.left = b;
        b.parent = node;

        T tmp = node.value;
        node.value = b.value;
        b.value = tmp;

        b.getBalance();
        node.getBalance();
    }
}