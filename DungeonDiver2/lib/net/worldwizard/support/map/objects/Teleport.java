/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.objects;

import net.worldwizard.support.map.generic.GenericTeleport;

public class Teleport extends GenericTeleport {
    // Constructors
    public Teleport() {
        super();
    }

    @Override
    public String getName() {
        return "Teleport";
    }

    @Override
    public String getPluralName() {
        return "Teleports";
    }

    @Override
    public String getDescription() {
        return "Teleports send you to a random destination when stepped on.";
    }
}
