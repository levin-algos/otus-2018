package ru.otus.algo;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;


import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HLLTest {

    long numberOfElements = 100_000_000;
    long toleratedDifference = 1_000_000;
    HashFunction hashFunction = Hashing.murmur3_128();

    @Test
    void basicTest() {
        final HyperLogLog hll = new HyperLogLog();
        LongStream.range(0, numberOfElements).forEach(element -> {
            int hashedValue = hashFunction.newHasher().putLong(element).hash().asInt();
            hll.add(hashedValue);
        }
        );

        long count = hll.count();

        assertThat(count).isCloseTo(numberOfElements, Offset.offset(toleratedDifference));
    }
}
