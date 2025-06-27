/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWallTrap;

public class WallTrap9 extends GenericWallTrap {
    public WallTrap9() {
        super(9, new TrappedWall9());
    }

    @Override
    public String getDescription() {
        return "Wall Traps 9 disappear when stepped on, causing all Trapped Walls 9 to also disappear.";
    }
}
