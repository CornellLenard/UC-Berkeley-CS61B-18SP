package lab9;

import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *  @author   Lenard Zhang
 */
public class MyHashMap<K, V> implements Map61B<K, V>
{

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap()
    {
        this(DEFAULT_SIZE);
    }

    public MyHashMap(int initialSize)
    {
        buckets = new ArrayMap[initialSize];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear()
    {
        size = 0;
        for(int i = 0; i < buckets.length; i++)
            buckets[i] = new ArrayMap<K, V>();
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key)
    {
        if(key == null)
            return 0;
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key)
    {
        int index = hash(key);
        return buckets[index].get(key);
    }

    /**
     * Resizes the buckets when the load factor is greater than MAX_LF
     * @param capacity  The length of the buckets after resizing
     */
    private void resize(int capacity)
    {
        ArrayMap<K, V>[] oldBuckets = buckets;
        buckets = new ArrayMap[capacity];
        for(int i = 0; i < buckets.length; i++)
            buckets[i] = new ArrayMap<K, V>();
        for(int i = 0; i < oldBuckets.length; i++)
        {
            for(K key : oldBuckets[i])
                buckets[hash(key)].put(key, oldBuckets[i].get(key));
        }
        oldBuckets = null;
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value)
    {
        if(loadFactor() > MAX_LF)
            resize(buckets.length * 2);
        int index = hash(key);
        if(buckets[index].containsKey(key) == false)
            size++;
        buckets[index].put(key, value);
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
        for(int i = 0; i < buckets.length; i++)
        {
            Set<K> subKeySet = buckets[i].keySet();
            for(K key : subKeySet)
                keySet.add(key);
        }
        return keySet;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key)
    {
        int index = hash(key);
        V res = buckets[index].remove(key);
        if(res != null)
            size--;
        return res;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value)
    {
        int index = hash(key);
        V res = buckets[index].remove(key, value);
        if(res != null)
            size--;
        return res;
    }

    @Override
    public Iterator<K> iterator()
    {
        return keySet().iterator();
    }

}
