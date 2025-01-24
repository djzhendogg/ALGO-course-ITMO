package graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class PartyPlan {
    public static int N, M;
    public static List<List<Integer>> graphG = new ArrayList<>();
    public static List<List<Integer>> graphH = new ArrayList<>();
    public static boolean[] visited;
    public static LinkedList<String> mapList = new LinkedList<>();
    public static Map<String, Integer> map = new HashMap<>();
    public static ArrayDeque<Integer> queue = new ArrayDeque<>();
    public static int[] colors;
    public static int counter = 1;


    public static void main(String[] args) {
        readOrientedGraph();
        if (hasBothInCycle()) {
            System.out.println(-1);
            return;
        }
        int ansLen = 0;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 2 * N; i += 2) {
            if (colors[i] > colors[i ^ 1]) {
                ansLen++;
                str.append(mapList.get(i / 2)).append("\n");
            }
        }
        System.out.println(ansLen);
        System.out.println(str);
    }
    private static boolean hasBothInCycle() {
        for (int i = 0; i < N * 2; i++) {
            if (!visited[i]) {
                dfsStrait(i);
            }
        }
        visited = new boolean[N * 2];

        while (!queue.isEmpty()) {
            if (!visited[queue.peekLast()]) {
                dfsBack(queue.pollLast());
                counter++;
            } else {
                queue.pollLast();
            }
        }

        for (int i = 0; i < N * 2; i += 2) {
            if (colors[i] == colors[i ^ 1]) {
                return true;
            }
        }
        return false;
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
                N = Integer.parseInt(tokenizer.nextToken());
                M = Integer.parseInt(tokenizer.nextToken());
                visited = new boolean[N * 2];
                colors = new int[N * 2];
                for (int i = 0; i < N; i++) {
                    tokenizer = new StringTokenizer(reader.readLine(), " ");
                    String tmp = tokenizer.nextToken();
                    mapList.add(tmp);
                    map.put(tmp, i);
                    graphG.add(new ArrayList<>());
                    graphG.add(new ArrayList<>());
                    graphH.add(new ArrayList<>());
                    graphH.add(new ArrayList<>());
                }

                for (int i = 0; i < M; i++) {
                    tokenizer = new StringTokenizer(reader.readLine(), " ");
                    int first = calculateId(tokenizer.nextToken());
                    tokenizer.nextToken();
                    int second = calculateId(tokenizer.nextToken());
                    graphG.get(first).add(second);
                    graphG.get(second ^ 1).add(first ^ 1);

                    graphH.get(second).add(first);
                    graphH.get(first ^ 1).add(second ^ 1);
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    private static int calculateId(String str) {
        int a = map.get(str.substring(1));
        int res;
        if (str.startsWith("-")) {
            res = 2 * a + 1;
        } else {
            res = 2 * a;
        }
        return res;
    }
}
