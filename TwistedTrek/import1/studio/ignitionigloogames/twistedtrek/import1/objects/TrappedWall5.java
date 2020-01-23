/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTrappedWall;

public class TrappedWall5 extends GenericTrappedWall {
    public TrappedWall5() {
	super(5);
    }

    @Override
    public String getDescription() {
	return "Trapped Walls 5 disappear when any Wall Trap 5 is triggered.";
    }
}
