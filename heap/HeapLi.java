import java.util.Scanner;

public class HeapLi {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] a = new int[n + 1];
        boolean ans = true;
        for (int i = 1; i <= n; i++) {
            a[i] = in.nextInt();
        }
        for (int i = 1; i <= n / 2; i++) {
            if (2 * i <= n && a[i] > a[2 * i]) {
                ans = false;
                break;
            }
            if (2 * i + 1 <= n && a[i] > a[2 * i + 1]) {
                ans = false;
                break;
            }
        }
        if (ans) {
            System.out.print("YES");
        } else {
            System.out.print("NO");
        }
    }
}
