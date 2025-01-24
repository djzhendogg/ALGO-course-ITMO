package dynamic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class LargestIncreasingSubsequence {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in,
                    "UTF8"));
            try {
                StringTokenizer tokenizer = new StringTokenizer(reader.readLine(), " ");
                int n = Integer.parseInt(tokenizer.nextToken());
                long[] vals = new long[n];
                tokenizer = new StringTokenizer(reader.readLine(), " ");

                for (int i = 0; i < n; i++) {
                    vals[i] = Long.parseLong(tokenizer.nextToken());
                }
                System.out.println(createDP(n, vals));

            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static String createDP(int n, long[] vals) {
        Map<Long, Long> preRes = new HashMap<>();
        long[] dp = new long[n + 1];
        dp[0] = (long) -10E10;
        int maxLen = -1;
        for (int i = 1; i <= n; i++) {
            dp[i] = (long) 10E10;
        }

        for (long val : vals) {
            int id = binarySearchLeft(dp, val);
            if (dp[id + 1] < val) continue;
            dp[id + 1] = val;
            preRes.put(dp[id + 1], dp[id]);
            if (id + 1 > maxLen) {
                maxLen = id + 1;
            }
        }
        StringBuilder str = new StringBuilder();
        long start = dp[maxLen];
        while (start > dp[0]) {
            str.insert(0, start + " ");
            start = preRes.get(start);
        }
        return maxLen + "\n" + str;
    }

    public static boolean checkLeft(long[] arr, int mid, long x) {
        return arr[mid] < x;
    }

    public static int binarySearchLeft(long[] arr, long x) {
        int left = -1;
        int right = arr.length;
        while (right - left > 1) {
            int mid = (left + right) / 2;
            if (checkLeft(arr, mid, x)) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }
}
