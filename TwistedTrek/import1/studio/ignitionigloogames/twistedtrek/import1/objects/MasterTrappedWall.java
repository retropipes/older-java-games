/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTrappedWall;

public class MasterTrappedWall extends GenericTrappedWall {
    public MasterTrappedWall() {
        super(GenericTrappedWall.NUMBER_MASTER);
    }

    @Override
    public String getDescription() {
        return "Master Trapped Walls disappear when any Wall Trap is triggered.";
    }
}
