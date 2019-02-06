package ru.otus.sort;

import org.junit.Test;

import static org.junit.Assert.*;

public class PrefixTreeTest {

    @Test
    public void contains() {
        PrefixTree trie = new PrefixTree(83, 123456789, 80, 82, 85, 89);

        assertTrue(trie.contains(83));
        assertTrue(trie.contains(123456789));
        assertTrue(trie.contains(80));
        assertTrue(trie.contains(82));
        assertTrue(trie.contains(85));
        assertTrue(trie.contains(89));
        assertFalse(trie.contains(8));
        assertFalse(trie.contains(1));
    }

    @Test
    public void traverse() {
        PrefixTree trie = new PrefixTree(83, 80, 82, 85);

        int[] res = trie.traverse();

        int[] expected = new int[]{80, 82, 83, 85};
        assertArrayEquals(expected, res);
    }

    @Test
    public void delete() {
        PrefixTree trie = new PrefixTree(83, 80, 82, 85);

        assertTrue(trie.delete(85));

        int[] traverse = trie.traverse();
        assertArrayEquals(new int[] {80, 82,83}, traverse);

        assertFalse(trie.delete(8));

        assertArrayEquals(new int[]{80,82,83}, trie.traverse());
    }
}