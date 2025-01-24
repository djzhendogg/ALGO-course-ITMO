import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DrunkPlay {
    public static void main(String[] args) {
        int parts = 0;
        Scanner in = new Scanner(System.in);
        String nIn = in.nextLine();
        int n = Integer.parseInt(nIn);
        String firstIn = in.nextLine();
        String[] firstStr = firstIn.split("\\s");
        MyQueue first = new MyQueue(firstStr);

        String secondIn = in.nextLine();
        String[] secondStr = secondIn.split("\\s");
        MyQueue second = new MyQueue(secondStr);


        while (!first.isEmpty() && !second.isEmpty() && parts <= 2_000_00) {
            int fc = first.pop();
            int sc = second.pop();
            if (Math.abs(fc - sc) == n - 1) {
                if (fc == 0) {
                    first.push(fc);
                    first.push(sc);
                } else {
                    second.push(fc);
                    second.push(sc);
                }
            } else {
                if (fc < sc) {
                    second.push(fc);
                    second.push(sc);
                } else {
                    first.push(fc);
                    first.push(sc);
                }
            }
            parts++;
        }
        if (first.isEmpty()) {
            System.out.println("second " + parts);
        } else if (second.isEmpty()) {
            System.out.println("first " + parts);
        } else {
            System.out.println("draw");
        }
    }

    public static class MyQueue {
        private List<Integer> s1 = new ArrayList<>();
        private List<Integer> s2 = new ArrayList<>();

        public MyQueue(String[] strs) {
            for (int i = strs.length - 1; i >= 0; i--) {
                s2.add(Integer.parseInt(strs[i]));
            }
        }

        public void push(int item) {
            s1.add(item);
        }

        public int pop() {
            if (s2.isEmpty()) {
                while (!s1.isEmpty()) {
                    s2.add(s1.getLast());
                    s1.removeLast();
                }
            }
            int el = s2.getLast();
            s2.removeLast();
            return el;
        }

        public boolean isEmpty() {
            return s1.isEmpty() && s2.isEmpty();
        }
    }
}
