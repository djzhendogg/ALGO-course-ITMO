package tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BinarySearchTree {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in,
                    "UTF8"));
            try {
                Vertex tree = null;
                String line = reader.readLine();
                while (line != null) {
                    StringTokenizer tokenizer = new StringTokenizer(line, " ");
                    String operation = tokenizer.nextToken();
                    switch (operation) {
                        case "insert": tree = insert(tree, Integer.parseInt(tokenizer.nextToken()));
                            break;
                        case "exists": System.out.println(find(tree, Integer.parseInt(tokenizer.nextToken())));
                            break;
                        case "delete": tree = remove(tree, Integer.parseInt(tokenizer.nextToken()));
                            break;
                        case "next": System.out.println(upperBound(tree, Integer.parseInt(tokenizer.nextToken())));
                            break;
                        case "prev": System.out.println(lowerBound(tree, Integer.parseInt(tokenizer.nextToken())));
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

    public static Vertex merge(Vertex root1, Vertex root2) {
        if (root1 == null) {
            return root2;
        }
        if (root2 == null) {
            return root1;
        }
        if (root1.priority() < root2.priority()) {
            root1.right = merge(root1.right, root2);
            return root1;
        } else {
            root2.left = merge(root1, root2.left);
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
            arr.add(root);
            arr.add(vertexList.get(1));
            return arr;
        } else {
            List<Vertex> vertexList = split(root.left, x);
            root.left = vertexList.get(1);
            arr.add(vertexList.get(0));
            arr.add(root);
            return arr;
        }
    }

    public static Vertex insert(Vertex root, int x) {
        List<Vertex> ab = split(root, x);
        List<Vertex> cd = split(ab.get(1), x + 1);
        int rand = (int) (100 + (Math.random() * (1000 - 100)));
        Vertex c = new Vertex(x, rand, null, null);
        return merge(ab.getFirst(), merge(c, cd.get(1)));
    }

    public static Vertex remove(Vertex root, int x) {
        List<Vertex> ab = split(root, x);
        List<Vertex> cd = split(ab.get(1), x + 1);
        return merge(ab.getFirst(), cd.getLast());
    }

    public static boolean find(Vertex root, int x) {
        List<Vertex> ab = split(root, x);
        List<Vertex> cd = split(ab.get(1), x + 1);
        boolean res = cd.getFirst() != null;
        merge(ab.getFirst(), merge(cd.getFirst(), cd.getLast()));
        return res;
    }

    public static String upperBound(Vertex root, int x) {
        Vertex ans = root;
        Vertex result = null;
        while (ans != null) {
            if (ans.key() > x) {
                result = ans;
                ans = ans.left;
            } else {
                ans = ans.right;

            }
        }
        if (result == null) {
            return "none";
        } else {
            return Integer.toString(result.key());
        }
    }

    public static String lowerBound(Vertex root, int x) {
        Vertex ans = root;
        Vertex result = null;
        while (ans != null) {
            if (ans.key() < x) {
                result = ans;
                ans = ans.right;
            } else {
                ans = ans.left;

            }
        }
        if (result == null) {
            return "none";
        } else {
            return Integer.toString(result.key());
        }
    }

    public static class Vertex {
        private final int key;
        private final int priority;
        protected Vertex left;
        protected Vertex right;

        public Vertex(int key, int priority, Vertex left, Vertex right) {
            this.key = key;
            this.priority = priority;
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
