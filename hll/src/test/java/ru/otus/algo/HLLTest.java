package ru.otus.algo;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class HLLTest {

    HashFunction hashFunction = Hashing.murmur3_128();

    @ParameterizedTest
    @MethodSource("dataProvider")
    void basicTest(int distinct, int error) {
        int i = 8;
            System.out.println(i);
            final HyperLogLog hll = new HyperLogLog(i);
            LongStream.range(0, distinct).parallel().forEach(element -> {
                long hashedValue = hashFunction.newHasher().putLong(element).hash().asLong();
                hll.add(hashedValue);
            });
            long count = hll.count();
            System.out.println(String.format("real: %d, hll: %d, error: %f", distinct, count, 100 - (double) count / distinct * 100));
//        assertThat(count).isCloseTo(numberOfElements, Offset.offset(toleratedDifference));
    }

    static Stream<? extends Arguments> dataProvider() {
        return Stream.of(
//                Arguments.of(100_000, 1_000),
//                Arguments.of(1_000_000, 10_000),
//                Arguments.of(10_000_000, 100_000),
                Arguments.of(100_000_000, 1_000_000),
                Arguments.of(1_000_000_000, 10_000_000)
        );
    }
}
