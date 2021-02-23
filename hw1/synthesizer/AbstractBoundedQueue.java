package synthesizer;

/**
 * The abstract class AbstractBoundedQueue that implements BoundedQueue.
 * @param    <T>
 * @author   Lenard Zhang
 */
public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T>
{

    protected int capacity;                                          //the capacity of the queue
    protected int fillCount;                                         //the number of the current items in the queue

    @Override
    public int capacity()
    {
        return capacity;
    }

    @Override
    public int fillCount()
    {
        return fillCount;
    }

}