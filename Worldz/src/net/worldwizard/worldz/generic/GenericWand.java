/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.generic;

import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.Worldz;

public abstract class GenericWand extends GenericUsableObject {
    // Constructors
    protected GenericWand() {
        super(1);
    }

    @Override
    public abstract String getName();

    @Override
    public void useAction(final WorldObject mo, final int x, final int y,
            final int z) {
        final Application app = Worldz.getApplication();
        app.getGameManager().morph(mo, x, y, z);
    }

    @Override
    public abstract void useHelper(int x, int y, int z);

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_WAND);
        this.type.set(TypeConstants.TYPE_USABLE);
        this.type.set(TypeConstants.TYPE_INVENTORYABLE);
        this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }
}
