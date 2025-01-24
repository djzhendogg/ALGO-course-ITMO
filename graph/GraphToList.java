package graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class GraphToList {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in,
                    "UTF8"));
            try {
                StringTokenizer tokenizer = new StringTokenizer(reader.readLine(), " ");
                int n = Integer.parseInt(tokenizer.nextToken());
                int m = Integer.parseInt(tokenizer.nextToken());
                List<List<Integer>> graph = new ArrayList<List<Integer>>();
                for (int i = 0; i < n; i++) {
                    graph.add(new ArrayList<>());
                }
                for (int i = 0; i < m; i++) {
                    tokenizer = new StringTokenizer(reader.readLine(), " ");
                    int v = Integer.parseInt(tokenizer.nextToken());
                    int w = Integer.parseInt(tokenizer.nextToken());
                    graph.get(v - 1).add(w);
                }
                System.out.println(n);
                for (int i = 0; i < n; i++) {
                    System.out.print(graph.get(i).size() + " ");
                    if (!graph.get(i).isEmpty()) {
                        Collections.sort(graph.get(i));
                        for (int j = 0; j < graph.get(i).size(); j++) {
                            System.out.print(graph.get(i).get(j) + " ");
                        }
                    }
                    System.out.print('\n');
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
