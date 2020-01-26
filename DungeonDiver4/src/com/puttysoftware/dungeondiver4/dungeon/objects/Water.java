/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractField;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class Water extends AbstractField {
    // Constructors
    public Water() {
        super(new AquaBoots(), true, ColorConstants.COLOR_WATER);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final DungeonObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_WALK_WATER);
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        DungeonDiver4.getApplication().showMessage("You'll drown");
        SoundManager.playSound(SoundConstants.SOUND_WATER);
    }

    @Override
    public void pushIntoAction(final DungeonObjectInventory inv,
            final AbstractDungeonObject pushed, final int x, final int y,
            final int z) {
        final Application app = DungeonDiver4.getApplication();
        if (pushed.isPushable()) {
            app.getGameManager().morph(new SunkenBlock(), x, y, z,
                    DungeonConstants.LAYER_GROUND);
            app.getGameManager().morph(new Empty(), x, y, z,
                    DungeonConstants.LAYER_OBJECT);
            SoundManager.playSound(SoundConstants.SOUND_SINK_BLOCK);
        }
    }

    @Override
    public String getName() {
        return "Water";
    }

    @Override
    public String getPluralName() {
        return "Squares of Water";
    }

    @Override
    public boolean overridesDefaultPostMove() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Water is too unstable to walk on without Aqua Boots.";
    }

    @Override
    protected void setTypes() {
        super.setTypes();
        this.type.set(TypeConstants.TYPE_GENERATION_ELIGIBLE);
    }
}
