/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.generic.GenericWand;
import net.worldwizard.worldz.generic.WorldObject;

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
    public void useAction(final WorldObject mo, final int x, final int y,
            final int z) {
        final Application app = Worldz.getApplication();
        app.getGameManager().setRemoteAction(x, y, z);
        int r = 1;
        final String[] rChoices = new String[] { "1", "2", "3" };
        final String rres = Messager.showInputDialog("Rotation Radius:",
                "Worldz", rChoices, rChoices[r - 1]);
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
        final String dres = Messager.showInputDialog("Rotation Direction:",
                "Worldz", dChoices, dChoices[di]);
        if (dres.equals(dChoices[0])) {
            d = RotationWand.CLOCKWISE;
        } else {
            d = RotationWand.COUNTERCLOCKWISE;
        }
        if (d) {
            Worldz.getApplication().getGameManager().doClockwiseRotate(r);
        } else {
            Worldz.getApplication().getGameManager()
                    .doCounterclockwiseRotate(r);
        }
        if (Worldz.getApplication().getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            WorldObject.playRotatedSound();
        }
    }

    @Override
    public String getUseSoundName() {
        return "rotated";
    }

    @Override
    public String getDescription() {
        return "Rotation Wands will rotate part of the world. You can choose the area of effect and the direction.";
    }
}
