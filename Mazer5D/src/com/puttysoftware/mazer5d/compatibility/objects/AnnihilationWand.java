/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.abc.GenericWand;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class AnnihilationWand extends GenericWand {
    // Constructors
    public AnnihilationWand() {
        super();
    }

    @Override
    public String getName() {
        return "Annihilation Wand";
    }

    @Override
    public String getPluralName() {
        return "Annihilation Wands";
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        this.useAction(GameObjects.getEmptySpace(), x, y, z);
        SoundPlayer.playSound(SoundIndex.DESTROY, SoundGroup.GAME);
    }

    @Override
    public String getDescription() {
        return "Annihilation Wands will destroy any object (not ground) when used, except the Void or a Sealing Wall.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.ANNIHILATION_WAND;
    }
}
