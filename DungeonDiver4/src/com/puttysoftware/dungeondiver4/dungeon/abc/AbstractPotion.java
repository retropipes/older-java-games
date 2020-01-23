/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: dungeonr5d@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.abc;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.creatures.StatConstants;
import com.puttysoftware.dungeondiver4.creatures.party.PartyManager;
import com.puttysoftware.dungeondiver4.creatures.party.PartyMember;
import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.objects.Empty;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;
import com.puttysoftware.randomrange.RandomRange;

public abstract class AbstractPotion extends AbstractDungeonObject {
    // Fields
    private int statAffected;
    private int effectValue;
    private RandomRange effect;
    private boolean effectValueIsPercentage;
    private static final long SCORE_SMASH = 20L;
    private static final long SCORE_CONSUME = 50L;

    // Constructors
    protected AbstractPotion(int stat, boolean usePercent) {
        super(false, false);
        this.statAffected = stat;
        this.effectValueIsPercentage = usePercent;
    }

    protected AbstractPotion(int stat, boolean usePercent, int min, int max) {
        super(false, false);
        this.statAffected = stat;
        this.effectValueIsPercentage = usePercent;
        this.effect = new RandomRange(min, max);
    }

    @Override
    public AbstractPotion clone() {
        AbstractPotion copy = (AbstractPotion) super.clone();
        copy.statAffected = this.statAffected;
        copy.effectValue = this.effectValue;
        copy.effectValueIsPercentage = this.effectValueIsPercentage;
        copy.effect = this.effect;
        return copy;
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return DungeonConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_POTION);
        this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }

    @Override
    public final void postMoveAction(boolean ie, int dirX, int dirY,
            DungeonObjectInventory inv) {
        PartyMember m = PartyManager.getParty().getLeader();
        if (this.effect != null) {
            this.effectValue = this.effect.generate();
        } else {
            this.effectValue = this.getEffectValue();
        }
        if (this.effectValueIsPercentage) {
            if (this.statAffected == StatConstants.STAT_CURRENT_HP) {
                if (this.effectValue >= 0) {
                    m.healPercentage(this.effectValue);
                } else {
                    m.doDamagePercentage(-this.effectValue);
                }
            } else if (this.statAffected == StatConstants.STAT_CURRENT_MP) {
                if (this.effectValue >= 0) {
                    m.regeneratePercentage(this.effectValue);
                } else {
                    m.drainPercentage(-this.effectValue);
                }
            }
        } else {
            if (this.statAffected == StatConstants.STAT_CURRENT_HP) {
                if (this.effectValue >= 0) {
                    m.heal(this.effectValue);
                } else {
                    m.doDamage(-this.effectValue);
                }
            } else if (this.statAffected == StatConstants.STAT_CURRENT_MP) {
                if (this.effectValue >= 0) {
                    m.regenerate(this.effectValue);
                } else {
                    m.drain(-this.effectValue);
                }
            }
        }
        DungeonDiver4.getApplication().getGameManager().decay();
        if (this.effectValue >= 0) {
            SoundManager.playSound(SoundConstants.SOUND_HEAL);
        } else {
            SoundManager.playSound(SoundConstants.SOUND_HURT);
        }
        DungeonDiver4.getApplication().getGameManager()
                .addToScore(AbstractPotion.SCORE_CONSUME);
    }

    @Override
    public boolean arrowHitAction(int locX, int locY, int locZ, int dirX,
            int dirY, int arrowType, DungeonObjectInventory inv) {
        DungeonDiver4.getApplication().getGameManager()
                .morph(new Empty(), locX, locY, locZ);
        SoundManager.playSound(SoundConstants.SOUND_SHATTER);
        DungeonDiver4.getApplication().getGameManager()
                .addToScore(AbstractPotion.SCORE_SMASH);
        return false;
    }

    @Override
    public boolean shouldGenerateObject(Dungeon dungeon, int row, int col,
            int floor, int level, int layer) {
        // Blacklist object
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
    public int getCustomProperty(int propID) {
        return AbstractDungeonObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(int propID, int value) {
        // Do nothing
    }
}