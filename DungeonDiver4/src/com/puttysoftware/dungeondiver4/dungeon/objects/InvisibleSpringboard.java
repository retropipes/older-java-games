/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class InvisibleSpringboard extends Springboard {
    // Constructors
    public InvisibleSpringboard() {
        super();
        this.setTemplateColor(ColorConstants.COLOR_WHITE);
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        DungeonDiver4.getApplication().showMessage(
                "Some unseen force prevents movement that way...");
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        Application app = DungeonDiver4.getApplication();
        app.getGameManager().updatePositionAbsolute(this.getDestinationRow(),
                this.getDestinationColumn(), this.getDestinationFloor());
        SoundManager.playSound(SoundConstants.SOUND_SPRING);
        DungeonDiver4.getApplication().showMessage("Invisible Springboard!");
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_SPRINGBOARD;
    }

    @Override
    public String getName() {
        return "Invisible Springboard";
    }

    @Override
    public String getGameName() {
        return "Empty";
    }

    @Override
    public String getPluralName() {
        return "Invislble Springboards";
    }

    @Override
    public String getDescription() {
        return "Invisible Springboards bounce anything that wanders into them to the floor above. If one of these is placed on the top-most floor, it is impassable.";
    }
}
