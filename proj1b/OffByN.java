/**
 * The OffByOne that implements CharacterComparator to define a new definition of equal between 2 chars.
 * @author   Lenard Zhang
 */
public class OffByN implements CharacterComparator
{

    private int N;

    public OffByN(int N)
    {
        this.N = N;
    }

    @Override
    public boolean equalChars(char x, char y)
    {
        return (x - y == N) || (y - x == N);
    }

}