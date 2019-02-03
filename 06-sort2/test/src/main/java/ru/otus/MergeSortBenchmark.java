/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package ru.otus;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import ru.otus.sort.Generator;
import ru.otus.sort.MergeSort;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/*
Benchmark                     (size)  Mode  Cnt          Score         Error  Units
MergeSortBenchmark.insertion     100  avgt   50        725,029 ?      12,042  ns/op
MergeSortBenchmark.insertion    1000  avgt   50      64039,514 ?     605,919  ns/op
MergeSortBenchmark.insertion   10000  avgt   50    5495999,222 ?   23281,051  ns/op
MergeSortBenchmark.insertion  100000  avgt   50  821760108,795 ? 4029916,694  ns/op

MergeSortBenchmark.mergeSort     100  avgt   50        752,088 ?       8,361  ns/op
MergeSortBenchmark.mergeSort    1000  avgt   50      24684,295 ?     827,570  ns/op
MergeSortBenchmark.mergeSort   10000  avgt   50     507606,222 ?    1724,349  ns/op
MergeSortBenchmark.mergeSort  100000  avgt   50    6864489,883 ?    5324,407  ns/op


searching threshold
Benchmark                     (size)  (threshold)  Mode  Cnt     Score    Error  Units
MergeSortBenchmark.mergeSort  100000           10  avgt   10  8227,352 ±  5,990  us/op
MergeSortBenchmark.mergeSort  100000           20  avgt   10  7689,110 ± 28,808  us/op
MergeSortBenchmark.mergeSort  100000           30  avgt   10  6986,244 ± 14,900  us/op
MergeSortBenchmark.mergeSort  100000           40  avgt   10  6991,124 ± 11,032  us/op
MergeSortBenchmark.mergeSort  100000           50  avgt   10  7206,264 ± 18,037  us/op
MergeSortBenchmark.mergeSort  100000           70  avgt   10  7161,672 ±  4,021  us/op
MergeSortBenchmark.mergeSort  100000           80  avgt   10  7169,626 ±  5,588  us/op
MergeSortBenchmark.mergeSort  100000           90  avgt   10  6991,654 ±  2,924  us/op
MergeSortBenchmark.mergeSort  100000          100  avgt   10  5636,142 ±  5,091  us/op
MergeSortBenchmark.mergeSort  100000          110  avgt   10  5588,136 ±  3,530  us/op
MergeSortBenchmark.mergeSort  100000          120  avgt   10  5589,313 ±  4,202  us/op
MergeSortBenchmark.mergeSort  100000          130  avgt   10  5622,352 ± 48,089  us/op
MergeSortBenchmark.mergeSort  100000          140  avgt   10  5629,952 ± 28,864  us/op
MergeSortBenchmark.mergeSort  100000          150  avgt   10  5626,301 ± 21,633  us/op
MergeSortBenchmark.mergeSort  100000          500  avgt   10  7213,087 ±  6,517  us/op

Benchmark                     (forkThres)  (insertThres)  (size)  Mode  Cnt    Score    Error  Units
MergeSortBenchmark.mergeSort            1            120      10  avgt   10    0,025 ±  0,001  us/op
MergeSortBenchmark.mergeSort            1            120     100  avgt   10    0,787 ±  0,017  us/op
MergeSortBenchmark.mergeSort            1            120    1000  avgt   10   27,899 ±  0,099  us/op
MergeSortBenchmark.mergeSort            1            120   10000  avgt   10  185,715 ±  0,622  us/op
MergeSortBenchmark.mergeSort           10            120      10  avgt   10    0,029 ±  0,001  us/op
MergeSortBenchmark.mergeSort           10            120     100  avgt   10    0,723 ±  0,014  us/op
MergeSortBenchmark.mergeSort           10            120    1000  avgt   10   25,562 ±  0,276  us/op
MergeSortBenchmark.mergeSort           10            120   10000  avgt   10  177,487 ±  0,742  us/op
MergeSortBenchmark.mergeSort          100            120      10  avgt   10    0,022 ±  0,001  us/op
MergeSortBenchmark.mergeSort          100            120     100  avgt   10    0,708 ±  0,008  us/op
MergeSortBenchmark.mergeSort          100            120    1000  avgt   10   23,401 ±  0,189  us/op
MergeSortBenchmark.mergeSort          100            120   10000  avgt   10  179,476 ±  0,648  us/op
MergeSortBenchmark.mergeSort         1000            120      10  avgt   10    0,030 ±  0,001  us/op
MergeSortBenchmark.mergeSort         1000            120     100  avgt   10    0,789 ±  0,009  us/op
MergeSortBenchmark.mergeSort         1000            120    1000  avgt   10   19,083 ±  0,304  us/op
MergeSortBenchmark.mergeSort         1000            120   10000  avgt   10  183,336 ±  4,309  us/op
MergeSortBenchmark.mergeSort        10000            120      10  avgt   10    0,023 ±  0,001  us/op
MergeSortBenchmark.mergeSort        10000            120     100  avgt   10    0,722 ±  0,003  us/op
MergeSortBenchmark.mergeSort        10000            120    1000  avgt   10   18,001 ±  0,224  us/op
MergeSortBenchmark.mergeSort        10000            120   10000  avgt   10  250,271 ±  2,699  us/op
MergeSortBenchmark.mergeSort       100000            120      10  avgt   10    0,020 ±  0,001  us/op
MergeSortBenchmark.mergeSort       100000            120     100  avgt   10    0,730 ±  0,004  us/op
MergeSortBenchmark.mergeSort       100000            120    1000  avgt   10   18,160 ±  0,372  us/op
MergeSortBenchmark.mergeSort       100000            120   10000  avgt   10  525,210 ±  0,918  us/op
 */

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 10, time = 10)
@Measurement(iterations = 10, time = 10)
@Fork(5)
public class MergeSortBenchmark {

    @State(Scope.Benchmark)
    public static class IntRandomArray {

        @Param({"10000", "100000", "1000000"})
        private static int size;

        int[] array;

        @Setup
        public void setup() {
            array = Generator.generateRandom(size, size/2);
        }
    }

    @State(Scope.Benchmark)
    public static class MergeSortClass {

        MergeSort sorter;

//        @Param({"70", "80", "90", "110", "120", "130", "140", "150"})
//        @Param({"10", "20", "30", "40", "50", "100", "500"})
        @Param({"120"})
        int insertThres;

        @Param({"1000"})
        int forkThres;

        @Setup
        public void setup() {
            sorter = new MergeSort(insertThres, forkThres);
        }
    }

    @Benchmark
    public int[] mergeSort(MergeSortClass sorter, IntRandomArray arr) {
        int[] ints = Arrays.copyOf(arr.array, arr.array.length);
        sorter.sorter.mergeSort(ints);
        return ints;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(MergeSortBenchmark.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();

    }

}
