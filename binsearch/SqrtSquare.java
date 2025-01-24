import java.util.Scanner;

public class SqrtSquare {
    static final double EPS = 10e-11;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        double C = in.nextDouble();
        System.out.println(binarySearchRight(C));

    }
    public static boolean checkRight(double mid, double c) {
        return mid * mid + Math.sqrt(mid) >= c;
    }

    public static double binarySearchRight(double c) {
        double left = 0;
        double right = 10e10;
        while (right - left > EPS) {
            double mid = (left + right) / 2;
            if (checkRight(mid, c)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }
}
