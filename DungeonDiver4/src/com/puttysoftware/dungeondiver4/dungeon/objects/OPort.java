/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractPort;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class OPort extends AbstractPort {
    // Constructors
    public OPort() {
        super(new OPlug(), 'O');
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_O_PORT;
    }
}