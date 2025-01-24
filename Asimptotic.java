import java.util.Scanner;

public class Asimptotic {
    public static void main(String[] args) {
        Scanner in  = new Scanner(System.in);
        String function = in.nextLine();
        String[] fStr = function.split("\\s*\\+\\s*");
        String time = in.nextLine();
        String[] timeStr = time.split("\\s*\\+\\s*");

        KPar forFun = calcMax(fStr);
        KPar forGan = calcMax(timeStr);
        if (forFun.p == 0 && forGan.p == 0) {
            if (forFun.hasX == forGan.hasX) {
                System.out.println("YES");
                System.out.println("YES");
                System.out.println("YES");
            } else if (forFun.hasX) {
                System.out.println("NO");
                System.out.println("YES");
                System.out.println("NO");
            } else if (forGan.hasX) {
                System.out.println("YES");
                System.out.println("NO");
                System.out.println("NO");
            } else {
                System.out.println("YES");
                System.out.println("YES");
                System.out.println("YES");
            }
        } else if (forFun.p == forGan.p) {
            System.out.println("YES");
            System.out.println("YES");
            System.out.println("YES");
        } else if (forFun.p > forGan.p) {
            System.out.println("NO");
            System.out.println("YES");
            System.out.println("NO");
        } else {
            System.out.println("YES");
            System.out.println("NO");
            System.out.println("NO");
        }

    }

    public static KPar calcMax(String[] seq) {
        int k = 0;
        int p = 0;
        boolean hasX = false;
        for (String term: seq) {
            int kex = 0;
            int pex = 0;
            String[] kp = term.split("x");
            if (kp.length == 0) {
                if (term.contains("x")) {
                    hasX = true;
                }
            } else if (kp.length == 1) {
                if (term.contains("x")) {
                    hasX = true;
                }
                kex = Integer.parseInt(kp[0]);
                if (kex > k) {
                    k = kex;
                }
            } else {
                if (kp[0].isEmpty()) {
                    k = 1;
                } else {
                    kex = Integer.parseInt(kp[0]);
                    if (kex > k) {
                        k = kex;
                    }
                }
                String[] rafl = kp[1].split("\\^");
                pex = Integer.parseInt(rafl[1]);
                if (pex > p) {
                    p = pex;
                }
            }
        }
        return new KPar(k, p, hasX);
    }

    public static class KPar {
        int k;
        int p;
        boolean hasX;

        public KPar(int k, int p, boolean hasX) {
            this.k = k;
            this.p = p;
            this.hasX = hasX;
        }
    }
}
