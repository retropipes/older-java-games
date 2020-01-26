/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractWand;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

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
    public void useAction(final AbstractDungeonObject mo, final int x,
            final int y, final int z) {
        final Application app = DungeonDiver4.getApplication();
        app.getGameManager().setRemoteAction(x, y, z);
        int r = 1;
        final String rres = CommonDialogs.showInputDialog("Rotation Radius:",
                "DungeonDiver4", RotationWand.rChoices,
                RotationWand.rChoices[r - 1]);
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
        final String dres = CommonDialogs.showInputDialog("Rotation Direction:",
                "DungeonDiver4", RotationWand.dChoices,
                RotationWand.dChoices[di]);
        if (dres.equals(RotationWand.dChoices[0])) {
            d = RotationWand.CLOCKWISE;
        } else {
            d = RotationWand.COUNTERCLOCKWISE;
        }
        if (d) {
            DungeonDiver4.getApplication().getGameManager()
                    .doClockwiseRotate(r);
        } else {
            DungeonDiver4.getApplication().getGameManager()
                    .doCounterclockwiseRotate(r);
        }
        SoundManager.playSound(SoundConstants.SOUND_CHANGE);
    }

    @Override
    public String getDescription() {
        return "Rotation Wands will rotate part of the dungeon. You can choose the area of effect and the direction.";
    }
}
