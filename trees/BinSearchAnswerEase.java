package tree;

import java.util.Scanner;

public class BinSearchAnswerEase {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int x = in.nextInt();
        int y = in.nextInt();
        System.out.println(binarySearchRight(n - 1, x, y));

    }

    public static boolean checkRight(int t, int x, int y, int n) {
        return t / x + t / y < n;
    }

    public static int binarySearchRight(int n, int x, int y) {
        int left = 0;
        int right = n * Math.max(x, y);
        while (right - left > 1) {
            int t = (left + right) / 2;
            if (checkRight(t, x, y, n)) {
                left = t;
            } else {
                right = t;
            }
        }
        return right + Math.min(x, y);
    }
}
