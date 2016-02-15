import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// http://codeforces.com/problemset/problem/1/B
public class CF1B {

    final static Pattern rcFormat = Pattern.compile("(?i)^R(\\d+)C(\\d+)$");
    final static Pattern excelFormat = Pattern.compile("(?i)^([A-Z]+)(\\d+)$");

    public static int fromLetter(char c) {
        assert c >= 'A' && c <= 'Z';
        return (int) c - 64;
    }

    public static int fromLetters(String s) {
        int sum = 0;
        final int len = s.length();
        // iterate right to left
        for (int i = 0; i < s.length(); i++) {
            final char c = s.charAt(len - i - 1);
            sum += (Math.pow(26, i) * fromLetter(c));
        }
        return sum;
    }

    public static char toLetter(int i) {
        assert i > 0 && i <= 26;
        return (char) (i + 64);
    }

    public static String toLetters(int i) {
        final int quot = i / 26;
        final int rem = i % 26;
        if (i <= 26) {
            return String.valueOf(toLetter(i));
        } else if (rem == 0) {
            return toLetters(quot - 1) + toLetter(26);
        } else {
            return toLetters(quot) + toLetter(rem);
        }
    }

    public static String toExcel(int row, int col) {
        return toLetters(col) + row;
    }

    public static String toRC(int row, String col) {
        return String.format("R%dC%d", row, fromLetters(col));
    }

    public static String convert(String in) {
        final Matcher m = rcFormat.matcher(in);
        if (m.find()) {
            return toExcel(Integer.valueOf(m.group(1)), Integer.valueOf(m.group(2)));
        } else {
            // assume it's the Excel format
            final Matcher m2 = excelFormat.matcher(in);
            m2.find();
            return toRC(Integer.valueOf(m2.group(2)), m2.group(1));
        }
    }

    public static void main(String[] args){
        try (Scanner s = new Scanner(System.in)) {
            int n = s.nextInt();
            while (n > 0) {
                System.out.println(convert(s.next()));
                n--;
            }

        }
    }
}
