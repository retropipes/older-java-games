/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTrappedWall;

public class TrappedWall6 extends GenericTrappedWall {
    public TrappedWall6() {
	super(6);
    }

    @Override
    public String getDescription() {
	return "Trapped Walls 6 disappear when any Wall Trap 6 is triggered.";
    }
}
