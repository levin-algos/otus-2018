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

package ru.otus.sort;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/*
LinearSorts.countSort           1000     10  avgt   10      2,824 ±    0,002  us/op
LinearSorts.countSort           1000    100  avgt   10      2,202 ±    0,003  us/op
LinearSorts.countSort           1000   1000  avgt   10      2,305 ±    0,003  us/op
LinearSorts.countSort          10000     10  avgt   10     29,324 ±    0,058  us/op
LinearSorts.countSort          10000    100  avgt   10     28,147 ±    0,041  us/op
LinearSorts.countSort          10000   1000  avgt   10     21,806 ±    0,023  us/op
LinearSorts.countSort         100000     10  avgt   10    310,143 ±    0,494  us/op
LinearSorts.countSort         100000    100  avgt   10    308,939 ±    0,507  us/op
LinearSorts.countSort         100000   1000  avgt   10    295,374 ±    0,432  us/op
LinearSorts.countSort        1000000     10  avgt   10   3171,168 ±   22,925  us/op
LinearSorts.countSort        1000000    100  avgt   10   3178,849 ±   17,882  us/op
LinearSorts.countSort        1000000   1000  avgt   10   3165,645 ±   22,161  us/op
LinearSorts.leastRadixSort      1000  10000  avgt   10     25,283 ±    0,045  us/op
LinearSorts.leastRadixSort     10000  10000  avgt   10    254,262 ±    0,378  us/op
LinearSorts.leastRadixSort    100000  10000  avgt   10   2614,808 ±    2,305  us/op
LinearSorts.leastRadixSort   1000000  10000  avgt   10  25883,086 ±  271,780  us/op
LinearSorts.prefixRadixSort     1000  10000  avgt   10     52,095 ±    1,516  us/op
LinearSorts.prefixRadixSort    10000  10000  avgt   10    396,125 ±    1,485  us/op
LinearSorts.prefixRadixSort   100000  10000  avgt   10   2506,483 ±   10,582  us/op
LinearSorts.prefixRadixSort  1000000  10000  avgt   10  19185,993 ± 1649,588  us/op

 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 10, time = 10)
@Measurement(iterations = 10, time = 5)
@Fork(5)
@State(Scope.Benchmark)
public class LinearSorts {

    @State(Scope.Benchmark)
    public static class Data {
        @Param({"1000", "10000", "100000", "1000000"})
        int count;

        @Param({"10", "100", "1000"})
        int max;
        int[] arr;

        @Setup
        public void setup() {
            arr = Common.generateRandom(count, max);
        }
    }


    private CountingSort sort = new CountingSort();

    @Benchmark
    public int[] countSort(Data data) {
        int[] c = Arrays.copyOf(data.arr, data.arr.length);
        sort.sort(data.arr, data.max);
        return c;
    }


    @State(Scope.Benchmark)
    public static class RadixData {
        @Param({"1000", "10000", "100000", "1000000"})
        int count;

        @Param({"10000"})
        int max;
        int[] arr;

        @Setup
        public void setup() {
            arr = Common.generateRandom(count, max);
        }
    }

    private RadixSort radix = new RadixSort();

    @Benchmark
    public int[] leastRadixSort(RadixData data) {
        int[] c = Arrays.copyOf(data.arr, data.arr.length);
        radix.sort(data.arr, RadixSort.Modes.LEAST_SIGN);
        return c;
    }

    @Benchmark
    public int[] prefixRadixSort(RadixData data) {
        int[] c = Arrays.copyOf(data.arr, data.arr.length);
        radix.sort(data.arr, RadixSort.Modes.PREFIX);
        return c;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(LinearSorts.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
