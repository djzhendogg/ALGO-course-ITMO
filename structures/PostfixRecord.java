import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PostfixRecord {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String firstIn = in.nextLine();
        String[] strLine = firstIn.split("\\s");
        List<Integer> stack = new ArrayList<>();
        for (String el: strLine) {
            if (el.equals("+")) {
                int res = stack.getLast() + stack.get(stack.size() - 2);
                stack.removeLast();
                stack.removeLast();
                stack.add(res);
            } else if (el.equals("-")) {
                int res = stack.get(stack.size() - 2) - stack.getLast();
                stack.removeLast();
                stack.removeLast();
                stack.add(res);
            } else if (el.equals("*")) {
                int res = stack.getLast() * stack.get(stack.size() - 2);
                stack.removeLast();
                stack.removeLast();
                stack.add(res);
            } else {
                stack.add(Integer.parseInt(el));
            }
        }
        System.out.println(stack.getFirst());
    }
}
