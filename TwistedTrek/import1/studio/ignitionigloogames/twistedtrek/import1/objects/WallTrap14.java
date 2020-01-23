/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWallTrap;

public class WallTrap14 extends GenericWallTrap {
    public WallTrap14() {
	super(14, new TrappedWall14());
    }

    @Override
    public String getDescription() {
	return "Wall Traps 14 disappear when stepped on, causing all Trapped Walls 14 to also disappear.";
    }
}
