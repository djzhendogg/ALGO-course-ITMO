package tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

public class KMax {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in,
                    "UTF8"));
            try {
                Vertex tree = null;
                long treeSize = 0;
                int n = Integer.parseInt(reader.readLine());
                for (int i = 0; i < n; i++) {
                    String line = reader.readLine();
                    StringTokenizer tokenizer = new StringTokenizer(line, " ");
                    String operation = tokenizer.nextToken();
                    if (Objects.equals(operation, "1")) {
                        tree = insert(tree, Long.parseLong(tokenizer.nextToken()));
                        treeSize++;
                    } else if (Objects.equals(operation, "-1")) {
                        tree = remove(tree, Long.parseLong(tokenizer.nextToken()));
                        treeSize--;
                    } else {
                        System.out.println(findKMax(tree, treeSize - Long.parseLong(tokenizer.nextToken())));
                    }
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static long findKMax(Vertex root, long k) {
        long leftSize = sizeOf(root.left);
        if (leftSize == k) {
            return root.data;
        } else if (leftSize > k) {
            return findKMax(root.left, k);
        } else {
            return findKMax(root.right, k - leftSize - 1);
        }
    }

    public static Vertex merge(Vertex root1, Vertex root2) {
        if (root1 == null) {
            return root2;
        }
        if (root2 == null) {
            return root1;
        }
        if (root1.priority() < root2.priority()) {
            root1.right = merge(root1.right, root2);
            return recalculate(root1);
        } else {
            root2.left = merge(root1, root2.left);
            return recalculate(root2);
        }
    }
    public static List<Vertex> split(Vertex root, long x) {
        List<Vertex> arr = new ArrayList<>();
        if (root == null) {
            arr.add(null);
            arr.add(null);
            return arr;
        }
        List<Vertex> vertexList;
        if (root.data < x) {
            vertexList = split(root.right, x);
            root.right = vertexList.get(0);
            arr.add(recalculate(root));
            arr.add(vertexList.get(1));
        } else {
            vertexList = split(root.left, x);
            root.left = vertexList.get(1);
            arr.add(vertexList.get(0));
            arr.add(recalculate(root));
        }
        return arr;
    }

    public static Vertex insert(Vertex root, long x) {
        List<Vertex> ab = split(root, x);
        List<Vertex> cd = split(ab.get(1), x + 1);
        int rand = (int) (100 + (Math.random() * (500_000 - 100)));
        Vertex c = new Vertex(x, rand, null, null);
        return merge(ab.getFirst(), merge(c, cd.getLast()));
    }

    public static Vertex remove(Vertex root, long x) {
        List<Vertex> ab = split(root, x);
        List<Vertex> cd = split(ab.get(1), x + 1);
        return merge(ab.getFirst(), cd.getLast());
    }

    public static Vertex recalculate(Vertex root) {
        if (root != null) {
            root.size = sizeOf(root.left) + sizeOf(root.right) + 1;
        }
        return root;
    }

    public static long sizeOf(Vertex root) {
        if (root != null) {
            return root.size;
        } else {
            return 0;
        }
    }

    public static class Vertex {
        protected long data;
        private final int priority;
        protected Vertex left;
        protected Vertex right;
        protected long size = 1;
        protected long flag;


        public Vertex(long data, int priority, Vertex left, Vertex right) {
            this.data = data;
            this.priority = priority;
            this.left = left;
            this.right = right;
        }

        public int priority() {
            return priority;
        }

    }
}
