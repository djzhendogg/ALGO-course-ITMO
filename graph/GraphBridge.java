package graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class GraphBridge {
    public static int[] colors;
    public static List<List<Integer>> graph = new ArrayList<>();
    public static int timer = 0;
    public static int[] tin;
    public static int[] f;
//    Map<Rippe>


    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in,
                    "UTF8"));
            try {
                StringTokenizer tokenizer = new StringTokenizer(reader.readLine(), " ");
                int n = Integer.parseInt(tokenizer.nextToken());
                int m = Integer.parseInt(tokenizer.nextToken());
                colors = new int[n];
                tin = new int[n];
                f = new int[n];
                Rippe[] rippes = new Rippe[m];

                for (int i = 0; i < n; i++) {
                    graph.add(new ArrayList<>());
                }
                for (int i = 0; i < m; i++) {
                    tokenizer = new StringTokenizer(reader.readLine(), " ");
                    int v = Integer.parseInt(tokenizer.nextToken()) - 1;
                    int w = Integer.parseInt(tokenizer.nextToken()) - 1;
                    graph.get(v).add(w);
                    graph.get(w).add(v);
                    if (v > w) {
                        rippes[i] = new Rippe(w, v);
                    } else {
                        rippes[i] = new Rippe(v, w);
                    }
                }
                for (int i = 0; i < n; i++) {
                    if (colors[i] == 0) {
                        dfs(i, -1);
                    }
                }
                List<Integer> ans = new ArrayList<>();
                for (int i = 0; i < m; i++) {
                    Rippe rippe = rippes[i];
                    if (tin[rippe.u] < f[rippe.v] || tin[rippe.v] < f[rippe.u]) {
                        ans.add(i + 1);
                    }
                }
                System.out.println(ans.size());
                for (int i : ans) {
                    System.out.print(i + " ");
                }

            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static void dfs(int x, int p) {
        colors[x] = 1;
        tin[x] = f[x] = timer++;
        for (int w : graph.get(x)) {
            if (colors[w] == 0) {
                dfs(w, x);
                f[x] = Math.min(f[x], f[w]);
            } else if (colors[w] != 0 && w != p) {
                f[x] = Math.min(f[x], tin[w]);
            }
        }
        colors[x] = 2;
    }

    public static class Rippe {
        protected final int u;
        protected final int v;

        public Rippe(int u, int v) {
            this.u = u;
            this.v = v;
        }
    }
}
