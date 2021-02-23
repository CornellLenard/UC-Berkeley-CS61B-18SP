import java.util.Map;
import java.util.HashMap;

/**
 * The Trie.
 * @author   Lenard Zhang
 */
public class Trie
{
    /**
     * The TrieNode.
     */
    private class TrieNode
    {

        private boolean hasSuchWord;                                 //to judge whether the word is in the Trie
        private Map<Character, TrieNode> succMap;                    //stores the character-TrieNode map

        public TrieNode()
        {
            hasSuchWord = false;
            succMap = new HashMap<Character, TrieNode>();
        }

    }

    private TrieNode root;                                           //the reference to the root TrieNode

    public Trie()
    {
        root = new TrieNode();
    }

    /**
     * Puts the word into the Trie.
     * @param   word   The word that is going to be put into the Trie.
     */
    public void put(String word)
    {
        TrieNode p = root;
        for(int i = 0; i < word.length(); i++)
        {
            p.succMap.putIfAbsent(word.charAt(i), new TrieNode());
            p = p.succMap.get(word.charAt(i));
        }
        p.hasSuchWord = true;
    }

    /**
     * Returns whether the word is in the Trie.
     * @param   word   The word that is going te be judged.
     * @return         Whether the word is in the Trie.
     */
    public boolean contains(String word)
    {
        TrieNode p = root;
        for(int i = 0; i < word.length(); i++)
        {
            p = p.succMap.get(word.charAt(i));
            if(p == null)
                return false;
        }
        return p.hasSuchWord;
    }

    /**
     * Returns whether there are words who have such prefix.
     * @param   prefix   The prefix that is going to be judged.
     * @return           Whether there are words who have such prefix.
     */
    public boolean hasSuchPrefix(String prefix)
    {
        TrieNode p = root;
        for(int i = 0; i < prefix.length(); i++)
        {
            p = p.succMap.get(prefix.charAt(i));
            if(p == null)
                return false;
        }
        return true;
    }

}


