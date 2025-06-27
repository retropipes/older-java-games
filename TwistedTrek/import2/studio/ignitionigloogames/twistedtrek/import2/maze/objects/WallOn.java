/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.objects;

import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractWall;
import studio.ignitionigloogames.twistedtrek.import2.maze.utilities.TypeConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ObjectImageConstants;

public class WallOn extends AbstractWall {
    // Constructors
    public WallOn() {
        super();
    }

    @Override
    public String getName() {
        return "Wall On";
    }

    @Override
    public String getPluralName() {
        return "Walls On";
    }

    @Override
    public String getDescription() {
        return "Walls On are impassable - you'll need to go around them.";
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_WALL_ON;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_PLAIN_WALL);
        this.type.set(TypeConstants.TYPE_WALL);
    }
}