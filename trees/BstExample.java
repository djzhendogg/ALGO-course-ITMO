package tree;

import java.util.ArrayList;
import java.util.List;

public class BstExample {
    //    public static Map<Integer, Vertex> rememberAll(Vertex root, int k, Map<Integer, Vertex> map) {
//        if (root == null) {
//            return map;
//        }
//        root.id = k + sizeOf(root.left);
//        map.put(root.id, root);
//        rememberAll(root.left, k, map);
//        rememberAll(root.right, k + sizeOf(root.left) + 1, map);
//        return map;
//    }
//
//    public static void print(Vertex tree) {
//        StringBuilder str = new StringBuilder(Integer.toString(tree.key()));
//        str.append(" ");
//        if (tree.left == null) {
//            str.append("-1");
//        } else {
//            str.append(tree.left.id);
//        }
//        str.append(" ");
//        if (tree.right == null) {
//            str.append("-1");
//        } else {
//            str.append(tree.right.id);
//        }
//        System.out.println(str);
//
//    }
    public static Vertex merge(Vertex root1, Vertex root2) {
        if (root1 == null) {
            return root2;
        }
        if (root2 == null) {
            return root1;
        }
        if (root1.priority() < root2.priority()) {
            root1.right = merge(root1.right, root2);
//            return resize(root1);
            return root1;
        } else {
            root2.left = merge(root1, root2.left);
//            return resize(root2);
            return root2;
        }
    }
    public static List<Vertex> split(Vertex root, int x) {
        List<Vertex> arr = new ArrayList<>();
        if (root == null) {
            arr.add(null);
            arr.add(null);
            return arr;
        }
        if (root.key() < x) {
            List<Vertex> vertexList = split(root.right, x);
            root.right = vertexList.get(0);
//            root = resize(root);
            arr.add(root);
            arr.add(vertexList.get(1));
            return arr;
        } else {
            List<Vertex> vertexList = split(root.left, x);
            root.left = vertexList.get(1);
//            root = resize(root);
            arr.add(vertexList.get(0));
            arr.add(root);
            return arr;
        }
    }

    public static Vertex insert(Vertex root, int x) {
        List<Vertex> ab = split(root, x);
        List<Vertex> cd = split(ab.get(1), x + 1);
        int rand = (int) (100 + (Math.random() * (1000 - 100)));
        Vertex c = new Vertex(x, rand, 1, null, null);
        return merge(ab.getFirst(), merge(c, cd.get(1)));
    }

    public static Vertex remove(Vertex root, int x) {
        List<Vertex> ab = split(root, x);
        List<Vertex> cd = split(ab.get(1), x);
        return merge(ab.getFirst(), cd.getLast());
    }

    public static boolean find(Vertex root, int x) {
        List<Vertex> ab = split(root, x);
        List<Vertex> cd = split(ab.get(1), x);
        return cd.getFirst() != null;
    }

    public static String upperBound(Vertex root, int x) {
        List<Vertex> ab = split(root, x);
        List<Vertex> cd = split(ab.get(1), x);
        if (cd.getLast() == null) {
            return "none";
        } else {
            return Integer.toString(cd.getLast().key);
        }
    }

    public static String lowerBound(Vertex root, int x) {
        List<Vertex> ab = split(root, x);
//        List<Vertex> cd = split(ab.get(1), x);
        if (ab.getFirst() == null) {
            return "none";
        } else {
            return Integer.toString(ab.getFirst().key);
        }
    }

    //    public static int sizeOf(Vertex root) {
//        if (root != null) {
//            return root.size;
//        } else {
//            return 0;
//        }
//    }
//    public static Vertex resize(Vertex root) {
//        if (root != null) {
//            root.size = sizeOf(root.left) + sizeOf(root.right) + 1;
//        }
//        return root;
//    }
    public static class Vertex {
        private final int key;
        private final int priority;
        //        protected int size;
//        protected int id;
        protected Vertex left;
        protected Vertex right;

        public Vertex(int key, int priority, int size, Vertex left, Vertex right) {
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
