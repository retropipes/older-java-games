/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.mazerunner2.Application;
import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.abc.AbstractWand;
import com.puttysoftware.mazerunner2.maze.abc.AbstractMazeObject;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;

public class RotationWand extends AbstractWand {
    // Fields
    private static final boolean CLOCKWISE = true;
    private static final boolean COUNTERCLOCKWISE = false;
    private static final String[] rChoices = new String[] { "1", "2", "3" };
    private static final String[] dChoices = new String[] { "Clockwise",
            "Counterclockwise" };

    // Constructors
    public RotationWand() {
        super(ColorConstants.COLOR_ORANGE);
    }

    @Override
    public String getName() {
        return "Rotation Wand";
    }

    @Override
    public String getPluralName() {
        return "Rotation Wands";
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        this.useAction(null, x, y, z);
    }

    @Override
    public void useAction(final AbstractMazeObject mo, final int x,
            final int y, final int z) {
        Application app = MazeRunnerII.getApplication();
        app.getGameManager().setRemoteAction(x, y, z);
        int r = 1;
        String rres = CommonDialogs.showInputDialog("Rotation Radius:",
                "MazeRunnerII", rChoices, rChoices[r - 1]);
        try {
            r = Integer.parseInt(rres);
        } catch (NumberFormatException nf) {
            // Ignore
        }
        boolean d = RotationWand.CLOCKWISE;
        int di;
        if (d) {
            di = 0;
        } else {
            di = 1;
        }
        String dres = CommonDialogs.showInputDialog("Rotation Direction:",
                "MazeRunnerII", dChoices, dChoices[di]);
        if (dres.equals(dChoices[0])) {
            d = RotationWand.CLOCKWISE;
        } else {
            d = RotationWand.COUNTERCLOCKWISE;
        }
        if (d) {
            MazeRunnerII.getApplication().getGameManager().doClockwiseRotate(r);
        } else {
            MazeRunnerII.getApplication().getGameManager()
                    .doCounterclockwiseRotate(r);
        }
        SoundManager.playSound(SoundConstants.SOUND_CHANGE);
    }

    @Override
    public String getDescription() {
        return "Rotation Wands will rotate part of the maze. You can choose the area of effect and the direction.";
    }
}
