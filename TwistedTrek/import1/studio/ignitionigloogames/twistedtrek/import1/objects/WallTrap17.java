/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWallTrap;

public class WallTrap17 extends GenericWallTrap {
    public WallTrap17() {
        super(17, new TrappedWall17());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 17 disappear when stepped on, causing all Trapped Walls 17 to also disappear.";
    }
}
