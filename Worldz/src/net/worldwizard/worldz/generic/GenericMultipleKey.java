/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.generic;

public abstract class GenericMultipleKey extends GenericKey {
    // Constructors
    protected GenericMultipleKey() {
        super(true);
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_MULTIPLE_KEY);
        this.type.set(TypeConstants.TYPE_KEY);
        this.type.set(TypeConstants.TYPE_INVENTORYABLE);
        this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }
}