package tree;

import java.util.ArrayList;
import java.util.List;

public class BstBasic {
    public static void main(String[] args) {
        int[] a = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        Vertex tree = null;
        for (int i = 0; i < a.length; i++) {
            tree = insert(tree, i, a[i], true);
        }

        print(tree);
        System.out.print('\n');
        System.out.println(sumBetween(tree, 3, 7));
        insert(tree, 4 - 1, 5, false);
        insert(tree, 7 - 1, 200, false);
        System.out.println(sumBetween(tree, 3, 7));
        print(tree);
    }
    public static void print(Vertex root) {
        if (root == null) {
            return;
        }
        push(root.left);
        print(root.left);
        System.out.print(root.data);
        System.out.print(" ");
        push(root.right);
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
        if (root1.priority() < root2.priority()) {
            root1.right = merge(root1.right, root2);
            return recalculate(root1);
        } else {
            root2.left = merge(root1, root2.left);
            return recalculate(root2);
        }
    }
    public static List<Vertex> split(Vertex root, long x, long offset) {
        push(root);
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
        int rand = (int) (100 + (Math.random() * (1000 - 100)));
        Vertex c = new Vertex(data, rand, null, null);
        return merge(ab.getFirst(), merge(c, b));
    }

    public static Vertex remove(Vertex root, long x) {
        List<Vertex> ab = split(root, x, 0);
        List<Vertex> cd = split(ab.get(1), 1, 0);
        return merge(ab.getFirst(), cd.getLast());
    }
    public static long get(Vertex root, long x) {
        List<Vertex> ab = split(root, x, 0);
        List<Vertex> cd = split(ab.get(1), 1, 0);
        long res = cd.getFirst().data;
        merge(ab.getFirst(), merge(cd.getFirst(), cd.getLast()));
        return res;
    }
    public static Vertex add(Vertex root, long left, long right, long value) {
        List<Vertex> ab = split(root, left - 1, 0);
        List<Vertex> cd = split(ab.get(1), right - left + 1, 0);
        update(cd.getFirst(), value);
        return merge(ab.getFirst(), merge(cd.getFirst(), cd.getLast()));
    }

    public static boolean find(Vertex root, long x) {
        List<Vertex> ab = split(root, x, 0);
        List<Vertex> cd = split(ab.get(1), 1, 0);
        boolean res = cd.getFirst() != null;
        merge(ab.getFirst(), merge(cd.getFirst(), cd.getLast()));
        return res;
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
            root.sum = sumOf(root.left) + sumOf(root.right) + dataOf(root);
        }
        return root;
    }

    public static long sumOf(Vertex root) {
        push(root);
        if (root != null) {
            return root.sum;
        } else {
            return 0;
        }
    }
    public static long sumBetween(Vertex root, long left, long right) {
        List<Vertex> ab = split(root, left - 1, 0);
        List<Vertex> cd = split(ab.get(1), right - left + 1, 0);
        long res = sumOf(cd.getFirst());
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
    public static void update(Vertex root, long flag) {
        if (root != null) {
            root.flag += flag;
        }
    }
    public static void push(Vertex root) {
        if (root != null && root.flag != 0) {
            root.data += root.flag;
            root.sum += root.flag * root.size;
            update(root.left, root.flag);
            update(root.right, root.flag);
            root.flag = 0;
        }
    }
    public static class Vertex {
        protected long data;
        private final int priority;
        protected Vertex left;
        protected Vertex right;
        protected long size = 1;
        protected long sum;
        protected long flag;


        public Vertex(long data, int priority, Vertex left, Vertex right) {
            this.data = data;
            this.sum = data;
            this.priority = priority;
            this.left = left;
            this.right = right;
        }

        public int priority() {
            return priority;
        }

    }
}
