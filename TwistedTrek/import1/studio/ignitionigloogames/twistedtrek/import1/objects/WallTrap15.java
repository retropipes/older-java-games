/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWallTrap;

public class WallTrap15 extends GenericWallTrap {
    public WallTrap15() {
	super(15, new TrappedWall15());
    }

    @Override
    public String getDescription() {
	return "Wall Traps 15 disappear when stepped on, causing all Trapped Walls 15 to also disappear.";
    }
}
