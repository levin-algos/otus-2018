package ru.otus.algo;

import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;

/*
10k - 696
100k - 6320
100k - 62568

 */
class EratosthenesJOL {

    public static void main(String[] args) {

        System.out.println(VM.current().details());

        BitSetEratosthenes era = BitSetEratosthenes.of(Integer.MAX_VALUE);
        GraphLayout layout = GraphLayout.parseInstance(era);
        System.out.println(layout.toFootprint());
    }
}