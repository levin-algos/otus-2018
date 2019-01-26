package ru.otus.sort;

import org.openjdk.jmh.annotations.*;
import ru.otus.ChessPlayer;
import sort.Sort;

import java.util.List;

@State(Scope.Benchmark)
public class JMHTest {

    private final static String xml = "standard_rating_list.xml";

    @Param({"10", "100", "1000"})
    private static int entries;

    private static int[] gap = new int[]{701, 301, 132, 57, 23, 10, 4, 1};

    @State(Scope.Thread)
    public static class ChessPlayers {
        List<ChessPlayer> players;

        @Setup(Level.Iteration)
        public void setup() {
            players = ChessPlayer.loadXML(xml, entries);
        }
    }

    @Benchmark
    public List<?> insertionSort(ChessPlayers list) {
        Sort.insertion(list.players, ChessPlayer.RATING_DESC);
        return list.players;
    }

    @Benchmark
    public List<?> shellSort(ChessPlayers list) {
        Sort.shell(list.players, ChessPlayer.RATING_DESC, gap);
        return list.players;
    }
}