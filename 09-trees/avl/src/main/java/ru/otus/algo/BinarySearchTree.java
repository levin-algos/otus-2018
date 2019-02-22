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

    private Comparator<? super T> comparator;
    private T value;
    private BinarySearchTree<T> parent;
    private BinarySearchTree<T> left;
    private BinarySearchTree<T> right;


    public BinarySearchTree() { comparator = null;}

    public BinarySearchTree(Comparator<? super T> comparator) {
        this.comparator = comparator;
    }

    public BinarySearchTree(T value, BinarySearchTree<T> left, BinarySearchTree<T> right, BinarySearchTree<T> parent) {
        comparator = null;
        this.value = value;
        this.parent = parent;
        this.left = left;
        this.right = right;
        if (left!=null)
            left.parent = this;
        if (right!=null)
            right.parent = this;
    }

    public static <T> BinarySearchTree<T> of(T[] arr) {
        BinarySearchTree<T> tree = new BinarySearchTree<>();
        for (T i: arr) {
            tree.add(i);
        }
        return tree;
    }

    private TriBooleanFunction<T, Function<BinarySearchTree<T>, Boolean>> proceedBool =
            (el, less, eq, greater) -> {
                int cmp;
                if (comparator != null) {
                    cmp = comparator.compare(this.value, el);
                } else {
                    @SuppressWarnings("unchecked")
                    Comparable<? super T> cpr = (Comparable<? super T>)this.value;
                    cmp = cpr.compareTo(el);
                }
                return cmp == 0 ? eq.apply(this) : cmp > 0 ? less.apply(this) : greater.apply(this);
            };

    private TriConsumer<T, Consumer<BinarySearchTree<T>>> proceedVoid =
            (el, less, eq, greater) -> {
                int cmp;
                if (comparator != null) {
                    cmp = comparator.compare(this.value, el);
                } else {
                    @SuppressWarnings("unchecked")
                    Comparable<? super T> cpr = (Comparable<? super T>)this.value;
                    cmp = cpr.compareTo(el);
                }
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
     * @param element - element to contains
     * @return - true if element exists in BST, else false
     */
    public boolean contains(T element) {
        if (element == null)
            throw new IllegalArgumentException();

        return proceedBool.apply(element,
                el -> el.left != null && el.left.contains(element),
                el -> true,
                el -> el.right != null && el.right.contains(element));
    }

    /**
     * Adds element to the BST
     *
     * @param element - element to add
     */
    public void add(T element) {
        if (element == null)
            throw new IllegalArgumentException();

        if (value == null) {
            value = element;
            return;
        }

        proceedVoid.accept(element,
                el -> {
                    if (left == null) {
                        left = new BinarySearchTree<>(element, null, null, this);
                    } else left.add(element);
                },
                el -> {},
                el -> {
                    if (right == null) {
                        right = new BinarySearchTree<>(element, null, null, this);
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

    public T getValue() {
        return value;
    }

    public Comparator<? super T> getComparator() {
        return comparator;
    }

    public BinarySearchTree<T> getParent() {
        return parent;
    }

    public BinarySearchTree<T> getLeft() {
        return left;
    }

    public BinarySearchTree<T> getRight() {
        return right;
    }

    public void setParent(BinarySearchTree<T> parent) {
        this.parent = parent;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setLeft(BinarySearchTree<T> left) {
        this.left = left;
    }

    public void setRight(BinarySearchTree<T> right) {
        this.right = right;
    }

    public int size() {
        return 0;
    }
}