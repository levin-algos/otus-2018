package ru.otus.sort;

import org.openjdk.jmh.annotations.*;


/**
 * Benchmark              (entries)  (gapSequence)      (method)  Mode  Cnt     Score    Error  Units
 * JMHTest.insertionSort         10            N/A  nearlySorted  avgt   10     0,089 ±  0,002  us/op
 * JMHTest.insertionSort         10            N/A        random  avgt   10     0,104 ±  0,001  us/op
 * JMHTest.insertionSort        100            N/A  nearlySorted  avgt   10     0,865 ±  0,002  us/op
 * JMHTest.insertionSort        100            N/A        random  avgt   10     2,720 ±  0,003  us/op
 * JMHTest.insertionSort        500            N/A  nearlySorted  avgt   10     4,287 ±  0,005  us/op
 * JMHTest.insertionSort        500            N/A        random  avgt   10    45,153 ±  0,099  us/op
 * JMHTest.insertionSort       1000            N/A  nearlySorted  avgt   10     8,314 ±  0,017  us/op
 * JMHTest.insertionSort       1000            N/A        random  avgt   10   179,087 ±  0,419  us/op
 * JMHTest.insertionSort       2000            N/A  nearlySorted  avgt   10    16,331 ±  0,015  us/op
 * JMHTest.insertionSort       2000            N/A        random  avgt   10  1243,504 ±  4,220  us/op
 * JMHTest.insertionSort       5000            N/A  nearlySorted  avgt   10    43,138 ±  0,060  us/op
 * JMHTest.insertionSort       5000            N/A        random  avgt   10  3868,324 ±  9,144  us/op
 * JMHTest.shellSort             10          CIURA  nearlySorted  avgt   10     0,142 ±  0,001  us/op
 * JMHTest.shellSort             10          CIURA        random  avgt   10     0,216 ±  0,001  us/op
 * JMHTest.shellSort             10      SEDGEWICK  nearlySorted  avgt   10     0,140 ±  0,001  us/op
 * JMHTest.shellSort             10      SEDGEWICK        random  avgt   10     0,148 ±  0,001  us/op
 * JMHTest.shellSort             10        HIBBARD  nearlySorted  avgt   10     0,151 ±  0,001  us/op
 * JMHTest.shellSort             10        HIBBARD        random  avgt   10     0,226 ±  0,001  us/op
 * JMHTest.shellSort            100          CIURA  nearlySorted  avgt   10     2,102 ±  0,003  us/op
 * JMHTest.shellSort            100          CIURA        random  avgt   10     4,992 ±  0,007  us/op
 * JMHTest.shellSort            100      SEDGEWICK  nearlySorted  avgt   10     1,915 ±  0,004  us/op
 * JMHTest.shellSort            100      SEDGEWICK        random  avgt   10     4,490 ±  0,006  us/op
 * JMHTest.shellSort            100        HIBBARD  nearlySorted  avgt   10     2,319 ±  0,004  us/op
 * JMHTest.shellSort            100        HIBBARD        random  avgt   10     5,778 ±  0,013  us/op
 * JMHTest.shellSort            500          CIURA  nearlySorted  avgt   10     7,448 ±  0,015  us/op
 * JMHTest.shellSort            500          CIURA        random  avgt   10    37,877 ±  0,126  us/op
 * JMHTest.shellSort            500      SEDGEWICK  nearlySorted  avgt   10     8,367 ±  0,015  us/op
 * JMHTest.shellSort            500      SEDGEWICK        random  avgt   10    36,130 ±  0,081  us/op
 * JMHTest.shellSort            500        HIBBARD  nearlySorted  avgt   10    16,899 ±  0,032  us/op
 * JMHTest.shellSort            500        HIBBARD        random  avgt   10    31,188 ±  0,058  us/op
 * JMHTest.shellSort           1000          CIURA  nearlySorted  avgt   10    15,800 ±  0,023  us/op
 * JMHTest.shellSort           1000          CIURA        random  avgt   10    87,349 ±  0,116  us/op
 * JMHTest.shellSort           1000      SEDGEWICK  nearlySorted  avgt   10    28,977 ±  0,055  us/op
 * JMHTest.shellSort           1000      SEDGEWICK        random  avgt   10    81,932 ±  0,165  us/op
 * JMHTest.shellSort           1000        HIBBARD  nearlySorted  avgt   10    37,937 ±  0,057  us/op
 * JMHTest.shellSort           1000        HIBBARD        random  avgt   10    98,324 ±  0,246  us/op
 * JMHTest.shellSort           2000          CIURA  nearlySorted  avgt   10    41,919 ±  0,106  us/op
 * JMHTest.shellSort           2000          CIURA        random  avgt   10   206,612 ±  0,294  us/op
 * JMHTest.shellSort           2000      SEDGEWICK  nearlySorted  avgt   10    40,206 ±  0,100  us/op
 * JMHTest.shellSort           2000      SEDGEWICK        random  avgt   10   143,653 ±  0,281  us/op
 * JMHTest.shellSort           2000        HIBBARD  nearlySorted  avgt   10    43,647 ±  0,067  us/op
 * JMHTest.shellSort           2000        HIBBARD        random  avgt   10   167,599 ±  0,160  us/op
 * JMHTest.shellSort           5000          CIURA  nearlySorted  avgt   10   171,545 ±  0,226  us/op
 * JMHTest.shellSort           5000          CIURA        random  avgt   10   531,776 ± 52,409  us/op
 * JMHTest.shellSort           5000      SEDGEWICK  nearlySorted  avgt   10   166,157 ±  0,085  us/op
 * JMHTest.shellSort           5000      SEDGEWICK        random  avgt   10   455,201 ± 52,946  us/op
 * JMHTest.shellSort           5000        HIBBARD  nearlySorted  avgt   10   241,032 ±  0,290  us/op
 * JMHTest.shellSort           5000        HIBBARD        random  avgt   10   641,537 ±  1,474  us/op
 */
@SuppressWarnings("ALL")
@State(Scope.Benchmark)
public class JMHTest {

    private final static String xml = "standard_rating_list.xml";

    @Param({"10", "100", "500", "1000", "2000", "5000"})
    private static int entries;

    @State(Scope.Thread)
    public static class IntArray {
        Integer[] arr;
        @Param({"nearlySorted", "random"})
        String method;

        @Setup(Level.Invocation)
        public void setup() {
            if ("random".equals(method)) {
                arr = Generator.generateRandom(entries, entries/2);
            } else if ("nearlySorted".equals(method)) {
                arr = Generator.generateNearlySorted(entries, 5);
            }
        }
    }

    @Benchmark
    public Integer[] insertionSort(IntArray list) {
        Sort.insertion(list.arr, Integer::compare);
        return list.arr;
    }

    @State(Scope.Thread)
    public static class GapSeq {
        int[] seq;

        @Param({"CIURA", "SEDGEWICK", "HIBBARD"})
        String gapSequence;

        @Setup(Level.Trial)
        public void setup() {
            Sort.GapSequence gap;
            if ("CIURA".equals(gapSequence))
                gap = Sort.GapSequence.CIURA;
            else if ("SEDGEWICK".equals(gapSequence)) {
                gap = Sort.GapSequence.SEDGEWICK;
            } else {
                gap = Sort.GapSequence.HIBBARD;
            }
            seq = Sort.generateGapSequence(gap, entries);
        }
    }

    @Benchmark
    public Integer[] shellSort(IntArray list, GapSeq gap) {
        Sort.shell(list.arr, Integer::compareTo, gap.seq);
        return list.arr;
    }
}