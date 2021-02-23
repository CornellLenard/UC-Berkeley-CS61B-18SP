package hw3.hash;

import java.util.List;

/**
 * The OomageTestUtility to test whether the hashCode() method could lead to a nice spread.
 * @author   Lenard Zhang
 */
public class OomageTestUtility
{
    /**
     * Returns whether the hashCode() method could lead to a nice spread.
     * @param   oomages   Stores the oomage objects.
     * @param   M         The length of the bucket.
     * @return            Whether the hashCode() method could lead to a nice spread.
     */
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M)
    {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        int[] buckets = new int[M];
        for(int i = 0; i < M; i++)
            buckets[i] = 0;
        for(Oomage o : oomages)
            buckets[(o.hashCode() & 0x7FFFFFFF) % M]++;
        double lowerBound = (double)(oomages.size()) / 50;
        double upperBound = (double)(oomages.size()) / 2.5;
        for(int i = 0; i < M; i++)
        {
            if(buckets[i] < lowerBound || buckets[i] > upperBound)
                return false;
        }
        return true;
    }

}
