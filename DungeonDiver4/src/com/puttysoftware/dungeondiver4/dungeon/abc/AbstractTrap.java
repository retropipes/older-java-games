/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: dungeonr5d@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.abc;

import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public abstract class AbstractTrap extends AbstractDungeonObject {
    // Constructors
    protected AbstractTrap(final int tc, final int attrName,
            final int attrColor) {
        super(false, false);
        this.setTemplateColor(tc);
        this.setAttributeID(attrName);
        this.setAttributeTemplateColor(attrColor);
    }

    // Scriptability
    @Override
    public abstract void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv);

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_TRAP_BASE;
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return DungeonConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_TRAP);
    }

    @Override
    public int getCustomProperty(final int propID) {
        return AbstractDungeonObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}