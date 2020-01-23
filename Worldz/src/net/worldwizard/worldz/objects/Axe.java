/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericInfiniteKey;

public class Axe extends GenericInfiniteKey {
    // Constructors
    public Axe() {
        super();
    }

    @Override
    public String getName() {
        return "Axe";
    }

    @Override
    public String getPluralName() {
        return "Axe";
    }

    @Override
    public String getDescription() {
        return "With an Axe, Trees can be cut down. Axes never lose their ability to cut trees.";
    }
}