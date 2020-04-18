/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.abc.MazeObjectModel;
import com.puttysoftware.mazer5d.editor.MazeEditor;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.gui.BagOStuff;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;

public class RandomOneShotTeleport extends RandomTeleport {
    // Constructors
    public RandomOneShotTeleport() {
        super();
    }

    public RandomOneShotTeleport(final int newRandomRangeY,
            final int newRandomRangeX) {
        super(newRandomRangeY, newRandomRangeX);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final BagOStuff app = Mazer5D.getBagOStuff();
        app.getGameManager().decay();
        int dr, dc;
        do {
            dr = this.getDestinationRow();
            dc = this.getDestinationColumn();
        } while (!app.getGameManager().tryUpdatePositionRelative(dr, dc));
        app.getGameManager().updatePositionRelative(dr, dc);
        SoundPlayer.playSound(SoundIndex.TELEPORT, SoundGroup.GAME);
    }

    @Override
    public String getName() {
        return "Random One-Shot Teleport";
    }

    @Override
    public String getPluralName() {
        return "Random One-Shot Teleports";
    }

    @Override
    public MazeObjectModel editorPropertiesHook() {
        final MazeEditor me = Mazer5D.getBagOStuff().getEditor();
        final MazeObjectModel mo = me.editTeleportDestination(
                MazeEditor.TELEPORT_TYPE_RANDOM_ONESHOT);
        return mo;
    }

    @Override
    public String getDescription() {
        return "Random One-Shot Teleports are random, and only work once.";
    }
}