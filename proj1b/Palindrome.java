/**
 * The Palindrome to perform the checking on palindromes.
 * @author   Lenard Zhang
 */
public class Palindrome
{
    /**
     * Adds every char of the word to the Deque.
     * @param   word   The chars of which are going to be added to the Deque.
     * @return         The Deque.
     */
    public Deque<Character> wordToDeque(String word)
    {
        Deque<Character> dq = new LinkedListDeque<Character>();
        for(int i = 0; i < word.length(); i++)
            dq.addLast(word.charAt(i));
        return dq;
    }

    /**
     * Returns whether the word is palindrome.
     * @param   word   The word that is going to be judged.
     * @return         Whether the word is palindrome.
     */
    public boolean isPalindrome(String word)
    {
        Deque<Character> dq = wordToDeque(word);
        boolean res = true;
        while(dq.size() > 1)
        {
            if(dq.removeFirst() != dq.removeLast())
            {
                res = false;
                break;
            }
        }
        return res;
    }

    /**
     * Returns whether the word is palindrome according to the rule of equal defined by cc.
     * @param   word   The word that is going to be judged.
     * @param   cc     Defines the rule of equal.
     * @return         Whether the word is palindrome according to the rule of equal defined by cc.
     */
    public boolean isPalindrome(String word, CharacterComparator cc)
    {
        Deque<Character> dq = wordToDeque(word);
        boolean res = true;
        while(dq.size() > 1)
        {
            if(cc.equalChars(dq.removeFirst(), dq.removeLast()) == false)
            {
                res = false;
                break;
            }
        }
        return res;
    }

}