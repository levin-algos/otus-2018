package ru.otus.algo;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import net.agkn.hll.HLL;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BritannicaTest {

    static HashFunction hashFunction = Hashing.murmur3_128();

    public static void main(String[] args) {
        Path dir = Paths.get("c:/java/test-data/Britannica 11/");
        int log2m = 14;
        final HLL hll = new HLL(log2m, 8);
        final HyperLogLog myHll = new HyperLogLog(log2m);
        HashSet<String> real = new HashSet<>();

        for (Path p : getFiles(dir)) {
            System.out.println(p.toString());
            try {
                Files.lines(p).flatMap(s -> Arrays.stream(s.split("\\s+")))
                        .filter(s -> s.chars().allMatch(Character::isLetter))
                        .forEach(s -> {
                            long hashedValue = hashFunction.newHasher().putString(s).hash().asLong();
                            hll.addRaw(hashedValue);
                            real.add(s);
                            myHll.add(hashedValue);
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println();
        }
        System.out.println("my hll :" + myHll.count());
        System.out.println("real: " + real.size());
        System.out.println("hll: " + hll.cardinality());
    }

    private static Set<Path> getFiles(Path dir) {
        HashSet<Path> files = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path p : stream) {
                if (!Files.isDirectory(p)) {
                    files.add(p);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }
}