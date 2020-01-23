/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.generic;

import net.worldwizard.images.BufferedImageIcon;
import net.worldwizard.support.creatures.Creature;
import net.worldwizard.support.map.MapConstants;
import net.worldwizard.support.map.objects.Empty;

public abstract class GenericBattleCharacter extends MapObject {
    // Fields
    private final Creature template;
    private int xLoc, yLoc;
    private int saveX, saveY;
    private int actionCounter;
    private int attackCounter;
    private int spellCounter;
    private boolean isActive;
    private int teamID;

    // Constructors
    protected GenericBattleCharacter(final Creature newTemplate) {
        super(true);
        this.template = newTemplate;
        this.xLoc = 0;
        this.yLoc = 0;
        this.saveX = 0;
        this.saveY = 0;
        this.actionCounter = newTemplate.getActionsPerRound();
        this.attackCounter = newTemplate.getEffectedAttacksPerRound();
        this.spellCounter = newTemplate.getEffectedSpellsPerRound();
        this.isActive = true;
        this.teamID = 0;
        this.setSavedObject(new Empty());
    }

    public int getX() {
        return this.xLoc;
    }

    public int getY() {
        return this.yLoc;
    }

    public void setX(final int newX) {
        this.xLoc = newX;
    }

    public void setY(final int newY) {
        this.yLoc = newY;
    }

    public void offsetX(final int newX) {
        this.xLoc += newX;
    }

    public void offsetY(final int newY) {
        this.yLoc += newY;
    }

    public void saveLocation() {
        this.saveX = this.xLoc;
        this.saveY = this.yLoc;
    }

    public void restoreLocation() {
        this.xLoc = this.saveX;
        this.yLoc = this.saveY;
    }

    public Creature getTemplate() {
        return this.template;
    }

    public int getTeamID() {
        return this.teamID;
    }

    public String getTeamString() {
        if (this.teamID == 0) {
            return "Team: Party";
        } else {
            return "Team: Enemies " + this.teamID;
        }
    }

    public void setTeamID(final int newTeam) {
        this.teamID = newTeam;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public void activate() {
        this.isActive = true;
    }

    public void resetAP() {
        this.actionCounter = this.template.getActionsPerRound();
    }

    public void modifyAP(final int mod) {
        this.actionCounter -= mod;
    }

    public int getAP() {
        return this.actionCounter;
    }

    public void resetAttacks() {
        this.attackCounter = this.template.getEffectedAttacksPerRound();
    }

    public void modifyAttacks(final int mod) {
        this.attackCounter -= mod;
    }

    public int getAttacks() {
        return this.attackCounter;
    }

    public void resetSpells() {
        this.spellCounter = this.template.getEffectedSpellsPerRound();
    }

    public void modifySpells(final int mod) {
        this.spellCounter -= mod;
    }

    public int getSpells() {
        return this.spellCounter;
    }

    public String getAPString() {
        return "Moves Left: "
                + (this.actionCounter >= 0 ? this.actionCounter : 0);
    }

    public String getAttackString() {
        return "Attacks Left: "
                + (this.attackCounter >= 0 ? this.attackCounter : 0);
    }

    public String getSpellString() {
        return "Spells Left: "
                + (this.spellCounter >= 0 ? this.spellCounter : 0);
    }

    @Override
    public BufferedImageIcon battleRenderHook(final int x, final int y) {
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
    public int getCustomProperty(final int propID) {
        return MapObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
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
        result = prime * result + this.saveX;
        result = prime * result + this.saveY;
        result = prime * result + this.spellCounter;
        result = prime * result + this.teamID;
        result = prime * result
                + (this.template == null ? 0 : this.template.hashCode());
        result = prime * result + this.xLoc;
        result = prime * result + this.yLoc;
        return result;
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
        if (this.saveX != other.saveX) {
            return false;
        }
        if (this.saveY != other.saveY) {
            return false;
        }
        if (this.spellCounter != other.spellCounter) {
            return false;
        }
        if (this.teamID != other.teamID) {
            return false;
        }
        if (this.template == null) {
            if (other.template != null) {
                return false;
            }
        } else if (!this.template.equals(other.template)) {
            return false;
        }
        if (this.xLoc != other.xLoc) {
            return false;
        }
        if (this.yLoc != other.yLoc) {
            return false;
        }
        return true;
    }
}
