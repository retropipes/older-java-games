/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.generic;

import com.puttysoftware.randomnumbers.RandomRange;
import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.game.ObjectInventory;
import com.puttysoftware.rulemazer.maze.Maze;
import com.puttysoftware.rulemazer.maze.MazeConstants;
import com.puttysoftware.rulemazer.objects.Empty;
import com.puttysoftware.rulemazer.resourcemanagers.SoundConstants;
import com.puttysoftware.rulemazer.resourcemanagers.SoundManager;

public abstract class GenericPotion extends MazeObject {
    // Fields
    private int effectValue;
    private RandomRange effect;
    private boolean effectValueIsPercentage;
    private static final long SCORE_SMASH = 20L;
    private static final long SCORE_CONSUME = 50L;

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
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_POTION);
        this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }

    @Override
    public final void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        final Maze m = Main.getApplication().getMazeManager().getMaze();
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
        Main.getApplication().getGameManager().decay();
        if (this.effectValue >= 0) {
            SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                    SoundConstants.SOUND_HEAL);
        } else {
            SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                    SoundConstants.SOUND_HURT);
        }
        Main.getApplication().getGameManager()
                .addToScore(GenericPotion.SCORE_CONSUME);
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY,
            final int arrowType, final ObjectInventory inv) {
        Main.getApplication().getGameManager()
                .morph(new Empty(), locX, locY, locZ);
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_SHATTER);
        Main.getApplication().getGameManager()
                .addToScore(GenericPotion.SCORE_SMASH);
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
        return MazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}