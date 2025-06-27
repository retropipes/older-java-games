/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWallTrap;

public class WallTrap13 extends GenericWallTrap {
    public WallTrap13() {
        super(13, new TrappedWall13());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 13 disappear when stepped on, causing all Trapped Walls 13 to also disappear.";
    }
}
