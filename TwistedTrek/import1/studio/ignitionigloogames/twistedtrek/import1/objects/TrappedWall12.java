/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTrappedWall;

public class TrappedWall12 extends GenericTrappedWall {
    public TrappedWall12() {
	super(12);
    }

    @Override
    public String getDescription() {
	return "Trapped Walls 12 disappear when any Wall Trap 12 is triggered.";
    }
}
