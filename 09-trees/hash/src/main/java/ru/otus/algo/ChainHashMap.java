package ru.otus.algo;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ChainHashMap<K, V> implements Map<K, V> {

    private Bucket<K, V>[] table;
    private final Hash<K> hash;
    private int size;

    private int BUCKETS_NUM;

    private static final float MAX_LOAD_FACTOR = 0.75f;

    private static final int TREEIFY_THRESHOLD = 8;

    private static final int UNTREEIFY_THRESHOLD = 6;

    public ChainHashMap(Hash<K> hash, int bucketSize) {
        BUCKETS_NUM = bucketSize;
        table = new Bucket[BUCKETS_NUM];
        this.hash = hash;
    }

    private int getBucketNum(K key) {
        int h = hash.get(key, BUCKETS_NUM);
        return (BUCKETS_NUM - 1) & h;
    }

    @Override
    public void put(K key, V value) {
        int i = getBucketNum(key);
        Bucket<K, V> bucket = table[i];

        if (bucket == null) {
            bucket = new ArrayBucket<>();
            table[i] = bucket;
        }

        if (bucket.put(key, value))
            size++;

        resize();
    }

    private void resize() {
        if (BUCKETS_NUM < 1073741824 && MAX_LOAD_FACTOR < (float) size / BUCKETS_NUM) {
            BUCKETS_NUM *= 2;
            Bucket<K, V>[] oldTable = this.table;
            this.table = new Bucket[BUCKETS_NUM];
            size = 0;
            for (Bucket<K, V> buck : oldTable) {
                if (buck == null)
                    continue;

                for (Node<K, V> node : buck)
                    put(node.getKey(), node.getValue());
            }
        }
    }

    public int size() {
        return size;
    }

    @Override
    public void remove(K key) {
        int i = getBucketNum(key);

        Bucket<K, V> bucket = table[i];

        if (bucket != null)
            if (bucket.remove(key))
                size++;
    }

    @Override
    public boolean containsKey(K key) {
        int i = getBucketNum(key);

        Bucket<K, V> bucket = table[i];

        return bucket != null && bucket.get(key) != null;
    }

    interface Bucket<K, V> extends Iterable<Node<K, V>> {

        boolean put(K key, V value);

        V get(K key);

        boolean remove(K key);

        int size();
    }

    static class ArrayBucket<K, V> implements Bucket<K, V> {
        private Node<K, V> root;
        private int size;

        @Override
        public boolean put(K key, V value) {
            if (root == null) {
                root = new Node<>(key, value, null);
                size++;
                return true;
            }

            Node<K, V> cur = root, p = null;
            while (cur != null) {
                if (cur.getKey().equals(key)) {
                    cur.setValue(value);
                    return false;
                }
                p = cur;
                cur = cur.getNext();
            }

            p.setNext(new Node<>(key, value, null));
            size++;
            return true;
        }

        @Override
        public V get(K key) {
            if (root == null)
                return null;


            Node<K, V> cur = root;
            while (cur != null) {
                if (cur.getKey().equals(key)) {
                    return cur.getValue();
                }
                cur = cur.getNext();
            }
            return null;
        }

        @Override
        public boolean remove(K key) {
            if (root == null)
                return false;

            Node<K, V> cur = root;
            while (cur != null) {
                if (cur.getKey().equals(key)) {
                    break;
                }
                cur = cur.getNext();
            }

            if (cur != null) {
                if (cur == root) {
                    root = cur.getNext();
                    size++;
                    return true;
                }

                Node<K, V> next = cur.getNext();

                cur.key = next.key;
                cur.value = next.value;
                cur.next = next.next;
                size++;
                return true;
            }

            return false;
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public Iterator<Node<K, V>> iterator() {
            return new Iterator<Node<K, V>>() {
                Node<K, V> cur = root;

                @Override
                public boolean hasNext() {
                    return cur != null;
                }

                @Override
                public Node<K, V> next() {
                    Node<K, V> res = cur;
                    cur = cur.getNext();
                    return res;
                }
            };
        }
    }


    static class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> next;

        Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public Node<K, V> getNext() {
            return next;
        }

        public void setNext(Node<K, V> next) {
            this.next = next;
        }
    }
}