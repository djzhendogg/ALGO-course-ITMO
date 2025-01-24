package graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class GraphCycle {
    public static int[] colors;
    public static List<List<Integer>> graph = new ArrayList<>();
    public static List<Integer> cycle = new ArrayList<>();
    public static int answer = -1;

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in,
                    "UTF8"));
            try {
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
                colors = new int[n];
                for (int i = 0; i < n; i++) {
                    if (colors[i] == 0) {
                        answer = dfs(i, -1);
                        if (answer != -1) {
                            printCycle(answer);
                            break;
                        }
                    }
                }
                if (answer == -1) {
                    System.out.println(-1);
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static void printCycle(int answer) {
        int a = -1;
        for (int t = 0; t < cycle.size(); t++) {
            if (cycle.get(t) == answer) {
                a = t;
            }
        }
        System.out.println(cycle.size() - a);
        for (int i = a; i < cycle.size(); i++) {
            System.out.print(cycle.get(i) + 1 + " ");
        }
    }
    private static int dfs(int x, int p) {
        int tmp = -1;
        colors[x] = 1;
        cycle.addLast(x);
        for (int w : graph.get(x)) {
            if (colors[w] == 0 && tmp == -1) {
                tmp = dfs(w, x);
            } else if (colors[w] == 1 && tmp == -1) {
                return w;
            }
        }
        if (tmp != -1) {
            return tmp;
        }
        colors[x] = 2;
        cycle.removeLast();
        return -1;
    }
}
