package hw2;

import edu.princeton.cs.introcs.StdRandom;

/**
 * The PercolationStats to perform mathematical processing on percolation process simulations.
 * @author   Lenard Zhang
 */
public class PercolationStats
{

    private int N;                                                   //the side length of the grid
    private int T;                                                   //how many rounds to perform simulation
    private double[] result;                                         //stores the result data

    /**
     * The constructor performs the simulation for T rounds.
     * @param   N   The side length of the grid.
     * @param   T   How many rounds to perform simulation.
     * @param   pf  Stores the result data.
     */
    public PercolationStats(int N, int T, PercolationFactory pf)
    {
        if(N <= 0 || T <= 0)
            throw new IllegalArgumentException("Both N and T should be greater than 0 !");
        this.N = N;
        this.T = T;
        result = new double[T];
        Percolation p = null;
        int row = 0, col = 0;
        for(int i = 0; i < T; i++)
        {
            p = pf.make(N);
            while(p.percolates() == false)
            {
                row = StdRandom.uniform(N);
                col = StdRandom.uniform(N);
                if(p.isOpen(row, col) == false)
                    p.open(row, col);
            }
            result[i] = (double)(p.numberOfOpenSites()) / (N * N);
        }
    }

    /**
     * Calculates the mean value of the thresholds.
     * @return   The mean value of the thresholds.
     */
    public double mean()
    {
        double sum = 0.0;
        for(int i = 0; i < T; i++)
            sum += result[i];
        return sum / T;
    }

    /**
     * Calculates the standard deviation of the thresholds.
     * @return   The standard deviation of the thresholds.
     */
    public double stddev()
    {
        double sum = 0.0;
        double mean = mean();
        for(int i = 0; i < T; i++)
            sum += (result[i] - mean) * (result[i] - mean);
        return Math.sqrt(sum / (T - 1));
    }

    /**
     * Calculates the low endpoint of the 95% confidence interval.
     * @return   The low endpoint of the 95% confidence interval.
     */
    public double confidenceLow()
    {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    /**
     * Calculates the high endpoint of the 95% confidence interval.
     * @return   The high endpoint of the 95% confidence interval.
     */
    public double confidenceHigh()
    {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }

}









