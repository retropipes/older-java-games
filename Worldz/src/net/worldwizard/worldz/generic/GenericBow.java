/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.generic;

import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.Worldz;

public abstract class GenericBow extends GenericUsableObject {
    // Fields
    private final int AT;

    // Constructors
    protected GenericBow(final int uses, final int arrowType) {
        super(uses);
        this.AT = arrowType;
    }

    @Override
    public abstract String getName();

    @Override
    public void useAction(final WorldObject mo, final int x, final int y,
            final int z) {
        Worldz.getApplication().getGameManager().setArrowType(this.AT);
        Worldz.getApplication().getGameManager().keepNextMessage();
        Messager.showMessage(this.getName() + " activated.");
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        this.useAction(null, x, y, z);
    }

    @Override
    public int getCustomProperty(final int propID) {
        return WorldObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
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
