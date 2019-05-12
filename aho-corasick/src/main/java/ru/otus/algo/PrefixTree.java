package ru.otus.algo;

import java.util.*;

class PrefixTree {

    private final PrefixTreeNode root;

    PrefixTree(String... arr) {
        root = new PrefixTreeNode(null);
        root.prefix = root;

        for (String s : arr) {
            add(s);
        }
    }

    PrefixTree(PrefixTree left, PrefixTree right) {
        Objects.requireNonNull(left);
        Objects.requireNonNull(right);

        root = union(left.root, right.root);
    }

    private PrefixTreeNode union(PrefixTreeNode left, PrefixTreeNode right) {
        assert left.value.equals(right.value);
        PrefixTreeNode node = new PrefixTreeNode(null);
        node.value = left.value;
        node.hasValue = left.hasValue ? left.hasValue : right.hasValue;

        for (Map.Entry<String, PrefixTreeNode> entry : left.nodes.entrySet()) {
            String key = entry.getKey();
            if (!node.nodes.containsKey(key)) {
                if (!right.nodes.containsKey(key))
                    node.nodes.put(key, entry.getValue());
                else {
                    node.nodes.put(key, union(entry.getValue(), right.nodes.get(key)));
                }
            }
        }

        for (Map.Entry<String, PrefixTreeNode> entry : right.nodes.entrySet()) {
            String key = entry.getKey();
            if (!node.nodes.containsKey(key)) {
                if (!left.nodes.containsKey(key))
                    node.nodes.put(key, entry.getValue());
                else {
                    node.nodes.put(key, union(entry.getValue(), left.nodes.get(key)));
                }
            }
        }
        return node;
    }

    boolean isEmpty() {
        return root.isEmpty();
    }

    boolean contains(String seq) {
        PrefixTreeNode node = find(seq);
        return node != null && node.hasValue;
    }

    private PrefixTreeNode find(String seq) {
        PrefixTreeNode cursor = root;
        for (char s : seq.toCharArray()) {
            cursor = cursor.getNode(Character.toString(s));
            if (cursor == null)
                return null;
        }
        return cursor;
    }

    private void add(String value) {
        PrefixTreeNode cursor = root;

        StringBuilder name = new StringBuilder();
        for (char s : value.toCharArray()) {
            name.append(s);
            cursor = cursor.addChild(Character.toString(s));
            cursor.ch = s;
            cursor.value = name.toString();
        }
        cursor.hasValue = true;
    }

    Set<String> traverse() {
        HashSet<String> set = new HashSet<>();
        Queue<PrefixTreeNode> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            PrefixTreeNode cur = q.remove();
            for (Map.Entry<String, PrefixTreeNode> entry : cur.nodes.entrySet()) {
                PrefixTreeNode node = entry.getValue();
                if (node.hasValue)
                    set.add(node.value);
                q.add(node);
            }
        }
        return set;
    }

    boolean delete(String s) {
        PrefixTreeNode cursor = root;

        char[] chars = s.toCharArray();
        int i;
        for (i = 0; i <= chars.length - 2; i++) {
            cursor = cursor.getNode(Character.toString(chars[i]));

            if (cursor == null)
                return false;
        }

        if (cursor != null) {
            cursor.deleteNode(Character.toString(chars[i]));
            return true;
        }
        return false;
    }

    String prefixFor(String a) {
        PrefixTreeNode node = find(a);
        if (node == null)
            throw new IllegalArgumentException();

        PrefixTreeNode prefix = node.getPrefix();
        return prefix == root ? "" : prefix.value;
    }

    private final class PrefixTreeNode {
        private final Map<String, PrefixTreeNode> nodes;
        private String value;
        private char ch;
        private boolean hasValue;
        private PrefixTreeNode parent;
        private PrefixTreeNode prefix;

        PrefixTreeNode(PrefixTreeNode parent) {
            this.nodes = new HashMap<>();
            value = "";
            this.parent = parent;
        }

        boolean isEmpty() {
            return nodes.size() == 0;
        }

        PrefixTreeNode addChild(String key) {
            PrefixTreeNode node = nodes.get(key);
            if (node == null) {
                PrefixTreeNode value = new PrefixTreeNode(this);
                nodes.put(key, value);
                return value;
            }
            return node;
        }

        PrefixTreeNode getNode(String key) {
            return nodes.get(key);
        }

        void deleteNode(String key) {
            PrefixTreeNode node = nodes.get(key);
            if (node == null)
                return;

            nodes.remove(key);
        }

        PrefixTreeNode getPrefix() {
            if (prefix == null) {
                if (parent == root)
                    return prefix = root;

                PrefixTreeNode u = parent;
                do {
                    u = u.getPrefix();
                } while (!(u == root || u.nodes.containsKey(Character.toString(ch))));
                    prefix = u.nodes.getOrDefault(Character.toString(ch), root);
            }
            return prefix;
        }

        @Override
        public String toString() {
            return "PrefixTreeNode{" +
                    "value='" + value + '\'' +
                    ", ch=" + ch +
                    ", hasValue=" + hasValue +
                    ", nodes: " + nodes.size() +
                    '}';
        }
    }
}