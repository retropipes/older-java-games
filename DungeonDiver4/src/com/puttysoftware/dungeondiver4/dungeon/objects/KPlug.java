/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractPlug;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class KPlug extends AbstractPlug {
    // Constructors
    public KPlug() {
        super('K');
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_K_PLUG;
    }
}