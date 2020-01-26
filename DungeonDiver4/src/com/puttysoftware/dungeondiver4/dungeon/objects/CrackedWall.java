/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractWall;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class CrackedWall extends AbstractWall {
    // Constructors
    public CrackedWall() {
        super(ColorConstants.COLOR_BROWN);
        this.setAttributeID(ObjectImageConstants.OBJECT_IMAGE_CRACKED);
        this.setAttributeTemplateColor(ColorConstants.COLOR_NONE);
    }

    @Override
    public String getName() {
        return "Cracked Wall";
    }

    @Override
    public String getPluralName() {
        return "Cracked Walls";
    }

    @Override
    public String getDescription() {
        return "Cracked Walls break up into Damaged Walls if walked into.";
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        final int z = DungeonDiver4.getApplication().getDungeonManager()
                .getDungeon().getPlayerLocationZ();
        DungeonDiver4.getApplication().getGameManager().morph(new DamagedWall(),
                dirX, dirY, z, DungeonConstants.LAYER_OBJECT);
        SoundManager.playSound(SoundConstants.SOUND_CRACK);
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_WALL);
    }
}