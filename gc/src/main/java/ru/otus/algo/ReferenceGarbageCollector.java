package ru.otus.algo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
To construct reference counter gc we need to build DAG:
1. When object A refers to object B, we add edge between A and B vertex in graph.
2. When object A release link to B object, we remove edge between A and B vertex in graph
3. When gc required we remove all the vertexes with 0 in-depth. Removing vertexes can lead us
   to situation where graph has new 0 in-depth vertexes.
4. Static and local variables need to have there own roots.
*/
public class ReferenceGarbageCollector {

    private static final Logger LOGGER = LogManager.getLogger();
    private final Graph graph = new Graph();

    void link(Node from, Node to) {
        LOGGER.info(String.format("Connecting %s and %s", from, to));
        graph.connect(from, to);
    }

    void gc() {
        LOGGER.info("staring gc:");
        while (true) {
            int zeroIns = 0;
            List<Node> refs = new ArrayList<>();
            for (Node ref : graph.getWithZeroInDegree()) {
                if (Node.STATE.COMMON == ref.getState()) {
                    LOGGER.info(String.format("Zero reference to %s. Removing.", ref));
                    zeroIns++;
                    refs.add(ref);
                }
            }
            refs.forEach(o -> {
                References.remove(o);
                graph.remove(o);});

            if (zeroIns == 0)
                break;
        }
    }

    void remove(Node ref) {
        Objects.requireNonNull(ref);

        LOGGER.info(String.format("removing vertex '%s'", ref));
        graph.remove(ref);
    }
}