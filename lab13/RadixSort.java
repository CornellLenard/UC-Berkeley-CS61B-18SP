import java.util.Queue;
import java.util.LinkedList;

/**
 * Class for doing Radix sort.
 * @author Akhil Batra, Alexander Hwang
 * @author   Lenard Zhang
 *
 */
public class RadixSort
{
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis)
    {
        // TODO: Implement LSD Sort
        if(asciis == null || asciis.length <= 1)
            return asciis;
        int maxLength = asciis[0].length();
        for(int i = 1; i < asciis.length; i++)
        {
            if(asciis[i].length() > maxLength)
                maxLength = asciis[i].length();
        }
        String[] sorted = new String[asciis.length];
        for(int i = 0; i < asciis.length; i++)
            sorted[i] = asciis[i];
        LinkedList<Integer>[] counts = new LinkedList[256];
        for(int i = 0; i < 256; i++)
            counts[i] = new LinkedList<Integer>();
        String[] temp = new String[asciis.length];
        for(int i = maxLength - 1; i >= 0; i--)
            sortHelperLSD(sorted, i, counts, temp);
        return sorted;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index, LinkedList<Integer>[] counts, String[] temp)
    {
        // Optional LSD helper method for required LSD radix sort
        for(int i = 0; i < asciis.length; i++)
        {
            if(index > asciis[i].length() - 1)
                counts[0].offer(i);
            else
                counts[(int)(asciis[i].charAt(index))].offer(i);
        }
        int count = 0;
        for(int i = 0; i < 256; i++)
            while(counts[i].isEmpty() == false)
                temp[count++] = asciis[counts[i].poll()];
        for(int i = 0; i < asciis.length; i++)
            asciis[i] = temp[i];
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index)
    {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }

}
