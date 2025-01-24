package tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class BstRsq {
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
                line = reader.readLine();
                while (line != null) {
                    tokenizer = new StringTokenizer(line, " ");
                    String operation = tokenizer.nextToken();
                    long l = Long.parseLong(tokenizer.nextToken());
                    long r = Long.parseLong(tokenizer.nextToken());
                    if (operation.equals("sum")) {
                        System.out.println(sumBetween(tree, l - 1, r));
                    } else {
                        tree = update(tree, l - 1, r);
                    }
                    line = reader.readLine();
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    public static void print(Vertex root) {
        if (root == null) {
            return;
        }
        print(root.left);
        System.out.print(root.data);
        System.out.print(" ");
        print(root.right);
    }
    public static Vertex merge(Vertex root1, Vertex root2) {
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

    public static Vertex update(Vertex root, long x, long data) {
        List<Vertex> ab = split(root, x, 0);
        List<Vertex> cd = split(ab.get(1), x + 1, sizeOf(ab.getFirst()));
        long rand = r.nextLong();
        Vertex c = new Vertex(data, rand, null, null);
        return merge(ab.getFirst(), merge(c, cd.getLast()));
    }


    public static long get(Vertex root, long x) {
        List<Vertex> ab = split(root, x, 0);
        List<Vertex> cd = split(ab.get(1), 1, 0);
        long res = cd.getFirst().data;
        merge(ab.getFirst(), merge(cd.getFirst(), cd.getLast()));
        return res;
    }

    public static long sizeOf(Vertex root) {
        if (root != null) {
            return root.size;
        } else {
            return 0;
        }
    }

    public static Vertex recalculate(Vertex root) {
        if (root != null) {
            root.size = sizeOf(root.left) + sizeOf(root.right) + 1;
            root.sum = sumOf(root.left) + sumOf(root.right) + dataOf(root);
        }
        return root;
    }
    public static long sumBetween(Vertex root, long left, long right) {
        List<Vertex> ab = split(root, left, 0);
        List<Vertex> cd = split(ab.getLast(), right - left, 0);
        long res = sumOf(cd.getFirst());
        merge(ab.getFirst(), merge(cd.getFirst(), cd.getLast()));
        return res;
    }
    public static long dataOf(Vertex root) {
        if (root != null) {
            return root.data;
        } else {
            return 0;
        }
    }
    public static long sumOf(Vertex root) {
        if (root != null) {
            return root.sum;
        } else {
            return 0;
        }
    }

    public static class Vertex {
        protected long data;
        protected final long priority;
        protected Vertex left;
        protected Vertex right;
        protected long size = 1;
        protected long flag;
        protected long sum;


        public Vertex(long data, long priority, Vertex left, Vertex right) {
            this.data = data;
            this.priority = priority;
            this.left = left;
            this.right = right;
            this.sum = data;
        }
    }
}
