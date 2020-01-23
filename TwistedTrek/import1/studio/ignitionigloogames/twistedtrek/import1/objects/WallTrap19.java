/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWallTrap;

public class WallTrap19 extends GenericWallTrap {
    public WallTrap19() {
	super(19, new TrappedWall19());
    }

    @Override
    public String getDescription() {
	return "Wall Traps 19 disappear when stepped on, causing all Trapped Walls 19 to also disappear.";
    }
}
