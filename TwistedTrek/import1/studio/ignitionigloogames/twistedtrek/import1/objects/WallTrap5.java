/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWallTrap;

public class WallTrap5 extends GenericWallTrap {
    public WallTrap5() {
        super(5, new TrappedWall5());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 5 disappear when stepped on, causing all Trapped Walls 5 to also disappear.";
    }
}
