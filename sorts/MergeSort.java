import java.util.Scanner;

public class MergeSort {
    public static long INV = 0;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = in.nextInt();
        }
        mergeSort(array, 0, array.length);
        System.out.print(INV);
    }

    public static void mergeSort(int[] arr, int left, int right) {
        if (left + 1 >= right) {
            return;
        }
        int mid = (left + right) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid, right);
        merge(arr, left, mid, right);
    }

    public static void merge(int[] arr, int left, int mid, int right) {
        int iterO = 0;
        int iterL = 0;
        int[] temp = new int[right - left];
        while ((left + iterO) < mid  && (mid + iterL) < right) {
            if (arr[left + iterO] < arr[mid + iterL]) {
                temp[iterO + iterL] = arr[left + iterO];
                iterO++;
            } else {
                temp[iterO + iterL] = arr[mid + iterL];
                iterL++;
                INV += mid - left - iterO;
            }
        }
        while (left + iterO < mid) {
            temp[iterO + iterL] = arr[left + iterO];
            iterO++;
//            INV++;
        }

        while (mid + iterL < right) {
            temp[iterO + iterL] = arr[mid + iterL];
            iterL++;
        }

        for (int i = 0; i < iterO + iterL; i++) {
            arr[left + i] = temp[i];
        }
    }
}