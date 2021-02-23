package creatures;

import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.HugLifeUtils;
import java.awt.Color;
import java.util.Map;
import java.util.List;

/** An implementation of a motile pacifist photosynthesizer.
 *  @author Josh Hug
 *  @author   Lenard Zhang
 */
public class Plip extends Creature
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

    /** creates plip with energy equal to E. */
    public Plip(double e)
    {
        super("plip");
        r = 0;
        g = 0;
        b = 0;
        energy = (e <= 2) ? e : 2;
    }

    /** creates a plip with energy equal to 1. */
    public Plip() {
        this(1);
    }

    /** Should return a color with red = 99, blue = 76, and green that varies
     *  linearly based on the energy of the Plip. If the plip has zero energy,
     *  it should have a green value of 63. If it has max energy, it should
     *  have a green value of 255. The green value should vary with energy
     *  linearly in between these two extremes. It's not absolutely vital
     *  that you get this exactly correct.
     */
    public Color color()
    {
        r = 99;
        g = (int)(96 * energy + 63);
        b = 76;
        return color(r, g, b);
    }

    /** Do nothing with C, Plips are pacifists. */
    @Override
    public void attack(Creature c) {}

    /** Plips should lose 0.15 units of energy when moving. If you want to
     *  to avoid the magic number warning, you'll need to make a
     *  private static final variable. This is not required for this lab.
     */
    @Override
    public void move()
    {
        energy = (energy - 0.15 <= 0) ? 0 : energy - 0.15;
    }


    /** Plips gain 0.2 energy when staying due to photosynthesis. */
    @Override
    public void stay()
    {
        energy = (energy + 0.2 >= 2) ? 2 : energy + 0.2;
    }

    /** Plips and their offspring each get 50% of the energy, with none
     *  lost to the process. Now that's efficiency! Returns a baby
     *  Plip.
     */
    @Override
    public Plip replicate()
    {
        double offspringEnergy = energy * repEnergyGiven;
        energy *= repEnergyRetained;
        return new Plip(offspringEnergy);
    }

    /** Plips take exactly the following actions based on NEIGHBORS:
     *  1. If no empty adjacent spaces, STAY.
     *  2. Otherwise, if energy >= 1, REPLICATE.
     *  3. Otherwise, if any Cloruses, MOVE with 50% probability.
     *  4. Otherwise, if nothing else, STAY
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
        boolean hasClorusNeighbors = false;
        for(Direction dir : neighbors)
        {
            if(neighbors.get(dir).name() == "empty")
            {
                emptyDirections[emptyNeighborsCount] = dir;
                emptyNeighborsCount++;
            }
            if(neighbors.get(dir).name() == "clorus")
                hasClorusNeighbors = true;
        }
        if(emptyNeighborsCount == 0)
            return new Action(Action.ActionType.STAY);
        else if(energy >= 1)
            return new Action(Action.ActionType.REPLICATE, emptyDirections[(int)(Math.random() * emptyNeighborsCount)]);
        else if(hasClorusNeighbors == true)
        {
            if(Math.random() < moveProbability)
                return new Action(Action.ActionType.MOVE, emptyDirections[(int)(Math.random() * emptyNeighborsCount)]);
        }
        else
            return new Action(Action.ActionType.STAY);
    }

}
