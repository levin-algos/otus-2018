package ru.otus.algo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChainHashMap<K, V> implements Map<K, V> {

    private Bucket<K, V>[] table;
    private final Hash<K> hash;

    private final int BUCKETS_NUM;

    public ChainHashMap(Hash<K> hash, int bucketSize) {
        BUCKETS_NUM = bucketSize;
        table = new Bucket[BUCKETS_NUM];
        this.hash = hash;
    }

    private int getBucketNum(K key) {
        int h = hash.get(key);
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

        bucket.put(key, value);
    }

    @Override
    public void remove(K key) {
        int i = getBucketNum(key);

        Bucket<K, V> bucket = table[i];

        if (bucket !=null)
            bucket.remove(key);
    }

    @Override
    public boolean containsKey(K key) {
        int i = getBucketNum(key);

        Bucket<K, V> bucket = table[i];

        return bucket != null && bucket.get(key) != null;
    }


    interface Bucket<K,V> extends Iterable<Node<K, V>> {

        void put(K key, V value);
        V get(K key);
        boolean remove(K key);
        int size();
    }

    class ArrayBucket<K, V> implements Bucket<K,V> {
        private List<Node<K,V>> list;

        private void createList() {
            list = new ArrayList<>();
        }

        @Override
        public void put(K key, V value) {
            if (list == null)
                createList();

            for (Node<K, V> node: list) {
                if (node.getKey().equals(key))
                    node.setValue(value);
                    return;
            }

            list.add(new Node<>(key, value));
        }

        @Override
        public V get(K key) {
            if (list == null)
                return null;

            for (Node<K, V> node: list) {
                if (node.getKey().equals(key))
                    return node.getValue();
            }
            return null;
        }

        @Override
        public boolean remove(K key) {
            if (list == null)
                return false;

            for (Node<K, V> node: list) {
                if (node.getKey().equals(key)) {
                    list.remove(node);
                    return true;
                }
            }
            return false;
        }

        @Override
        public int size() {
            return list == null ? 0 : list.size();
        }

        @Override
        public Iterator<Node<K, V>> iterator() {
            return list.iterator();
        }
    }

    static class Node<K, V> {
        private final K key;
        private V value;

        public Node(K key, V value) {
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
    }

}
