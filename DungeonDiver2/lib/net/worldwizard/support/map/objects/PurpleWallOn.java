/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.objects;

import net.worldwizard.support.map.generic.GenericToggleWall;
import net.worldwizard.support.map.generic.TemplateTransform;

public class PurpleWallOn extends GenericToggleWall {
    // Constructors
    public PurpleWallOn() {
        super(true, new TemplateTransform(0.25, 0.125, 0.5, ""));
    }

    // Scriptability
    @Override
    public String getName() {
        return "Purple Wall On";
    }

    @Override
    public String getPluralName() {
        return "Purple Walls On";
    }

    @Override
    public String getDescription() {
        return "Purple Walls On can NOT be walked through, and will change to Purple Walls Off when a Purple Button is pressed.";
    }
}