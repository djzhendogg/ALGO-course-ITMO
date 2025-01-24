import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StackSorter {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int now = 1;
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        List<String> answer = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            a.add(in.nextInt());
        }
        for (int i : a) {
            if (i == now) {
                answer.add("push");
                answer.add("pop");
                now++;
                while (!b.isEmpty() && now == b.getLast()) {
                    answer.add("pop");
                    now++;
                    b.removeLast();
                }
            } else {
                answer.add("push");
                b.add(i);
            }
        }
        if (b.isEmpty()) {
            for (String str : answer) {
                System.out.println(str);
            }
        } else {
            System.out.println("impossible");
        }
    }
}
