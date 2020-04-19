/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.abc.GenericTrap;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;
import com.puttysoftware.mazer5d.mazemodel.VisionModes;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class NoExploreTrap extends GenericTrap {
    // Constructors
    public NoExploreTrap() {
        super();
    }

    @Override
    public String getName() {
        return "No Explore Trap";
    }

    @Override
    public String getPluralName() {
        return "No Explore Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        SoundPlayer.playSound(SoundIndex.CHANGE, SoundGroup.GAME);
        Mazer5D.getBagOStuff().getMazeManager().getMaze().removeVisionMode(
                VisionModes.EXPLORE);
        Mazer5D.getBagOStuff().getGameManager().decay();
    }

    @Override
    public String getDescription() {
        return "No Explore Traps turn exploring mode off, then disappear.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.NO_EXPLORE_TRAP;
    }
}