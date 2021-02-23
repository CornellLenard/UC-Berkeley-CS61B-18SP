package synthesizer;

import java.util.Iterator;

/**
 * The interface BoundedQueue that extends Iterable.
 * @param    <T>
 * @author   Lenard Zhang
 */
public interface BoundedQueue<T> extends Iterable<T>
{
    /**
     * Returns the size of the buffer.
     * @return   The size of the buffer.
     */
    public int capacity();

    /**
     * Returns the number of the items currently in the buffer.
     * @return   The number of the items currently in the buffer.
     */
    public int fillCount();

    /**
     * Returns whether the queue is empty.
     * @return   Whether the queue is empty.
     */
    public default boolean isEmpty()
    {
        return fillCount() == 0;
    }

    /**
     * Returns whether the queue is full.
     * @return   Whether the queue is full.
     */
    public default boolean isFull()
    {
        return fillCount() == capacity();
    }

    /**
     * Adds item x to the end of the queue.
     * @param   x   The item that is going to be enqueued.
     */
    public void enqueue(T x);

    /**
     * Deletes the item in the front of the queue and returns it.
     * @return   The deleted item.
     */
    public T dequeue();

    /**
     * Returns the item in the front of the queue.
     * @return   The item in the front of the queue.
     */
    public T peek();

}