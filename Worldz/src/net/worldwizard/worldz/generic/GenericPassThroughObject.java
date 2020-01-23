/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.generic;

import net.worldwizard.worldz.world.WorldConstants;

public abstract class GenericPassThroughObject extends WorldObject {
    // Constructors
    protected GenericPassThroughObject() {
        super(false);
    }

    protected GenericPassThroughObject(final boolean acceptPushInto,
            final boolean acceptPushOut, final boolean acceptPullInto,
            final boolean acceptPullOut) {
        super(false, false, acceptPushInto, acceptPushOut, false,
                acceptPullInto, acceptPullOut, true, false, 0);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return WorldConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_PASS_THROUGH);
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