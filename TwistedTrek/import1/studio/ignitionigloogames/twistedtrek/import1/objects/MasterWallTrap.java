/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWallTrap;

public class MasterWallTrap extends GenericWallTrap {
    public MasterWallTrap() {
        super(GenericWallTrap.NUMBER_MASTER, null);
    }

    @Override
    public String getDescription() {
        return "Master Wall Traps disappear when stepped on, causing all types of Trapped Walls to also disappear.";
    }
}
