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

import java.util.*;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class MyBenchmark {

    public static class Entity<T> {
        T val;

        public Entity(T val) {
            this.val = val;
        }

        @Override
        public int hashCode() {
            return 1;
        }
    }

    @State(Scope.Benchmark)
    public static class Data {
        Map<Entity<Integer>, String> data;
        Entity<Integer> last;
        @Param({"1000", "10000", "100000"})
        int size;

        @Setup
        public void setup() {
            data = new HashMap<>();
            for (int i = 0; i < size; i++) {
                Entity<Integer> k = new Entity<>(i);
                data.put(k, "val"+i);
                last = k;
            }
        }
    }

    /*
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
    public String wrongCache(Data data) {
        return data.data.get(data.last);
    }

}
