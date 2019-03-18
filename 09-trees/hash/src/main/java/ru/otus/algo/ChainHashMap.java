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
        if (BUCKETS_NUM < 1073741823 && MAX_LOAD_FACTOR < (float)size / BUCKETS_NUM) {
            BUCKETS_NUM *= 2;
            Bucket<K, V>[] oldTable = this.table;
            this.table = new Bucket[BUCKETS_NUM];
            for (Bucket<K, V> buck: oldTable) {
                if (buck == null)
                    continue;

                for (Node<K, V> node: buck)
                    put(node.getKey(), node.getValue());
            }
        }
    }

    @Override
    public void remove(K key) {
        int i = getBucketNum(key);

        Bucket<K, V> bucket = table[i];

        if (bucket !=null)
            if (bucket.remove(key))
                size++;
    }

    @Override
    public boolean containsKey(K key) {
        int i = getBucketNum(key);

        Bucket<K, V> bucket = table[i];

        return bucket != null && bucket.get(key) != null;
    }

    interface Bucket<K,V> extends Iterable<Node<K, V>> {

        boolean put(K key, V value);
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
        public boolean put(K key, V value) {
            if (list == null)
                createList();

            for (Node<K, V> node: list) {
                if (node.getKey().equals(key)) {
                    node.setValue(value);
                    return false;
                }
            }

            list.add(new Node<>(key, value));
            return true;
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

        Node(K key, V value) {
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
