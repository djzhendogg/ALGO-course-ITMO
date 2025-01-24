package tree;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BstVertex {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in,
                    "UTF8"));
            try {
                int n = Integer.parseInt(reader.readLine());
                List<Vertex1> arr = new ArrayList<>();
                arr.add(new Vertex1(0, 0, 0));
                for (int i = 1; i <= n; i++) {
                    String line = reader.readLine();
                    StringTokenizer tokenizer = new StringTokenizer(line, " ");
                    arr.add(
                        new Vertex1(
                                Integer.parseInt(tokenizer.nextToken()),
                                Integer.parseInt(tokenizer.nextToken()),
                                Integer.parseInt(tokenizer.nextToken())
                        )
                    );
                }
                int rootId = Integer.parseInt(reader.readLine());
                boolean tree = checkTree(rootId, arr, true);

                if (!tree) {
                    System.out.println("NO");
                } else {
                    System.out.println("YES");
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    public static boolean checkTree(int id, List<Vertex1> arr, boolean tree) {
        if (!tree) {
            return false;
        }
        boolean treeNow = true;
        if (arr.get(id).leftId > -1) {
            treeNow = checkLeft(id, arr, true);
            treeNow = checkTree(arr.get(id).leftId, arr, treeNow);
        }
        if (treeNow) {
            if (arr.get(id).rightId > -1) {
                treeNow = checkRight(id, arr, treeNow);
                treeNow = checkTree(arr.get(id).rightId, arr, treeNow);
            }
        }
        return  treeNow;
    }
    public static boolean checkLeft(int id, List<Vertex1> arr, boolean tree) {
        return arr.get(arr.get(id).leftId).key < arr.get(id).key;
    }

    public static boolean checkRight(int id, List<Vertex1> arr, boolean tree) {
        return arr.get(arr.get(id).rightId).key > arr.get(id).key;
    }

    public record Vertex1(int key, int leftId, int rightId) {}

}
