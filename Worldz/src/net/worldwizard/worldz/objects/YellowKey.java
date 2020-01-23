/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericSingleKey;

public class YellowKey extends GenericSingleKey {
    // Constructors
    public YellowKey() {
        super();
    }

    // Scriptability
    @Override
    public String getName() {
        return "Yellow Key";
    }

    @Override
    public String getPluralName() {
        return "Yellow Keys";
    }

    @Override
    public String getDescription() {
        return "Yellow Keys will unlock Yellow Locks, and can only be used once.";
    }
}