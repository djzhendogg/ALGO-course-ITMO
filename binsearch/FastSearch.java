import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class FastSearch {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = in.nextInt();
        }
        quickSort(arr, 0, n - 1);
        int k = in.nextInt();
        for (int i = 0; i < k; i++) {
            int l = in.nextInt();
            int r = in.nextInt();
            int lBound = binarySearchRight(arr, l);
            int rBound = binarySearchLeft(arr, r);
            System.out.println(rBound - lBound + 1);
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
        return left;
    }

    public static void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        Random rand = new Random();
        int randId = rand.nextInt(right - left + 1) + left;
        int q = arr[randId];
        int i = left;
        int j = right;
        while (i <= j){
            while (arr[i] < q) {
                i++;
            }
            while (arr[j] > q) {
                j--;
            }
            if (i <= j) {
                int saveJ = arr[j];
                int saveI = arr[i];
                arr[i++] = saveJ;
                arr[j--] = saveI;
            }
        }
        quickSort(arr, left, j);
        quickSort(arr, j + 1, right);
    }
}
