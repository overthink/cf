import org.junit.Test;

import static org.junit.Assert.*;

public class CF1CTest {
    @Test
    public void testDblFormat() {
        assertEquals("0.333333", CF1C.fmtDouble(1/3d));
        assertEquals("0.666667", CF1C.fmtDouble(2/3d));
        assertEquals("145077.133344", CF1C.fmtDouble(145077.13334388));
        assertEquals("1.000000", CF1C.fmtDouble(1));
    }

    private CF1C.Point p(double x, double y) {
        return new CF1C.Point(x, y);
    }

    @Test
    public void testPoint() {
        CF1C.Point p = p(1/3d, 6/7d);
        assertEquals("0.333333 0.857143", p.toString());
    }

    @Test
    public void testTriangleArea() {
        assertEquals(4.5d, CF1C.triangleArea(p(0, 0), p(3, 0), p(1.5,3)), 1e-6);
        assertEquals(117d, CF1C.triangleArea(p(6, 8), p(14, 18), p(43,25)), 1e-6);
        assertEquals(6d, CF1C.triangleArea(p(0, 0), p(3, 0), p(3,4)), 1e-6); // right
        assertEquals(0d, CF1C.triangleArea(p(0, 0), p(3, 0), p(5,0)), 1e-6); // co-linear
    }

    @Test
    public void testRotate() {
        assertEquals(p(1, 0), CF1C.rotateAbout(p(0,0), p(0, 1), -90));
        assertEquals(p(4.414214, 0.585786), CF1C.rotateAbout(p(3,2), p(5, 2), -45));
    }

    @Test
    public void testFindTheta() {
        // data created with geogebra.org
        assertEquals(72d, CF1C.findTheta(p(5.88, 5.22), p(6.2, 7.17), p(6.767296, 3.454326), p(4.124325, 6.126921)), 1e-6);
    }

    private CF1C.Line L(double A, double B, double C) {
        return new CF1C.Line(A, B, C);
    }

    private CF1C.Line lfp(CF1C.Point p1, CF1C.Point p2) {
        return CF1C.lineFromPoints(p1, p2);
    }

    private CF1C.Point intersect(CF1C.Line l1, CF1C.Line l2) {
        return CF1C.intersection(l1, l2);
    }

    private void linesEqual(CF1C.Line l1, CF1C.Line l2) {
        //System.out.println(l1.A + " " + l1.B + " " + l1.C);
        //System.out.println(l2.A + " " + l2.B + " " + l2.C);
        assertEquals("A", l1.A, l2.A, 1e-6);
        assertEquals("B", l1.B, l2.B, 1e-6);
        assertEquals("C", l1.C, l2.C, 1e-6);
    }

    private CF1C.Line perp(CF1C.Line l, CF1C.Point p) {
        return CF1C.perpendicular(l, p);
    }

    @Test
    public void testLineFromPoints() {
        linesEqual(L(-3, 3, 0), lfp(p(2, 2), p(5, 5)));
        linesEqual(L(-3, 3, -6), lfp(p(2, 4), p(5, 7)));
        linesEqual(L(-10, 0, -30), lfp(p(-3, -2), p(-3, 8)));
        linesEqual(L(-1, 0, 3), lfp(p(3, 1), p(3, 2))); // vert
        linesEqual(L(0, 1, -1), lfp(p(1, 1), p(2, 1))); // horiz
    }

    @Test
    public void testIntersection() {
        assertEquals(p(0, 0), intersect(lfp(p(1, 1), p(2, 2)), lfp(p(-1, 1), p(-2, 2))));
        assertEquals(p(0.5, 0.5), intersect(lfp(p(1, 1), p(2, 2)), lfp(p(-1, 2), p(-2, 3))));
        assertEquals("vertical line", p(3, 3), intersect(lfp(p(3, 1), p(3, 2)), lfp(p(1, 1), p(2, 2))));
        assertEquals("horizontal line", p(1, 1), intersect(lfp(p(1, 1), p(2, 1)), lfp(p(1, 1), p(2, 2))));
        assertEquals("horizontal & vertical lines", p(3, 2), intersect(lfp(p(1, 2), p(4, 2)), lfp(p(3, 1), p(3, 5))));
        assertEquals("horizontal & vertical lines", p(3, 1), intersect(lfp(p(1, 1), p(2, 1)), lfp(p(3, 1), p(3, 2))));
        // test no itersection (parallel)
        try {
            assertEquals("parallel", p(3, 1), intersect(lfp(p(1, 1), p(2, 1)), lfp(p(3, 2), p(5, 2))));
            fail("should throw when parallel");
        } catch (RuntimeException e) {
            // ignored
        }
    }

    @Test
    public void testPerpendicular() {
        linesEqual(L(3, -1, 5), perp(L(1, 3, 3), p(0, 5)));
    }

    @Test
    public void testSolve() {
        //assertEquals("1.000000", CF1C.solve(p(0, 0), p(1, 1), p(0, 1)));
        //assertEquals("9.240058", CF1C.solve(p(4.13, 6.13), p(6.201089, 7.169821), p(6.765665, 3.462833)));
        assertEquals("23949.552164", CF1C.solve(p(-46.482632, -31.161247), p(19.689679, -70.646972), p(-17.902656, -58.455808)));
    }
}