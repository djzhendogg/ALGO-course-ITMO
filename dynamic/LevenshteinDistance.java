package dynamic;

import java.io.IOException;
import java.util.Scanner;

public class LevenshteinDistance {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String str1 = scanner.nextLine();
        String str2 = scanner.nextLine();

        int str1Len = str1.length();
        int str2Len = str2.length();

        int[][] dp = new int[str1Len + 1][str2Len + 1];

        for (int i = 1; i <= str1Len; i++) {
            dp[i][0] = i;
        }

        for (int i = 1; i <= str2Len; i++) {
            dp[0][i] = i;
        }

        for (int i = 1; i <= str1Len; i++) {
            for (int j = 1; j <= str2Len; j++) {
                int add = str1.charAt(i - 1) == str2.charAt(j - 1) ? 0: 1;
                int tmp = dp[i - 1][j - 1] + add;
                dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), tmp);
            }
        }

        System.out.println(dp[str1Len][str2Len]);
    }
}
