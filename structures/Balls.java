import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Balls {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        List<Integer> list = new ArrayList<>();
        while (in.hasNextInt()) {
            list.add(in.nextInt());
        }
        int res = 0;
        int left = 0;
        int right = list.size() - 1;
        int count = 1;
        int lastColor = 100;

        for (int i = 0; i < list.size(); i++) {
            int now = list.get(i);
            if (now == lastColor) {
                count++;
                if (count >= 3) {
                    left = i - count;
                    right = i + 1;
                    res = count;
                }
            } else {
                count = 1;
                lastColor = now;
            }
        }
        while (left >= 0 && right < list.size()) {
            int li = list.get(left);
            int ri = list.get(right);
            int count2 = 2;
            if (li == ri) {
                while (left > 0 && list.get(left - 1) == li) {
                    left--;
                    count2++;
                }
                while (right < list.size() - 1 && list.get(right + 1) == ri) {
                    right++;
                    count2++;
                }
                if (count2 >= 3) {
                    res += count2;
                } else {
                    break;
                }
                if (left > 0) {
                    left--;
                }
                if (right < list.size() - 1) {
                    right++;
                }
            } else {
                break;
            }
        }
        System.out.println(res);
    }
}
