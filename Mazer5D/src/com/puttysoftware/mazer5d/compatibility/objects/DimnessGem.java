/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.abc.GenericGem;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class DimnessGem extends GenericGem {
    // Constructors
    public DimnessGem() {
        super();
    }

    @Override
    public String getName() {
        return "Dimness Gem";
    }

    @Override
    public String getPluralName() {
        return "Dimness Gems";
    }

    @Override
    public void postMoveActionHook() {
        Mazer5D.getBagOStuff().getMazeManager().getMaze()
                .decrementVisionRadius();
        SoundPlayer.playSound(SoundIndex.DARKNESS, SoundGroup.GAME);
    }

    @Override
    public String getDescription() {
        return "Dimness Gems decrease the visible area by 1.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.DIMNESS_GEM;
    }
}
