/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: dungeonr5d@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.effects;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JLabel;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DirectionResolver;

public class DungeonEffectManager {
    // Fields
    private DungeonEffect[] activeEffects;
    private static final int NUM_EFFECTS = 12;
    private static final int MAX_ACTIVE_EFFECTS = 3;
    private Container activeEffectMessageContainer;
    private JLabel[] activeEffectMessages;
    private int newEffectIndex;
    private int[] activeEffectIndices;

    // Constructors
    public DungeonEffectManager() {
        // Create effects array
        this.activeEffects = new DungeonEffect[DungeonEffectManager.NUM_EFFECTS];
        this.activeEffects[DungeonEffectConstants.EFFECT_ROTATED_CLOCKWISE] = new RotatedCW(
                0);
        this.activeEffects[DungeonEffectConstants.EFFECT_ROTATED_COUNTERCLOCKWISE] = new RotatedCCW(
                0);
        this.activeEffects[DungeonEffectConstants.EFFECT_U_TURNED] = new UTurned(
                0);
        this.activeEffects[DungeonEffectConstants.EFFECT_CONFUSED] = new Confused(
                0);
        this.activeEffects[DungeonEffectConstants.EFFECT_DIZZY] = new Dizzy(0);
        this.activeEffects[DungeonEffectConstants.EFFECT_DRUNK] = new Drunk(0);
        this.activeEffects[DungeonEffectConstants.EFFECT_FIERY] = new Fiery(0);
        this.activeEffects[DungeonEffectConstants.EFFECT_ICY] = new Icy(0);
        this.activeEffects[DungeonEffectConstants.EFFECT_GHOSTLY] = new Ghostly(
                0);
        this.activeEffects[DungeonEffectConstants.EFFECT_POISONOUS] = new Poisonous(
                0);
        this.activeEffects[DungeonEffectConstants.EFFECT_COUNTER_POISONED] = new CounterPoisoned(
                0);
        this.activeEffects[DungeonEffectConstants.EFFECT_TRUE_SIGHT] = new TrueSight(
                0);
        // Create GUI
        this.activeEffectMessageContainer = new Container();
        this.activeEffectMessages = new JLabel[DungeonEffectManager.MAX_ACTIVE_EFFECTS];
        this.activeEffectMessageContainer.setLayout(new GridLayout(
                DungeonEffectManager.MAX_ACTIVE_EFFECTS, 1));
        for (int z = 0; z < DungeonEffectManager.MAX_ACTIVE_EFFECTS; z++) {
            this.activeEffectMessages[z] = new JLabel("");
            this.activeEffectMessageContainer.add(this.activeEffectMessages[z]);
        }
        // Set up miscellaneous things
        this.activeEffectIndices = new int[DungeonEffectManager.MAX_ACTIVE_EFFECTS];
        for (int z = 0; z < DungeonEffectManager.MAX_ACTIVE_EFFECTS; z++) {
            this.activeEffectIndices[z] = -1;
        }
        this.newEffectIndex = -1;
    }

    // Methods
    public boolean isEffectActive(int effectID) {
        return this.activeEffects[effectID].isActive();
    }

    public Container getEffectMessageContainer() {
        return this.activeEffectMessageContainer;
    }

    public void decayEffects() {
        for (int x = 0; x < DungeonEffectManager.NUM_EFFECTS; x++) {
            if (this.activeEffects[x].isActive()) {
                this.activeEffects[x].useEffect();
                // Update effect grid
                this.updateGridEntry(x);
                if (!this.activeEffects[x].isActive()) {
                    DungeonDiver4.getApplication().showMessage(
                            "You feel normal again.");
                    // Clear effect grid
                    this.clearGridEntry(x);
                    // Pack
                    DungeonDiver4.getApplication().getGameManager()
                            .getOutputFrame().pack();
                }
            }
        }
    }

    public void activateEffect(int effectID, int duration) {
        this.handleMutualExclusiveEffects(effectID);
        boolean active = this.activeEffects[effectID].isActive();
        this.activeEffects[effectID].extendEffect(duration);
        // Update effect grid
        if (active) {
            this.updateGridEntry(effectID);
        } else {
            this.addGridEntry(effectID);
        }
        // Keep effect message
        DungeonDiver4.getApplication().getGameManager().keepNextMessage();
    }

    public void deactivateEffect(int effectID) {
        if (this.activeEffects[effectID].isActive()) {
            this.activeEffects[effectID].deactivateEffect();
            this.clearGridEntry(effectID);
        }
    }

    private void handleMutualExclusiveEffects(int effectID) {
        if (effectID == DungeonEffectConstants.EFFECT_ROTATED_CLOCKWISE) {
            this.deactivateEffect(DungeonEffectConstants.EFFECT_ROTATED_COUNTERCLOCKWISE);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_U_TURNED);
        } else if (effectID == DungeonEffectConstants.EFFECT_ROTATED_COUNTERCLOCKWISE) {
            this.deactivateEffect(DungeonEffectConstants.EFFECT_ROTATED_CLOCKWISE);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_U_TURNED);
        } else if (effectID == DungeonEffectConstants.EFFECT_U_TURNED) {
            this.deactivateEffect(DungeonEffectConstants.EFFECT_ROTATED_CLOCKWISE);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_ROTATED_COUNTERCLOCKWISE);
        } else if (effectID == DungeonEffectConstants.EFFECT_CONFUSED) {
            this.deactivateEffect(DungeonEffectConstants.EFFECT_DIZZY);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_DRUNK);
        } else if (effectID == DungeonEffectConstants.EFFECT_DIZZY) {
            this.deactivateEffect(DungeonEffectConstants.EFFECT_CONFUSED);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_DRUNK);
        } else if (effectID == DungeonEffectConstants.EFFECT_DRUNK) {
            this.deactivateEffect(DungeonEffectConstants.EFFECT_CONFUSED);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_DIZZY);
        } else if (effectID == DungeonEffectConstants.EFFECT_FIERY) {
            this.deactivateEffect(DungeonEffectConstants.EFFECT_ICY);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_GHOSTLY);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_POISONOUS);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_COUNTER_POISONED);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_TRUE_SIGHT);
        } else if (effectID == DungeonEffectConstants.EFFECT_ICY) {
            this.deactivateEffect(DungeonEffectConstants.EFFECT_FIERY);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_GHOSTLY);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_POISONOUS);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_COUNTER_POISONED);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_TRUE_SIGHT);
        } else if (effectID == DungeonEffectConstants.EFFECT_GHOSTLY) {
            this.deactivateEffect(DungeonEffectConstants.EFFECT_FIERY);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_ICY);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_POISONOUS);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_COUNTER_POISONED);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_TRUE_SIGHT);
        } else if (effectID == DungeonEffectConstants.EFFECT_POISONOUS) {
            this.deactivateEffect(DungeonEffectConstants.EFFECT_FIERY);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_ICY);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_GHOSTLY);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_COUNTER_POISONED);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_TRUE_SIGHT);
        } else if (effectID == DungeonEffectConstants.EFFECT_COUNTER_POISONED) {
            this.deactivateEffect(DungeonEffectConstants.EFFECT_FIERY);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_ICY);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_GHOSTLY);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_POISONOUS);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_TRUE_SIGHT);
        } else if (effectID == DungeonEffectConstants.EFFECT_TRUE_SIGHT) {
            this.deactivateEffect(DungeonEffectConstants.EFFECT_FIERY);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_ICY);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_GHOSTLY);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_POISONOUS);
            this.deactivateEffect(DungeonEffectConstants.EFFECT_COUNTER_POISONED);
        }
    }

    public int[] doEffects(int x, int y) {
        int[] res = new int[] { x, y };
        int dir = DirectionResolver.resolveRelativeDirection(x, y);
        for (int z = 0; z < DungeonEffectManager.NUM_EFFECTS; z++) {
            if (this.activeEffects[z].isActive()) {
                dir = this.activeEffects[z].modifyMove1(dir);
                res = DirectionResolver.unresolveRelativeDirection(dir);
                res = this.activeEffects[z].modifyMove2(res);
            }
        }
        return res;
    }

    private void addGridEntry(int effectID) {
        if (this.newEffectIndex < DungeonEffectManager.MAX_ACTIVE_EFFECTS - 1) {
            this.newEffectIndex++;
            this.activeEffectIndices[this.newEffectIndex] = effectID;
            String effectString = this.activeEffects[effectID]
                    .getEffectString();
            this.activeEffectMessages[this.newEffectIndex]
                    .setText(effectString);
        }
    }

    private void clearGridEntry(int effectID) {
        int index = this.lookupEffect(effectID);
        if (index != -1) {
            this.clearGridEntryText(index);
            // Compact grid
            for (int z = index; z < DungeonEffectManager.MAX_ACTIVE_EFFECTS - 1; z++) {
                this.activeEffectMessages[z]
                        .setText(this.activeEffectMessages[z + 1].getText());
                this.activeEffectIndices[z] = this.activeEffectIndices[z + 1];
            }
            // Clear last entry
            this.clearGridEntryText(DungeonEffectManager.MAX_ACTIVE_EFFECTS - 1);
            this.newEffectIndex--;
        }
    }

    private void clearGridEntryText(int index) {
        this.activeEffectIndices[index] = -1;
        this.activeEffectMessages[index].setText("");
    }

    private void updateGridEntry(int effectID) {
        int index = this.lookupEffect(effectID);
        if (index != -1) {
            String effectString = this.activeEffects[effectID]
                    .getEffectString();
            this.activeEffectMessages[index].setText(effectString);
        }
    }

    private int lookupEffect(int effectID) {
        for (int z = 0; z < DungeonEffectManager.MAX_ACTIVE_EFFECTS; z++) {
            if (this.activeEffectIndices[z] == effectID) {
                return z;
            }
        }
        return -1;
    }
}