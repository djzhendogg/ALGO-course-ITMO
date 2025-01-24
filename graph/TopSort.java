import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TopSort {
    public static List<List<Integer>> graph;
    public static int[] colors;
    public static boolean[] visited;
    public static ArrayDeque<Integer> queue = new ArrayDeque<>();

    public static void main(String[] args) {
        graph = readOrientedGraph();
        if (graph == null) return;

        int n = graph.size();

        visited = new boolean[n];
        colors = new int[n];

        for (int i = 0; i < n; i++) {
            if (colors[i] == 0) {
                if (hasCycle(i)) {
                    System.out.println(-1);
                    return;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(i);
            }
        }

        while (!queue.isEmpty()) {
            System.out.print(queue.poll() + 1 + " ");
        }
    }

    private static List<List<Integer>> readOrientedGraph() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in,
                    "UTF8"));
            try {
                List<List<Integer>> graph = new ArrayList<>();
                StringTokenizer tokenizer = new StringTokenizer(reader.readLine(), " ");
                int n = Integer.parseInt(tokenizer.nextToken());
                int m = Integer.parseInt(tokenizer.nextToken());
                for (int i = 0; i < n; i++) {
                    graph.add(new ArrayList<>());
                }
                for (int i = 0; i < m; i++) {
                    tokenizer = new StringTokenizer(reader.readLine(), " ");
                    int v = Integer.parseInt(tokenizer.nextToken());
                    int w = Integer.parseInt(tokenizer.nextToken());
                    graph.get(v - 1).add(w - 1);
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

    private static boolean hasCycle(int x) {
        colors[x] = 1;
        for (int w : graph.get(x)) {
            if (colors[w] == 1) {
                return true;
            } else if (colors[w] == 0) {
                if (hasCycle(w)) {
                    return true;
                }
            }
        }
        colors[x] = 2;
        return false;
    }

    private static void dfs(int x) {
        visited[x] = true;
        for (int w : graph.get(x)) {
            if (!visited[w]) {
                dfs(w);
            }
        }
        queue.addFirst(x);
    }
}
