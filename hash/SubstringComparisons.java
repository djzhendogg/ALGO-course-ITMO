package hash;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SubstringComparisons {
    static final long M = 1_000_000_000 + 7;
    static long[] hashes;
    static long[] pows;

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in,
                    "UTF8"));
            try {
                String str = reader.readLine();
                fill(str);
                int n = Integer.parseInt(reader.readLine());
                for (long i = 0; i < n; i++) {
                    StringTokenizer tokenizer = new StringTokenizer(reader.readLine(), " ");
                    int a = Integer.parseInt(tokenizer.nextToken());
                    int b = Integer.parseInt(tokenizer.nextToken());
                    int c = Integer.parseInt(tokenizer.nextToken());
                    int d = Integer.parseInt(tokenizer.nextToken());
                    System.out.println(getHash(a, b) == getHash(c, d) ? "Yes": "No");
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static void fill(String str) {
        pows = new long[str.length() + 1];
        hashes = new long[str.length() + 1];
        hashes[0] = 0;
        pows[0] = 1;
        for (int i = 1; i <= str.length(); i++) {
            hashes[i] = hashes[i - 1] * M + str.charAt(i - 1);
            pows[i] = pows[i - 1] * M;
        }
    }

    private static long getHash(int a, int b) {
        return hashes[b] - hashes[a - 1] * pows[b - a + 1];
    }
}
