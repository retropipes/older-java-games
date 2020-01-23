/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericSingleKey;

public class OrangeKey extends GenericSingleKey {
    // Constructors
    public OrangeKey() {
        super();
    }

    // Scriptability
    @Override
    public String getName() {
        return "Orange Key";
    }

    @Override
    public String getPluralName() {
        return "Orange Keys";
    }

    @Override
    public String getDescription() {
        return "Orange Keys will unlock Orange Locks, and can only be used once.";
    }
}