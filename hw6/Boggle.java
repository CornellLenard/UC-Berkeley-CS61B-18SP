import java.io.File;
import edu.princeton.cs.algs4.In;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;

/**
 * The BoggleWordsComparator that implements Comparator to make String type comparable in the given way.
 * @author   Lenard Zhang
 */
class BoggleWordsComparator implements Comparator<String>
{
    @Override
    public int compare(String s1, String s2)
    {
        if(s1.length() != s2.length())
            return s2.length() - s1.length();
        else
            return s1.compareTo(s2);
    }

}

/**
 * The Boggle to perform the Boggle Game.
 * @author   Lenard Zhang
 */
public class Boggle
{
    // File path of dictionary file
    static String dictPath = "words.txt";
    private static int row;                                          //the row size of the board
    private static int col;                                          //the col size of the board
    private static char[][] board;                                   //the board
    private static boolean[][] marked;                               //to mark whether each board[i][j] is in the path
    private static Trie trie;                                        //stores all the words in the dictionary
    private static TreeSet<String> selectedWordsSet;                 //stores the words constructed from the board that are in the dictionary
    private static int[] moveX;                                      //helps with moving in X direction on the board
    private static int[] moveY;                                      //helps with moving in Y direction on the board

    /**
     * Performs Depth First Search to traverse all possible paths to generate words from row x col y.
     * @param   x        The x index of the starting location.
     * @param   y        The y index of the starting location.
     * @param   prefix   The current word.
     */
    private static void DFSForBoggle(int x, int y, String prefix)
    {
        if(x < 0 || x >= row || y < 0 || y >= col || marked[x][y] == true || trie.hasSuchPrefix(prefix) == false)
            return;
        prefix += Character.toString(board[x][y]);
        marked[x][y] = true;
        if(prefix.length() >= 3 && trie.contains(prefix) == true)
            selectedWordsSet.add(prefix);
        for(int i = 0; i < 8; i++)
            DFSForBoggle(x + moveX[i], y + moveY[i], prefix);
        marked[x][y] = false;
    }

    /**
     * Solves a Boggle puzzle.
     * 
     * @param k The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     *         The Strings are sorted in descending order of length.
     *         If multiple words have the same length,
     *         have them in ascending alphabetical order.
     */
    public static List<String> solve(int k, String boardFilePath)
    {
        // YOUR CODE HERE
        if(k <= 0)
            throw new IllegalArgumentException("k must be positive !");
        File dictFile = new File(dictPath);
        if(dictFile.exists() == false)
            throw new IllegalArgumentException("The dictionary file does not exist !");
        moveX = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
        moveY = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};
        In fileInput = new In(boardFilePath);
        String[] rawBoard = fileInput.readAllLines();
        row = rawBoard.length;
        col = rawBoard[0].length();
        board = new char[row][col];
        marked = new boolean[row][col];
        for(int i = 0; i < row; i++)
        {
            if(rawBoard[i].length() != col)
                throw new IllegalArgumentException("The input board is not rectangular !");
            for(int j = 0; j < col; j++)
            {
                board[i][j] = rawBoard[i].charAt(j);
            }
        }
        fileInput = new In(dictPath);
        trie = new Trie();
        String[] wordsInDict = fileInput.readAllLines();
        for(int i = 0; i < wordsInDict.length; i++)
            trie.put(wordsInDict[i]);
        BoggleWordsComparator comp = new BoggleWordsComparator();
        selectedWordsSet = new TreeSet<String>(comp);
        for(int i = 0; i < row; i++)
            for(int j = 0; j < col; j++)
                DFSForBoggle(i, j, "");
        List<String> res = new ArrayList<String>();
        while(res.size() < k)
        {
            if(selectedWordsSet.isEmpty() == true)
                break;
            res.add(selectedWordsSet.pollFirst());
        }
        return res;
    }

}



