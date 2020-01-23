/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map.objects;

import com.puttysoftware.dungeondiver3.support.map.generic.GenericPassThroughObject;
import com.puttysoftware.dungeondiver3.support.map.generic.TemplateTransform;

public class OpenDoor extends GenericPassThroughObject {
    // Constructors
    public OpenDoor() {
        super();
        this.setTemplateTransform(new TemplateTransform(0.8, 0.6, 0.4));
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
