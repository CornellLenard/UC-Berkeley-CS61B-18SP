package lab11.graphs;

import java.util.Queue;
import java.util.LinkedList;

/**
 * The MazeBreadthFirstPaths that extends MazeExplorer to perform Breadth First Search to find a qualified path.
 * @author   Lenard Zhang
 */
public class MazeBreadthFirstPaths extends MazeExplorer
{
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;                                                   //the source point's 1D coordinate
    private int t;                                                   //the target point's 1D coordinate
    private boolean targetFound = false;                             //tells whether the target has been reached
    private Maze maze;                                               //the reference to the maze

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY)
    {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs()
    {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        marked[s] = true;
        announce();
        Queue<Integer> q = new LinkedList<Integer>();
        q.offer(s);
        int v = 0;
        while(q.isEmpty() == false)
        {
            v = q.poll();
            if(v == t)
                targetFound = true;
            if(targetFound == true)
                return;
            for(int u : maze.adj(v))
            {
                if(marked[u] == false)
                {
                    marked[u] = true;
                    edgeTo[u] = v;
                    announce();
                    distTo[u] = distTo[v] + 1;
                    q.offer(u);
                }
            }
        }
    }

    @Override
    public void solve()
    {
        // bfs();
        bfs();
    }

}

