/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWallTrap;

public class WallTrap2 extends GenericWallTrap {
    public WallTrap2() {
        super(2, new TrappedWall2());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 2 disappear when stepped on, causing all Trapped Walls 2 to also disappear.";
    }
}
