package graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ShortcutK {
    private static long[][] dp;
    public static List<List<Integer>> entryEdges = new ArrayList();
    private static int N, M, S, K;
    public static List<Edge> V = new ArrayList<>();
    public static final long INF = Long.MAX_VALUE - 1_000_000_000;

    public static void main(String[] args) {
        readOrientedGraph();
        dp[S][0] = 0;
        fordBellman();
        for (int i = 0; i < N; i++) {
            long tmp = dp[i][K - 1];
            if (tmp == INF) {
                System.out.println(-1);
                continue;
            }
            System.out.println(dp[i][K - 1]);
        }
        PriorityQueue<Edge> pq = new PriorityQueue<>();
    }

    private static void fordBellman() {
        for (int k = 1; k < K; k++) {
            for (int i = 0; i < V.size(); i++) {
                Edge e = V.get(i);
                if (dp[e.from][k - 1] < INF) {
                    dp[e.to][k] = Math.min(dp[e.from][k - 1] + e.weight, dp[e.to][k]);
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
                M = Integer.parseInt(tokenizer.nextToken());
                K = Integer.parseInt(tokenizer.nextToken()) + 1;
                S = Integer.parseInt(tokenizer.nextToken()) - 1;
                dp = new long[N][K];
                for (int i = 0; i < N; i++) {
                    entryEdges.add(new ArrayList<>());
                    Arrays.fill(dp[i], INF);
                }
                for (int i = 0; i < M; i++) {
                    tokenizer = new StringTokenizer(reader.readLine(), " ");
                    int from = Integer.parseInt(tokenizer.nextToken()) - 1;
                    int to = Integer.parseInt(tokenizer.nextToken()) - 1;
                    int w = Integer.parseInt(tokenizer.nextToken());
                    entryEdges.get(to).add(from);
                    V.add(new Edge(from, to, w));
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static class Edge {
        protected final int from;
        protected final int to;
        protected final long weight;

        public Edge(int from, int to, long weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge edge = (Edge) o;
            return from == edge.from && to == edge.to && weight == edge.weight;
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to, weight);
        }
    }

}
