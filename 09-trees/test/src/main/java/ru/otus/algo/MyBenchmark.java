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

package ru.otus.algo;

import org.openjdk.jmh.annotations.*;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@State(Scope.Benchmark)
public class MyBenchmark {

    @State(Scope.Benchmark)
    public static class Data1 {
        Integer[] data;
        @Param({"100000"})
        int size;
        Integer last;

        Map<Integer, String> map = new HashMap<>(key -> 1);

        @Setup
        public void setup() {

            data = IntStream.range(0, size).boxed().toArray(Integer[]::new);
            for (Integer i: data) {
                map.put(i, "" + i);
                last = i;
            }
        }
    }


    static class WrongHash {
        private Integer i;

        public WrongHash(Integer i) {
            this.i = i;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WrongHash wrongHash = (WrongHash) o;
            return Objects.equals(i, wrongHash.i);
        }

        @Override
        public int hashCode() {
            return 1;
        }
    }

    @State(Scope.Benchmark)
    public static class UtilData {
        WrongHash[] data;
        @Param({"1000", "10000", "100000"})
        int size;
        WrongHash last;

        java.util.Map<WrongHash, String> map = new java.util.HashMap<>();

        @Setup
        public void setup() {
            int[] data = IntStream.range(0, size).toArray();
            for (int i: data) {
                WrongHash key = new WrongHash(i);
                map.put(key, "" + i);
                last = key;
            }
        }
    }


    /*

Benchmark                      (size)  Mode  Cnt  Score    Error  Units
MyBenchmark.wrongCache           1000  avgt   25  0,002 ?  0,001  ms/op
MyBenchmark.wrongCache:·stack    1000  avgt         NaN             ---
MyBenchmark.wrongCache          10000  avgt   25  0,040 ?  0,001  ms/op
MyBenchmark.wrongCache:·stack   10000  avgt         NaN             ---
MyBenchmark.wrongCache         100000  avgt   25  1,032 ?  0,044  ms/op
MyBenchmark.wrongCache:·stack  100000  avgt         NaN             ---

queue
    Benchmark                      (size)  Mode  Cnt  Score    Error  Units
MyBenchmark.wrongCache           1000  avgt   25  0,009 ?  0,001  ms/op
MyBenchmark.wrongCache:·stack    1000  avgt         NaN             ---
MyBenchmark.wrongCache          10000  avgt   25  0,081 ?  0,003  ms/op
MyBenchmark.wrongCache:·stack   10000  avgt         NaN             ---
MyBenchmark.wrongCache         100000  avgt   25  1,580 ?  0,058  ms/op
MyBenchmark.wrongCache:·stack  100000  avgt         NaN             ---


    java.util
    Benchmark                      (size)  Mode  Cnt  Score   Error  Units
MyBenchmark.wrongCache           1000  avgt    5  0.006 ± 0.001  ms/op
MyBenchmark.wrongCache:·stack    1000  avgt         NaN            ---
MyBenchmark.wrongCache          10000  avgt    5  0.116 ± 0.057  ms/op
MyBenchmark.wrongCache:·stack   10000  avgt         NaN            ---
MyBenchmark.wrongCache         100000  avgt    5  2.843 ± 0.063  ms/op
MyBenchmark.wrongCache:·stack  100000  avgt         NaN            ---
     */
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String wrongCache(Data1 data) {
        return data.map.get(data.last);
    }


    /*
    Benchmark                          (size)  Mode  Cnt  Score    Error  Units
MyBenchmark.wrongCache               1000  avgt   25  0,002 ?  0,001  ms/op
MyBenchmark.wrongCache:·stack        1000  avgt         NaN             ---
MyBenchmark.wrongCache              10000  avgt   25  0,038 ?  0,001  ms/op
MyBenchmark.wrongCache:·stack       10000  avgt         NaN             ---
MyBenchmark.wrongCache              20000  avgt   25  0,079 ?  0,002  ms/op
MyBenchmark.wrongCache:·stack       20000  avgt         NaN             ---
MyBenchmark.wrongCache              50000  avgt   25  0,167 ?  0,005  ms/op
MyBenchmark.wrongCache:·stack       50000  avgt         NaN             ---
MyBenchmark.wrongUtilCache           1000  avgt   25  0,007 ?  0,002  ms/op
MyBenchmark.wrongUtilCache:·stack    1000  avgt         NaN             ---
MyBenchmark.wrongUtilCache          10000  avgt   25  0,107 ?  0,025  ms/op
MyBenchmark.wrongUtilCache:·stack   10000  avgt         NaN             ---
MyBenchmark.wrongUtilCache         100000  avgt   25  0,695 ?  0,260  ms/op
MyBenchmark.wrongUtilCache:·stack  100000  avgt         NaN             ---

     */

//    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String wrongUtilCache(UtilData data) {
        return data.map.get(data.last);
    }

}
