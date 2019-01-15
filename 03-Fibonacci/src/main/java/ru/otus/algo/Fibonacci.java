package ru.otus.algo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class Fibonacci {

    public static BigInteger definition(int input) {
        if (input < 0)
            throw new IllegalArgumentException();
        if (input == 0)
            return BigInteger.ZERO;
        if (input == 1)
            return BigInteger.ONE;

        BigInteger a = BigInteger.ONE, b = BigInteger.ONE;
        BigInteger f = BigInteger.ONE;
        for (int i = 2; i < input; i++) {
            f = a.add(b);
            a = b;
            b = f;
        }
        return f;
    }

    public static BigInteger matrix(int input) {
        BigInteger[] arr = new BigInteger[2];
        arr[0] = BigInteger.ZERO;
        arr[1] = BigInteger.ONE;

        if (input < 2)
            return arr[input];

        int c = 0;
        Matrix2D res = new Matrix2D(BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE);
        while (input != 0) {
            if (bases.size() == c) {
                bases.add(bases.get(c-1).pow2());
            }

            if ((input & 1) == 1) {
                res = res.multiply(bases.get(c));
            }
            input >>= 1;
            c++;
        }
        return res.x21;
    }

    private static class Matrix2D {
        private BigInteger x11, x12, x21, x22;

        public Matrix2D(BigInteger x11, BigInteger x12, BigInteger x21, BigInteger x22) {
            this.x11 = x11;
            this.x12 = x12;
            this.x21 = x21;
            this.x22 = x22;
        }

        private Matrix2D pow2() {
            return multiply(this);
        }


        Matrix2D multiply(Matrix2D m) {
            return new Matrix2D(x11.multiply(m.x11).add(x12.multiply(m.x21)), x11.multiply(m.x12).add(x12.multiply(m.x22)),
                    x21.multiply(m.x11).add(x22.multiply(m.x21)), x21.multiply(m.x12).add(x22.multiply(m.x22)));
        }

        @Override
        public String toString() {
            return "Matrix2D{" +
                    "x11=" + x11 +
                    ", x12=" + x12 +
                    ", x21=" + x21 +
                    ", x22=" + x22 +
                    '}';
        }
    }

    private static Matrix2D BASE = new Matrix2D(BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO);
    private static List<Matrix2D> bases = new ArrayList<>();

    static {
        bases.add(BASE);
    }
}