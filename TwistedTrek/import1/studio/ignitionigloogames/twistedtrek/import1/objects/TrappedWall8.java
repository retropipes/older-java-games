/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTrappedWall;

public class TrappedWall8 extends GenericTrappedWall {
    public TrappedWall8() {
	super(8);
    }

    @Override
    public String getDescription() {
	return "Trapped Walls 8 disappear when any Wall Trap 8 is triggered.";
    }
}
