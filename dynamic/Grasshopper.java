package dynamic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Grasshopper {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in,
                    "UTF8"));
            try {
                StringTokenizer tokenizer = new StringTokenizer(reader.readLine(), " ");
                int n = Integer.parseInt(tokenizer.nextToken());
                int k = Integer.parseInt(tokenizer.nextToken());

                int[] vals = new int[n + 1];
                vals[0] = 0;
                vals[n] = 0;

                tokenizer = new StringTokenizer(reader.readLine(), " ");
                for (int i = 1; i < n - 1; i++) {
                    vals[i] = Integer.parseInt(tokenizer.nextToken());
                }
                List<Step> fullDP = createDP(n, k, vals);
                ArrayDeque<Integer> res = createPath(fullDP);

                System.out.println(fullDP.getLast().money);
                System.out.println(res.size() - 1);
                StringBuilder str = new StringBuilder();
                while (!res.isEmpty()) {
                    str.append(res.removeFirst()).append(" ");
                }
                System.out.println(str);

            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static List<Step> createDP(int n, int k, int[] vals) {
        List<Step> dp = new ArrayList<>();
        dp.add(new Step(0, -1));
        dp.add(new Step(vals[1], 0));

        for (int i = 2; i <= n; i++) {
             Step prev = dp.getLast();
             long maxMoney = prev.money + vals[i];
             int bestFrom = i - 1;
             int big = Math.max(i - k, 0);
             for (int j = big; j < i - 1; j++) {
                 long tmp = dp.get(j).money + vals[i];
                 if (tmp > maxMoney) {
                     bestFrom = j;
                     maxMoney = tmp;
                 }
             }
             dp.add(new Step(maxMoney, bestFrom));
         }
        return dp;
    }

    public static ArrayDeque<Integer> createPath(List<Step> dp) {
        ArrayDeque<Integer> res = new ArrayDeque<>();

        Step last = dp.getLast();
        int start = last.from;
        while (start != -1) {
            res.addFirst(start + 1);
            start = dp.get(start).from;
        }
        return res;
    }

    public static class Step {
        public long money;
        public int from;

        public Step(long money, int from) {
            this.money = money;
            this.from = from;
        }
    }
}
