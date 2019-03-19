package ru.otus.algo;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;

public class Measures {

    public static void main(String[] args) {

        System.out.println(VM.current().details());
//        System.out.println(ClassLayout.parseInstance(bucket).toPrintable());
        System.out.println(ClassLayout.parseClass(ChainHashMap.ArrayBucket.class).toPrintable());

//        for (int i=0; i < 16; i++) {
//            System.out.println(GraphLayout.parseInstance(bucket).toFootprint());
//            bucket.put(""+i, "val"+i);
//        }
    }
}
