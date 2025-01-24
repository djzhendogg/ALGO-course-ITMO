import java.util.Scanner;

public class AproxBinSearch {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int[] base = new int[n];
        for (int i = 0; i < n; i++) {
            base[i] = in.nextInt();
        }
        for (int i = 0; i < k; i++) {
            int x = in.nextInt();
            int l = base[binarySearchLeft(base, x)];
            int r = base[binarySearchRight(base, x)];
            if (Math.abs(x - l) > Math.abs(x - r)) {
                System.out.println(r);
            } else if (Math.abs(x - l) < Math.abs(x - r)) {
                System.out.println(l);
            } else {
                System.out.println(Math.min(r, l));
            }
        }
    }
    public static boolean checkRight(int[] arr, int mid, int x) {
        return arr[mid] >= x;
    }

    public static boolean checkLeft(int[] arr, int mid, int x) {
        return arr[mid] <= x;
    }

    public static int binarySearchRight(int[] arr, int x) {
        int left = -1;
        int right = arr.length;
        while (right - left > 1) {
            int mid = (left + right) / 2;
            if (checkRight(arr, mid, x)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        if (right >= arr.length) {
            right = arr.length - 1;
        }
        return right;
    }

    public static int binarySearchLeft(int[] arr, int x) {
        int left = -1;
        int right = arr.length;
        while (right - left > 1) {
            int mid = (left + right) / 2;
            if (checkLeft(arr, mid, x)) {
                left = mid;
            } else {
                right = mid;
            }
        }
        if (left < 0) {
            left = 0;
        }
        return left;
    }

}
