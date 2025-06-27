/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTrappedWall;

public class TrappedWall7 extends GenericTrappedWall {
    public TrappedWall7() {
        super(7);
    }

    @Override
    public String getDescription() {
        return "Trapped Walls 7 disappear when any Wall Trap 7 is triggered.";
    }
}
