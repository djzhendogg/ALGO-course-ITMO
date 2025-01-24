package graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Floyd {
    private static long[][] dp;
    private static int N;
    public static final long INF = Long.MAX_VALUE - 1_000_000_000;

    public static void main(String[] args) {
        readOrientedGraph();
        floyd();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                str.append(dp[i][j]).append(" ");
            }
            str.append('\n');
        }
        System.out.println(str);
    }

    private static void floyd() {
        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k][j]);
                }
            }
        }
    }

    private static void readOrientedGraph() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in,
                    "UTF8"));
            try {
                StringTokenizer tokenizer = new StringTokenizer(reader.readLine(), " ");
                N = Integer.parseInt(tokenizer.nextToken());
                dp = new long[N][N];
                for (int i = 0; i < N; i++) {
                    Arrays.fill(dp[i], INF);
                }
                for (int i = 0; i < N; i++) {
                    tokenizer = new StringTokenizer(reader.readLine(), " ");
                    for (int j = 0; j < N; j++) {
                        dp[i][j] = Integer.parseInt(tokenizer.nextToken());
                    }
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
