/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.effects.EffectConstants;
import net.worldwizard.worldz.game.ObjectInventory;
import net.worldwizard.worldz.generic.GenericTrap;
import net.worldwizard.worldz.generic.WorldObject;

public class ClockwiseRotationTrap extends GenericTrap {
    // Fields
    private static final int EFFECT_DURATION = 10;

    // Constructors
    public ClockwiseRotationTrap() {
        super();
    }

    @Override
    public String getName() {
        return "Clockwise Rotation Trap";
    }

    @Override
    public String getPluralName() {
        return "Clockwise Rotation Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        if (Worldz.getApplication().getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            WorldObject.playRotatedSound();
        }
        Messager.showMessage("Your controls are rotated!");
        Worldz.getApplication().getGameManager().activateEffect(
                EffectConstants.EFFECT_ROTATED_CLOCKWISE,
                ClockwiseRotationTrap.EFFECT_DURATION);
    }

    @Override
    public String getMoveSuccessSoundName() {
        return "change";
    }

    @Override
    public String getDescription() {
        return "Clockwise Rotation Traps rotate your controls clockwise for 10 steps when stepped on.";
    }
}