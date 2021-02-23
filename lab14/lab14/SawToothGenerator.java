package lab14;

import lab14lib.Generator;

/**
 * The SawToothGenerator that implements Generator to generate saw tooth waves.
 * @author   Lenard Zhang
 */
public class SawToothGenerator implements Generator
{

    private int period;
    private int state;

    public SawToothGenerator(int p)
    {
        period = p;
        state = 0;
    }

    private double normalize()
    {
        return 2 * (state % period) / (double)(period) - 1;
    }

    public double next()
    {
        state++;
        double next = normalize();
        System.out.println(next);
        if(next == 1)
            next = -1;
        return next;
    }

}