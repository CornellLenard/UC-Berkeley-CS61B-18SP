package lab14;

import lab14lib.Generator;

/**
 * The StrangeBitwiseGenerator that implements Generator to generate strange bitwise waves.
 * @author   Lenard Zhang
 */
public class StrangeBitwiseGenerator implements Generator
{

    private int period;
    private int state;

    public StrangeBitwiseGenerator(int p)
    {
        period = p;
        state = 0;
    }

    private double normalize()
    {
        return 2 * (state % period) / (double)(period) - 1;
    }

    private double normalizeWithParameter(int ws)
    {
        return 2 * (ws % period) / (double)(period) - 1;
    }

    public double next()
    {
        state++;
        double next = normalizeWithParameter(state & (state >>> 3) % period);
        System.out.println(next);
        if(next == 1)
            next = -1;
        return next;
    }

}