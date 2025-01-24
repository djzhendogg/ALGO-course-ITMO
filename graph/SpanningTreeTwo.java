package graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringTokenizer;

public class SpanningTreeTwo {
    private static Edge[] V;
    public static int N, M;
    public static int[] parent, rank;


    public static void main(String[] args) {
        readOrientedGraph();
        Arrays.sort(V);
        long ans = 0;
        for (Edge v: V) {
            if (union(v.from, v.to)) {
                ans += v.weight;
            }
        }
        System.out.println(ans);
    }

    private static int find(int x) {
        if (parent[x] == x) {
            return x;
        } else {
            parent[x] = find(parent[x]);
            return parent[x];
        }
    }

    private static boolean union(int x, int y) {
       x = find(x);
       y = find(y);
       if (x == y) {
           return false;
       } else if (rank[x] > rank[y]) {
           parent[y] = x;
       } else if (rank[x] == rank[y]) {
           parent[x] = y;
           rank[y]++;
       } else {
           parent[x] = y;
       }
       return true;
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
                V = new Edge[M];
                parent = new int[N];
                rank = new int[N];

                for (int i = 0; i < N; i++) {
                    parent[i] = i;
                    rank[i] = 1;
                }

                for (int i = 0; i < M; i++) {
                    tokenizer = new StringTokenizer(reader.readLine(), " ");
                    int from = Integer.parseInt(tokenizer.nextToken()) - 1;
                    int to = Integer.parseInt(tokenizer.nextToken()) - 1;
                    int w = Integer.parseInt(tokenizer.nextToken());
                    V[i] = new Edge(from, to, w);
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static class Edge implements Comparable<Edge> {
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

        @Override
        public int compareTo(Edge other) {
            if (this.weight == other.weight) {
                if (this.from == other.from) {
                    return Integer.compare(this.to, other.to);
                }
                return Integer.compare(this.from, other.from);
            }
            return Long.compare(this.weight, other.weight);
        }
    }
}
