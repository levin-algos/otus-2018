package ru.otus.algo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.algo.common.UnionFind;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UnionFindTest {

    private UnionFind<Integer> uf;
    @BeforeEach
    void init() {
        uf = new UnionFind<>();
        uf.union(1, 2);
        uf.union(1, 5);
        uf.union(1, 6);
        uf.union(1, 8);
        uf.union(3, 4);
        uf.makeSet(7);
    }

    @Test
    void findTest() {
        assertEquals(uf.find(2), uf.find(1));
        assertEquals(uf.find(5), uf.find(1));
        assertEquals(uf.find(6), uf.find(1));
        assertEquals(uf.find(8), uf.find(1));


        assertEquals(uf.find(3), uf.find(4));

        assertNotEquals(uf.find(7), uf.find(1));
        assertNotEquals(uf.find(7), uf.find(2));
        assertNotEquals(uf.find(7), uf.find(3));
        assertNotEquals(uf.find(7), uf.find(4));
        assertNotEquals(uf.find(7), uf.find(5));
        assertNotEquals(uf.find(7), uf.find(6));
        assertNotEquals(uf.find(7), uf.find(8));
        assertEquals(uf.find(7), uf.find(7));
    }
}
