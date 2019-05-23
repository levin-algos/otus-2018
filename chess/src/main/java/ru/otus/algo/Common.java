package ru.otus.algo;

public class Common {

    public static String longToBinary(long l) {
        StringBuilder sb = new StringBuilder();

        int c = 0;
        while (c < 64) {
            sb.append(l & 1L);
            c++;
            l = l >>> 1;
            if (c % 8 == 0)
                sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
