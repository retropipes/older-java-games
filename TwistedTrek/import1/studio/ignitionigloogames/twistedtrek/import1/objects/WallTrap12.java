/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWallTrap;

public class WallTrap12 extends GenericWallTrap {
    public WallTrap12() {
	super(12, new TrappedWall12());
    }

    @Override
    public String getDescription() {
	return "Wall Traps 12 disappear when stepped on, causing all Trapped Walls 12 to also disappear.";
    }
}
