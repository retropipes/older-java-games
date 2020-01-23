/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWallTrap;

public class WallTrap6 extends GenericWallTrap {
    public WallTrap6() {
	super(6, new TrappedWall6());
    }

    @Override
    public String getDescription() {
	return "Wall Traps 6 disappear when stepped on, causing all Trapped Walls 6 to also disappear.";
    }
}
