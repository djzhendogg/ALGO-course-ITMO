import java.util.Scanner;

public class CountingSort {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = in.nextInt();
        }
        countingSort(array);
        for (int i : array) {
            System.out.print(i + " ");
        }
    }
    public static void countingSort(int[] arr) {
        int[] count = new int[101];
        for (int k : arr) {
            count[k]++;
        }
        int pointer = 0;
        for (int i = 0; i < count.length; i++) {
            for (int j = 0; j < count[i]; j++) {
                arr[pointer++] = i;
            }
        }
    }
}
