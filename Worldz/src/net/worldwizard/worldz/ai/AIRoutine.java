package net.worldwizard.worldz.ai;

import net.worldwizard.worldz.items.combat.CombatUsableItem;
import net.worldwizard.worldz.spells.Spell;

public abstract class AIRoutine {
    // Fields
    protected Spell spell;
    protected CombatUsableItem item;
    protected int moveX;
    protected int moveY;
    protected boolean lastResult;
    public static final int ACTION_MOVE = 0;
    public static final int ACTION_CAST_SPELL = 1;
    public static final int ACTION_STEAL = 2;
    public static final int ACTION_DRAIN = 3;
    public static final int ACTION_USE_ITEM = 4;
    public static final int ACTION_END_TURN = 5;

    // Constructor
    protected AIRoutine() {
        this.spell = null;
        this.item = null;
        this.moveX = 0;
        this.moveY = 0;
        this.lastResult = true;
    }

    // Methods
    public abstract int getNextAction(AIContext ac);

    public final int getMoveX() {
        return this.moveX;
    }

    public final int getMoveY() {
        return this.moveY;
    }

    public final Spell getSpellToCast() {
        return this.spell;
    }

    public final CombatUsableItem getItemToUse() {
        return this.item;
    }

    public final void setLastResult(final boolean res) {
        this.lastResult = res;
    }
}
