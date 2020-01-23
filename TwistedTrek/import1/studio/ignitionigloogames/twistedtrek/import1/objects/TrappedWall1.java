/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTrappedWall;

public class TrappedWall1 extends GenericTrappedWall {
    public TrappedWall1() {
	super(1);
    }

    @Override
    public String getDescription() {
	return "Trapped Walls 1 disappear when any Wall Trap 1 is triggered.";
    }
}
