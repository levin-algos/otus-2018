package ru.otus.algo;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.assertj.core.data.Offset;
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
        final HyperLogLog hll = new HyperLogLog(14);
        LongStream.range(0, distinct).parallel().forEach(element -> {
            long hashedValue = hashFunction.newHasher().putLong(element).hash().asLong();
            hll.add(hashedValue);
        });

        long count = hll.count();
        assertThat(count).isCloseTo(distinct, Offset.offset((long)error));
    }

    static Stream<? extends Arguments> dataProvider() {
        return Stream.of(
                Arguments.of(100_000, 1_000),
                Arguments.of(1_000_000, 10_000),
                Arguments.of(10_000_000, 100_000),
                Arguments.of(100_000_000, 1_000_000),
                Arguments.of(1_000_000_000, 10_000_000)
        );
    }
}