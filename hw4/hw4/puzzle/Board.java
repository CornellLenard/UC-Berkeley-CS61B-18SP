package hw4.puzzle;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Arrays;

/**
 * The Board that implements WorldState.
 * @author   Lenard Zhang
 */
public class Board implements WorldState
{

    private int[][] tiles;
    private int N;                                                   //the side length of the tiles

    /**
     * Constructs a board from an N-by-N array of tiles where tiles[i][j] = tile at row i col j.
     * @param   tiles   The given tiles.
     */
    public Board(int[][] tiles)
    {
        this.tiles = copyTiles(tiles);
        N = tiles.length;
    }

    /**
     * Creates a new int[][] whose each item is the same as tiles'.
     * @param   tiles   The tiles whose each item is going to be copied.
     * @return          The new tiles, which are the same as the given ones.
     */
    private int[][] copyTiles(int[][] tiles)
    {
        int[][] res = new int[tiles.length][tiles.length];
        for(int i = 0; i < tiles.length; i++)
            for(int j = 0; j < tiles.length; j++)
                res[i][j] = tiles[i][j];
        return res;
    }

    /**
     * Returns value of tile at row i col j, the value is 0 if blank.
     * @param   i   The row index.
     * @param   j   The col index.
     * @return      The value of tile at row i col j.
     */
    public int tileAt(int i, int j)
    {
        if(i < 0 || i >= N || j < 0 || j >= N)
            throw new IndexOutOfBoundsException("Both row and col should value between 0 and N - 1 !");
        return tiles[i][j];
    }

    /**
     * Returns the board size N.
     * @return   The N.
     */
    public int size()
    {
        return N;
    }

    /**
     * Returns the four neighbors of the current board.
     * The definition of "neighbors" could be found in the following web page of the course.
     * https://sp18.datastructur.es/materials/hw/hw4/hw4
     * @return   The four neighbors of the current board.
     */
    public Iterable<WorldState> neighbors()
    {
        int zeroRow = 0, zeroCol = 0;
        for(int i = 0; i < N; i++)
        {
            for(int j = 0; j < N; j++)
            {
                if(tiles[i][j] == 0)
                {
                    zeroRow = i;
                    zeroCol = j;
                    break;
                }
            }
        }
        int[][] up = copyTiles(tiles);
        if(zeroRow != 0)
        {
            up[zeroRow][zeroCol] = up[zeroRow - 1][zeroCol];
            up[zeroRow - 1][zeroCol] = 0;
        }
        int[][] down = copyTiles(tiles);
        if(zeroRow != N - 1)
        {
            down[zeroRow][zeroCol] = down[zeroRow + 1][zeroCol];
            down[zeroRow + 1][zeroCol] = 0;
        }
        int[][] left = copyTiles(tiles);
        if(zeroCol != 0)
        {
            left[zeroRow][zeroCol] = left[zeroRow][zeroCol - 1];
            left[zeroRow][zeroCol - 1] = 0;
        }
        int[][] right = copyTiles(tiles);
        if(zeroCol != N - 1)
        {
            right[zeroRow][zeroCol] = right[zeroRow][zeroCol + 1];
            right[zeroRow][zeroCol + 1] = 0;
        }
        Queue<WorldState> neighbors = new LinkedList<WorldState>();
        neighbors.offer(new Board(up));
        neighbors.offer(new Board(down));
        neighbors.offer(new Board(left));
        neighbors.offer(new Board(right));
        return neighbors;
    }

    /**
     * Returns the hamming distance between the current tiles and the target tiles.
     * @return   The hamming distance between the current tiles and the target tiles.
     */
    public int hamming()
    {
        int count = 0;
        for(int i = 0; i < N; i++)
        {
            for(int j = 0; j < N; j++)
            {
                if(tiles[i][j] == 0)
                    continue;
                if(tiles[i][j] != i * N + j + 1)
                    count++;
            }
        }
        return count;
    }

    /**
     * Returns the manhattan distance between the current tiles and the target tiles.
     * @return   The manhattan distance between the current tiles and the target tiles.
     */
    public int manhattan()
    {
        int count = 0;
        for(int i = 0; i < N; i++)
        {
            for(int j = 0; j < N; j++)
            {
                if(tiles[i][j] == 0)
                    continue;
                count += Math.abs((tiles[i][j] - 1) / N - i);
                count += Math.abs((tiles[i][j] - 1) % N - j);
            }
        }
        return count;
    }

    /**
     * Returns the estimated distance to the goal.
     * This method should simply return the results of manhattan() when submitted to Gradescope.
     * @return   The estimated distance to the goal.
     */
    public int estimatedDistanceToGoal()
    {
        return manhattan();
    }

    @Override
    public int hashCode()
    {
        return Arrays.hashCode(tiles);
    }

    @Override
    public boolean equals(Object y)
    {
        if(y instanceof Board)
        {
            Board b = (Board)y;
            if(size() != b.size())
                return false;
            for(int i = 0; i < size(); i++)
                for(int j = 0; j < size(); j++)
                    if(tileAt(i, j) != b.tileAt(i, j))
                        return false;
            return true;
        }
        return false;
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for(int i = 0; i < N; i++)
        {
            for(int j = 0; j < N; j++)
                s.append(String.format("%2d ", tileAt(i, j)));
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
