package graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class GraphCondensation {
    public static List<List<Integer>> graphG = new ArrayList<>();
    public static List<List<Integer>> graphH = new ArrayList<>();
    public static boolean[] visited;
    public static ArrayDeque<Integer> queue = new ArrayDeque<>();
    public static int[] colors;
    public static int counter = 1;
    public static Set<Edge> edges = new HashSet<>();
    public static Set<Edge> backwardEdge = new HashSet<>();
    public static Set<Edge> rope = new HashSet<>();


    public static void main(String[] args) {
        readOrientedGraph();
        int n = graphG.size();
        visited = new boolean[n];
        colors = new int[n];
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfsStrait(i);
            }
        }
        visited = new boolean[n];

        while (!queue.isEmpty()) {
            if (!visited[queue.peekLast()]) {
                dfsBack(queue.pollLast());
                counter++;
            } else {
                queue.pollLast();
            }
        }
        System.out.println(rope.size());
    }

    private static void dfsStrait(int x) {
        visited[x] = true;
        for (int w : graphG.get(x)) {
            if (!visited[w]) {
                dfsStrait(w);
            }
        }
        queue.addLast(x);
    }

    private static void dfsBack(int x) {
        visited[x] = true;
        colors[x] = counter;
        for (int w : graphH.get(x)) {
            if (!visited[w]) {
                dfsBack(w);
            } else if ( colors[w] != colors[x] &&
                    !backwardEdge.contains(new Edge(w, x))) {
                if (colors[w] > colors[x]) {
                    rope.add(new Edge(colors[w], colors[x]));
                } else {
                    rope.add(new Edge(colors[x], colors[w]));
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
                int n = Integer.parseInt(tokenizer.nextToken());
                int m = Integer.parseInt(tokenizer.nextToken());
                for (int i = 0; i < n; i++) {
                    graphG.add(new ArrayList<>());
                    graphH.add(new ArrayList<>());
                }
                for (int i = 0; i < m; i++) {
                    tokenizer = new StringTokenizer(reader.readLine(), " ");
                    int v = Integer.parseInt(tokenizer.nextToken()) - 1;
                    int w = Integer.parseInt(tokenizer.nextToken()) - 1;
                    graphG.get(v).add(w);
                    graphH.get(w).add(v);
                    Edge rippesTo = new Edge(w, v);
                    if (edges.contains(rippesTo)) {
                        backwardEdge.add(rippesTo);
                    } else {
                        edges.add(new Edge(v, w));
                    }
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static class Edge {
        protected final int u;
        protected final int v;

        public Edge(int u, int v) {
            this.u = u;
            this.v = v;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge edge = (Edge) o;
            return u == edge.u && v == edge.v;
        }

        @Override
        public int hashCode() {
            return Objects.hash(u, v);
        }
    }
}

