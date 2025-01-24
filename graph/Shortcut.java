package graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Shortcut {
    public static List<List<Integer>> graph;
    public static Set<Integer> V = new HashSet<>();
    public static long[] dp;
    public static int S;
    public static int F;
    public static int N;
    public static int[][] weights;
    public static final long MAX = Long.MAX_VALUE - 2;
    public static TreeSet<Pair> next = new TreeSet<>();

    public static void main(String[] args) {
        graph = readOrientedGraph();
        if (graph == null) return;
        int n = graph.size();
        dp = new long[n];
        Arrays.fill(dp, MAX);

        dp[S] = 0;
        next.add(new Pair(S, 0));

        while (!V.isEmpty()) {
            int id = findMin();
            if (id < 0) break;
            for (int w: graph.get(id)) {
                if (V.contains(w)) {
                    long pre = dp[w];
                    dp[w] = Math.min(pre, dp[id] + weights[id][w]);
                    if (pre > dp[w]) {
                        next.remove(new Pair(w, pre));
                        next.add(new Pair(w, dp[w]));
                    }
                }
            }
        }
        if (dp[F] == MAX) {
            System.out.println(-1);
        } else {
            System.out.println(dp[F]);
        }
    }

    private static int findMin() {
        if (next.isEmpty()) return -1;
        int res = next.first().id;
        V.remove(res);
        next.removeFirst();
        return res;
    }

    private static List<List<Integer>> readOrientedGraph() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in,
                    "UTF8"));
            try {
                List<List<Integer>> graph = new ArrayList<>();
                StringTokenizer tokenizer = new StringTokenizer(reader.readLine(), " ");
                N = Integer.parseInt(tokenizer.nextToken());
                S = Integer.parseInt(tokenizer.nextToken()) - 1;
                F = Integer.parseInt(tokenizer.nextToken()) - 1;
                weights = new int[N][N];
                for (int i = 0; i < N; i++) {
                    graph.add(new ArrayList<>());
                }
                for (int i = 0; i < N; i++) {
                    tokenizer = new StringTokenizer(reader.readLine(), " ");
                    for (int j = 0; j < N; j++) {
                        int weigth = Integer.parseInt(tokenizer.nextToken());
                        weights[i][j] = weigth;
                        if (weigth <= 0) continue;
                        graph.get(i).add(j);
                        V.add(i);
                        V.add(j);
                        if (i != S) {
                            next.add(new Pair(i, MAX));
                        }
                        if (j != S) {
                            next.add(new Pair(j, MAX));
                        }
                    }
                }
                return graph;
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return null;
    }

    public static class Pair implements Comparable<Pair> {
        protected final int id;
        protected long cost;

        public Pair(int id, long cost) {
            this.id = id;
            this.cost = cost;
        }

        public void setCost(long cost) {
            this.cost = cost;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return id == pair.id && cost == pair.cost;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, cost);
        }

        @Override
        public int compareTo(Pair other) {
            if (this.cost == other.cost) {
                return Integer.compare(this.id, other.id);
            }
            return Long.compare(this.cost, other.cost);
        }
    }
}
