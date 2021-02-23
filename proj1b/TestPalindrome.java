import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    } //Uncomment this class once you've created your Palindrome class.

    @Test
    public void testIsPalindrome()
    {
        assertFalse(palindrome.isPalindrome("aaaab"));
        assertTrue(palindrome.isPalindrome("aabaa"));
        assertTrue(palindrome.isPalindrome("aabbaa"));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome(""));
    }

    @Test
    public void testOverloadedIsPalindrome()
    {
        CharacterComparator cc = new OffByOne();
        assertFalse(palindrome.isPalindrome("aaaab", cc));
        assertTrue(palindrome.isPalindrome("aabbb", cc));
        assertTrue(palindrome.isPalindrome("ccbabb", cc));
        assertTrue(palindrome.isPalindrome("a", cc));
        assertTrue(palindrome.isPalindrome("", cc));
    }
}
