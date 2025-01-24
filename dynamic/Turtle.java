package dynamic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Turtle {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in,
                    "UTF8"));
            try {
                StringTokenizer tokenizer = new StringTokenizer(reader.readLine(), " ");
                int n = Integer.parseInt(tokenizer.nextToken());
                int m = Integer.parseInt(tokenizer.nextToken());

                int[][] vals = new int[n][m];

                for (int i = 0; i < n; i++) {
                    tokenizer = new StringTokenizer(reader.readLine(), " ");
                    for (int j = 0; j < m; j++) {
                        vals[i][j] = Integer.parseInt(tokenizer.nextToken());
                    }
                }
                Step[][] res = createDP(n, m, vals);
                System.out.println(createPath(n, m, res));

            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static Step[][] createDP(int n, int m, int[][] vals) {
        Step[][] res = new Step[n][m];
        res[0][0] = new Step(vals[0][0], (char) 0);
        for (int i = 1; i < m; i++) {
            res[0][i] = new Step(res[0][i - 1].money + vals[0][i], 'R');
        }
        for (int i = 1; i < n; i++) {
            res[i][0] = new Step(res[i - 1][0].money + vals[i][0], 'D');
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                long right = res[i][j - 1].money + vals[i][j];
                long down = res[i - 1][j].money + vals[i][j];
                if (right > down) {
                    res[i][j] = new Step(right, 'R');
                } else {
                    res[i][j] = new Step(down, 'D');
                }
            }
        }
        return res;
    }

    public static String createPath(int n, int m, Step[][] dp) {
        StringBuilder str = new StringBuilder();
        int nowN = n - 1;
        int nowM = m - 1;
        Step last = dp[nowN][nowM];
        long ans = last.money;
        str.append(ans).append("\n");
        StringBuilder path = new StringBuilder();
        char prev = last.from;

        while (prev != 0) {
            path.insert(0, prev);
            if (prev == 'R') {
                nowM--;
            } else {
                nowN--;
            }
            prev = dp[nowN][nowM].from;
        }
        str.append(path);
        return str.toString();
    }

    public static class Step {
        public long money;
        public char from;

        public Step(long money, char from) {
            this.money = money;
            this.from = from;
        }
    }

}
