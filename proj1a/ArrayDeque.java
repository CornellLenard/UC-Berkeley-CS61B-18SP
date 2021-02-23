/**
 * The ArrayDeque.
 * @param    <T>
 * @author   Lenard Zhang
 */
public class ArrayDeque<T>
{

    private T[] element;
    private int capacity;
    private int size;
    private int front;
    private int rear;

    public ArrayDeque()
    {
        capacity = 8;
        element = (T[]) new Object[capacity];
        size = 0;
        front = 0;
        rear = 0;
    }

    private void expand()
    {
        T[] oldElement = element;
        int oldCapacity = capacity;
        capacity *= 2;
        element = (T[]) new Object[capacity];
        element[0] = oldElement[front];
        int index = 1;
        for(int i = (front + 1) % oldCapacity; i != rear; i = (i + 1) % oldCapacity)
        {
            element[index] = oldElement[i];
            index++;
        }
        front = 0;
        rear = index;
        oldElement = null;
    }

    private void shrink()
    {
        T[] oldElement = element;
        int oldCapacity = capacity;
        capacity /= 2;
        element = (T[]) new Object[capacity];
        element[0] = oldElement[front];
        int index = 1;
        for(int i = front + 1; i != rear; i = (i + 1) % oldCapacity)
        {
            element[index] = oldElement[i];
            index++;
        }
        front = 0;
        rear = index;
        oldElement = null;
    }

    public void addFirst(T item)
    {
        if(size == capacity)
            expand();
        front = (front == 0) ? capacity - 1 : front - 1;
        element[front] = item;
        size++;
    }

    public void addLast(T item)
    {
        if(size == capacity)
            expand();
        element[rear] = item;
        rear = (rear + 1) % capacity;
        size++;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    public int size()
    {
        return size;
    }

    public void printDeque()
    {
        for(int i = front; i != rear; i = (i + 1) % capacity)
            System.out.print(element[i] + " ");
    }

    public T removeFirst()
    {
        if(size == 0)
            return null;
        T res = element[front];
        front = (front + 1) % capacity;
        size--;
        if(size * 4 < capacity && capacity > 8)
            shrink();
        return res;
    }

    public T removeLast()
    {
        if(size == 0)
            return null;
        rear = (rear == 0) ? capacity - 1 : rear - 1;
        T res = element[rear];
        size--;
        if(size * 4 < capacity && capacity > 8)
            shrink();
        return res;
    }

    public T get(int index)
    {
        if(index < 0 || index >= size)
            return null;
        return element[(front + index) % capacity];
    }

}