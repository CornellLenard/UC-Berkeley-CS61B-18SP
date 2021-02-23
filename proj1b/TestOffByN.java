import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByN = new OffByN(5);

    @Test
    public void testOffByN()
    {
        assertFalse(offByN.equalChars('a', 'b'));
        assertTrue(offByN.equalChars('a', 'f'));
        assertFalse(offByN.equalChars('g', 'a'));
        assertTrue(offByN.equalChars('g', 'b'));
        assertFalse(offByN.equalChars('a', 'a'));
    }
    // Your tests go here.
    //Uncomment this class once you've created your CharacterComparator interface and OffByN class.
}