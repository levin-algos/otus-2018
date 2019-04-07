package ru.otus.algo;

public class HashMap<K, V> implements Map<K, V> {

    private Bucket<K, V>[] table;
    private final Hash<K> hash;
    private int size;

    private int BUCKETS_NUM = 16;

    private static final float MAX_LOAD_FACTOR = 0.75f;
    private static final int TREEIFY_THRESHOLD = 32;

    public HashMap(Hash<K> hash) {
        table = new Bucket[BUCKETS_NUM];
        this.hash = hash;
    }

    private int getBucketNum(int hash) {
        return (BUCKETS_NUM - 1) & hash;
    }

    @Override
    public V put(K key, V value) {
        return put(key, value, table);
    }

    private V put(K key, V value, Bucket<K, V>[] table) {
        int h = hash.get(key);
        int i = getBucketNum(h);
        Bucket<K, V> bucket = table[i];

        if (bucket == null) {
            bucket = new ArrayBucket<>();
            table[i] = bucket;
        }

        V prev = bucket.put(key, value, h);
        if (prev == null)
            size++;

        if (bucket.size() == TREEIFY_THRESHOLD && bucket instanceof ArrayBucket) {
            table[i] = treeifyBin((ArrayBucket<K, V>)bucket);
        }

        if (MAX_LOAD_FACTOR < (float) size / BUCKETS_NUM)
            resize();
        return prev;
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
    public V remove(K key) {
        int h = hash.get(key);
        int i = getBucketNum(h);

        Bucket<K, V> bucket = table[i];
        V value = null;
        if (bucket != null) {
            value = bucket.remove(key, h);
            if (value != null)
                size--;
        }
        return value;
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

        V put(K key, V value, int hash);

        V get(K key, int hash);

        V remove(K key, int hash);

        int size();
    }

    static class TreeBucket<K, V> implements Bucket<K, V> {
        private TreeNode<K, V> root;
        private TreeNode<K, V> last;
        private int size;

        @Override
        public V put(K key, V value, int hash) {
            if (root == null) {
                this.root = new TreeNode<>(key, value, hash, null);
                last = this.root;
                insertionFixup(root);
                size=1;
                return null;
            }

            TreeNode<K, V> cur = root, p = null;
            boolean searched = false;
            while (cur!= null) {
                p = cur;
                if (hash == cur.hash) {
                    if (key != null && key.equals(cur.key)) {
                        V prev = cur.value;
                        cur.value = value;
                        return prev;
                    } else {
                        if (!searched) {
                            searched = true;
                            TreeNode<K, V> q;
                            if (cur.left != null &&( q = find(key, hash, cur.left)) != null ||
                                    (cur.right != null && (q = find(key, hash, cur.right)) != null)) {
                                V prev = q.value;
                                q.value = value;
                                return prev;
                            }
                        }
                        cur = cur.left;
                    }
                } else if (hash > cur.hash) {
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
            insertionFixup(node);
            return null;
        }

        @Override
        public V get(K key, int hash) {
            TreeNode<K, V> cur = find(key, hash, root);
            return cur == null? null : cur.value;
        }

        private TreeNode<K, V> find(K key, int hash, TreeNode<K, V> node) {
            TreeNode<K, V> cur = node;
            while (cur !=null) {
                if ( cur.hash > hash) {
                    cur = cur.right;
                } else if ( cur.hash < hash) {
                    cur = cur.left;
                } else if (cur.key.equals(key)) {
                    return cur;
                } else if (cur.left == null) {
                    cur = cur.right;
                } else if (cur.right == null) {
                    cur = cur.left;
                } else {
                    TreeNode<K, V> q;
                    if ((q = find(key, hash, cur.right)) != null)
                        return q;
                    else
                        cur = cur.left;
                }
            }
            return null;
        }

        @Override
        public V remove(K key, int hash) {

            if (key == null)
                throw new IllegalArgumentException();

            TreeNode<K, V> node = find(key, hash, root);
            V prev = null;
            if (node != null) {
                prev = delete(node);
                size--;
            }
            return prev;
        }

        private V delete(TreeNode<K, V> z) {
            V prev = z.value;
            if (z.right != null && z.left != null) {
                TreeNode<K, V> successor = successor(z);
                z.value = successor.value;
                z.key = successor.key;
                z = successor;
            }

            TreeNode<K, V> replacement = z.left != null ? z.left : z.right;

            if (replacement != null) {
                replacement.parent = z.parent;
                if (z.parent == null)
                    root = replacement;
                else if (z == z.parent.left)
                    z.parent.left = replacement;
                else
                    z.parent.right = replacement;

                z.left = z.right = z.parent = null;

                removeFixup(replacement);
            } else if (z.parent == null) {
                prev = root.value;
                root = null;
            } else {

                removeFixup(z);
                if (z == z.parent.left)
                    z.parent.left = null;
                else if (z == z.parent.right)
                    z.parent.right = null;
                z.parent = null;
            }

            size--;
            return prev;
        }

        @Override
        public int size() {
            return size;
        }

        void insertionFixup(TreeNode<K, V> node) {
//        setColor(node, BLACK);

            while (node != null && root != node && colorOf(parentOf(node)) == RED) {
                TreeNode<K, V> grand = parentOf(parentOf(node));
                if (parentOf(node) == leftOf(grand)) {
                    TreeNode<K, V> r = rightOf(grand);
                    if (colorOf(r) == RED) {
                        setColor(parentOf(node), BLACK);
                        setColor(r, BLACK);
                        setColor(grand, RED);
                        node = grand;
                    } else {
                        if (node == rightOf(parentOf(node))) {
                            node = parentOf(node);
                            rotateLeft(node);
                        }
                        setColor(parentOf(node), BLACK);
                        setColor(grand, RED);
                        rotateRight(grand);
                    }
                } else {
                    TreeNode<K, V> l = leftOf(grand);
                    if (colorOf(l) == RED) {
                        setColor(parentOf(node), BLACK);
                        setColor(l, BLACK);
                        setColor(grand, RED);
                        node = grand;
                    } else {
                        if (node == leftOf(parentOf(node))) {
                            node = parentOf(node);
                            rotateRight(node);
                        }
                        setColor(parentOf(node), BLACK);
                        setColor(grand, RED);
                        rotateLeft(grand);
                    }
                }
            }
            setColor(root, BLACK);
        }

        void removeFixup(TreeNode<K, V> x) {
            while (x != root && colorOf(x) == BLACK) {
                if (x == leftOf(parentOf(x))) {
                    TreeNode<K, V> sib = rightOf(parentOf(x));
                    if (colorOf(sib) == RED) {
                        setColor(sib, BLACK);
                        setColor(parentOf(x), RED);
                        rotateLeft(parentOf(x));
                        sib = rightOf(parentOf(x));
                    }
                    if (colorOf(leftOf(sib)) == BLACK && colorOf(rightOf(sib)) == BLACK) {
                        setColor(sib, RED);
                        x = parentOf(x);
                    } else {
                        if (colorOf(rightOf(sib)) == BLACK) {
                            setColor(leftOf(sib), BLACK);
                            setColor(sib, RED);
                            rotateRight(sib);
                            sib = rightOf(parentOf(x));
                        }
                        setColor(sib, colorOf(parentOf(x)));
                        setColor(parentOf(x), BLACK);
                        setColor(rightOf(sib), BLACK);
                        rotateLeft(parentOf(x));
                        x = root;
                    }
                } else {
                    TreeNode<K, V> sib = leftOf(parentOf(x));
                    if (colorOf(sib) == RED) {
                        setColor(sib, BLACK);
                        setColor(parentOf(x), RED);
                        rotateRight(parentOf(x));
                        sib = leftOf(parentOf(x));
                    }
                    if (colorOf(rightOf(sib)) == BLACK && colorOf(leftOf(sib)) == BLACK) {
                        setColor(sib, RED);
                        x = parentOf(x);
                    } else {
                        if (colorOf(leftOf(sib)) == BLACK) {
                            setColor(rightOf(sib), BLACK);
                            setColor(sib, RED);
                            rotateLeft(sib);
                            sib = leftOf(parentOf(x));
                        }
                        setColor(sib, colorOf(parentOf(x)));
                        setColor(parentOf(x), BLACK);
                        setColor(leftOf(sib), BLACK);
                        rotateRight(parentOf(x));
                        x = root;
                    }
                }
            }
            setColor(x, BLACK);
        }

        void rotateRight(TreeNode<K, V> node) {
            if (node != null) {
                TreeNode<K, V> l = node.left;
                node.left = rightOf(l);
                if (l.right != null) l.right.parent = node;
                l.parent = parentOf(node);
                if (node.parent == null)
                    root = l;
                else if (node.parent.right == node)
                    node.parent.right = l;
                else node.parent.left = l;
                l.right = node;
                node.parent = l;
            }
        }

        void rotateLeft(TreeNode<K, V> node) {
            if (node != null) {
                TreeNode<K, V> r = node.right;
                node.right = r.left;
                if (r.left != null)
                    r.left.parent = node;
                r.parent = node.parent;
                if (node.parent == null)
                    root = r;
                else if (node == node.parent.left)
                    node.parent.left = r;
                else node.parent.right = r;
                r.left = node;
                node.parent = r;
            }
        }

        TreeNode<K, V> successor(TreeNode<K, V> it) {
            if (it == null)
                return null;

            else if (it.right != null) {
                TreeNode<K, V> p = it.right;
                while (p.left != null)
                    p = p.left;
                return p;
            } else {
                TreeNode<K, V> p = it.parent;
                TreeNode<K, V> ch = it;

                while (p != null && p.right == ch) {
                    ch = p;
                    p = p.parent;
                }

                return p;
            }
        }

        private static <K, V> boolean colorOf(TreeNode<K, V> node) {
            return node == null ? BLACK : ( node).color;
        }

        private static <K, V> void setColor(TreeNode<K, V> node, boolean color) {
            if (node != null) {
                node.color = color;
            }
        }

        static <K, V> TreeNode<K, V> parentOf(TreeNode<K, V> node) {
            return node == null ? null : node.parent;
        }

        static <K, V> TreeNode<K, V> leftOf(TreeNode<K, V> node) {
            return node == null ? null : node.left;
        }

        static <K, V> TreeNode<K, V> rightOf(TreeNode<K, V> node) {
            return node == null ? null : node.right;
        }
    }

    static class ArrayBucket<K, V> implements Bucket<K, V> {
        private Node<K, V> root;
        private int size;

        @Override
        public V put(K key, V value, int hash) {
            if (root == null) {
                root = new Node<>(key, value, null, hash);
                size++;
                return null;
            }

            Node<K, V> cur = root, p = null;
            while (cur != null) {
                if (cur.getHash() == hash && cur.getKey().equals(key)) {
                    V prev = cur.value;
                    cur.setValue(value);
                    return prev;
                }
                p = cur;
                cur = cur.getNext();
            }

            p.setNext(new Node<>(key, value, null, hash));
            size++;
            return null;
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
        public V remove(K key, int hash) {
            if (root == null)
                return null;

            Node<K, V> cur = root, p = null;
            while (cur != null) {
                if (cur.getHash() == hash && cur.getKey().equals(key)) {
                    break;
                }
                p = cur;
                cur = cur.getNext();
            }

            if (cur != null) {
                V prev;
                if (p == null) {
                    prev = root.value;
                    root = cur.getNext();
                } else {
                    prev = cur.value;
                    p.setNext(cur.getNext());
                }

                size--;
                return prev;
            }

            return null;
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
            this.next = next;
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

    private final static boolean RED = true;
    private final static boolean BLACK = false;

    static class TreeNode<K, V> {
        private K key;
        private V value;
        private TreeNode<K, V> left, right, parent, prev;
        private int hash;
        private boolean color = RED;

        TreeNode(K key, V value, int hash, TreeNode<K, V> parent) {
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.parent = parent;
        }
    }
}