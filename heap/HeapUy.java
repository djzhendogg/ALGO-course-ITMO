import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HeapUy {
    public static void main(String[] args) {
        List<Integer> arr = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        for (int i = 0; i < n; i++) {
            int operation = in.nextInt();
            if (operation == 0) {
                int x = in.nextInt();
                insert(arr, x);
            } else if (operation == 1) {
                System.out.println(extract(arr));
            }
        }
    }

    public static void swap(List<Integer> arr, int f, int s) {
        int temp = arr.get(f);
        arr.set(f, arr.get(s));
        arr.set(s, temp);
    }

    public static void siftUp(List<Integer> arr, int pos) {
        int parent = (pos - 1) / 2;
        while (arr.get(parent) < arr.get(pos)) {
            swap(arr, parent, pos);
            pos = parent;
            parent = (parent - 1) / 2;
        }
    }

    public static void siftDown(List<Integer> arr, int pos) {
        int childLeft = 2 * pos + 1;
        while (childLeft < arr.size()) {
            int childRight = 2 * pos + 2;
            int swapWith = childLeft;
            if (childRight < arr.size() && arr.get(childLeft) < arr.get(childRight)) {
                swapWith = childRight;
            }
            if (arr.get(pos) >= arr.get(swapWith)) {
                break;
            }
            swap(arr, pos, swapWith);
            pos = swapWith;
            childLeft = 2 * pos + 1;
        }
    }

    public static int extract(List<Integer> arr) {
        int max = arr.getFirst();
        arr.set(0, arr.getLast());
        arr.removeLast();
        siftDown(arr, 0);
        return max;
    }

    public static void insert(List<Integer> arr, int x) {
        arr.add(x);
        siftUp(arr, arr.size() - 1);
    }
}
