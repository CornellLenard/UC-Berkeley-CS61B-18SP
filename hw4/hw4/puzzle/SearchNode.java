package hw4.puzzle;

import java.util.Comparator;

/**
 * The SearchNode.
 * @author   Lenard Zhang
 */
public class SearchNode
{
    /* The following instance variables are package private */
    WorldState worldState;                                           //each node should store a WorldState
    int previousStepsNum;                                            //how many steps from the initial node to get here
    SearchNode previousNode;                                         //points to the previous node on the path

    public SearchNode(WorldState ws, int psn, SearchNode pn)
    {
        worldState = ws;
        previousStepsNum = psn;
        previousNode = pn;
    }

    /**
     * The SearchNodeComparator that implements Comparator so that the SearchNode is comparable.
     */
    public static class SearchNodeComparator implements Comparator<SearchNode>
    {
        public int compare(SearchNode sn1, SearchNode sn2)
        {
            int priority1 = sn1.worldState.estimatedDistanceToGoal() + sn1.previousStepsNum;
            int priority2 = sn2.worldState.estimatedDistanceToGoal() + sn2.previousStepsNum;
            if(priority1 < priority2)
                return -1;
            else if(priority1 > priority2)
                return 1;
            else
                return 0;
        }
    }

}