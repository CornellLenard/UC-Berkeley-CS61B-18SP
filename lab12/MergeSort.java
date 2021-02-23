import edu.princeton.cs.algs4.Queue;

/**
 * The MergeSort to perform the Merge Sort algorithm.
 * @author   Lenard Zhang
 */
public class MergeSort
{
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(Queue<Item> q1, Queue<Item> q2)
    {
        if(q1.isEmpty() == true)
            return q2.dequeue();
        else if(q2.isEmpty() == true)
            return q1.dequeue();
        else
        {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            // Make sure to call dequeue, so that the minimum item gets removed.
            if(q1Min.compareTo(q2Min) <= 0)
                return q1.dequeue();
            else
                return q2.dequeue();
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>> makeSingleItemQueues(Queue<Item> items)
    {
        // Your code here!
        Queue<Queue<Item>> res = new Queue<Queue<Item>>();
        Queue<Item> q = null;
        for(Item item : items)
        {
            q = new Queue<Item>();
            q.enqueue(item);
            res.enqueue(q);
        }
        return res;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(Queue<Item> q1, Queue<Item> q2)
    {
        // Your code here!
        Queue<Item> res = new Queue<Item>();
        while(q1.isEmpty() == false || q2.isEmpty() == false)
            res.enqueue(getMin(q1, q2));
        return res;
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> mergeSort(Queue<Item> items)
    {
        // Your code here!
        if(items.size() <= 1)
            return items;
        int mi = items.size() / 2;
        Queue<Item> q1 = new Queue<Item>();
        Queue<Item> q2 = new Queue<Item>();
        Queue<Queue<Item>> q = makeSingleItemQueues(items);
        for(int i = 0; i < mi; i++)
            q1.enqueue((q.dequeue()).peek());
        for(int i = mi; i < items.size(); i++)
            q2.enqueue((q.dequeue()).peek());
        Queue<Item> res1 = mergeSort(q1);
        Queue<Item> res2 = mergeSort(q2);
        return mergeSortedQueues(res1, res2);
    }

}
