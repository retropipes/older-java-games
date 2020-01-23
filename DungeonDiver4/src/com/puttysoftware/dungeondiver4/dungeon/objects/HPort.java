/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractPort;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class HPort extends AbstractPort {
    // Constructors
    public HPort() {
        super(new HPlug(), 'H');
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_H_PORT;
    }
}