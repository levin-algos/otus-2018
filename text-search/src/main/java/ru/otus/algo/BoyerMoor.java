package ru.otus.algo;

import java.util.*;

class BoyerMoor {

    static List<Integer> boyerMoor(String text, String pattern) {
        Objects.requireNonNull(text);
        Objects.requireNonNull(pattern);

        int pLen = pattern.length();
        int tLen = text.length();

        if (tLen < pLen)
            return Collections.emptyList();

        BadCharacterTable bad = new BadCharacterTable(pattern);
        int[] good = goodSuffix(pattern);

        List<Integer> ints = new ArrayList<>();
        int shift = 0;
        int prevK = -1;
        while (shift <= tLen - pLen) {
            int j = pLen - 1, h = shift+j;
            boolean eq;
            char tChar;
            do {
                tChar = text.charAt(shift + j);
                eq = tChar == pattern.charAt(j);
                if (eq) {
                    j--;
                    h--;
                }

            } while  (j >= 0 && h > prevK && eq);
            if (eq || h == prevK) {
                ints.add(shift);
                shift++;
            } else {
                int badShift = bad.get(tChar);
                int goodShift = good[j];
                prevK = j == pLen - 1? prevK: shift;
                shift += Math.max(badShift, goodShift) - (pLen - 1 - j);
            }
        }
        return ints;
    }

    private static int[] goodSuffix(String pattern) {
        int[] table = new int[pattern.length()];

        int lastPrefixPosition = pattern.length();
        for (int i = pattern.length() - 1; i >= 0; i--) {
            if (isPrefix(pattern, i + 1)) {
                lastPrefixPosition = i + 1;
            }
            table[i] = lastPrefixPosition + (pattern.length() - 1 - i);
        }

        for (int i = 0; i < pattern.length() - 1; i++) {
            int slen = suffixLength(pattern, i);
            table[pattern.length() - 1 - slen] = pattern.length() - 1 - i + slen;
        }

        return table;
    }

    private static int suffixLength(String pattern, int i) {
        int len = 0;
        for (int p = i; p >= 0 && pattern.charAt(p) == pattern.charAt(pattern.length() - 1 - i + p); p--) {
            len += 1;
        }

        return len;
    }

    private static boolean isPrefix(String pattern, int i) {
        for (int p = i, j = 0; p < pattern.length(); p++, j++) {
            if ((pattern.charAt(p) != pattern.charAt(j))) {
                return false;
            }
        }
        return true;
    }

    static int[] findPrefixes(String str) {
        int len = str.length();
        if (len == 0)
            return new int[0];

        if (len == 1)
            return new int[]{1};

        int[] z = new int[len];
        z[0] = len;
        z[1] = matchLen(str, 0, 1);

//        for (int i = 2; i < 1+z[1]; i++)
//            z[i] = z[1]-i+1;

        int l = 0, r = 0;
        for (int i = 2; i < len; i++) {
            if (i <= r) {
                int k = i - l;
                int b = z[k];
                int a = r - i + 1;
                if (b < a)
                    z[i] = b;
                else {
                    z[i] = a + matchLen(str, a, r + 1);
                    l = i;
                    r = i + z[i] - 1;
                }
            } else {
                z[i] = matchLen(str, 0, i);
                if (z[i] > 0) {
                    l = i;
                    r = i + z[i] - 1;
                }
            }
        }
        return z;
    }

    private static int matchLen(String str, int id1, int id2) {
        int len = str.length();
        if (id1 == id2)
            return len - id1;

        int match_count = 0;

        while (id1 < len && id2 < len && str.charAt(id1) == str.charAt(id2)) {
            match_count += 1;
            id1++;
            id2++;
        }
        return match_count;
    }


    private static class BadCharacterTable {
        Map<Character, Integer> map;
        int len;

        BadCharacterTable(String pattern) {
            Objects.requireNonNull(pattern);

            len = pattern.length();
            map = new HashMap<>(len);
            init(pattern);
        }

        private void init(String pattern) {
            for (int i = 0; i < len - 1; i++) {
                map.put(pattern.charAt(i), len - 1 - i);
            }
        }

        int get(char ch) {
            Integer shift = map.get(ch);
            return shift == null ? len : shift;
        }
    }
}


