package graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ColoredGraph {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in,
                    "UTF8"));
            try {
                StringTokenizer tokenizer = new StringTokenizer(reader.readLine(), " ");
                int n = Integer.parseInt(tokenizer.nextToken());
                int m = Integer.parseInt(tokenizer.nextToken());
                List<List<Integer>> graph = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    graph.add(new ArrayList<>());
                }
                for (int i = 0; i < m; i++) {
                    tokenizer = new StringTokenizer(reader.readLine(), " ");
                    int v = Integer.parseInt(tokenizer.nextToken());
                    int w = Integer.parseInt(tokenizer.nextToken());
                    graph.get(v - 1).add(w - 1);
                    graph.get(w - 1).add(v - 1);
                }

                boolean[] visited = new boolean[n];
                int[] colors = new int[n];
                int color = 0;
                for (int i = 0; i < n; i++) {
                    if (!visited[i]) {
                        color++;
                        colors[i] = color;
                        dfs(visited, i, graph, color, colors);
                    }
                }
                for (int i = 0; i < n; i++) {
                    System.out.print(colors[i] + " ");
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static void dfs(boolean[] v, int x, List<List<Integer>> graph, int color, int[] colors) {
        v[x] = true;
        for (int w : graph.get(x)) {
            if (!v[w]) {
                colors[w] = color;
                dfs(v, w, graph, color, colors);
            }
        }
    }
}
