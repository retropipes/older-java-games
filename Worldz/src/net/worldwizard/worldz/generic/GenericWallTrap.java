/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.generic;

import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.game.ObjectInventory;
import net.worldwizard.worldz.objects.MasterTrappedWall;
import net.worldwizard.worldz.world.WorldConstants;

public abstract class GenericWallTrap extends WorldObject {
    // Fields
    private int number;
    private GenericTrappedWall trigger;
    private final GenericTrappedWall masterTrigger = new MasterTrappedWall();
    protected static final int NUMBER_MASTER = -1;

    // Constructors
    protected GenericWallTrap(final int newNumber,
            final GenericTrappedWall newTrigger) {
        super(false);
        this.number = newNumber;
        this.trigger = newTrigger;
    }

    @Override
    public GenericWallTrap clone() {
        final GenericWallTrap copy = (GenericWallTrap) super.clone();
        copy.number = this.number;
        copy.trigger = this.trigger.clone();
        return copy;
    }

    public int getNumber() {
        return this.number;
    }

    public GenericTrappedWall getTrigger() {
        return this.trigger;
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        Worldz.getApplication().getGameManager().decay();
        Worldz.getApplication().getWorldManager().getWorld()
                .findAllMatchingObjectsAndDecay(this.masterTrigger);
        if (this.number == GenericWallTrap.NUMBER_MASTER) {
            Worldz.getApplication().getWorldManager().getWorld()
                    .masterTrapTrigger();
        } else {
            Worldz.getApplication().getWorldManager().getWorld()
                    .findAllMatchingObjectsAndDecay(this);
            Worldz.getApplication().getWorldManager().getWorld()
                    .findAllMatchingObjectsAndDecay(this.trigger);
        }
        Worldz.getApplication().getGameManager().redrawWorld();
        if (Worldz.getApplication().getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            WorldObject.playWallTrapSound();
        }
    }

    @Override
    public String getName() {
        if (this.number != GenericWallTrap.NUMBER_MASTER) {
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
        if (this.number != GenericWallTrap.NUMBER_MASTER) {
            return "Wall Traps " + this.number;
        } else {
            return "Master Wall Traps";
        }
    }

    @Override
    public int getLayer() {
        return WorldConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_WALL_TRAP);
    }

    @Override
    public String getMoveSuccessSoundName() {
        return "walltrap";
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