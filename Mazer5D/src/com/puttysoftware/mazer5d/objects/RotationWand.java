/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objects;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.mazer5d.Application;
import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.generic.GenericWand;
import com.puttysoftware.mazer5d.generic.MazeObject;
import com.puttysoftware.mazer5d.resourcemanagers.SoundConstants;
import com.puttysoftware.mazer5d.resourcemanagers.SoundManager;

public class RotationWand extends GenericWand {
    // Fields
    private static final boolean CLOCKWISE = true;
    private static final boolean COUNTERCLOCKWISE = false;

    // Constructors
    public RotationWand() {
        super();
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
    public void useAction(final MazeObject mo, final int x, final int y,
            final int z) {
        final Application app = Mazer5D.getApplication();
        app.getGameManager().setRemoteAction(x, y, z);
        int r = 1;
        final String[] rChoices = new String[] { "1", "2", "3" };
        final String rres = CommonDialogs.showInputDialog("Rotation Radius:",
                "Mazer5D", rChoices, rChoices[r - 1]);
        try {
            r = Integer.parseInt(rres);
        } catch (final NumberFormatException nf) {
            // Ignore
        }
        boolean d = RotationWand.CLOCKWISE;
        int di;
        if (d) {
            di = 0;
        } else {
            di = 1;
        }
        final String[] dChoices = new String[] { "Clockwise",
                "Counterclockwise" };
        final String dres = CommonDialogs.showInputDialog(
                "Rotation Direction:", "Mazer5D", dChoices, dChoices[di]);
        if (dres.equals(dChoices[0])) {
            d = RotationWand.CLOCKWISE;
        } else {
            d = RotationWand.COUNTERCLOCKWISE;
        }
        if (d) {
            Mazer5D.getApplication().getGameManager().doClockwiseRotate(r);
        } else {
            Mazer5D.getApplication().getGameManager()
                    .doCounterclockwiseRotate(r);
        }
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_CHANGE);
    }

    @Override
    public String getDescription() {
        return "Rotation Wands will rotate part of the maze. You can choose the area of effect and the direction.";
    }
}
