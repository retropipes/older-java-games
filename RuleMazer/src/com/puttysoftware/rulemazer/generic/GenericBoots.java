/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.generic;

public abstract class GenericBoots extends GenericPass {
    // Constructors
    protected GenericBoots() {
        super();
    }

    @Override
    public abstract String getName();

    public final String getBootsName() {
        final String name = this.getName();
        return name.substring(0, name.length() - 6);
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_BOOTS);
        this.type.set(TypeConstants.TYPE_PASS);
        this.type.set(TypeConstants.TYPE_INFINITE_KEY);
        this.type.set(TypeConstants.TYPE_KEY);
        this.type.set(TypeConstants.TYPE_INVENTORYABLE);
        this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }
}
