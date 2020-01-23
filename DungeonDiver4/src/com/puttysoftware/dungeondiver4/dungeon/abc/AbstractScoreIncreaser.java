/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: dungeonr5d@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.abc;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.objects.Empty;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public abstract class AbstractScoreIncreaser extends AbstractDungeonObject {
    // Constructors
    protected AbstractScoreIncreaser(int tc) {
        super(false, false);
        this.setTemplateColor(tc);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_GEM;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        DungeonDiver4.getApplication().getGameManager().decay();
        SoundManager.playSound(SoundConstants.SOUND_GRAB);
        this.postMoveActionHook();
        DungeonDiver4.getApplication().getGameManager().redrawDungeon();
    }

    public abstract void postMoveActionHook();

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_SCORE_INCREASER);
        this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }

    @Override
    public int getLayer() {
        return DungeonConstants.LAYER_OBJECT;
    }

    @Override
    public boolean arrowHitAction(int locX, int locY, int locZ, int dirX,
            int dirY, int arrowType, DungeonObjectInventory inv) {
        DungeonDiver4.getApplication().getGameManager()
                .morph(new Empty(), locX, locY, locZ);
        SoundManager.playSound(SoundConstants.SOUND_SHATTER);
        return false;
    }

    @Override
    public int getCustomProperty(int propID) {
        return AbstractDungeonObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(int propID, int value) {
        // Do nothing
    }
}
