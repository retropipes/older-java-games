/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.objects;

import net.worldwizard.support.map.generic.GenericPassThroughObject;
import net.worldwizard.support.map.generic.TemplateTransform;

public class OpenDoor extends GenericPassThroughObject {
    // Constructors
    public OpenDoor() {
        super();
        this.setTemplateTransform(new TemplateTransform(0.8, 0.6, 0.4, ""));
    }

    // Scriptability
    @Override
    public String getName() {
        return "Open Door";
    }

    @Override
    public String getPluralName() {
        return "Open Doors";
    }

    @Override
    public String getDescription() {
        return "Open Doors are purely decorative.";
    }

    @Override
    public boolean enabledInBattle() {
        return false;
    }
}
