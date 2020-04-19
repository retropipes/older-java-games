/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.abc.GenericTeleport;
import com.puttysoftware.mazer5d.compatibility.abc.MazeObjectModel;
import com.puttysoftware.mazer5d.compatibility.abc.RandomGenerationRule;
import com.puttysoftware.mazer5d.compatibility.maze.MazeModel;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.gui.BagOStuff;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class Finish extends GenericTeleport {
    // Constructors
    public Finish() {
        super(0, 0, 0);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final BagOStuff app = Mazer5D.getBagOStuff();
        SoundPlayer.playSound(SoundIndex.FINISH, SoundGroup.GAME);
        app.getGameManager().solvedLevel();
    }

    @Override
    public String getName() {
        return "Finish";
    }

    @Override
    public String getPluralName() {
        return "Finishes";
    }

    @Override
    public boolean defersSetProperties() {
        return false;
    }

    @Override
    public void editorProbeHook() {
        Mazer5D.getBagOStuff().showMessage(this.getName());
    }

    @Override
    public MazeObjectModel editorPropertiesHook() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Finishes lead to the next level, if one exists. Otherwise, entering one solves the maze.";
    }

    @Override
    public int getCustomFormat() {
        return 0;
    }

    // Random Generation Rules
    @Override
    public boolean isRequired() {
        return true;
    }

    @Override
    public int getMinimumRequiredQuantity(final MazeModel maze) {
        return 1;
    }

    @Override
    public int getMaximumRequiredQuantity(final MazeModel maze) {
        return RandomGenerationRule.NO_LIMIT;
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.FINISH;
    }
}