/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.abc;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.creatures.StatConstants;
import com.puttysoftware.fantastlex.creatures.party.PartyManager;
import com.puttysoftware.fantastlex.creatures.party.PartyMember;
import com.puttysoftware.fantastlex.maze.MazeConstants;
import com.puttysoftware.fantastlex.maze.objects.Empty;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.maze.utilities.TypeConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;
import com.puttysoftware.randomrange.RandomRange;

public abstract class AbstractPotion extends AbstractMazeObject {
    // Fields
    private int statAffected;
    private int effectValue;
    private RandomRange effect;
    private boolean effectValueIsPercentage;
    private static final long SCORE_SMASH = 20L;
    private static final long SCORE_CONSUME = 50L;

    // Constructors
    protected AbstractPotion(final int stat, final boolean usePercent) {
        super(false, false);
        this.statAffected = stat;
        this.effectValueIsPercentage = usePercent;
    }

    protected AbstractPotion(final int stat, final boolean usePercent,
            final int min, final int max) {
        super(false, false);
        this.statAffected = stat;
        this.effectValueIsPercentage = usePercent;
        this.effect = new RandomRange(min, max);
    }

    @Override
    public AbstractPotion clone() {
        final AbstractPotion copy = (AbstractPotion) super.clone();
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
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_POTION);
        this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }

    @Override
    public final void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        final PartyMember m = PartyManager.getParty().getLeader();
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
        FantastleX.getApplication().getGameManager().decay();
        if (this.effectValue >= 0) {
            SoundManager.playSound(SoundConstants.SOUND_HEAL);
        } else {
            SoundManager.playSound(SoundConstants.SOUND_HURT);
        }
        FantastleX.getApplication().getGameManager()
                .addToScore(AbstractPotion.SCORE_CONSUME);
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY,
            final int arrowType, final MazeObjectInventory inv) {
        FantastleX.getApplication().getGameManager()
                .morph(new Empty(), locX, locY, locZ);
        SoundManager.playSound(SoundConstants.SOUND_SHATTER);
        FantastleX.getApplication().getGameManager()
                .addToScore(AbstractPotion.SCORE_SMASH);
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
        return AbstractMazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}