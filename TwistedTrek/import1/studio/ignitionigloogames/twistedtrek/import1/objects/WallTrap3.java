/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWallTrap;

public class WallTrap3 extends GenericWallTrap {
    public WallTrap3() {
	super(3, new TrappedWall3());
    }

    @Override
    public String getDescription() {
	return "Wall Traps 3 disappear when stepped on, causing all Trapped Walls 3 to also disappear.";
    }
}
