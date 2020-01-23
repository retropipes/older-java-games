/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWallTrap;

public class WallTrap18 extends GenericWallTrap {
    public WallTrap18() {
	super(18, new TrappedWall18());
    }

    @Override
    public String getDescription() {
	return "Wall Traps 18 disappear when stepped on, causing all Trapped Walls 18 to also disappear.";
    }
}
