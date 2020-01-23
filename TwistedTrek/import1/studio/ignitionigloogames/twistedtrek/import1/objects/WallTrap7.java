/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWallTrap;

public class WallTrap7 extends GenericWallTrap {
    public WallTrap7() {
	super(7, new TrappedWall7());
    }

    @Override
    public String getDescription() {
	return "Wall Traps 7 disappear when stepped on, causing all Trapped Walls 7 to also disappear.";
    }
}
