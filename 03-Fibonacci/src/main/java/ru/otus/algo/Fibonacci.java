package ru.otus.algo;

import java.math.BigInteger;

class Fibonacci {

    static BigInteger definition(int input) {
        if (input < 0)
            throw new IllegalArgumentException();
        if (input == 0)
            return BigInteger.ZERO;
        if (input == 1)
            return BigInteger.ONE;

        BigInteger a = BigInteger.ONE, b = BigInteger.ONE;
        BigInteger f=BigInteger.ONE;
        for (int i = 2; i < input; i++) {
            f = a.add(b);
            a = b;
            b = f;
        }
        return f;
    }

    static BigInteger matrix(int input) {

        BigInteger[] arr = new BigInteger[2];
        arr[0] = BigInteger.ZERO;
        arr[1] = BigInteger.ONE;

        if (input< 2)
            return arr[input];

        arr[0] = BigInteger.ONE;
        for (int i = 2; i < input; i++) {
            BigInteger tmp = arr[1];
            arr[1] = arr[0].add(arr[1]);
            arr[0] = tmp;
        }
        return arr[1];
    }
}
