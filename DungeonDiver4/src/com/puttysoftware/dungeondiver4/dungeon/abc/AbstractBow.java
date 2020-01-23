/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: dungeonr5d@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.abc;

import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public abstract class AbstractBow extends AbstractUsableObject {
    // Fields
    private int AT;

    // Constructors
    protected AbstractBow(int uses, int arrowType, int tc) {
        super(uses);
        this.AT = arrowType;
        this.setTemplateColor(tc);
    }

    @Override
    public void useHelper(int x, int y, int z) {
        // Do nothing
    }

    @Override
    public void useAction(AbstractDungeonObject mo, int x, int y, int z) {
        // Do nothing
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_BOW;
    }

    @Override
    public abstract String getName();

    public int getArrowType() {
        return this.AT;
    }

    @Override
    public int getCustomProperty(int propID) {
        return AbstractDungeonObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(int propID, int value) {
        // Do nothing
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_BOW);
        this.type.set(TypeConstants.TYPE_USABLE);
        this.type.set(TypeConstants.TYPE_INVENTORYABLE);
        this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }
}
