package lab14;

import lab14lib.Generator;

/**
 * The AcceleratingSawToothGenerator that implements Generator to generate accelerating saw tooth waves.
 * @author   Lenard Zhang
 */
public class AcceleratingSawToothGenerator implements Generator
{

    private int period;
    private int state;
    private double factor;

    public AcceleratingSawToothGenerator(int p, double f)
    {
        period = p;
        state = 0;
        factor = f;
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
        {
            next = -1;
            period = (int)(period * factor);
        }
        return next;
    }

}