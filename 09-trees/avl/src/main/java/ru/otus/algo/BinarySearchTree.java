package ru.otus.algo;

import java.util.function.Consumer;

/**
 * Implementation of common methods of binary search tree (BST).
 * <p>
 * 1. Add element to tree
 * 2. Remove element from the tree
 * 3. Find element in the tree
 * 4. Traverse
 * </p>
 * This implementation cannot keep null values.
 *
 * @param <T> - type of elements in BST
 */
public class BinarySearchTree<T extends Comparable<T>> {

    public BinarySearchTree(T value, BinarySearchTree<T> parent) {
        this.value = value;
        this.parent = parent;
    }

    /**
     * Searches element in BST.
     * If {@code element} is {@code null} - throws IllegalArgumentException
     *
     * @param element - element to find
     * @return - true if element exists in BST, else false
     */
    public boolean find(T element) {
        if (element == null)
            throw new IllegalArgumentException();

        int compare = this.value.compareTo(element);
        if (compare == 0)
            return true;
        else if (compare < 0)
            return this.right != null && this.right.find(element);
        else
            return this.left != null && this.left.find(element);
    }

    /**
     * Adds element to the BST
     *
     * @param element - element to add
     */
    public void add(T element) {
        if (element == null)
            throw new IllegalArgumentException();

        int compare = this.value.compareTo(element);
        if (compare > 0) {
            if (left == null) {
                left = new BinarySearchTree<>(element, this);
            } else {
                left.add(element);
            }
        } else if (compare < 0) {
            if (right == null) {
                right = new BinarySearchTree<>(element, this);
            } else {
                right.add(element);
            }
        }
    }

    /**
     * Removes the {@code element} from BST.
     *
     * @param element - element to remove
     */
    public void remove(T element) {
        if (element == null)
            throw new IllegalArgumentException();

        int compare = this.value.compareTo(element);
        if (compare == 0) {
            remove();
        } else if (compare < 0) {
            if (this.right != null)
                this.right.remove(element);
        } else {
            if (this.left != null)
                this.left.remove(element);
        }
    }

    private void remove() {
        if (left != null && right != null) {
            BinarySearchTree<T> successor = right.findMin();
            value = successor.value;
            successor.remove();
        } else if (left != null) {
            replaceInParent(left);
        } else if (right != null) {
            replaceInParent(right);
        } else {
            replaceInParent(null);
        }
    }

    private void replaceInParent(BinarySearchTree<T> value) {
        if (parent != null) {
            if (parent.left == this) {
                parent.left = value;
            } else if (parent.right == this) {
                parent.right = value;
            }
        } else {
            left = value.left;
            right = value.right;
            this.value = value.value;
        }
    }

    private BinarySearchTree<T> findMin() {
        BinarySearchTree<T> min = this;
        while (min.left != null) {
            min = min.left;
        }
        return min;
    }

    public void traverse(TraversalOrder order, Consumer<BinarySearchTree<T>> action) {
        if (TraversalOrder.PREORDER == order) {
            action.accept(this);

            if (left != null) left.traverse(order, action);
            if (right != null) right.traverse(order, action);

        } else if (TraversalOrder.INORDER == order) {
            if (left != null) left.traverse(order, action);

            action.accept(this);

            if (right != null) right.traverse(order, action);
        } else {
            if (left != null) left.traverse(order, action);
            if (right != null) right.traverse(order, action);
            action.accept(this);
        }
    }


    private T value;
    private BinarySearchTree<T> parent;
    private BinarySearchTree<T> left;
    private BinarySearchTree<T> right;

    public T getValue() {
        return value;
    }
}