/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWallTrap;

public class WallTrap1 extends GenericWallTrap {
    public WallTrap1() {
	super(1, new TrappedWall1());
    }

    @Override
    public String getDescription() {
	return "Wall Traps 1 disappear when stepped on, causing all Trapped Walls 1 to also disappear.";
    }
}
