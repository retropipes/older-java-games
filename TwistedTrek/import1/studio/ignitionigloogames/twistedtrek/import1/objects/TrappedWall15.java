/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTrappedWall;

public class TrappedWall15 extends GenericTrappedWall {
    public TrappedWall15() {
        super(15);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 15 disappear when any Wall Trap 15 is triggered.";
    }
}
