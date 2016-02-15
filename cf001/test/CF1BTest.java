import org.junit.Test;

import static org.junit.Assert.*;

public class CF1BTest {

    @Test
    public void testToExcel() {
        assertEquals("A1", CF1B.toExcel(1, 1));
        assertEquals("Z1", CF1B.toExcel(1, 26));
        assertEquals("AA1", CF1B.toExcel(1, 27));
        assertEquals("AZ1", CF1B.toExcel(1, 52));
        assertEquals("BDWGN1000000", CF1B.toExcel((int)1e6, (int)1e6));
        assertEquals("RZ228", CF1B.toExcel(228, 494));
    }

    @Test
    public void testToRC() {
        assertEquals("R1C1", CF1B.toRC(1, "A"));
        assertEquals("R1C27", CF1B.toRC(1, "AA"));
        assertEquals("R1000000C1000000", CF1B.toRC((int)1e6, "BDWGN"));
        assertEquals("R228C494", CF1B.toRC(228, "RZ"));
        assertEquals("R1000000C1000000", CF1B.convert("BDWGN1000000"));
    }


}