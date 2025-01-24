package dynamic;

import java.math.BigInteger;
import java.util.Scanner;

public class HorseStep {
    private final static long MOD = 1000_000_000;
    public static void main(String[] args) {
        int[] init = {0, 1, 2, 3, 4, 6, 7, 8, 9};
        int[][] base = {{4, 6}, {6, 8}, {7, 9}, {4, 8}, {0, 3, 9}, {}, {0, 1, 7}, {2, 6}, {1, 3}, {2, 4}};
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long byBig = answerByMod(n, init, base);
        System.out.println(byBig);
    }

    public static long answerByMod(int n, int[] init, int[][] base) {
        if (n == 1) {
            return 8;
        } else if (n == 2) {
            return 16;
        }
        long[][] dp = new long[10][n + 1];
        for (int id : init) {
            dp[id][2] = base[id].length;
        }

        for (int i = 3; i <= n; i++){
            for (int id : init) {
                int[] tmp = base[id];
                for (int k: tmp) {
                    dp[id][i] = (dp[id][i] + dp[k][i - 1]) % MOD;
                }
            }
        }
        long res = 0;
        for (int id : init) {
            res = (res + dp[id][n]) % MOD;
        }
        res = (res + MOD - dp[8][n]) % MOD;
        res = (res + MOD - dp[0][n]) % MOD;
        return res;
    }

    public static long answerBigInt(int n, int[] init, int[][] base) {
        if (n == 1) {
            return 8;
        } else if (n == 2) {
            return 16;
        }
        BigInteger[][] dp = new BigInteger[10][n + 1];
        for (int id : init) {
            dp[id][2] = BigInteger.valueOf(base[id].length);
        }

        for (int i = 3; i <= n; i++){
            for (int id : init) {
                int[] tmp = base[id];
                for (int k: tmp) {
                    if (dp[id][i] == null) dp[id][i] = BigInteger.ZERO;
                    dp[id][i] = dp[id][i].add(dp[k][i - 1]);
                }
            }
        }
        BigInteger res = BigInteger.ZERO;
        for (int id : init) {
            if (dp[id][n] == null) dp[id][n] = BigInteger.ZERO;
            res = res.add(dp[id][n]);
        }
        res = res.subtract(dp[8][n]);
        res = res.subtract(dp[0][n]);
        return Long.parseLong(res.mod(BigInteger.valueOf(MOD)).toString());
    }
}
