/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTrappedWall;

public class TrappedWall10 extends GenericTrappedWall {
    public TrappedWall10() {
        super(10);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 10 disappear when any Wall Trap 10 is triggered.";
    }
}
