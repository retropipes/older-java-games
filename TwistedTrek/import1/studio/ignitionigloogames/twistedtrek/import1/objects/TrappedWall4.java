/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTrappedWall;

public class TrappedWall4 extends GenericTrappedWall {
    public TrappedWall4() {
	super(4);
    }

    @Override
    public String getDescription() {
	return "Trapped Walls 4 disappear when any Wall Trap 4 is triggered.";
    }
}
