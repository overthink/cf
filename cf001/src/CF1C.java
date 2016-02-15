import java.util.Scanner;

// http://codeforces.com/problemset/problem/1/C
public class CF1C {

    public static String fmtDouble(double d) {
        return String.format("%.6f", d);
    }

    public static class Point {
        private static final double EPSILON = 1e-5;  // Thought this should be 1e-6, but it fails
        public final double x;
        public final double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return fmtDouble(x) + " " + fmtDouble(y);
        }

        @Override
        public boolean equals(Object that) {
            if (this == that) return true;
            if (that == null || getClass() != that.getClass()) return false;
            Point p = (Point) that;
            return (Math.abs(this.x - p.x) <= EPSILON) && (Math.abs(this.y - p.y) <= EPSILON);
        }

        @Override
        public int hashCode() {
            long temp = Double.doubleToLongBits(x);
            int result = (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(y);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }
    }

    // Ax + By + C = 0 form
    public static class Line {
        public final double A;
        public final double B;
        public final double C;

        public Line(double A, double B, double C) {
            this.A = A;
            this.B = B;
            this.C = C;
        }
    }

    // Solve system of equations: Ax+By+C=0, Dx+Ey+F=0
    public static Point intersection(Line L1, Line L2) {
        double delta = L1.B*L2.A - L1.A*L2.B;
        if (delta == 0) {
            throw new RuntimeException("Lines are parallel, no intersection");
        }
        double x = (L1.C*L2.B - L1.B*L2.C) / delta;
        double y = (L1.A*L2.C - L1.C*L2.A) / delta;
        return new Point(x, y);
    }

    // Return's the line perpendicular to L through p.
    public static Line perpendicular(Line L, Point p) {
        // slope of Ax+By+C=0 is -A/B (put in y=mx+b form to see)
        // slope of perpendicular will be B/A (negative reciprocal)
        // in general form, this perpendicular is Bx-Ay-D=0 (sub slope B/A into y=mx+b form; we solve for D later)
        double A = L.B;
        double B = -L.A;
        double C = -B*p.y - A*p.x; // Solve for C in Ax+By+C=0
        return new Line(A, B, C);
    }

    // via wikipedia :(
    // (y - y0)(x1 - x0) = (y1 - y0)(x - x0)
    // expand, collect like terms, to get Ax+By+C form
    public static Line lineFromPoints(Point p1, Point p2) {
        double A = p1.y - p2.y;
        double B = p2.x - p1.x;
        double C = p2.y*p1.x - p2.x*p1.y;
        return new Line(A, B, C);
    }

    private static Point midpoint(Point a, Point b) {
        return new Point((a.x + b.x) / 2, (a.y + b.y) / 2);
    }

    // return the circumcenter of triangle abc
    public static Point findCircumcenter(Point a, Point b, Point c) {
        Point p1 = midpoint(a, b);
        Point p2 = midpoint (b, c);
        Line bisector1 = perpendicular(lineFromPoints(a, b), p1);
        Line bisector2 = perpendicular(lineFromPoints(b, c), p2);
        return intersection(bisector1, bisector2);
    }

    private static double deg2rad(double degrees) {
      return (degrees * Math.PI) / 180;
    }

    // Rotate p degrees counterclockwise about center.
    public static Point rotateAbout(Point center, Point p, double degrees) {
        // use 2d rotation matrix R = [cos theta -sin theta]
        //                            [sin theta  cos theta]
        // recall: this rotates about the origin
        double cos = Math.cos(deg2rad(degrees));
        double sin = Math.sin(deg2rad(degrees));
        // translate p to be relative to origin
        double px = p.x - center.x;
        double py = p.y - center.y;
        double newx = cos*px - sin*py;
        double newy = sin*px + cos*py;
        // translate back to original center and return
        return new Point(newx + center.x, newy + center.y);
    }

    // Find theta (degrees) s.t. rotating a by integer multiples of theta will generate b and c.
    public static double findTheta(Point center, Point a, Point b, Point c) {
        double theta = 0d;
        boolean gotb = false;
        boolean gotc = false;
        outer:
        for (int i = 3; i <= 100; i++) {
            theta = 360d / i;
            // System.out.println("# try theta: " + theta);
            for (int j = 1; j <= i; j++) {
                // System.out.println(theta*j);
                Point p = rotateAbout(center, a, theta * j);
                // System.out.println("b: " + b + " == " + p + " " + (p.equals(b)));
                // System.out.println("c: " + c + " == " + p + " " + (p.equals(c)));
                if (p.equals(b)) gotb = true;
                if (p.equals(c)) gotc = true;
                if (gotb && gotc) break outer;
            }
            gotb = false;
            gotc = false;
        }
        if (!gotb) {
           throw new RuntimeException("No solution");
        }
        return theta;
    }

    public static double triangleArea(Point a, Point b, Point c) {
        // area = abs((Ax(By-Cy) + Bx(Cy-Ay) + Cx(Ay-By)) / 2)
        // Derivation: draw bounding box around triangle, subtract
        // from the box's area the areas of the resulting negative space right triangles.
        return Math.abs((a.x*(b.y-c.y) + b.x*(c.y-a.y) + c.x*(a.y-b.y)) / 2);
    }

    public static String solve(Point a, Point b, Point c) {
        Point center = findCircumcenter(a, b, c);
        double theta = findTheta(center, a, b, c);
        Point x = rotateAbout(center, a, theta);
        double area = triangleArea(center, a, x);
        double result = (360 / theta) * area;
        return fmtDouble(result);
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Point a = new Point(s.nextDouble(), s.nextDouble());
        Point b = new Point(s.nextDouble(), s.nextDouble());
        Point c = new Point(s.nextDouble(), s.nextDouble());
        System.out.println(solve(a, b, c));
    }

}
