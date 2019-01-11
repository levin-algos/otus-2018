package ru.otus.algo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {


    @ParameterizedTest
    @CsvFileSource(resources = "/fibonacci_seq", delimiter = ' ')
    void testFibonacci(int input, String res) {

//        assertEquals(res, Fibonacci.definition(input).toString());
//        assertEquals(res, Fibonacci.matrix(input).toString());
        assertEquals(res, Fibonacci.fibo(input).toString());
    }
}