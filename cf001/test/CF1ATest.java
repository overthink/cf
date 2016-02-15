import org.junit.Test;
import static org.junit.Assert.*;

public class CF1ATest {
    @Test
    public void testMain() throws Exception {
        assertEquals("4", CF1A.main0(6, 6, 4));
        assertEquals("1000000000000000000", CF1A.main0(1000000000, 1000000000, 1));
        assertEquals("1", CF1A.main0(1, 1, 1));
        assertEquals("1", CF1A.main0(1, 1, 1000000000));
    }
}