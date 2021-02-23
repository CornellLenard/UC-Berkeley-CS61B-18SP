import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

/**
 * The SeamCarver to find the vertical or horizontal seam of the picture and remove it.
 * The core idea is Dynamic Programming.
 * @author   Lenard Zhang
 */

public class SeamCarver
{

    private Picture picture;                                         //the reference to the picture
    private int height;                                              //the height of the picture
    private int width;                                               //the width of the picture
    private double[][] energyMatrix;                                 //the energyMatrix, which stores each pixel's energy

    private final double precision = 0.05;                           //the precision to judge whether two doubles are equal

    public SeamCarver(Picture picture)
    {
        this.picture = new Picture(picture);
        height = picture.height();
        width = picture.width();
    }

    /**
     * Returns a new picture that is constructed based on the this.picture.
     * @return   A new picture that is constructed based on the this.picture.
     */
    public Picture picture()
    {
        return new Picture(picture);
    }

    /**
     * Returns the width of the picture.
     * @return   The width of the picture.
     */
    public int width()
    {
        return width;
    }

    /**
     * Returns the height of the picture.
     * @return   The height of the picture.
     */
    public int height()
    {
        return height;
    }

    /**
     * Returns the energy of the pixel at col x row y.
     * @param   x   The col index of the pixel.
     * @param   y   The row index of the pixel.
     * @return      The energy of the pixel at col x row y.
     */
    public double energy(int x, int y)
    {
        if(x < 0 || x >= width || y < 0 || y >= height)
            throw new IndexOutOfBoundsException("either x or y is out of range !");
        Color left = (x - 1 < 0) ? picture.get(width - 1, y) : picture.get(x - 1, y);
        Color right = (x + 1 >= width) ? picture.get(0, y) : picture.get(x + 1, y);
        Color up = (y - 1 < 0) ? picture.get(x, height - 1) : picture.get(x, y - 1);
        Color down = (y + 1 >= height) ? picture.get(x, 0) : picture.get(x, y + 1);
        double temp = 0.0, energy = 0.0;
        temp = left.getRed() - right.getRed();      temp *= temp;   energy += temp;
        temp = left.getGreen() - right.getGreen();  temp *= temp;   energy += temp;
        temp = left.getBlue() - right.getBlue();    temp *= temp;   energy += temp;
        temp = up.getRed() - down.getRed();         temp *= temp;   energy += temp;
        temp = up.getGreen() - down.getGreen();     temp *= temp;   energy += temp;
        temp = up.getBlue() - down.getBlue();       temp *= temp;   energy += temp;
        return energy;
    }

    /**
     * Calculates each pixel's energy and stores them in the energyMatrix.
     */
    private void createEnergyMatrix()
    {
        energyMatrix = new double[height][width];
        for(int i = 0; i < height; i++)
            for(int j = 0; j < width; j++)
                energyMatrix[i][j] = energy(j, i);
    }

    /**
     * If isVertical is true, then calculates the M value at every position based on energyMatrix.
     * Otherwise, transposes the energyMatrix and calculates the M values based on the transposed energyMatrix.
     * The algorithm utilizes Dynamic Programming rather than Recursion.
     * @param    isVertical   Records that this method is called by findVerticalSeam() or findHorizontalSeam().
     * @return                Please read the description above.
     */
    private double[][] getMMatrix(boolean isVertical)
    {
        createEnergyMatrix();
        if(height == 1 || width == 1)
            return null;
        if(isVertical == false)
        {
            double[][] temp = energyMatrix;
            energyMatrix = new double[width][height];
            for(int i = 0; i < temp.length; i++)
                for(int j = 0; j < temp[0].length; j++)
                    energyMatrix[j][i] = temp[i][j];
            temp = null;
        }
        int Height = energyMatrix.length, Width = energyMatrix[0].length;
        double[][] M = new double[Height][Width];
        double upLeftM = 0.0, upM = 0.0, upRightM = 0.0, minM = 0.0;
        for(int j = 0; j < Width; j++)
            M[0][j] = energyMatrix[0][j];
        for(int i = 1; i < Height; i++)
        {
            for(int j = 1; j < Width - 1; j++)
            {
                upLeftM = M[i - 1][j - 1];
                upM = M[i - 1][j];
                upRightM = M[i - 1][j + 1];
                minM = (upLeftM < upM) ? upLeftM : upM;
                minM = (minM < upRightM) ? minM : upRightM;
                M[i][j] = energyMatrix[i][j] + minM;
            }
            upM = M[i - 1][0];
            upRightM = M[i - 1][1];
            M[i][0] = energyMatrix[i][0] + ((upM < upRightM) ? upM : upRightM);
            upLeftM = M[i - 1][Width - 2];
            upM = M[i - 1][Width - 1];
            M[i][Width - 1] = energyMatrix[i][Width - 1] + ((upLeftM < upM) ? upLeftM : upM);
        }
        return M;
    }

    /**
     * Returns the vertical seam of the picture that has the minimum energy.
     * Finds the right end pixel at the bottom and backtracks the previous pixels.
     * @param   M   The M matrix of the picture.
     * @return      The vertical seam of the picture that has the minimum energy.
     */
    private int[] helperFindVerticalSeam(double[][] M, boolean isVertical)
    {
        if(M == null)
        {
            if(height == 1)
            {
                if(isVertical == true)
                {
                    int minCol = 0;
                    for(int j = 1; j < width; j++)
                        if(energyMatrix[0][j] < energyMatrix[0][minCol])
                            minCol = j;
                    int[] verticalSeam = new int[1];
                    verticalSeam[0] = minCol;
                    return verticalSeam;
                }
                else
                    return new int[width];
            }
            else
            {
                if(isVertical == true)
                    return new int[height];
                else
                {
                    int minRow = 0;
                    for(int i = 1; i < height; i++)
                        if(energyMatrix[i][0] < energyMatrix[minRow][0])
                            minRow = i;
                    int[] horizontalSeam = new int[1];
                    horizontalSeam[0] = minRow;
                    return horizontalSeam;
                }
            }
        }
        int Height = energyMatrix.length, Width = energyMatrix[0].length;
        int[] verticalSeam = new int[Height];
        int minCol = 0;
        double minEnergy = M[Height - 1][0];
        for(int j = 1; j < Width; j++)
        {
            if(M[Height - 1][j] < minEnergy)
            {
                minEnergy = M[Height - 1][j];
                minCol = j;
            }
        }
        verticalSeam[Height - 1] = minCol;
        int count = Height - 2;
        while(count >= 0)
        {
            minCol = verticalSeam[count + 1];
            minEnergy = M[count + 1][minCol] - energyMatrix[count + 1][minCol];
            if(minCol == 0)
                verticalSeam[count] = (Math.abs(minEnergy - M[count][0]) <= precision) ? 0 : 1;
            else if(minCol == Width - 1)
                verticalSeam[count] = (Math.abs(minEnergy - M[count][Width - 1]) <= precision) ? Width - 1 : Width - 2;
            else
            {
                int targetCol = 0;
                if(Math.abs(minEnergy - M[count][minCol - 1]) <= precision)
                    targetCol = minCol - 1;
                else if(Math.abs(minEnergy - M[count][minCol]) <= precision)
                    targetCol = minCol;
                else
                    targetCol = minCol + 1;
                verticalSeam[count] = targetCol;
            }
            count--;
        }
        return verticalSeam;
    }

    /**
     * Returns the horizontal seam of the picture that has the minimum energy, which utilizes helperFindVerticalSeam().
     * Just makes the energyMatrix transposed and constructs the M matrix based on it then it's OK.
     * @return   The horizontal seam of the picture that has the minimum energy.
     */
    public int[] findHorizontalSeam()
    {
        double[][] M = getMMatrix(false);
        return helperFindVerticalSeam(M, false);
    }

    /**
     * Returns the vertical seam of the picture that has the minimum energy utilizing helperFindVerticalSeam().
     * @return   The vertical seam of the picture that has the minimum energy.
     */
    public int[] findVerticalSeam()
    {
        double[][] M = getMMatrix(true);
        return helperFindVerticalSeam(M, true);
    }

    /**
     * Removes the horizontal seam of the picture.
     * @param   seam   The horizontal seam of the picture.
     */
    public void removeHorizontalSeam(int[] seam)
    {
        if(seam.length != width)
            throw new IllegalArgumentException("invalid seam array !");
        for(int i = 0; i < seam.length - 1; i++)
            if(Math.abs(seam[i] - seam[i + 1]) > 1)
                throw new IllegalArgumentException("invalid seam array !");
        picture = SeamRemover.removeHorizontalSeam(picture, seam);
        height = picture.height();
        width = picture.width();
    }

    /**
     * Removes the vertical seam of the picture.
     * @param   seam   The vertical seam of the picture.
     */
    public void removeVerticalSeam(int[] seam)
    {
        if(seam.length != height)
            throw new IllegalArgumentException("invalid seam array !");
        for(int i = 0; i < seam.length - 1; i++)
            if(Math.abs(seam[i] - seam[i + 1]) > 1)
                throw new IllegalArgumentException("invalid seam array !");
        picture = SeamRemover.removeVerticalSeam(picture, seam);
        height = picture.height();
        width = picture.width();
    }

}




