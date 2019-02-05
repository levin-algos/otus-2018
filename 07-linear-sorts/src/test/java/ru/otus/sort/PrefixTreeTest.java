package ru.otus.sort;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PrefixTreeTest {

    @Test
    public void contains() {
        PrefixTree trie = new PrefixTree();

        trie.addAll("083", "0123456789", "080", "082", "085", "089");

        assertTrue(trie.contains("083"));
        assertTrue(trie.contains("0123456789"));
        assertTrue(trie.contains("080"));
        assertTrue(trie.contains("082"));
        assertTrue(trie.contains("085"));
        assertTrue(trie.contains("089"));
        assertFalse(trie.contains("8"));
        assertFalse(trie.contains("1"));
    }

    @Test
    public void traverse() {
        PrefixTree trie = new PrefixTree();

        trie.addAll("083", "080", "082", "085");

        List<String> res = trie.traverse();

        String[] expected = new String[]{"080", "082", "083", "085"};
        assertArrayEquals(expected, res.toArray());
    }

    @Test
    public void delete() {
        PrefixTree trie = new PrefixTree();

        trie.addAll("083", "080", "082", "085");

        trie.delete("085");

        List<String> traverse = trie.traverse();
        assertArrayEquals(new String[] {"080", "082","083"}, traverse.toArray());

        trie.delete("08");

        assertArrayEquals(new String[]{}, trie.traverse().toArray());
    }



}