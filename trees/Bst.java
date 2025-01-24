package tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Bst {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in,
                    "UTF8"));
            try {
                int n = Integer.parseInt(reader.readLine());
                String line = reader.readLine();
                StringTokenizer tokenizer = new StringTokenizer(line, " ");
                List<Vertex> arr = new ArrayList<>(n + 1);
                int header = 0;
                for (int i = 1; i <= n; i++) {
                    int x = Integer.parseInt(tokenizer.nextToken());
                    header = insert(header, x, arr);
                }
                System.out.println(n);
                print(arr);
                System.out.println(header + 1);
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static void print(List<Vertex> arr) {
        StringBuilder str = new StringBuilder();
        for (Vertex i : arr) {
            str.append(i.key());
            str.append(" ");
            if (i.left > -1) {
                str.append(i.left + 1);
            } else {
                str.append("-1");
            }
            str.append(" ");
            if (i.right > -1) {
                str.append(i.right + 1);
            } else {
                str.append("-1");
            }
            System.out.println(str);
            str.setLength(0);
        }
    }
    public static int merge(int root1, int root2, List<Vertex> arr) {
        if (root1 < 0) {
            return root2;
        }
        if (root2 < 0) {
            return root1;
        }
        if (arr.get(root1).priority() < arr.get(root2).priority()) {
            arr.get(root1).right = merge(arr.get(root1).right, root2, arr);
            return root1;
        } else {
            arr.get(root2).left = merge(root1, arr.get(root2).left, arr);
            return root2;
        }
    }
    public static List<Integer> split(int rootId, List<Vertex> arr, int x) {
        if (arr.isEmpty() || rootId < 0) {
            return List.of(-1, -1);
        }
        if (arr.get(rootId).key() < x) {
            List<Integer> vertexIdList = split(arr.get(rootId).right, arr, x);
            arr.get(rootId).right = vertexIdList.getFirst();
//            resize(rootId, arr);
            return List.of(rootId, vertexIdList.getLast());
        } else {
            List<Integer> vertexIdList = split(arr.get(rootId).left, arr, x);
            arr.get(rootId).left = vertexIdList.getLast();
//            resize(rootId, arr);
            return List.of(vertexIdList.getFirst(), rootId);
        }
    }

    public static int insert(int rootId, int x, List<Vertex> arr) {
        List<Integer> ab = split(rootId, arr, x);
        List<Integer> cd = split(ab.getLast(), arr, x + 1);
        int rand = (int) (100 + (Math.random() * (1000 - 100)));
        Vertex c = new Vertex(x, rand, 1, -1, -1);
        arr.add(c);
        int a = merge(ab.getFirst(), merge(arr.size() - 1, cd.get(1), arr), arr);
        return a;
    }


//    public static int sizeOf(Vertex root) {
//        if (root != null) {
//            return root.size;
//        } else {
//            return 0;
//        }
//    }
//    public static void resize(int rootId, List<Vertex> arr) {
//        if (rootId > -1) {
//            int size = 1;
//            if (arr.get(rootId).right > -1) {
//                size += sizeOf(arr.get(arr.get(rootId).right));
//            }
//            if (arr.get(rootId).left > -1) {
//                size += sizeOf(arr.get(arr.get(rootId).left));
//            }
//            arr.get(rootId).size = size;
//        }
//    }
    public static class Vertex {
        private final int key;
        private final int priority;
//        protected int size;
//        protected int id;
        protected int left;
        protected int right;

        public Vertex(int key, int priority, int size, int left, int right) {
            this.key = key;
            this.priority = priority;
//            this.size = size;
            this.left = left;
            this.right = right;
        }

        public int key() {
            return key;
        }

        public int priority() {
            return priority;
        }
    }
}
