/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: dungeonr5d@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.abc;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.objects.MasterTrappedWall;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public abstract class AbstractWallTrap extends AbstractDungeonObject {
    // Fields
    private int number;
    private AbstractTrappedWall trigger;
    private final AbstractTrappedWall masterTrigger = new MasterTrappedWall();
    protected static final int NUMBER_MASTER = -1;

    // Constructors
    protected AbstractWallTrap(int newNumber, AbstractTrappedWall newTrigger) {
        super(false, false);
        this.number = newNumber;
        this.trigger = newTrigger;
    }

    @Override
    public AbstractWallTrap clone() {
        AbstractWallTrap copy = (AbstractWallTrap) super.clone();
        copy.number = this.number;
        copy.trigger = this.trigger.clone();
        return copy;
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        DungeonDiver4.getApplication().getGameManager().decay();
        DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                .findAllMatchingObjectsAndDecay(this.masterTrigger);
        if (this.number == AbstractWallTrap.NUMBER_MASTER) {
            DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                    .masterTrapTrigger();
        } else {
            DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                    .findAllMatchingObjectsAndDecay(this);
            DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                    .findAllMatchingObjectsAndDecay(this.trigger);
        }
        DungeonDiver4.getApplication().getGameManager().redrawDungeon();
        SoundManager.playSound(SoundConstants.SOUND_WALL_TRAP);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_TRAP_BASE;
    }

    @Override
    public int getGameBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_GENERIC_WALL_TRAP;
    }

    @Override
    public abstract int getAttributeID();

    @Override
    public int getGameAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_NONE;
    }

    @Override
    public int getTemplateColor() {
        return ColorConstants.COLOR_LIGHT_YELLOW;
    }

    @Override
    public int getAttributeTemplateColor() {
        return ColorConstants.COLOR_DARK_BLUE;
    }

    @Override
    public int getGameAttributeTemplateColor() {
        return ColorConstants.COLOR_NONE;
    }

    @Override
    public String getName() {
        if (this.number != AbstractWallTrap.NUMBER_MASTER) {
            return "Wall Trap " + this.number;
        } else {
            return "Master Wall Trap";
        }
    }

    @Override
    public String getGameName() {
        return "Wall Trap";
    }

    @Override
    public String getPluralName() {
        if (this.number != AbstractWallTrap.NUMBER_MASTER) {
            return "Wall Traps " + this.number;
        } else {
            return "Master Wall Traps";
        }
    }

    @Override
    public int getLayer() {
        return DungeonConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_WALL_TRAP);
    }

    @Override
    public boolean shouldGenerateObject(Dungeon dungeon, int row, int col,
            int floor, int level, int layer) {
        // Blacklist object
        return false;
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