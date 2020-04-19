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

public class DarknessGem extends GenericGem {
    // Constructors
    public DarknessGem() {
        super();
    }

    @Override
    public String getName() {
        return "Darkness Gem";
    }

    @Override
    public String getPluralName() {
        return "Darkness Gems";
    }

    @Override
    public void postMoveActionHook() {
        Mazer5D.getBagOStuff().getMazeManager().getMaze()
                .setVisionRadiusToMinimum();
        SoundPlayer.playSound(SoundIndex.DARKNESS, SoundGroup.GAME);
    }

    @Override
    public String getDescription() {
        return "Darkness Gems decrease the visible area to its minimum.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.DARKNESS_GEM;
    }
}
