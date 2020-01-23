/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWallTrap;

public class WallTrap0 extends GenericWallTrap {
    public WallTrap0() {
	super(0, new TrappedWall0());
    }

    @Override
    public String getDescription() {
	return "Wall Traps 0 disappear when stepped on, causing all Trapped Walls 0 to also disappear.";
    }
}
