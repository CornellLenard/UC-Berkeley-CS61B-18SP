package lab11.graphs;

import java.util.Random;

/**
 * The MazeCycles that extends MazeExplorer to solve the cycle-detection problem.
 * @author   Lenard Zhang
 */
public class MazeCycles extends MazeExplorer
{
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int[] assistEdgeTo;                                      //just like egdeTo, stores the path information
    private boolean cycleFound = false;                              //tells whether there are cycles in the graph

    public MazeCycles(Maze m)
    {
        super(m);
    }

    @Override
    public void solve()
    {
        // TODO: Your code here!
        assistEdgeTo = new int[maze.V()];
        Random rand = new Random();
        int sx = rand.nextInt(maze.N());
        int sy = rand.nextInt(maze.N());
        int s = maze.xyTo1D(sx, sy);
        dfs(s);
    }

    // Helper methods go here
    private void dfs(int v)
    {
        marked[v] = true;
        announce();
        for(int u : maze.adj(v))
        {
            if(marked[u] == false)
            {
                assistEdgeTo[u] = v;
                announce();
                dfs(u);
            }
            if(marked[u] == true && assistEdgeTo[v] != u)
            {
                assistEdgeTo[u] = v;
                int pred = v;
                edgeTo[pred] = assistEdgeTo[pred];
                while(pred != u)
                {
                    pred = assistEdgeTo[pred];
                    edgeTo[pred] = assistEdgeTo[pred]
                }
                cycleFound = true;
                announce();
                return;
            }
            if(cycleFound == true)
                return;
        }
    }

}

