/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.objects;

import studio.ignitionigloogames.randomrange.RandomRange;
import studio.ignitionigloogames.twistedtrek.import2.Application;
import studio.ignitionigloogames.twistedtrek.import2.Import2;
import studio.ignitionigloogames.twistedtrek.import2.maze.Maze;
import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractTrigger;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ObjectImageConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundManager;

public class Button extends AbstractTrigger {
    // Constructors
    public Button() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_BUTTON;
    }

    @Override
    public String getName() {
        return "Button";
    }

    @Override
    public String getPluralName() {
        return "Buttons";
    }

    @Override
    public String getDescription() {
        return "Buttons toggle Walls On and Walls Off.";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY) {
        SoundManager.playSound(SoundConstants.SOUND_BUTTON);
        final Application app = Import2.getApplication();
        app.getMazeManager().getMaze().fullScanButton(this.getLayer());
        app.getGameManager().redrawMaze();
    }

    @Override
    public boolean shouldGenerateObject(final Maze maze, final int row, final int col, final int floor, final int level,
            final int layer) {
        // Generate Buttons at 50% rate
        final RandomRange reject = new RandomRange(1, 100);
        return reject.generate() < 50;
    }
}