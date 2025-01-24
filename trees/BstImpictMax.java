package tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class BstImpictMax {
    static java.util.Random r = new Random();

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in,
                    "UTF8"));
            try {
                int n = Integer.parseInt(reader.readLine());
                String line = reader.readLine();
                Vertex tree = null;
                StringTokenizer tokenizer = new StringTokenizer(line, " ");
                for (long i = 0; i < n; i++) {
                    tree = insert(tree, i, Long.parseLong(tokenizer.nextToken()), true);
                }
                int m = Integer.parseInt(reader.readLine());
                for (int i = 0; i < m; i++) {
                    line = reader.readLine();
                    tokenizer = new StringTokenizer(line, " ");
                    String operation = tokenizer.nextToken();
                    if (Objects.equals(operation, "m")) {
                        long l = Long.parseLong(tokenizer.nextToken());
                        long r = Long.parseLong(tokenizer.nextToken());
                        System.out.println(maxBetween(tree, l, r));
                    } else {
                        long l = Long.parseLong(tokenizer.nextToken());
                        long r = Long.parseLong(tokenizer.nextToken());
                        long val = Long.parseLong(tokenizer.nextToken());
                        add(tree, l, r, val);
                    }
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static Vertex merge(Vertex root1, Vertex root2) {
        push(root1);
        push(root2);
        if (root1 == null) {
            return root2;
        }
        if (root2 == null) {
            return root1;
        }
        if (root1.priority< root2.priority) {
            root1.right = merge(root1.right, root2);
            return recalculate(root1);
        } else {
            root2.left = merge(root1, root2.left);
            return recalculate(root2);
        }
    }
    public static List<Vertex> split(Vertex root, long x, long offset) {
        List<Vertex> arr = new ArrayList<>();
        if (root == null) {
            arr.add(null);
            arr.add(null);
            return arr;
        }
        push(root);
        if (offset + sizeOf(root.left) < x) {
            List<Vertex> vertexList = split(root.right, x, offset + sizeOf(root.left) + 1);
            root.right = vertexList.get(0);
            arr.add(recalculate(root));
            arr.add(vertexList.get(1));
            return arr;
        } else {
            List<Vertex> vertexList = split(root.left, x, offset);
            root.left = vertexList.get(1);
            arr.add(vertexList.get(0));
            arr.add(recalculate(root));
            return arr;
        }
    }

    public static Vertex insert(Vertex root, long x, long data, boolean shifting) {
        List<Vertex> ab = split(root, x, 0);
        Vertex b = ab.get(1);
        if (!shifting) {
            List<Vertex> cd = split(ab.get(1), 1, 0);
            b = cd.get(1);
        }
        long rand = r.nextLong();
        Vertex c = new Vertex(data, rand, null, null);
        return merge(ab.getFirst(), merge(c, b));
    }

    public static long get(Vertex root, long x) {
        List<Vertex> ab = split(root, x, 0);
        List<Vertex> cd = split(ab.get(1), 1, 0);
        long res = cd.getFirst().data;
        merge(ab.getFirst(), merge(cd.getFirst(), cd.getLast()));
        return res;
    }
    public static Vertex add(Vertex root, long left, long right, long value) {
        push(root);
        List<Vertex> ab = split(root, left - 1, 0);
        List<Vertex> cd = split(ab.get(1), right - left + 1, 0);
        update(cd.getFirst(), value);
        return merge(ab.getFirst(), merge(cd.getFirst(), cd.getLast()));
    }

    public static long sizeOf(Vertex root) {
        push(root);
        if (root != null) {
            return root.size;
        } else {
            return 0;
        }
    }

    public static Vertex recalculate(Vertex root) {
        push(root);
        if (root != null) {
            root.size = sizeOf(root.left) + sizeOf(root.right) + 1;
            root.max = Math.max(dataOf(root), Math.max(maxOf(root.left), maxOf(root.right)));
        }
        return root;
    }
    public static long maxBetween(Vertex root, long left, long right) {
        List<Vertex> ab = split(root, left - 1, 0);
        List<Vertex> cd = split(ab.get(1), right - left + 1, 0);
        long res = maxOf(cd.getFirst());
        merge(ab.getFirst(), merge(cd.getFirst(), cd.getLast()));
        return res;
    }
    public static long dataOf(Vertex root) {
        push(root);
        if (root != null) {
            return root.data;
        } else {
            return 0;
        }
    }
    public static long maxOf(Vertex root) {
        push(root);
        if (root != null) {
            return root.max;
        } else {
            return 0;
        }
    }

    public static void update(Vertex root, long flag) {
        if (root != null) {
            root.flag += flag;
        }
    }

    public static void push(Vertex root) {
        if (root != null && root.flag != 0) {
            root.data += root.flag;
            root.max += root.flag;
            update(root.left, root.flag);
            update(root.right, root.flag);
            root.flag = 0;
        }
    }

    public static class Vertex {
        protected long data;
        protected final long priority;
        protected Vertex left;
        protected Vertex right;
        protected long size = 1;
        protected long flag;
        protected long max;


        public Vertex(long data, long priority, Vertex left, Vertex right) {
            this.data = data;
            this.priority = priority;
            this.left = left;
            this.right = right;
            this.max = data;
        }
    }
}
