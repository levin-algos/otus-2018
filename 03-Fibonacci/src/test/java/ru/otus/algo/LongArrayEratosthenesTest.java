package ru.otus.algo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class LongArrayEratosthenesTest {

        private static Eratosthenes era;

        @BeforeAll
        static void fillSet() {
            int PRIME_SIZE = 104730;
            era = LongArrayEratosthenes.of(PRIME_SIZE);
        }

        @ParameterizedTest
        @CsvFileSource(resources = "/primes10000")
        void testPrime(int prime) {
            assertFalse(era.isPrime(prime));
        }

        @Test
        void calcPrimeNumber() {
            assertEquals(10_000, era.size());
        }
}
