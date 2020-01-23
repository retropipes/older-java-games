/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericSingleKey;

public class GreenKey extends GenericSingleKey {
    // Constructors
    public GreenKey() {
        super();
    }

    // Scriptability
    @Override
    public String getName() {
        return "Green Key";
    }

    @Override
    public String getPluralName() {
        return "Green Keys";
    }

    @Override
    public String getDescription() {
        return "Green Keys will unlock Green Locks, and can only be used once.";
    }
}