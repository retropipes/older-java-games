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

public class DizzinessTrap extends GenericTrap {
    // Fields
    private static final int EFFECT_DURATION = 10;

    // Constructors
    public DizzinessTrap() {
        super();
    }

    @Override
    public String getName() {
        return "Dizziness Trap";
    }

    @Override
    public String getPluralName() {
        return "Dizziness Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        Messager.showMessage("You feel dizzy!");
        Worldz.getApplication().getGameManager().activateEffect(
                EffectConstants.EFFECT_DIZZY, DizzinessTrap.EFFECT_DURATION);
        if (Worldz.getApplication().getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            WorldObject.playDizzySound();
        }
    }

    @Override
    public String getMoveSuccessSoundName() {
        return "dizzy";
    }

    @Override
    public String getDescription() {
        return "Dizziness Traps randomly alter your controls each step for 10 steps when stepped on.";
    }
}