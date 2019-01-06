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
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
/*
Benchmark                    (length)  Mode  Cnt   Score   Error  Units
DynamicArrayBench.bArrayAdd       100  avgt    5   8,867 ± 0,058  us/op
DynamicArrayBench.bArrayAdd      1000  avgt    5   8,936 ± 0,045  us/op
DynamicArrayBench.bArrayAdd     10000  avgt    5   9,704 ± 0,127  us/op
DynamicArrayBench.bArrayAdd    100000  avgt    5  20,786 ± 0,244  us/op
DynamicArrayBench.dArrayAdd       100  avgt    5  13,070 ± 0,202  us/op
DynamicArrayBench.dArrayAdd      1000  avgt    5  13,289 ± 0,203  us/op
DynamicArrayBench.dArrayAdd     10000  avgt    5  14,839 ± 0,324  us/op
DynamicArrayBench.dArrayAdd    100000  avgt    5  38,505 ± 1,441  us/op
DynamicArrayBench.iArrayAdd       100  avgt    5   0,247 ± 0,005  us/op
DynamicArrayBench.iArrayAdd      1000  avgt    5   0,248 ± 0,004  us/op
DynamicArrayBench.iArrayAdd     10000  avgt    5   0,248 ± 0,005  us/op
DynamicArrayBench.iArrayAdd    100000  avgt    5   0,252 ± 0,008  us/op
*/

@State(Scope.Thread)
public class DynamicArrayBench {

    @Param({"100", "1000", "10000", "100000"})
    private static int length;

    @State(Scope.Thread)
    public static class Arr {

        DArray<Integer> array;

        @Setup(Level.Iteration)
        public void setup() {
            array = new DArray<>();
            for (int i=0; i< length; i++) {
                array.add(i, i);
            }
        }
    }

    @State(Scope.Thread)
    public static class ImpArr {
        BArray<Integer> array;

        @Setup(Level.Iteration)
        public void setup() {
            array = new BArray<>();
            for (int i=0; i< length; i++) {
                array.add(i, i);
            }
        }
    }

    @State(Scope.Thread)
    public static class IArr {
        IArray<Integer> array;

        @Setup(Level.Iteration)
        public void setup() {
            array = new IArray<>();
            for (int i=0; i< length; i++) {
                array.add(i, i);
            }
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 5, time = 1)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void dArrayAdd(Arr arr) {
        arr.array.add(0, 10);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 5, time = 1)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void bArrayAdd(ImpArr arr) {
        arr.array.add(0, 10);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 5, time = 1)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void iArrayAdd(IArr arr) {
        arr.array.add(0, 10);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(DynamicArrayBench.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();

    }

}
