package lab9;

import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.Stack;

/**
 * The BSTMap that implements the interface Map61B.
 * Utilizes the Binary Search Tree as core data structure.
 * @author   Lenard Zhang
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>
{

    private class Node
    {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        public Node(K k, V v)
        {
            key = k;
            value = v;
        }

    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear()
    {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p)
    {
        if(p == null)
            return null;
        int cmp = key.compareTo(p.key);
        if(cmp < 0)
            return getHelper(key, p.left);
        else if(cmp > 0)
            return getHelper(key, p.right);
        else
            return p.value;
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key)
    {
        if(key == null)
            throw new IllegalArgumentException("calls get() with a null key !");
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p)
    {
        Node hot = null;
        int cmp = 0;
        while(p != null)
        {
            cmp = key.compareTo(p.key);
            hot = p;
            if(cmp < 0)
                p = p.left;
            else if(cmp > 0)
                p = p.right;
            else
                break;
        }
        if(p == null)
            return hot;
        else
            return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value)
    {
        if(key == null)
            throw new IllegalArgumentException("calls put() with a null key !");
        if(root == null)
        {
            root = new Node(key, value);
            size = 1;
            return;
        }
        Node p = putHelper(key, value, root);
        if(key.compareTo(p.key) == 0)
            p.value = value;
        else
        {
            if(key.compareTo(p.key) < 0)
                p.left = new Node(key, value);
            else
                p.right = new Node(key, value);
            size++;
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size()
    {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet()
    {
        Set<K> keySet = new HashSet<K>();
        Node p = root;
        Stack<Node> st = new Stack<Node>();
        while(true)
        {
            if(p != null)
            {
                st.push(p);
                p = p.left;
            }
            else if(st.isEmpty() == false)
            {
                p = st.pop();
                keySet.add(p.key);
                p = p.right;
            }
            else
                break;
        }
        return keySet;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key)
    {
        if(size == 0)
            return null;
        Node p = putHelper(key, null, root);
        if(key.compareTo(p.key) != 0)
            return null;
        V value = p.value;
        boolean hasLeft = (p.left != null), hasRight = (p.right != null);
        Node hot = root;
        if(p != root)
        {
            while(hot.left != p && hot.right != p)
                hot = (key.compareTo(hot.key) < 0) ? hot.left : hot.right;
        }
        else
            hot = null;
        if(hasLeft == true && hasRight == true)
        {
            Node succ = getSuccessor(p);
            hot = root;
            while(hot.left != succ && hot.right != succ)
                hot = ((succ.key).compareTo(hot.key) < 0) ? hot.left : hot.right;
            if(succ.right == null)
            {
                if(hot.left == succ)
                    hot.left = null;
                else
                    hot.right = null;
            }
            else
            {
                if(hot.left == succ)
                    hot.left = succ.right;
                else
                    hot.right = succ.right;
            }
            p.key = succ.key;
            p.value = succ.value;
            succ = null;
        }
        else if(hasLeft == true || hasRight == true)
        {
            if(p == root)
            {
                root = (hasLeft == true) ? root.left : root.right;
                p = null;
            }
            else
            {
                if(hot.left == p)
                    hot.left = (p.left == null) ? p.right : p.left;
                else
                    hot.right = (p.left == null) ? p.right : p.left;
            }
        }
        else
        {
            if(p == root)
                root = null;
            else
            {
                if(hot.left == p)
                    hot.left = null;
                else
                    hot.right = null;
            }
        }
        size--;
        return value;
    }

    /**
     * Gets the successor of the Node p according to In-Order-Traversal.
     * @param   p   The Node p.
     * @return      The successor of the Node p according to In-Order-Traversal.
     */
    private Node getSuccessor(Node p)
    {
        Node q = root;
        Stack<Node> st = new Stack<Node>();
        while(true)
        {
            if(q != null)
            {
                st.push(q);
                q = q.left;
            }
            else if(st.isEmpty() == false)
            {
                q = st.pop();
                if((q.key).compareTo(p.key) > 0)
                    break;
                q = q.right;
            }
            else
                break;
        }
        return q;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value)
    {
        if(size == 0)
            return null;
        Node p = putHelper(key, value, root);
        if(key.compareTo(p.key) == 0 && value.equals(p.value) == true)
            return remove(key);
        return null;
    }

    @Override
    public Iterator<K> iterator()
    {
        return keySet().iterator();
    }

}
