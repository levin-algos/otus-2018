package ru.otus.algo;

import java.util.ArrayList;
import java.util.List;

class FiniteAutomata {

    private static char[] getAlphabet(char to) {
        assert to > 0;

        char[] chars = new char[to];
        for (char i =0; i < to; i++) chars[i] = i;

        return chars;
    }

    static List<Integer> faSearch(String text, String pattern) {
        int q = 0;
        int[][] delta = computeDelta(pattern, getAlphabet((char) 255));
        List<Integer> matches = new ArrayList<>();
        int m = pattern.length();
        for (int i = 0; i< text.length(); i++) {
            q = delta[q][text.charAt(i)];
            if (q == m)
                matches.add(i-m+1);
        }

        return matches;
    }

    private static int[][] computeDelta(String pattern, char[] alphabet) {
        int m = pattern.length();
        int[][] delta = new int[m+1][alphabet.length];
        for (int q = 0; q <= m; q++) {
            for (char a: alphabet) {
                int k = Math.min(m + 1, q + 2);
                do {
                    k -=1;
                } while (k > 0 && !isSuffix(pattern, k, q, a));
                delta[q][a] = k;
            }
        }
        return delta;
    }

    private static boolean isSuffix(String p, int k , int q, char a) {
        String pk = p.substring(0, k);
        String pq = p.substring(0, q).concat(String.valueOf(a));
        String substring = pq.substring(pq.length() - pk.length());
        return pk.equals(substring);
    }
}
