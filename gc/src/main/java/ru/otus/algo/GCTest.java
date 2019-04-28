package ru.otus.algo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class GCTest {

    private static final Logger LOGGER = LogManager.getLogger();
    ReferenceGarbageCollector gc = new ReferenceGarbageCollector();

    public static void main(String[] args) {
        GCTest gcTest = new GCTest();
        gcTest.local();
    }

    void local() {
        Node root = References.of("local-root", Node.STATE.ROOT);
        Integer sum = 0;
        gc.link(root, References.of(sum));
        Node cycle = References.of("cycle-root", Node.STATE.ROOT);
        for (int i=0; i< 100; i++) {
            Integer k = i*2;
            gc.link(cycle, References.of(k));
            sum += k;
        }
        gc.remove(cycle);
        gc.gc();
        LOGGER.info(sum);

        gc.remove(root);
        gc.gc();
    }
}