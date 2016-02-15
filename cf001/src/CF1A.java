import java.util.Scanner;

// http://codeforces.com/problemset/problem/1/A
public class CF1A {

    // testable
    public static String main0(long n, long m, long a) {
        final long ndivs = (long) Math.ceil((double) n / a);
        final long mdivs = (long) Math.ceil((double) m / a);
        return String.format("%d", ndivs * mdivs);
    }

    public static void main(String[] args){
        try (Scanner s = new Scanner(System.in)) {
            System.out.println(main0(s.nextLong(), s.nextLong(), s.nextLong()));
        }
    }
}