package ru.otus.algo;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) throws InterruptedException {
	// write your code here
        long start = System.nanoTime();
        System.out.println(fibo(2000));
        long end = System.nanoTime();
        System.out.println((end - start)/1000 + " µs");
    }
    public static boolean[] era(int max) {
        boolean[] arr = new boolean[max];
        arr[0]=arr[1] = true;

        int t = 2;
        while(true) {
            int k = findFalse(arr, t);
            if (k >= max) return arr;
            for (int p = k; p< max; p +=2) {
                arr[p] = true;
                t = p;
            }
        }
    }

    public static BigDecimal fibo(int n) {
        BigDecimal a = BigDecimal.ZERO, b = BigDecimal.ONE;
        BigDecimal f=BigDecimal.ZERO;
        for (int i = 1; i < n; i++) {
            f = a.add(b);
            a = b;
            b = f;
        }
        return f;
    }

    public static BigDecimal fibo_gold(int n) {
        double phiN = Math.pow((1+Math.sqrt(5))/2, n);

        return new BigDecimal((phiN/Math.sqrt(5)+0.5));
    }

    private static int findFalse(boolean[] arr, int t) {
        int i = t+1;
        while (arr.length-1 > i && arr[i]) {
            i++;
        }
        return i;
    }

    public static boolean[] era1(int max) {
        boolean[] arr = new boolean[max];

        for (int i = 2; i*i <= max; i++) {
            if (!arr[i]) {
                int k = i*i;
                for (int j=k; j <= max; j = k+i ) {
                    arr[j] = true;
                }
            }
        }
        return arr;
    }


    public static long gcd(long a, long b) {
        int i = 0;
        while (a != b ) {
            if (a > b) {
                a = a - b;
            } else {
                b = b - a;
            }
            i++;
        }
        System.out.println(i);
        return b;
    }

    public static long gcd1(long a, long b) {
        int i = 0;
        while (a != b && b!=0 ) {
            if (a > b) {
                a = a % b;
            } else {
                b = b % a;
            }
            i++;
        }
        System.out.println(i);
        return a;
    }
}
