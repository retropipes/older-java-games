/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractField;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class ForceField extends AbstractField {
    // Constructors
    public ForceField() {
        super(new EnergySphere(), false, ColorConstants.COLOR_NONE);
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        DungeonDiver4.getApplication().showMessage("You'll get zapped");
        SoundManager.playSound(SoundConstants.SOUND_FORCE_FIELD);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_FORCE_FIELD;
    }

    @Override
    public String getName() {
        return "Force Field";
    }

    @Override
    public String getPluralName() {
        return "Force Fields";
    }

    @Override
    public String getDescription() {
        return "Force Fields block movement without an Energy Sphere.";
    }
}
