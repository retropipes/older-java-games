/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWallTrap;

public class WallTrap8 extends GenericWallTrap {
    public WallTrap8() {
	super(8, new TrappedWall8());
    }

    @Override
    public String getDescription() {
	return "Wall Traps 8 disappear when stepped on, causing all Trapped Walls 8 to also disappear.";
    }
}
