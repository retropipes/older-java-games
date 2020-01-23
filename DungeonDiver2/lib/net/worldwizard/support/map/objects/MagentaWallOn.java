/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.objects;

import net.worldwizard.support.map.generic.GenericToggleWall;
import net.worldwizard.support.map.generic.TemplateTransform;

public class MagentaWallOn extends GenericToggleWall {
    // Constructors
    public MagentaWallOn() {
        super(true, new TemplateTransform(1.0, 0.0, 1.0, ""));
    }

    // Scriptability
    @Override
    public String getName() {
        return "Magenta Wall On";
    }

    @Override
    public String getPluralName() {
        return "Magenta Walls On";
    }

    @Override
    public String getDescription() {
        return "Magenta Walls On can NOT be walked through, and will change to Magenta Walls Off when a Magenta Button is pressed.";
    }
}