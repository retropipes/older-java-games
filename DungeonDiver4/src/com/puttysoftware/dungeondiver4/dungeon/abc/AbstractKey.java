/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.abc;

import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;

public abstract class AbstractKey extends AbstractInventoryableObject {
    // Fields
    private boolean infinite;

    // Constructors
    protected AbstractKey(final boolean infiniteUse) {
        super();
        this.infinite = infiniteUse;
    }

    @Override
    public AbstractKey clone() {
        final AbstractKey copy = (AbstractKey) super.clone();
        copy.infinite = this.infinite;
        return copy;
    }

    public boolean isInfinite() {
        return this.infinite;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_KEY);
        this.type.set(TypeConstants.TYPE_INVENTORYABLE);
    }

    @Override
    public abstract String getName();
}
