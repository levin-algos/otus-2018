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

        Eratosthenes era = Eratosthenes.of(1_000_000);
        GraphLayout layout = GraphLayout.parseInstance(era);
        System.out.println(layout.toFootprint());
    }
}