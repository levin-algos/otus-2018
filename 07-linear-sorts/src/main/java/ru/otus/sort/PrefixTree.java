package ru.otus.sort;

import java.util.ArrayList;
import java.util.List;

public class PrefixTree {

    private final PrefixTreeNode root;

    public PrefixTree() {
        root = new PrefixTreeNode();
    }

    public boolean contains(String seq) {
        PrefixTreeNode cursor = root;
        for (char el : seq.toCharArray()) {
            cursor = cursor.getNode(map(el));

            if (cursor == null)
                return false;
        }
        return true;
    }

    private char map(char ch) {
        if (ch > 47 && ch <= 57)
            return (char) (ch - 48);
        else
            throw new IllegalStateException();
    }

    void addAll(String... arr) {
        for (String s : arr) {
            add(s);
        }
    }

    void add(String value) {
        PrefixTreeNode cursor = root;

        for (char ch : value.toCharArray()) {
            cursor = cursor.addChild(map(ch));
        }
        cursor.hasValue = true;
        cursor.setValue(value);
    }

    List<String> traverse() {
        List<String> integers = new ArrayList<>();
        traverse(root, integers);
        return integers;
    }

    private void traverse(PrefixTreeNode node, List<String> arr) {
        if (node.hasValue) {
            arr.add(node.value);
        }
        for (PrefixTreeNode n : node.nodes) {
            if (n != null)
                traverse(n, arr);
        }
    }

    public boolean delete(String s) {
        PrefixTreeNode cursor = root;
        int len = s.toCharArray().length - 1;
        for (int i = 0; i < len; i++) {
            char el = s.charAt(i);
            cursor = cursor.getNode(map(el));

            if (cursor == null)
                return false;
        }

        char key = map(s.charAt(len));
        if (cursor.getNode(key) != null) {
            cursor.deleteNode(key);

        }
        return true;
    }

    private final class PrefixTreeNode {
        private PrefixTreeNode[] nodes;
        private String value;
        private boolean hasValue;

        PrefixTreeNode() {
            this.nodes = new PrefixTreeNode[10];
        }

        PrefixTreeNode addChild(char key) {
            if (nodes[key] == null) {
                PrefixTreeNode value = new PrefixTreeNode();
                nodes[key] = value;
                return value;
            }
            return nodes[key];
        }

        PrefixTreeNode getNode(char key) {
            return nodes[key];
        }

        void setValue(String value) {
            this.value = value;
        }

        public void deleteNode(char key) {
            nodes[key] = null;
        }
    }
}