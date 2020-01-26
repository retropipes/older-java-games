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

public class UTurnTrap extends GenericTrap {
    // Fields
    private static final int EFFECT_DURATION = 10;

    // Constructors
    public UTurnTrap() {
        super();
    }

    @Override
    public String getName() {
        return "U Turn Trap";
    }

    @Override
    public String getPluralName() {
        return "U Turn Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        Messager.showMessage("Your controls are turned around!");
        Worldz.getApplication().getGameManager().activateEffect(
                EffectConstants.EFFECT_U_TURNED, UTurnTrap.EFFECT_DURATION);
        if (Worldz.getApplication().getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            WorldObject.playRotatedSound();
        }
    }

    @Override
    public String getMoveSuccessSoundName() {
        return "change";
    }

    @Override
    public String getDescription() {
        return "U Turn Traps invert your controls for 10 steps when stepped on.";
    }
}