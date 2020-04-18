/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.abc.GenericField;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;

public class Slime extends GenericField {
    // Constructors
    public Slime() {
        super(new BioHazardBoots());
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        SoundPlayer.playSound(SoundIndex.WALK_ON_SLIME, SoundGroup.GAME);
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        Mazer5D.getBagOStuff().showMessage("You'll corrode");
        SoundPlayer.playSound(SoundIndex.SLIME, SoundGroup.GAME);
    }

    @Override
    public String getName() {
        return "Slime";
    }

    @Override
    public String getPluralName() {
        return "Squares of Slime";
    }

    @Override
    public boolean overridesDefaultPostMove() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Slime is too corrosive to walk on without Bio-Hazard Boots.";
    }
}