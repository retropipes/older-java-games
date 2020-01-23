/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.objects;

import net.worldwizard.support.map.generic.GenericToggleWall;
import net.worldwizard.support.map.generic.TemplateTransform;

public class GreenWallOn extends GenericToggleWall {
    // Constructors
    public GreenWallOn() {
        super(true, new TemplateTransform(0.0, 1.0, 0.0, ""));
    }

    // Scriptability
    @Override
    public String getName() {
        return "Green Wall On";
    }

    @Override
    public String getPluralName() {
        return "Green Walls On";
    }

    @Override
    public String getDescription() {
        return "Green Walls On can NOT be walked through, and will change to Green Walls Off when a Green Button is pressed.";
    }
}