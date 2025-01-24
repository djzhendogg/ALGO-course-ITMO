package tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class BstReverse {
    static Random r = new Random();
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in,
                    "UTF8"));
            try {
                String line = reader.readLine();
                StringTokenizer tokenizer = new StringTokenizer(line, " ");
                Vertex tree = null;
                int n = Integer.parseInt(tokenizer.nextToken());
                int m = Integer.parseInt(tokenizer.nextToken());

                for (long i = 1; i <= n; i++) {
                    tree = insert(tree, i-1, i, true);
                }
                for (int i = 0; i < m; i++) {
                    line = reader.readLine();
                    tokenizer = new StringTokenizer(line, " ");
                    long l = Long.parseLong(tokenizer.nextToken());
                    long r = Long.parseLong(tokenizer.nextToken());
                    addMark(tree, l - 1, r);
                }
                print(tree);
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
        push(root);
        print(root.left);
        System.out.print(root.data);
        System.out.print(" ");
        print(root.right);
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
        if (root1.priority < root2.priority) {
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
            root.right = vertexList.getFirst();
            arr.add(recalculate(root));
            arr.add(vertexList.getLast());
            return arr;
        } else {
            List<Vertex> vertexList = split(root.left, x, offset);
            root.left = vertexList.getLast();
            arr.add(vertexList.getFirst());
            arr.add(recalculate(root));
            return arr;
        }
    }

    public static Vertex insert(Vertex root, long x, long data, boolean shifting) {
        List<Vertex> ab = split(root, x, 0);
        Vertex b = ab.getLast();
        if (!shifting) {
            List<Vertex> cd = split(b, 1, 0);
            b = cd.getLast();
        }
        long rand = r.nextLong();
        Vertex c = new Vertex(data, rand, null, null);
        return merge(ab.getFirst(), merge(c, b));
    }

    public static Vertex remove(Vertex root, long x) {
        List<Vertex> ab = split(root, x, 0);
        List<Vertex> cd = split(ab.getLast(), 1, 0);
        return merge(ab.getFirst(), cd.getLast());
    }

    public static long get(Vertex root, long x) {
        List<Vertex> ab = split(root, x, 0);
        List<Vertex> cd = split(ab.getLast(), 1, 0);
        long res = cd.getFirst().data;
        merge(ab.getFirst(), merge(cd.getFirst(), cd.getLast()));
        return res;
    }

    public static Vertex addMark(Vertex root, long left, long right) {
        push(root);
        List<Vertex> ab = split(root, left, 0);
        List<Vertex> cd = split(ab.getLast(), right - left, 0);
        update(cd.getFirst(), true);
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
        }
        return root;
    }

    public static void update(Vertex root, boolean f) {
        if (root != null) {
            root.flag ^= f;
        }
    }

    public static void push(Vertex root) {
        if (root != null) {
            update(root.right, root.flag);
            update(root.left, root.flag);
            if (root.flag) {
                Vertex rightToLeft = root.right;
                root.right = root.left;
                root.left = rightToLeft;
                root.flag = false;
            }
        }
    }

    public static class Vertex {
        protected long data;
        protected final long priority;
        public Vertex left;
        public Vertex right;
        public long size = 1;
        public boolean flag = false;


        public Vertex(long data, long priority, Vertex left, Vertex right) {
            this.data = data;
            this.priority = priority;
            this.left = left;
            this.right = right;
        }
    }
}
