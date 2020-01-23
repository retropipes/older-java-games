/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.generic;

public abstract class GenericBoots extends GenericPass {
    // Constructors
    protected GenericBoots(final int tc) {
        super();
        this.setTemplateColor(tc);
    }

    @Override
    public abstract String getName();

    @Override
    public final String getBaseName() {
        return "boots";
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_BOOTS);
        this.type.set(TypeConstants.TYPE_PASS);
        this.type.set(TypeConstants.TYPE_INFINITE_KEY);
        this.type.set(TypeConstants.TYPE_KEY);
        this.type.set(TypeConstants.TYPE_INVENTORYABLE);
    }
}
