package ru.otus.algo;

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

    private int getBucketNum(int hash) {
        return (BUCKETS_NUM - 1) & hash;
    }

    @Override
    public void put(K key, V value) {
        put(key, value, table);
    }

    private void put(K key, V value, Bucket<K, V>[] table) {
        int h = hash.get(key, BUCKETS_NUM);
        int i = getBucketNum(h);
        Bucket<K, V> bucket = table[i];

        if (bucket == null) {
            bucket = new ArrayBucket<>();
            table[i] = bucket;
        }

        if (bucket.put(key, value, h))
            size++;

        if (MAX_LOAD_FACTOR < (float) size / BUCKETS_NUM)
            resize();
    }

    private void resize() {
        BUCKETS_NUM  = BUCKETS_NUM > 1073741824 ? Integer.MAX_VALUE : BUCKETS_NUM * 2;

        Bucket[] newTable = new Bucket[BUCKETS_NUM];

        size = 0;
        for (Bucket<K, V> buck : table) {
            if (buck == null)
                continue;

            if (buck instanceof Node) {
                Node<K, V> cur = ((Node<K, V>) buck);

                while (cur != null) {
                    put(cur.key, cur.value, newTable);
                    cur = cur.next;
                }

            }
//                for (Node<K, V> node : buck)
//                    put(node.getKey(), node.getValue());
        }
        this.table = newTable;

    }

    public int size() {
        return size;
    }

    @Override
    public void remove(K key) {
        int h = hash.get(key, BUCKETS_NUM);
        int i = getBucketNum(h);

        Bucket<K, V> bucket = table[i];

        if (bucket != null)
            if (bucket.remove(key))
                size++;
    }

    @Override
    public boolean containsKey(K key) {
        int h = hash.get(key, BUCKETS_NUM);
        int i = getBucketNum(h);

        Bucket<K, V> bucket = table[i];

        return bucket != null && bucket.get(key) != null;
    }

    interface Bucket<K, V> {

        boolean put(K key, V value, int hash);

        V get(K key);

        boolean remove(K key);

        int size();
    }

    static class TreeBucket<K, V> implements Bucket<K, V> {
        private TreeNode<K, V> root;
        private int size;

        @Override
        public boolean put(K key, V value, int hash) {
            if (root == null) {
                root = new TreeNode<>(key, value, hash);
                size++;
                return false;
            }


            return false;
        }

        @Override
        public V get(K key) {
            return null;
        }

        @Override
        public boolean remove(K key) {
            return false;
        }

        @Override
        public int size() {
            return size;
        }
    }

    static class ArrayBucket<K, V> implements Bucket<K, V> {
        private Node<K, V> root;
        private int size;

        @Override
        public boolean put(K key, V value, int hash) {
            if (root == null) {
                root = new Node<>(key, value, null, hash);
                size++;
                return true;
            }

            Node<K, V> cur = root, p = null;
            while (cur != null) {
                if (cur.getHash() == hash) {
                    cur.setValue(value);
                    return false;
                }
                p = cur;
                cur = cur.getNext();
            }

            p.setNext(new Node<>(key, value, null, hash));
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
    }


    static class Node<K, V> {
        private K key;
        private V value;
        private int hash;
        private Node<K, V> next;

        Node(K key, V value, Node<K, V> next, int hash) {
            this.key = key;
            this.value = value;
            this.hash = hash;
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

        public int getHash() {
            return hash;
        }
    }

    static class TreeNode<K, V> {
        private K key;
        private V value;
        private TreeNode<K, V> left, right;
        private int hash;

        public TreeNode(K key, V value, int hash) {
            this.key = key;
            this.value = value;
            this.hash = hash;
        }
    }
}