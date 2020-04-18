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

public class BrightnessGem extends GenericGem {
    // Constructors
    public BrightnessGem() {
        super();
    }

    @Override
    public String getName() {
        return "Brightness Gem";
    }

    @Override
    public String getPluralName() {
        return "Brightness Gems";
    }

    @Override
    public void postMoveActionHook() {
        Mazer5D.getBagOStuff().getMazeManager().getMaze()
                .setVisionRadiusToMaximum();
        SoundPlayer.playSound(SoundIndex.LIGHT, SoundGroup.GAME);
    }

    @Override
    public String getDescription() {
        return "Brightness Gems increase the visible area to its maximum.";
    }
}
