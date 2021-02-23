import org.junit.Test;
import static org.junit.Assert.*;

/**
 * The TestArrayDequeGold.
 * @author   Lenard Zhang
 */
public class TestArrayDequeGold
{

    private static final int numOfCall = 1000;                       //the number of random method-call

    private static String message = "";                              //the failure message

    /**
     * Adds i to the front or the end of the ArrayDeque according to rand.
     * @param   rand     The random number to decide whether adds i to the front or the end.
     * @param   i        The element added to the ArrayDeque.
     * @param   stuArDq  The StudentArrayDeque.
     * @param   arDqSol  The ArrayDequeSolution.
     */
    private void randomAdd(double rand, int i, StudentArrayDeque<Integer> stuArDq, ArrayDequeSolution<Integer> arDqSol)
    {
        if(rand < 0.5)
        {
            stuArDq.addFirst(i);
            arDqSol.addFirst(i);
            message += "\naddFirst(" + i + ")";
        }
        else
        {
            stuArDq.addLast(i);
            arDqSol.addLast(i);
            message += "\naddLast(" + i + ")";
        }
    }

    /**
     * Removes the front or the end of the ArrayDeque according to rand.
     * @param   rand     The random number to decide whether removes the front or the end.
     * @param   stuArDq  The StudentArrayDeque.
     * @param   arDqSol  The ArrayDequeSolution.
     */
    private void randomRemove(double rand, StudentArrayDeque<Integer> stuArDq, ArrayDequeSolution<Integer> arDqSol)
    {
        int expectedInt = 0, actualInt = 0;
        if(rand < 0.5)
        {
            expectedInt = arDqSol.removeFirst();
            actualInt = stuArDq.removeFirst();
            message += "\nremoveFirst()";
        }
        else
        {
            expectedInt = arDqSol.removeLast();
            actualInt = stuArDq.removeLast();
            message += "\nremoveLast()";
        }
        assertEquals(message, expectedInt, actualInt);
    }

    public TestArrayDequeGold() {}

    /**
     * Randomly tests the ArrayDeque according to random number.
     */
    @Test
    public void testRandomized()
    {
        StudentArrayDeque<Integer> stuArDq = new StudentArrayDeque<Integer>();
        ArrayDequeSolution<Integer> arDqSol = new ArrayDequeSolution<Integer>();
        double rand1 = 0.0, rand2 = 0.0;
        for(int i = 0; i < numOfCall; i++)
        {
            rand1 = StdRandom.uniform();
            if(stuArDq.isEmpty() == true)
                randomAdd(rand1, i, stuArDq, arDqSol);
            else
            {
                rand2 = StdRandom.uniform();
                if(rand1 < 0.5)
                    randomAdd(rand2, i, stuArDq, arDqSol);
                else
                    randomRemove(rand2, stuArDq, arDqSol);
            }
        }
    }

}