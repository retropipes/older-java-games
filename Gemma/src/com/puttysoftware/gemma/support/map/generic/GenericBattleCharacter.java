/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell


 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.generic;

import com.puttysoftware.gemma.support.creatures.Creature;
import com.puttysoftware.gemma.support.map.MapConstants;
import com.puttysoftware.gemma.support.map.objects.Empty;
import com.puttysoftware.images.BufferedImageIcon;

public abstract class GenericBattleCharacter extends MapObject {
    // Fields
    private final Creature template;
    private int actionCounter;
    private int attackCounter;
    private int spellCounter;
    private int itemCounter;
    private int stealCounter;
    private boolean isActive;

    // Constructors
    protected GenericBattleCharacter(final Creature newTemplate) {
        super(true);
        this.template = newTemplate;
        this.actionCounter = newTemplate.getActionsPerRound();
        this.attackCounter = newTemplate.getEffectedAttacksPerRound();
        this.spellCounter = newTemplate.getEffectedSpellsPerRound();
        this.itemCounter = newTemplate.getEffectedItemsPerRound();
        this.stealCounter = newTemplate.getEffectedStealsPerRound();
        this.isActive = true;
        this.setSavedObject(new Empty());
    }

    public final int getX() {
        return this.template.getX();
    }

    public final int getY() {
        return this.template.getY();
    }

    public final void setX(final int newX) {
        this.template.setX(newX);
    }

    public final void setY(final int newY) {
        this.template.setY(newY);
    }

    public final void offsetX(final int newX) {
        this.template.offsetX(newX);
    }

    public final void offsetY(final int newY) {
        this.template.offsetY(newY);
    }

    public final void saveLocation() {
        this.template.saveLocation();
    }

    public final void restoreLocation() {
        this.template.restoreLocation();
    }

    public final void resetLocation() {
        this.template.setX(-1);
        this.template.setY(-1);
    }

    public final Creature getTemplate() {
        return this.template;
    }

    public final int getTeamID() {
        return this.template.getTeamID();
    }

    public final String getTeamString() {
        if (this.getTemplate().getTeamID() == 0) {
            return "Team: Party";
        } else {
            return "Team: Enemies " + this.getTemplate().getTeamID();
        }
    }

    public final boolean isActive() {
        return this.isActive;
    }

    public final void deactivate() {
        this.isActive = false;
    }

    public final void activate() {
        this.isActive = true;
    }

    public final void resetAP() {
        this.actionCounter = this.template.getActionsPerRound();
    }

    public final void modifyAP(final int mod) {
        this.actionCounter -= mod;
    }

    public final int getCurrentAP() {
        return this.actionCounter;
    }

    public final void resetAttacks() {
        this.attackCounter = this.template.getEffectedAttacksPerRound();
    }

    public final void modifyAttacks(final int mod) {
        this.attackCounter -= mod;
    }

    public final int getCurrentAT() {
        return this.attackCounter;
    }

    public final void resetSpells() {
        this.spellCounter = this.template.getEffectedSpellsPerRound();
    }

    public final void modifySpells(final int mod) {
        this.spellCounter -= mod;
    }

    public final int getCurrentSP() {
        return this.spellCounter;
    }

    public final void resetItems() {
        this.itemCounter = this.template.getEffectedItemsPerRound();
    }

    public final void modifyItems(final int mod) {
        this.itemCounter -= mod;
    }

    public final int getCurrentIT() {
        return this.itemCounter;
    }

    public final void resetSteals() {
        this.stealCounter = this.template.getEffectedStealsPerRound();
    }

    public final void modifySteals(final int mod) {
        this.stealCounter -= mod;
    }

    public final int getCurrentST() {
        return this.stealCounter;
    }

    public final String getAPString() {
        return "Moves Left: "
                + (this.actionCounter >= 0 ? this.actionCounter : 0);
    }

    public final String getAttackString() {
        return "Attacks Left: "
                + (this.attackCounter >= 0 ? this.attackCounter : 0);
    }

    public final String getSpellString() {
        return "Spells Left: "
                + (this.spellCounter >= 0 ? this.spellCounter : 0);
    }

    public final String getItemString() {
        return "Item Uses Left: "
                + (this.itemCounter >= 0 ? this.itemCounter : 0);
    }

    public final String getStealString() {
        return "Steals Left: "
                + (this.stealCounter >= 0 ? this.stealCounter : 0);
    }

    @Override
    public BufferedImageIcon battleRenderHook() {
        return this.template.getImage();
    }

    @Override
    public String getName() {
        return this.template.getName();
    }

    @Override
    public int getLayer() {
        return MapConstants.LAYER_OBJECT;
    }

    @Override
    public int getCustomFormat() {
        return 2;
    }

    @Override
    public int getCustomProperty(final int propID) {
        switch (propID) {
        case 0:
            return this.getX();
        case 1:
            return this.getY();
        default:
            return MapObject.DEFAULT_CUSTOM_VALUE;
        }
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        switch (propID) {
        case 0:
            this.setX(value);
            break;
        case 1:
            this.setY(value);
            break;
        default:
            break;
        }
    }

    @Override
    public String getDescription() {
        // Description isn't used for battle characters
        return null;
    }

    @Override
    public String getPluralName() {
        // Plural name isn't used for battle characters
        return null;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_BATTLE_CHARACTER);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + this.actionCounter;
        result = prime * result + this.attackCounter;
        result = prime * result + (this.isActive ? 1231 : 1237);
        result = prime * result + this.spellCounter;
        result = prime * result + this.itemCounter;
        result = prime * result + this.stealCounter;
        return prime * result
                + (this.template == null ? 0 : this.template.hashCode());
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof GenericBattleCharacter)) {
            return false;
        }
        final GenericBattleCharacter other = (GenericBattleCharacter) obj;
        if (this.actionCounter != other.actionCounter) {
            return false;
        }
        if (this.attackCounter != other.attackCounter) {
            return false;
        }
        if (this.isActive != other.isActive) {
            return false;
        }
        if (this.spellCounter != other.spellCounter) {
            return false;
        }
        if (this.itemCounter != other.itemCounter) {
            return false;
        }
        if (this.stealCounter != other.stealCounter) {
            return false;
        }
        if (this.template == null) {
            if (other.template != null) {
                return false;
            }
        } else if (!this.template.equals(other.template)) {
            return false;
        }
        return true;
    }
}
