/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell


Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.generic;

import net.worldwizard.randomnumbers.RandomRange;
import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.creatures.PartyManager;
import net.worldwizard.worldz.creatures.PartyMember;
import net.worldwizard.worldz.game.ObjectInventory;
import net.worldwizard.worldz.objects.Empty;
import net.worldwizard.worldz.resourcemanagers.SoundManager;
import net.worldwizard.worldz.world.WorldConstants;

public abstract class GenericPotion extends WorldObject {
    // Fields
    private int effectValue;
    private RandomRange effect;
    private boolean effectValueIsPercentage;

    // Constructors
    protected GenericPotion(final boolean usePercent) {
        super(false);
        this.effectValueIsPercentage = usePercent;
    }

    protected GenericPotion(final boolean usePercent, final int min,
            final int max) {
        super(false);
        this.effectValueIsPercentage = usePercent;
        this.effect = new RandomRange(min, max);
    }

    @Override
    public GenericPotion clone() {
        final GenericPotion copy = (GenericPotion) super.clone();
        copy.effectValue = this.effectValue;
        copy.effectValueIsPercentage = this.effectValueIsPercentage;
        copy.effect = this.effect;
        return copy;
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return WorldConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_POTION);
        this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }

    @Override
    public final void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        final PartyMember m = PartyManager.getParty().getLeader();
        if (this.effect != null) {
            this.effectValue = this.effect.generate();
        } else {
            this.effectValue = this.getEffectValue();
        }
        if (this.effectValueIsPercentage) {
            if (this.effectValue >= 0) {
                m.healPercentage(this.effectValue);
            } else {
                m.doDamagePercentage(-this.effectValue);
            }
        } else {
            if (this.effectValue >= 0) {
                m.heal(this.effectValue);
            } else {
                m.doDamage(-this.effectValue);
            }
        }
        Worldz.getApplication().getGameManager().decay();
        if (this.effectValue >= 0) {
            if (Worldz.getApplication().getPrefsManager()
                    .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
                SoundManager.playSound("heal");
            }
        } else {
            if (Worldz.getApplication().getPrefsManager()
                    .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
                SoundManager.playSound("hurt");
            }
        }
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int arrowType,
            final ObjectInventory inv) {
        Worldz.getApplication().getGameManager().morph(new Empty(), locX, locY,
                locZ);
        if (Worldz.getApplication().getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            SoundManager.playSound("shatter");
        }
        return false;
    }

    public int getEffectValue() {
        if (this.effect != null) {
            return this.effect.generate();
        } else {
            return 0;
        }
    }

    @Override
    public int getCustomProperty(final int propID) {
        return WorldObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}