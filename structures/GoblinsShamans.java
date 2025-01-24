import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GoblinsShamans {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String nstr  = in.nextLine();
        int n = Integer.parseInt(nstr);
        MyQueue qStart = new MyQueue();
        MyQueue qFinish = new MyQueue();
        int sLen = 0;
        int fLen = 0;
        for (int i = 0; i < n; i++) {
            String operation = in.nextLine();
            String[] opList = operation.split("\\s");
            if (opList[0].equals("+")) {
                qFinish.pushBack(Integer.parseInt(opList[1]));
                fLen++;
            } else if (opList[0].equals("*")) {
                qFinish.pushFront(Integer.parseInt(opList[1]));
                fLen++;
            } else if (opList[0].equals("-")) {
                System.out.println(qStart.popFront());
                sLen--;
            }
            if (sLen < fLen) {
                qStart.pushBack(qFinish.popFront());
                sLen++;
                fLen--;
            }
        }

    }

    public static class MyQueue {
        private List<Integer> front = new ArrayList<>();
        private List<Integer> back = new ArrayList<>();
        private List<Integer> balance = new ArrayList<>();

        public void pushFront(int el) {
            front.add(el);
        }

        public void pushBack(int el) {
            back.add(el);
        }

        public int popBack() {
            int res = back.getLast();
            back.removeLast();
            return res;
        }

        public int popFront() {
            balancing();
            int res = front.getLast();
            front.removeLast();
            return res;
        }

        public void balancing() {
            if (front.isEmpty()) {
                for (int i = 0 ; i < back.size() / 2; i++) {
                    balance.add(back.getLast());
                    back.removeLast();
                }
                while (!back.isEmpty()) {
                    front.add(back.getLast());
                    back.removeLast();
                }
                while (!balance.isEmpty()) {
                    back.add(balance.getLast());
                    balance.removeLast();
                }
            }
        }
    }
}
