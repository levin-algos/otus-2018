package ru.otus.sort;

class PrefixTree {

    private final PrefixTreeNode root;
    private final int maxDigitNumber;
    private int count;

    PrefixTree(int... arr) {
        maxDigitNumber = Common.getNumberOfDigits(Common.max(arr));
        root = new PrefixTreeNode();

        for (int s : arr) {
            add(s);
        }
    }

    boolean contains(int seq) {
        PrefixTreeNode cursor = root;
        for (int i = maxDigitNumber - 1; i >= 0; i--) {
            cursor = cursor.getNode((seq / Common.POWER_OF_10[i]) % 10);

            if (cursor == null)
                return false;
        }
        return true;
    }

    private void add(int value) {
        PrefixTreeNode cursor = root;

        for (int i = maxDigitNumber - 1; i >= 0; i--) {
            cursor = cursor.addChild((value / Common.POWER_OF_10[i]) % 10);
        }
        cursor.hasValue = true;
        cursor.setValue(value);
        count++;
    }

    private final class IntArray {
        private final int[] array;
        private int pos;

        IntArray(int size) {
            array = new int[size];
        }

        void add(int value) {
            array[pos++] = value;
        }

        int[] toArray() {
            return array;
        }
    }

    int[] traverse() {
        IntArray array = new IntArray(count);
        traverse(root, array);
        return array.toArray();
    }

    private void traverse(PrefixTreeNode node, IntArray arr) {
        if (node.hasValue) {
            arr.add(node.value);
        }
        for (PrefixTreeNode n : node.nodes) {
            if (n != null)
                traverse(n, arr);
        }
    }

    boolean delete(int s) {
        PrefixTreeNode cursor = root;

        for (int i =maxDigitNumber - 1; i > 0; i--) {
            cursor = cursor.getNode((s / Common.POWER_OF_10[i]) % 10);

            if (cursor == null)
                return false;
        }

        if (cursor != null) {
            cursor.deleteNode(s % 10);
            count--;
        }
        return true;
    }

    private final class PrefixTreeNode {
        private final PrefixTreeNode[] nodes;
        private int value;
        private boolean hasValue;

        PrefixTreeNode() {
            this.nodes = new PrefixTreeNode[10];
        }

        PrefixTreeNode addChild(int key) {
            if (nodes[key] == null) {
                PrefixTreeNode value = new PrefixTreeNode();
                nodes[key] = value;
                return value;
            }
            return nodes[key];
        }

        PrefixTreeNode getNode(int key) {
            return nodes[key];
        }

        void setValue(int value) {
            this.value = value;
        }

        void deleteNode(int key) {
            nodes[key] = null;
        }
    }
}