/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.abc;

import net.dynamicdungeon.dynamicdungeon.creatures.AbstractCreature;
import net.dynamicdungeon.dynamicdungeon.creatures.StatConstants;
import net.dynamicdungeon.dynamicdungeon.dungeon.DungeonConstants;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.Empty;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.ObjectImageConstants;
import net.dynamicdungeon.images.BufferedImageIcon;

public abstract class AbstractBattleCharacter extends AbstractDungeonObject {
    // Fields
    private final AbstractCreature template;
    private final int actionCounter;
    private final int attackCounter;
    private final int spellCounter;
    private final boolean isActive;

    // Constructors
    protected AbstractBattleCharacter(final AbstractCreature newTemplate) {
        super(true, false);
        this.template = newTemplate;
        this.actionCounter = newTemplate.getMapBattleActionsPerRound();
        this.attackCounter = (int) newTemplate
                .getEffectedStat(StatConstants.STAT_ATTACKS_PER_ROUND);
        this.spellCounter = (int) newTemplate
                .getEffectedStat(StatConstants.STAT_SPELLS_PER_ROUND);
        this.isActive = true;
        this.setSavedObject(new Empty());
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        // Do nothing
    }

    @Override
    protected void setTypes() {
        // Do nothing
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

    public final AbstractCreature getTemplate() {
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

    public final int getCurrentAP() {
        return this.actionCounter;
    }

    public final int getCurrentAT() {
        return this.attackCounter;
    }

    public final int getCurrentSP() {
        return this.spellCounter;
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

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_NONE;
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
        return DungeonConstants.LAYER_OBJECT;
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
                return AbstractDungeonObject.DEFAULT_CUSTOM_VALUE;
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
        return "";
    }

    @Override
    public String getPluralName() {
        // Plural name isn't used for battle characters
        return "";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + this.actionCounter;
        result = prime * result + this.attackCounter;
        result = prime * result + (this.isActive ? 1231 : 1237);
        result = prime * result + this.spellCounter;
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
        if (!(obj instanceof AbstractBattleCharacter)) {
            return false;
        }
        final AbstractBattleCharacter other = (AbstractBattleCharacter) obj;
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
