/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericSingleKey;

public class RedKey extends GenericSingleKey {
    // Constructors
    public RedKey() {
        super();
    }

    // Scriptability
    @Override
    public String getName() {
        return "Red Key";
    }

    @Override
    public String getPluralName() {
        return "Red Keys";
    }

    @Override
    public String getDescription() {
        return "Red Keys will unlock Red Locks, and can only be used once.";
    }
}