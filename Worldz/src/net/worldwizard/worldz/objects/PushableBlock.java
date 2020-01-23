/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericMovableObject;

public class PushableBlock extends GenericMovableObject {
    // Constructors
    public PushableBlock() {
        super(true, false);
    }

    @Override
    public String getName() {
        return "Pushable Block";
    }

    @Override
    public String getPluralName() {
        return "Pushable Blocks";
    }

    @Override
    public String getDescription() {
        return "Pushable Blocks can only be pushed, not pulled.";
    }
}