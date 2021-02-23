// TODO: Make sure to make this class a part of the synthesizer package
// package <package name>;
package synthesizer;

import java.util.Iterator;

/**
 * The ArrayRingBuffer that extends AbstractBoundedQueue.
 * @param    <T>
 * @author   Lenard Zhang
 */
//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T>
{
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Creates a new ArrayRingBuffer with the given capacity.
     * @param   capacity   The given capacity.
     */
    public ArrayRingBuffer(int capacity)
    {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        this.capacity = capacity;
        first = 0;
        last = 0;
        fillCount = 0;
        rb = (T[])new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     * @param   x   The item that is going to be added to the end of the ring buffer.
     */
    @Override
    public void enqueue(T x)
    {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        if(fillCount == capacity)
            throw new RuntimeException("Ring Buffer Overflow");
        rb[last] = x;
        last = (last + 1) % capacity;
        fillCount++;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     * @Return   The item in the front of the ring buffer.
     */
    @Override
    public T dequeue()
    {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update
        if(fillCount == 0)
            throw new RuntimeException("Ring Buffer Underflow");
        T res = rb[first];
        first = (first + 1) % capacity;
        fillCount--;
        return res;
    }

    /**
     * Return oldest item, but don't remove it.
     * @return   The item in the front of the ring buffer.
     */
    @Override
    public T peek()
    {
        // TODO: Return the first item. None of your instance variables should change.
        if(fillCount == 0)
            throw new RuntimeException("Ring Buffer Underflow");
        return rb[first];
    }

    /**
     * Overrides the iterator() from the Iterable<T>.
     * @return   The iterator.
     */
    @Override
    public Iterator<T> iterator()
    {
        return new ArrayRingBufferIterator();
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.
    /**
     * Defines an iterator class to enable iteration on ArrayRingBuffer<T>.
     */
    private class ArrayRingBufferIterator implements Iterator<T>
    {

        private int ptr;

        public ArrayRingBufferIterator()
        {
            ptr = first;
        }

        @Override
        public boolean hasNext()
        {
            return ptr != last;
        }

        @Override
        public T next()
        {
            T res = rb[ptr];
            ptr = (ptr + 1) % capacity;
            return res;
        }

    }

}
