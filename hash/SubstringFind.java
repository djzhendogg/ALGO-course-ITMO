package hash;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SubstringFind {
    static final long p = Character.MAX_VALUE + 1;
    static final long M1 = 1_000_000_000 + 7;
//    static final long M2 = 1_000_000_000 + 9;
    static long[] hashes1;
    static long[] hashes2;
    static long[] pows1;
    static long[] pows2;

    static long patternhash1 = 0;
    static long patternhash2 = 0;

    static int textLen;
    static int patternLen;

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in,
                    "UTF8"));
            try {
                List<Integer> res = new ArrayList<>();
                String pattern = reader.readLine();
                String text = reader.readLine();
                textLen = text.length();
                patternLen = pattern.length();
                int maxL = Math.max(textLen, patternLen);
                makePows(maxL);
                hashText(text);
                hashPattern(pattern);
                HashPair patternHash = new HashPair(patternhash1, patternhash2);
                for (int i = 0; i + patternLen <= textLen; i++) {
                    if (getHash(i, patternLen).equals(patternHash)) {
                        res.add(i + 1);
                    }
                }
                System.out.println(res.size());
                for (int z: res) {
                    System.out.print(z);
                    System.out.print(" ");
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static void hashText(String str) {
        hashes1 = new long[str.length() + 1];
        hashes2 = new long[str.length() + 1];
        hashes1[0] = 0;
        hashes2[0] = 0;
        for (int i = 1; i <= str.length(); i++) {
            hashes1[i] = (hashes1[i - 1] * p + str.charAt(i - 1)) % M1;
            hashes2[i] = hashes2[i - 1] * p + str.charAt(i - 1);
        }
    }

    private static void makePows(int len) {
        pows1 = new long[len + 1];
        pows1[0] = 1;
        pows2 = new long[len + 1];
        pows2[0] = 1;
        for (int i = 0; i < len; i++) {
            pows1[i + 1] = pows1[i] * p % M1;
            pows2[i + 1] = pows2[i] * p;
        }
    }
    private static void hashPattern(String str) {
        long[] hashesN1 = new long[str.length() + 1];
        long[] hashesN2 = new long[str.length() + 1];
        hashesN1[0] = 0;
        hashesN2[0] = 0;
        for (int i = 1; i <= str.length(); i++) {
            hashesN1[i] = (hashesN1[i - 1] * p + str.charAt(i - 1)) % M1;
            hashesN2[i] = (hashesN2[i - 1] * p + str.charAt(i - 1));
        }
        patternhash1 = hashesN1[str.length()];
        patternhash2 = hashesN2[str.length()];
    }



    private static HashPair getHash(int start, int len) {
        long hash1 = hashes1[start + len] - hashes1[start] * pows1[len] % M1;
        if (hash1 < 0) hash1 += M1;
        hash1 = hash1 % M1;
        long hash2 = hashes2[start + len] - hashes2[start] * pows2[len];
        return new HashPair(hash1, hash2);
    }

    public static class HashPair {
        public long one;
        public long two;

        public HashPair(long one, long two) {
            this.one = one;
            this.two = two;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HashPair hashPair = (HashPair) o;
            return one == hashPair.one && two == hashPair.two;
        }

        @Override
        public int hashCode() {
            return Objects.hash(one, two);
        }
    }
}