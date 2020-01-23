/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractField;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class Lava extends AbstractField {
    // Constructors
    public Lava() {
        super(new FireBoots(), ColorConstants.COLOR_ORANGE);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_WALK_LAVA);
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        DungeonDiver4.getApplication().showMessage("You'll burn");
        SoundManager.playSound(SoundConstants.SOUND_OW);
    }

    @Override
    public String getName() {
        return "Lava";
    }

    @Override
    public String getPluralName() {
        return "Squares of Lava";
    }

    @Override
    public boolean overridesDefaultPostMove() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Lava is too hot to walk on without Fire Boots.";
    }

    @Override
    protected void setTypes() {
        super.setTypes();
        this.type.set(TypeConstants.TYPE_GENERATION_ELIGIBLE);
    }
}