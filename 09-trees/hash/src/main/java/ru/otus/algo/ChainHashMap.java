package ru.otus.algo;

public class ChainHashMap<K, V> implements Map<K, V> {

    private Bucket<K, V>[] table;
    private final Hash<K> hash;
    private int size;

    private int BUCKETS_NUM = 16;

    private static final float MAX_LOAD_FACTOR = 0.75f;
    private static final int TREEIFY_THRESHOLD = 8;
    private static final int UNTREEIFY_THRESHOLD = 6;

    public ChainHashMap(Hash<K> hash) {
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
        int h = hash.get(key);
        int i = getBucketNum(h);
        Bucket<K, V> bucket = table[i];

        if (bucket == null) {
            bucket = new ArrayBucket<>();
            table[i] = bucket;
        }
        if (bucket.put(key, value, h))
            size++;

        if (bucket.size() == TREEIFY_THRESHOLD && bucket instanceof ArrayBucket) {
            table[i] = treeifyBin((ArrayBucket<K, V>)bucket);
        }

        if (MAX_LOAD_FACTOR < (float) size / BUCKETS_NUM)
            resize();
    }

    private void resize() {
        BUCKETS_NUM = BUCKETS_NUM > 1073741824 ? Integer.MAX_VALUE : BUCKETS_NUM * 2;

        Bucket[] newTable = new Bucket[BUCKETS_NUM];

        size = 0;
        for (Bucket<K, V> buck : table) {
            if (buck == null)
                continue;

            if (buck instanceof ArrayBucket) {
                Node<K, V> cur = ((ArrayBucket<K, V>) buck).root;

                while (cur != null) {
                    put(cur.key, cur.value, newTable);
                    cur = cur.next;
                }
            } else if (buck instanceof TreeBucket) {
                TreeNode<K, V> cur = ((TreeBucket<K, V>) buck).last;

                while (cur != null) {
                    put(cur.key, cur.value, newTable);
                    cur = cur.prev;
                }
            }
        }
        this.table = newTable;
    }

    private TreeBucket<K, V> treeifyBin(ArrayBucket<K, V> bin) {
        TreeBucket<K, V> tree = new TreeBucket<>();

        Node<K, V> node = bin.root;
        while (node != null) {
            tree.put(node.key, node.value, node.hash);
            node = node.next;
        }

        return tree;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void remove(K key) {
        int h = hash.get(key);
        int i = getBucketNum(h);

        Bucket<K, V> bucket = table[i];

        if (bucket != null)
            if (bucket.remove(key, h))
                size--;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        int h = hash.get(key);
        int i = getBucketNum(h);

        Bucket<K, V> bucket = table[i];

        if (bucket == null)
            return null;

        return bucket.get(key, h);
    }


    interface Bucket<K, V> {

        boolean put(K key, V value, int hash);

        V get(K key, int hash);

        boolean remove(K key, int hash);

        int size();
    }

    static class TreeBucket<K, V> implements Bucket<K, V> {
        private TreeNode<K, V> root;
        private TreeNode<K, V> last;
        private int size;

        @Override
        public boolean put(K key, V value, int hash) {
            if (root == null) {
                this.root = new TreeNode<>(key, value, hash, null);
                last = this.root;
                size++;
                return true;
            }

            TreeNode<K, V> cur = root, p = null;
            while (cur!= null) {
                p = cur;
                if (hash > cur.hash) {
                    cur = cur.right;
                } else {
                    cur = cur.left;
                }
            }

            TreeNode<K, V> node = new TreeNode<>(key, value, hash, p);
            node.prev = last;
            last = node;
            if (p.hash > hash)
                p.right = node;
            else
                p.left = node;

            size++;
            return true;
        }

        @Override
        public V get(K key, int hash) {
            TreeNode<K, V> cur = find(key, hash);
            return cur == null? null : cur.value;
        }

        private TreeNode<K, V> find(K key, int hash) {
            TreeNode<K, V> cur = root;

            while (cur!= null) {
                if (cur.hash == hash && cur.key.equals(key)) {
                    return cur;
                } else if ( cur.hash > hash) {
                    cur = cur.right;
                } else
                    cur = cur.left;
            }

            return null;
        }

        @Override
        public boolean remove(K key, int hash) {
            TreeNode<K, V> cur = find(key, hash);

            if (cur == null) return false;

            if (cur.left != null && cur.right != null) {
                TreeNode<K, V> max = getMax(cur.left);
                cur.key = max.key;
                cur.value = max.value;
                cur.hash = max.hash;

                cur = max;
            }

            TreeNode<K, V> replacement = cur.left == null ? cur.right : cur.left;

            if (replacement != null) {
                replacement.parent = cur.parent;
                if (cur.parent == null)
                    root = replacement;
                else if (cur == cur.parent.left)
                    cur.parent.left = replacement;
                else
                    cur.parent.right = replacement;

                cur.left = cur.right = cur.parent = null;

            } else if (cur.parent == null) {
                root = null;
            } else {
                if (cur.parent != null) {
                    if (cur == cur.parent.left)
                        cur.parent.left = null;
                    else if (cur == cur.parent.right)
                        cur.parent.right = null;
                    cur.parent = null;
                }
            }

            size--;
            return true;
        }


        private TreeNode<K, V> getMax(TreeNode<K, V> root) {
            TreeNode<K, V> cur = root;
            while (cur.right != null)
                cur = cur.right;

            return cur;
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
                if (cur.getHash() == hash && cur.getKey().equals(key)) {
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
        public V get(K key, int hash) {
            if (root == null)
                return null;


            Node<K, V> cur = root;
            while (cur != null) {
                if (cur.getHash() == hash && cur.getKey().equals(key)) {
                    return cur.getValue();
                }
                cur = cur.getNext();
            }
            return null;
        }

        @Override
        public boolean remove(K key, int hash) {
            if (root == null)
                return false;

            Node<K, V> cur = root, p = null;
            while (cur != null) {
                if (cur.getHash() == hash && cur.getKey().equals(key)) {
                    break;
                }
                p = cur;
                cur = cur.getNext();
            }

            if (cur != null) {
                if (p == null) {
                    root = cur.getNext();
                } else
                    p.setNext(cur.getNext());

                size--;
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
        private TreeNode<K, V> left, right, parent, prev;
        private int hash;

        public TreeNode(K key, V value, int hash, TreeNode<K, V> parent) {
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.parent = parent;
        }
    }
}