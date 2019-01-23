package ru.otus.sort;

import org.openjdk.jmh.annotations.Benchmark;

public class JMHTest {

    @Benchmark
    public int test1() {
        return 1+1;
    }

}