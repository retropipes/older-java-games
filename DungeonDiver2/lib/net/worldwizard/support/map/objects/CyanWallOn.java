/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.objects;

import net.worldwizard.support.map.generic.GenericToggleWall;
import net.worldwizard.support.map.generic.TemplateTransform;

public class CyanWallOn extends GenericToggleWall {
    // Constructors
    public CyanWallOn() {
        super(true, new TemplateTransform(0.0, 1.0, 1.0, ""));
    }

    // Scriptability
    @Override
    public String getName() {
        return "Cyan Wall On";
    }

    @Override
    public String getPluralName() {
        return "Cyan Walls On";
    }

    @Override
    public String getDescription() {
        return "Cyan Walls On can NOT be walked through, and will change to Cyan Walls Off when a Cyan Button is pressed.";
    }
}