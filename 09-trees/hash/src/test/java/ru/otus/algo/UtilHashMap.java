package ru.otus.algo;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilHashMap {

    private static final int MAX = 100;


    static class Entity<V> {
        private V val;

        public Entity(V val) {
            this.val = val;
        }

        @Override
        public int hashCode() {
            return 1;
        }

        @Override
        public String toString() {
            return val.toString();
        }
    }


    @Test
    void main() {
        Map<Entity, String> map = new HashMap<>();
        List<Entity<Integer>> entities = new ArrayList<>();

        for (int i = 0; i < MAX; i++)
        {
            Entity<Integer> k = new Entity<>(i);
            entities.add(k);
            map.put(k, "val"+i);
        }

        for (Entity<Integer> i: entities) {
            String actual = map.get(i);
            assertEquals("val"+i.val, actual);
        }
    }

}
