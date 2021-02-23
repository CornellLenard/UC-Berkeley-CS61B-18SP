package creatures;

import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.HugLifeUtils;
import java.awt.Color;
import java.util.Map;
import java.util.List;

/** An implementation of a fierce blue-colored predator.
 *  @author Josh Hug
 *  @author   Lenard Zhang
 */
public class Clorus extends Creature
{
    /** red color. */
    private int r;
    /** green color. */
    private int g;
    /** blue color. */
    private int b;
    /** probability of taking a move when ample space available. */
    private double moveProbability = 0.5;
    /** fraction of energy to retain when replicating. */
    private double repEnergyRetained = 0.5;
    /** fraction of energy to bestow upon offspring. */
    private double repEnergyGiven = 0.5;

    /** creates a clorus with energy equal to E. */
    public Clorus(double e)
    {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    /** creates a clorus with energy equal to 1. */
    public Clorus()
    {
        this(1);
    }

    /** Should always return the color red = 34, green = 0, blue = 231
     *
     */
    public Color color()
    {
        r = 34;
        g = 0;
        b = 231;
        return color(r, g, b);
    }

    /** If a clorus attacks another creature, it should gain that creature's energy
     *  This should happen in the attack() method, not in chooseAction()
     *  You do not need to worry about making sure the attacked creature dies -the
     *  simulator does that for you
     *
     */
    @Override
    public void attack(Creature c)
    {
        energy += c.energy();
        c.energy = 0;
    }

    /** Cloruses should lose 0.03 units of energy when moving. If you want to
     *  to avoid the magic number warning, you'll need to make a
     *  private static final variable. This is not required for this lab.
     */
    @Override
    public void move()
    {
        energy = (energy - 0.03 <= 0) ? 0 : energy - 0.03;
    }


    /** Cloruses should lose 0.01 units of energy on a STAY action */
    @Override
    public void stay()
    {
        energy = (energy - 0.01 <= 0) ? 0 : energy - 0.01;
    }

    /** Cloruses and their offspring each get 50% of the energy, with none
     *  lost to the process. Now that's efficiency! Returns a baby clorus
     */
    @Override
    public Clorus replicate()
    {
        double offspringEnergy = energy * repEnergyGiven;
        energy *= repEnergyRetained;
        return new Clorus(offspringEnergy);
    }

    /** Cloruses take exactly the following actions based on NEIGHBORS:
     *  1. If no empty adjacent spaces, STAY
     *  2. Otherwise, if there are plip-neighbors, ATTACK one of them randomly
     *  3. Otherwise, if energy >= 1, REPLICATE to a random empty square
     *  4. Otherwise, if nothing else, MOVE to a random empty square
     *
     *  Returns an object of type Action. See Action.java for the
     *  scoop on how Actions work. See SampleCreature.chooseAction()
     *  for an example to follow.
     */
    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors)
    {
        int emptyNeighborsCount = 0;
        Direction[] emptyDirections = new Direction[4];
        int plipNeighborsCount = 0;
        Direction[] plipDirections = new Direction[4];
        for(Direction dir : neighbors)
        {
            if(neighbors.get(dir).name() == "empty")
            {
                emptyDirections[emptyNeighborsCount] = dir;
                emptyNeighborsCount++;
            }
            if(neighbors.get(dir).name() == "plip")
            {
                plipDirections[plipNeighborsCount] = dir;
                plipNeighborsCount++;
            }
        }
        if(emptyNeighborsCount == 0)
            return new Action(Action.ActionType.STAY);
        else if(plipNeighborsCount > 0)
            return new Action(Action.ActionType.ATTACK, plipDirections[(int)(Math.random() * plipNeighborsCount)]);
        else if(energy >= 1)
            return new Action(Action.ActionType.REPLICATE, emptyDirections[(int)(Math.random() * emptyNeighborsCount)]);
        else
            return new Action(Action.ActionType.MOVE, emptyDirections[(int)(Math.random() * emptyNeighborsCount)]);
    }

}