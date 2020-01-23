/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008  Iric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractPort;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class IPort extends AbstractPort {
    // Constructors
    public IPort() {
        super(new IPlug(), 'I');
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_I_PORT;
    }
}