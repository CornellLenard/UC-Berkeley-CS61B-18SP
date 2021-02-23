package hw3.hash;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;


public class TestSimpleOomage
{

    @Test
    public void testHashCodeDeterministic()
    {
        SimpleOomage so = SimpleOomage.randomSimpleOomage();
        int hashCode = so.hashCode();
        for(int i = 0; i < 100; i++)
            assertEquals(hashCode, so.hashCode());
    }

    @Test
    public void testHashCodePerfect()
    {
        /* TODO: Write a test that ensures the hashCode is perfect,
          meaning no two SimpleOomages should EVER have the same
          hashCode UNLESS they have the same red, blue, and green values!
         */
        Set<Integer> st = new HashSet<Integer>();
        SimpleOomage so = null;
        int hashCode = 0;
        for(int i = 0; i < 256; i += 5)
        {
            for(int j = 0; j < 256; j += 5)
            {
                for(int k = 0; k < 256; k += 5)
                {
                    so = new SimpleOomage(i, j, k);
                    hashCode = so.hashCode();
                    assertTrue(st.contains(hashCode) == false);
                    st.add(hashCode);
                }
            }
        }
    }

    @Test
    public void testEquals()
    {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        SimpleOomage ooB = new SimpleOomage(50, 50, 50);
        assertEquals(ooA, ooA2);
        assertNotEquals(ooA, ooB);
        assertNotEquals(ooA2, ooB);
        assertNotEquals(ooA, "ketchup");
    }


    @Test
    public void testHashCodeAndEqualsConsistency()
    {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        HashSet<SimpleOomage> hashSet = new HashSet<>();
        hashSet.add(ooA);
        assertTrue(hashSet.contains(ooA2));
    }

    /* TODO: Uncomment this test after you finish haveNiceHashCode Spread in OomageTestUtility */
    @Test
    public void testRandomOomagesHashCodeSpread()
    {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for(int i = 0; i < N; i++)
            oomages.add(SimpleOomage.randomSimpleOomage());

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args)
    {
        jh61b.junit.textui.runClasses(TestSimpleOomage.class);
    }

}
