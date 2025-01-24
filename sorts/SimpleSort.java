import java.util.Random;
import java.util.Scanner;

public class SimpleSort {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = in.nextInt();
        }
        quickSort(array, 0, n - 1);
        for (int i : array) {
            System.out.print(i + " ");
        }

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
