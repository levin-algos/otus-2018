package ru.otus.algo;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;

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
public class BinarySearchTree<T> {

    private BinarySearchTree<T> create(Comparator<T> cmp) {
        return new BinarySearchTree<>(cmp);
    }

    public BinarySearchTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    Comparator<T> comparator;
    public BinarySearchTree(T value, BinarySearchTree<T> left, BinarySearchTree<T> right, BinarySearchTree<T> parent) {
        this.value = value;
        this.parent = parent;
        this.left = left;
        this.right = right;
        if (left!=null)
            left.parent = this;
        if (right!=null)
            right.parent = this;
    }

    private TriBooleanFunction<T, Function<BinarySearchTree<T>, Boolean>> proceedBool =
            (el, less, eq, greater) -> {
                int cmp = comparator.compare(this.value, el);
                return cmp == 0 ? eq.apply(this) : cmp > 0 ? less.apply(this) : greater.apply(this);
            };

    private TriConsumer<T, Consumer<BinarySearchTree<T>>> proceedVoid =
            (el, less, eq, greater) -> {
                int cmp = comparator.compare(this.value, el);
                if (cmp == 0)
                    eq.accept(this);
                else if (cmp > 0)
                    less.accept(this);
                else
                    greater.accept(this);
            };

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

        return proceedBool.apply(element,
                el -> el.left != null && el.left.find(element),
                el -> true,
                el -> el.right != null && el.right.find(element));
    }

    /**
     * Adds element to the BST
     *
     * @param element - element to add
     */
    public void add(T element) {
        if (element == null)
            throw new IllegalArgumentException();

        proceedVoid.accept(element,
                el -> {
                    if (left == null) {
                        left = create(comparator);
                        left.setValue(element);
                        left.setParent(this);
                    } else left.add(element);
                },
                el -> {},
                el -> {
                    if (right == null) {
                        right = create(comparator);
                        right.setValue(element);
                        right.setParent(this);
                    } else right.add(element);
                });
    }

    /**
     * Removes the {@code element} from BST.
     *
     * @param element - element to remove
     */
    public void remove(T element) {
        if (element == null)
            throw new IllegalArgumentException();

        proceedVoid.accept(element,
                el -> {
                    if (this.left != null) this.left.remove(element);
                },
                el -> remove(),
                el -> {
                    if (this.right != null) this.right.remove(element);
                });
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

    T value;
    BinarySearchTree<T> parent;
    BinarySearchTree<T> left;
    BinarySearchTree<T> right;

    public T getValue() {
        return value;
    }

    public void setParent(BinarySearchTree<T> parent) {
        this.parent = parent;
    }

    public void setValue(T value) {
        this.value = value;
    }
}