import org.junit.Test;

import static org.junit.Assert.*;

public class CF2ATest {

    @Test
    public void testSinglePlayer() {
        CF2A.Game g = new CF2A.Game();
        g.add("foo", 1);
        assertEquals("foo", g.winner());
    }

    @Test
    public void testEmpty() {
        CF2A.Game g = new CF2A.Game();
        try {
            g.winner();
            fail("should have thrown");
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void testSimple() {
        CF2A.Game g = new CF2A.Game();
        g.add("foo", 1);
        g.add("bar", 1);
        g.add("foo", 2);
        g.add("bar", -3);
        assertEquals("foo", g.winner());
    }

    @Test
    public void testTwoPlayerTie() {
        CF2A.Game g = new CF2A.Game();
        g.add("foo", 1);
        g.add("bar", 1);
        g.add("foo", 1);
        g.add("bar", 2);
        g.add("foo", 1);
        assertEquals("bar", g.winner());
    }

    @Test
    public void firstAchieverDrops() {
        CF2A.Game g = new CF2A.Game();
        g.add("foo", 10);
        g.add("bar", 1);
        g.add("foo", -10);
        g.add("bar", 9);
        g.add("quux", 10);
        assertEquals("bar", g.winner());
    }

    @Test
    public void manyPlayers() {
        CF2A.Game g = new CF2A.Game();
        g.add("foo", 1);
        g.add("bar", 1);
        g.add("quux", 1);
        g.add("bipppy", 1);
        g.add("bar", 1);
        g.add("quux", 1);
        g.add("foo", 1);
        assertEquals("bar", g.winner());
    }

    @Test
    public void doh() {
        CF2A.Game g = new CF2A.Game();
        g.add("foo", 1000);
        g.add("bar", 7);
        g.add("foo", -993);
        assertEquals("foo", g.winner());
    }

    @Test
    public void test6() {
        CF2A.Game g = new CF2A.Game();
        g.add("aawtvezfntstrcpgbzjbf", 681);
        g.add("zhahpvqiptvksnbjkdvmknb", -74);
        g.add("aawtvezfntstrcpgbzjbf", 661);
        g.add("jpdwmyke", 474);
        g.add("aawtvezfntstrcpgbzjbf", -547);
        g.add("aawtvezfntstrcpgbzjbf", 600);
        g.add("zhahpvqiptvksnbjkdvmknb", -11);
        g.add("jpdwmyke", 711);
        g.add("bjmj", 652);
        g.add("aawtvezfntstrcpgbzjbf", -1000);
        g.add("aawtvezfntstrcpgbzjbf", -171);
        g.add("bjmj", -302);
        g.add("aawtvezfntstrcpgbzjbf", 961);
        g.add("zhahpvqiptvksnbjkdvmknb", 848);
        g.add("bjmj", -735);
        assertEquals("aawtvezfntstrcpgbzjbf", g.winner());
    }

}