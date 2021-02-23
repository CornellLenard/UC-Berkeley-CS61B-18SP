package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.Set;
import java.util.HashSet;

/**
 * The Percolation to model the percolation process.
 * @author   Lenard Zhang
 */
public class Percolation
{

    private int N;                                                   //the side length of the grid
    private boolean[][] sites;                                       //the reference to the grid
    private int numOpenSites;                                        //the number of the open sites
    private WeightedQuickUnionUF connected;                          //stores the number of the set each site belongs to
    private Set<Integer> topOpen;                                    //stores the set numbers of the top open sites
    private Set<Integer> bottomOpen;                                 //stores the set numbers of the bottom open sites
    private boolean isPercolated;                                    //is the gird percolated

    public Percolation(int N)
    {
        if(N <= 0)
            throw new IllegalArgumentException("N should be greater than 0 !");
        this.N = N;
        sites = new boolean[N][N];
        for(int i = 0; i < N; i++)
            for(int j = 0; j < N; j++)
                sites[i][j] = false;
        numOpenSites = 0;
        connected = new WeightedQuickUnionUF(N * N);
        topOpen = new HashSet<Integer>();
        bottomOpen = new HashSet<Integer>();
        isPercolated = false;
    }

    /**
     * Opens the site(row, col) if it is not open, and updates all sorts of status.
     * @param   row   The site's row index in the grid.
     * @param   col   The site's col index in the grid.
     */
    public void open(int row, int col)
    {
        if(row < 0 || row >= N || col < 0 || col >= N)
            throw new IndexOutOfBoundsException("Grid index out of bounds !");
        if(sites[row][col] == true)
            return;
        sites[row][col] = true;
        numOpenSites++;
        int setNumber = connected.find(row * N + col);
        if(row == 0)
            topOpen.add(setNumber);
        if(row == N - 1)
            bottomOpen.add(setNumber);
        updateNeighborConnection(row, col, row - 1, col);
        updateNeighborConnection(row, col, row + 1, col);
        updateNeighborConnection(row, col, row, col - 1);
        updateNeighborConnection(row, col, row, col + 1);
        setNumber = connected.find(row * N + col);
        if(topOpen.contains(setNumber) == true && bottomOpen.contains(setNumber) == true)
            isPercolated = true;
    }

    /**
     * Updates the connected, topOpen and bottomOpen.
     * @param   row       The site's row index in the grid.
     * @param   col       The site's col index in the grid.
     * @param   nextRow   The site's neighbor's row index in the grid.
     * @param   nextCol   The site's neighbor's col index in the grid.
     */
    private void updateNeighborConnection(int row, int col, int nextRow, int nextCol)
    {
        if(nextRow < 0 || nextRow >= N || nextCol < 0 || nextCol >= N || isOpen(nextRow, nextCol) == false)
            return;
        int index = row * N + col;
        int setNumber = connected.find(index);
        int nextIndex = nextRow * N + nextCol;
        int nextSetNumber = connected.find(nextIndex);
        connected.union(index, nextIndex);
        int newNextSetNumber = connected.find(nextIndex);
        if(topOpen.contains(setNumber) == true)
        {
            topOpen.remove(setNumber);
            topOpen.add(newNextSetNumber);
        }
        if(topOpen.contains(nextSetNumber) == true)
        {
            topOpen.remove(nextSetNumber);
            topOpen.add(newNextSetNumber);
        }
        if(bottomOpen.contains(setNumber) == true)
        {
            bottomOpen.remove(setNumber);
            bottomOpen.add(newNextSetNumber);
        }
        if(bottomOpen.contains(nextSetNumber) == true)
        {
            bottomOpen.remove(nextSetNumber);
            bottomOpen.add(newNextSetNumber);
        }
    }

    /**
     * Returns whether the site is open.
     * @param   row   The site's row index in the grid.
     * @param   col   The site's col index in the grid.
     * @return        Whether the site is open.
     */
    public boolean isOpen(int row, int col)
    {
        if(row < 0 || row >= N || col < 0 || col >= N)
            throw new IndexOutOfBoundsException("Grid index out of bounds !");
        return sites[row][col];
    }

    /**
     * Returns whether the site is full.
     * @param   row   The site's row index in the grid.
     * @param   col   The site's col index in the grid.
     * @return        Whether the site is full.
     */
    public boolean isFull(int row, int col)
    {
        if(row < 0 || row >= N || col < 0 || col >= N)
            throw new IndexOutOfBoundsException("Grid index out of bounds !");
        return topOpen.contains(connected.find(row * N + col));
    }

    /**
     * Returns the number of the sites that are open.
     * @return   The number of the sites that are open.
     */
    public int numberOfOpenSites()
    {
        return numOpenSites;
    }

    /**
     * Returns whether the grid is percolated.
     * @return   Whether the grid is percolated.
     */
    public boolean percolates()
    {
        return isPercolated;
    }

    public static void main(String[] args) {}

}






