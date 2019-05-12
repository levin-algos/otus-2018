package ru.otus.algo;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PrefixTreeTest {

    @Test
    void contains() {
        PrefixTree trie = new PrefixTree("83", "123456789", "80", "82", "85", "89");

        assertTrue(trie.contains("83"));
        assertTrue(trie.contains("123456789"));
        assertTrue(trie.contains("80"));
        assertTrue(trie.contains("82"));
        assertTrue(trie.contains("85"));
        assertTrue(trie.contains("89"));
        assertFalse(trie.contains("8"));
        assertFalse(trie.contains("1"));
    }

    @Test
    void traverse() {
        PrefixTree trie = new PrefixTree("83", "80", "82", "85");

        Set<String> res = trie.traverse();

        Set<String> expected = new HashSet<>();
        Collections.addAll(expected, "80", "82", "83", "85");
        assertIterableEquals(expected, res);
    }

    @Test
    void delete() {
        PrefixTree trie = new PrefixTree("83", "80", "82", "85");

        assertTrue(trie.delete("85"));
        assertFalse(trie.isEmpty());

        Set<String> traverse = trie.traverse();
        Set<String> expected = new HashSet<>();
        Collections.addAll(expected, "83", "80", "82");
        assertIterableEquals(expected, traverse);

        assertTrue(trie.delete("8"));
        assertTrue(trie.isEmpty());
        assertIterableEquals(Collections.emptySet(), trie.traverse());
    }

    @Test
    void unionEmptyWithTrie() {
        PrefixTree emptyTrie = new PrefixTree();
        PrefixTree trie = new PrefixTree("83", "80", "82", "85");

        PrefixTree united = new PrefixTree(emptyTrie, trie);
        Set<String> expected = new HashSet<>();
        Collections.addAll(expected, "83", "80", "82", "85");

        assertIterableEquals(expected, united.traverse());
        united = new PrefixTree(trie, emptyTrie);
        assertIterableEquals(expected, united.traverse());
    }

    @Test
    void unionTwoTries() {
        PrefixTree trie1 = new PrefixTree("83", "80", "82", "85");
        PrefixTree trie2 = new PrefixTree("93", "80", "92", "85");

        PrefixTree tree = new PrefixTree(trie1, trie2);
        Set<String> expected = new HashSet<>();
        Collections.addAll(expected, "80", "82", "83", "85", "93", "92");
        assertIterableEquals(expected, tree.traverse());
        tree = new PrefixTree(trie2, trie1);
        assertIterableEquals(expected, tree.traverse());

    }

    @Test
    void prefixLinkTest() {
        // prefix tree from https://ru.wikipedia.org/wiki/%D0%90%D0%BB%D0%B3%D0%BE%D1%80%D0%B8%D1%82%D0%BC_%D0%90%D1%85%D0%BE_%E2%80%94_%D0%9A%D0%BE%D1%80%D0%B0%D1%81%D0%B8%D0%BA

        PrefixTree tree = new PrefixTree("a", "ab", "bc", "bca", "c", "caa");

        assertEquals("b", tree.prefixFor("ab"));
        assertEquals("c", tree.prefixFor("bc"));
        assertEquals("a", tree.prefixFor("ca"));
        assertEquals("", tree.prefixFor("a"));
        assertEquals("", tree.prefixFor("b"));
        assertEquals("", tree.prefixFor("c"));
        assertEquals("a", tree.prefixFor("caa"));
        assertEquals("ca", tree.prefixFor("bca"));
    }
}