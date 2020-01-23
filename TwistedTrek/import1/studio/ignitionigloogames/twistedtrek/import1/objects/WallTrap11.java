/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWallTrap;

public class WallTrap11 extends GenericWallTrap {
    public WallTrap11() {
	super(11, new TrappedWall11());
    }

    @Override
    public String getDescription() {
	return "Wall Traps 11 disappear when stepped on, causing all Trapped Walls 11 to also disappear.";
    }
}
