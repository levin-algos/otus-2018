package ru.otus.algo;

import org.junit.jupiter.api.Test;
import ru.otus.algo.common.Utils;

import java.util.*;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class CartesianTreeTest {

    private final Integer[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static Random rnd = new Random();
    private Function<Integer, Integer> priority = o -> rnd.nextInt();


    @Test
    void buildTest() {
        CartesianTree<Integer> tree = CartesianTree.of(values, priority);

    }

    @Test
    void buildTestNotSorted() {
        Integer[] values = {1, 3, 2, 4, 5, 6, 7, 8, 9, 10};

        CartesianTree<Integer> tree = CartesianTree.of(values, priority);

    }

    @Test
    void sequentialAdd() {
        int height = 12;
        Integer[] values = Utils.generateRandom(1 << height, Integer.MAX_VALUE);
        CartesianTree<Integer> tree = CartesianTree.of(new Integer[0], priority);

        for (Integer v : values) {
            tree.add(v);
        }

        for (Integer v : values) {
            assertTrue(tree.contains(v));
        }

        System.out.println(tree.getMaxHeight());
    }

    @Test
    void sequentialRemove() {
        List<Integer> values = new ArrayList<>(Arrays.asList(Utils.generateRandom(1 << 10, 100)));
        CartesianTree<Integer> tree = CartesianTree.of(new Integer[0], priority);

        for (Integer v : values) {
            tree.add(v);
        }

        for (Integer v : values) {
            assertTrue(tree.contains(v));
        }

        for (int i = 0; i < values.size(); i++) {
            tree.remove(values.get(i));

        }


        for (Integer i: values) {
            assertFalse(tree.contains(i));
        }
    }
}