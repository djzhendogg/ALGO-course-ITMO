package graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ParentsInTree {
    public static boolean[] visited;
    public static List<List<Integer>> graph = new ArrayList<>();
    public static long timer = 1;
    public static long[] tin;
    public static long[] tout;

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
                tin = new long[n];
                tout = new long[n];
                for (int i = 0; i < n; i++) {
                    graph.add(new ArrayList<>());
                }
                tokenizer = new StringTokenizer(reader.readLine(), " ");
                int start = 1;
                while (tokenizer.hasMoreTokens()) {
                    int vis = Integer.parseInt(tokenizer.nextToken());
                    graph.get(vis - 1).add(start++);
                }
                dfs(0, -1);
                StringBuilder str = new StringBuilder();
                for (int i = 0; i < m; i++) {
                    tokenizer = new StringTokenizer(reader.readLine(), " ");
                    int u = Integer.parseInt(tokenizer.nextToken()) - 1;
                    int v = Integer.parseInt(tokenizer.nextToken()) - 1;
                    if (tin[u] < tin[v] && tin[v] < tout[u]) {
                        str.append(1).append("\n");
                    } else {
                        str.append(0).append("\n");
                    }
                }
                System.out.println(str);
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static void dfs(int x, int p) {
        visited[x] = true;
        tin[x] = timer++;
        for (int w : graph.get(x)) {
            if (!visited[w]) {
                dfs(w, x);
            }
        }
        tout[x] = timer++;
    }
}
