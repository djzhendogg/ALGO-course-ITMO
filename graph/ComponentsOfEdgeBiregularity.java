package graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ComponentsOfEdgeBiregularity {
    public static int[] colors;
    public static boolean[] visited;
    public static List<List<Integer>> graph = new ArrayList<>();
    public static int timer = 0;
    public static int counter = 0;
    public static int[] tin;
    public static int[] f;
    public static Set<Edge> multiples = new HashSet<>();
    public static Set<Edge> edges = new HashSet<>();
    public static ArrayDeque<Integer> stack = new ArrayDeque<>();

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in,
                    "UTF8"));
            try {
                StringTokenizer tokenizer = new StringTokenizer(reader.readLine(), " ");
                int n = Integer.parseInt(tokenizer.nextToken());
                int m = Integer.parseInt(tokenizer.nextToken());
                visited = new boolean[n];
                colors = new int[n];
                tin = new int[n];
                f = new int[n];
                Edge[] rippes = new Edge[m];

                for (int i = 0; i < n; i++) {
                    graph.add(new ArrayList<>());
                }
                for (int i = 0; i < m; i++) {
                    tokenizer = new StringTokenizer(reader.readLine(), " ");
                    int v = Integer.parseInt(tokenizer.nextToken()) - 1;
                    int w = Integer.parseInt(tokenizer.nextToken()) - 1;
                    graph.get(v).add(w);
                    graph.get(w).add(v);
                    Edge rippesTo;
                    if (v > w) {
                        rippesTo = new Edge(w, v);
                    } else {
                        rippesTo = new Edge(v, w);
                    }
                    rippes[i] = rippesTo;
                    if (edges.contains(rippesTo)) {
                        multiples.add(rippesTo);
                    } else {
                        edges.add(rippesTo);
                    }
                }

                for (int i = 0; i < n; i++) {
                    if (!visited[i]) {
                        dfs(i, -1);
                        paint(i);
                    }
                }
                int[] unique = Arrays.stream(colors).distinct().toArray();

                Map<Integer, Integer> map = new HashMap<>();
                int start = 0;
                for (int i = 0; i < unique.length; i++) {
                    map.put(unique[i], ++start);
                }

                System.out.println(start);
                for (int i : colors) {
                    System.out.print(map.get(i) + " ");
                }

            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static void dfs(int x, int p) {
        timer++;
        visited[x] = true;
        stack.push(x);
        tin[x] = timer;
        f[x] = timer;
        for (int w : graph.get(x)) {
            if (w == p) continue;
            if (visited[w]) {
                f[x] = Math.min(f[x], tin[w]);
            } else {
                dfs(w, x);
                f[x] = Math.min(f[x], f[w]);
                if (f[w] > tin[x] && !(multiples.contains(new Edge(x, w)) || multiples.contains(new Edge(w, x)))) {
                    paint(w);
                }
            }
        }
    }

    private static void paint(int x) {
        counter++;
        int last = -1;
        while (last != x && !stack.isEmpty()) {
            colors[stack.getFirst()] = counter;
            last = stack.getFirst();
            stack.pop();
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
