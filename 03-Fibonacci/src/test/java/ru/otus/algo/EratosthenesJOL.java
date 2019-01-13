package ru.otus.algo;

import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;

class EratosthenesJOL {

    public static void main(String[] args) {

        System.out.println(VM.current().details());

        int[] length = {100, 1_000, 10_000, 100_000, 1_000_000, 10_000_000};
        for (int count : length) {
            System.out.println("length: " + count + " bits:");
            Eratosthenes era = BitSetEratosthenes.of(count);
            GraphLayout layout = GraphLayout.parseInstance(era);
            System.out.println(layout.toFootprint());

            era = LongArrayEratosthenes.of(count);
            layout = GraphLayout.parseInstance(era);
            System.out.println(layout.toFootprint());
        }
    }
}