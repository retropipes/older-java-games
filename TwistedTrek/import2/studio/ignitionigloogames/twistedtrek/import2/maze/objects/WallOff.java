/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.objects;

import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractPassThroughObject;
import studio.ignitionigloogames.twistedtrek.import2.maze.utilities.TypeConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ObjectImageConstants;

public class WallOff extends AbstractPassThroughObject {
    // Constructors
    public WallOff() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_WALL_OFF;
    }

    @Override
    public String getName() {
        return "Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Walls Off";
    }

    @Override
    public String getDescription() {
        return "Walls Off can be walked through.";
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_PASS_THROUGH);
        this.type.set(TypeConstants.TYPE_EMPTY_SPACE);
    }
}