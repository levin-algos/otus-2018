package ru.otus.algo;

import org.junit.Test;

import javax.rmi.CORBA.Util;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

public class CartesianTreeTest {

    @Test
    public void build() {
        Comparator<Pair<Integer, Integer>> cmp = (o1, o2) -> Integer.compare(o1.getPriority(), o2.getPriority());
        List<Pair<Integer, Integer>> pairs = Arrays.asList(
                new Pair<>(3, 10), new Pair<>(8, 1), new Pair<>(11, 5),
                new Pair<>(12, 17), new Pair<>(14, 19), new Pair<>(17, 15));

        CartesianTree<Integer, Integer> tree = new CartesianTree<>(cmp).build(pairs);

        assertNotNull(tree);
        assertTrue(Utils.isBST(tree, (o1, o2) -> Integer.compare(o1.getValue(), o2.getValue())));

    }
}