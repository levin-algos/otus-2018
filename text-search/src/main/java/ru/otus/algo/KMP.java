package ru.otus.algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class KMP {

    static List<Integer> kmp(String text, String pattern) {
        Objects.requireNonNull(text);
        Objects.requireNonNull(pattern);

        int n = text.length();
        int m = pattern.length();
        int[] table = computePrefix(pattern);
        int q = 0;
        List<Integer> matches = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            while (q > 0 && q< m && pattern.charAt(q) != text.charAt(i)) {
                q = table[q-1];
            }
            if (q <= m-1 && pattern.charAt(q) == text.charAt(i))
                q++;
            if (q == m) {
                matches.add(i - m + 1);
                q = table[q-1];
            }
        }
        return matches;
    }

    private static int[] computePrefix(String pattern) {
        int m = pattern.length();
        int[] table = new int[m];
        int k = 0;
        for (int q = 1; q < m; q++) {
            while (k > 0 && pattern.charAt(k) != pattern.charAt(q)) {
                k = table[k-1];
            }
            if (pattern.charAt(k) == pattern.charAt(q))
                k++;
            table[q] = k;
        }
        return table;
    }
}