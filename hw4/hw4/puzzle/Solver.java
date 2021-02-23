package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import java.util.Set;
import java.util.HashSet;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

/**
 * The Solver to solve the given puzzle.
 * @author   Lenard Zhang
 */
public class Solver
{

    private MinPQ<SearchNode> pq;                                    //stores the possible "next nodes"
    private Set<WorldState> previousWorldStates;                     //stores the WorldState of the nodes on the path
    private SearchNode finalNode;                                    //the final node

    /**
     * The Constructor which solves the puzzle, computing everything necessary for moves() and solution()
     * to not have to solve the problem again.
     * Solves the puzzle using the A* algorithm.
     * Assumes a solution exists.
     * @param   initial   The initial WorldState.
     */
    public Solver(WorldState initial)
    {
        pq = new MinPQ<SearchNode>(new SearchNode.SearchNodeComparator());
        previousWorldStates = new HashSet<WorldState>();
        SearchNode p = new SearchNode(initial, 0, null);
        previousWorldStates.add(p.worldState);
        while(p.worldState.isGoal() == false)
        {
            for(WorldState neighbor : p.worldState.neighbors())
            {
                if(previousWorldStates.contains(neighbor) == true)
                    continue;
                pq.insert(new SearchNode(neighbor, p.previousStepsNum + 1, p));
            }
            p = pq.delMin();
            previousWorldStates.add(p.worldState);
        }
        finalNode = p;
    }

    /**
     * Returns the minimum number of moves to solve the puzzle starting at the initial WorldState.
     * @return   The minimum number of moves to solve the puzzle starting at the initial WorldState.
     */
    public int moves()
    {
        return finalNode.previousStepsNum;
    }

    /**
     * Returns a sequence of WorldStates from the initial WorldState to the final node's WorldState.
     * @return   A sequence of WorldStates from the initial WorldState to the final node's WorldState.
     */
    public Iterable<WorldState> solution()
    {
        Stack<WorldState> st = new Stack<WorldState>();
        SearchNode p = finalNode;
        while(p != null)
        {
            st.push(p.worldState);
            p = p.previousNode;
        }
        Queue<WorldState> q = new LinkedList<WorldState>();
        while(st.isEmpty() == false)
            q.offer(st.pop());
        return q;
    }

}