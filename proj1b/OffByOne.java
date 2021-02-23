/**
 * The OffByOne that implements CharacterComparator to define a new definition of equal between 2 chars.
 * @author   Lenard Zhang
 */
public class OffByOne implements CharacterComparator
{

    @Override
    public boolean equalChars(char x, char y)
    {
        return (x - y == 1) || (y - x == 1);
    }

}