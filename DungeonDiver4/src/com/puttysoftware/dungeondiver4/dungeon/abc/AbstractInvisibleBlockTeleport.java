/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.abc;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public abstract class AbstractInvisibleBlockTeleport
        extends AbstractBlockTeleport {
    // Constructors
    protected AbstractInvisibleBlockTeleport(final int destinationRow,
            final int destinationColumn, final int destinationFloor,
            final int attrName) {
        super(destinationRow, destinationColumn, destinationFloor, attrName);
        this.setTemplateColor(ColorConstants.COLOR_SAND);
        this.setAttributeTemplateColor(
                ColorConstants.COLOR_INVISIBLE_BLOCK_TELEPORT_ATTRIBUTE);
    }

    @Override
    public void pushIntoAction(final DungeonObjectInventory inv,
            final AbstractDungeonObject mo, final int x, final int y,
            final int z) {
        final Application app = DungeonDiver4.getApplication();
        final AbstractMovableObject pushedInto = (AbstractMovableObject) mo;
        app.getGameManager().updatePushedIntoPositionAbsolute(
                this.getDestinationRow(), this.getDestinationColumn(),
                this.getDestinationFloor(), x, y, z, pushedInto, this);
        app.getGameManager().keepNextMessage();
        app.showMessage("Invisible Teleport!");
        SoundManager.playSound(SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public void pullIntoAction(final DungeonObjectInventory inv,
            final AbstractDungeonObject mo, final int x, final int y,
            final int z) {
        final Application app = DungeonDiver4.getApplication();
        final AbstractMovableObject pushedInto = (AbstractMovableObject) mo;
        app.getGameManager().updatePushedIntoPositionAbsolute(
                this.getDestinationRow(), this.getDestinationColumn(),
                this.getDestinationFloor(), x, y, z, pushedInto, this);
        app.getGameManager().keepNextMessage();
        app.showMessage("Invisible Teleport!");
        SoundManager.playSound(SoundConstants.SOUND_TELEPORT);
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_INVISIBLE_TELEPORT);
        this.type.set(TypeConstants.TYPE_TELEPORT);
    }
}
