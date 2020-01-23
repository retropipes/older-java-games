/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.generic.GenericWand;
import net.worldwizard.worldz.generic.WorldObject;

public class RemoteActionWand extends GenericWand {
    // Constructors
    public RemoteActionWand() {
        super();
    }

    @Override
    public String getName() {
        return "Remote Action Wand";
    }

    @Override
    public String getPluralName() {
        return "Remote Action Wands";
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        this.useAction(null, x, y, z);
    }

    @Override
    public void useAction(final WorldObject mo, final int x, final int y,
            final int z) {
        final Application app = Worldz.getApplication();
        app.getGameManager().doRemoteAction(x, y, z);
    }

    @Override
    public String getDescription() {
        return "Remote Action Wands will act on the target object as if you were there, on top of it.";
    }
}
