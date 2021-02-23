/**
 * The Node, which is the node of the LinkedListDeque.
 * @param    <T>
 * @author   Lenard Zhang
 */
class Node<T>
{

    public T data;
    public Node<T> pred;
    public Node<T> succ;

    public Node(T d, Node<T> p, Node<T> s)
    {
        data = d;
        pred = p;
        succ = s;
    }

    public Node(Node<T> p, Node<T> s)
    {
        pred = p;
        succ = s;
    }

}

/**
 * The LinkedListDeque that implements Deque.
 * The core data structure is the Linked List.
 * @param    <T>
 * @author   Lenard Zhang
 */
public class LinkedListDeque<T>
{

    private Node<T> header;
    private Node<T> trailer;
    private int size;

    private T helperGetRecursive(Node<T> p, int index)
    {
        if(index == 0)
            return p.data;
        p = p.succ;
        return helperGetRecursive(p, index - 1);
    }

    public LinkedListDeque()
    {
        header = new Node<T>(null, null);
        trailer = new Node<T>(null, null);
        header.succ = trailer;
        trailer.pred = header;
        size = 0;
    }

    public void addFirst(T item)
    {
        Node<T> p = new Node<T>(item, header, header.succ);
        p.succ.pred = p;
        header.succ = p;
        size++;
    }

    public void addLast(T item)
    {
        Node<T> p = new Node<T>(item, trailer.pred, trailer);
        p.pred.succ = p;
        trailer.pred = p;
        size++;
    }

    public int size()
    {
        return size;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    public void printDeque()
    {
        Node<T> p = header.succ;
        while(p != trailer)
        {
            System.out.print(p.data + " ");
            p = p.succ;
        }
    }

    public T removeFirst()
    {
        if(size == 0)
            return null;
        T res = header.succ.data;
        header.succ = header.succ.succ;
        header.succ.pred = header;
        size--;
        return res;
    }

    public T removeLast()
    {
        if(size == 0)
            return null;
        T res = trailer.pred.data;
        trailer.pred.pred.succ = trailer;
        trailer.pred = trailer.pred.pred;
        size--;
        return res;
    }

    public T get(int index)
    {
        if(index < 0 || index >= size)
            return null;
        Node<T> p = header.succ;
        for(; index > 0; index--)
            p = p.succ;
        return p.data;
    }

    public T getRecursive(int index)
    {
        if(index < 0 || index >= size)
            return null;
        return helperGetRecursive(header.succ, index);
    }

}