/**
 * The interface Deque.
 * @param    <T>
 * @author   Lenard Zhang
 */
public interface Deque<T>
{
    /**
     * Adds the item to the front of the Deque.
     * @param   item   The item that is going to be added to the front of the Deque.
     */
    public void addFirst(T item);

    /**
     * Adds the item to the end of the Deque.
     * @param   item   The item that is going to be added to the last of the Deque.
     */
    public void addLast(T item);

    /**
     * Returns whether the Deque is empty.
     * @return   Whether the Deque is empty.
     */
    public boolean isEmpty();

    /**
     * Returns how many items are in the Deque currently.
     * @return   How many items are in the Deque currently.
     */
    public int size();

    /**
     * Prints the items in the Deque.
     */
    public void printDeque();

    /**
     * Removes the first item in the Deque and returns it.
     * @return   The first item in the Deque.
     */
    public T removeFirst();

    /**
     * Removes the last item in the Deque and returns it.
     * @return   The last item in the Deque.
     */
    public T removeLast();

    /**
     * Returns the item whose index in the Deque is index.
     * @param   index   The index of the item.
     * @return          The item whose index in the Deque is index.
     */
    public T get(int index);

}