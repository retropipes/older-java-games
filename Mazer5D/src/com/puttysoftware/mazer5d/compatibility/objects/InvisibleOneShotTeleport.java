/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.abc.GenericInvisibleTeleport;
import com.puttysoftware.mazer5d.compatibility.abc.MazeObjectModel;
import com.puttysoftware.mazer5d.editor.MazeEditor;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.gui.BagOStuff;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;

public class InvisibleOneShotTeleport extends GenericInvisibleTeleport {
    // Constructors
    public InvisibleOneShotTeleport() {
        super(0, 0, 0);
    }

    public InvisibleOneShotTeleport(final int destinationRow,
            final int destinationColumn, final int destinationFloor) {
        super(destinationRow, destinationColumn, destinationFloor);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final BagOStuff app = Mazer5D.getBagOStuff();
        app.getGameManager().decay();
        app.getGameManager().updatePositionAbsolute(this.getDestinationRow(),
                this.getDestinationColumn(), this.getDestinationFloor());
        Mazer5D.getBagOStuff().showMessage("Invisible Teleport!");
        SoundPlayer.playSound(SoundIndex.TELEPORT, SoundGroup.GAME);
    }

    @Override
    public String getName() {
        return "Invisible One-Shot Teleport";
    }

    @Override
    public String getGameName() {
        return "Empty";
    }

    @Override
    public String getPluralName() {
        return "Invisible One-Shot Teleports";
    }

    @Override
    public MazeObjectModel editorPropertiesHook() {
        final MazeEditor me = Mazer5D.getBagOStuff().getEditor();
        final MazeObjectModel mo = me.editTeleportDestination(
                MazeEditor.TELEPORT_TYPE_INVISIBLE_ONESHOT);
        return mo;
    }

    @Override
    public String getDescription() {
        return "Invisible One-Shot Teleports are a combination of invisible and one-shot teleport behaviors.";
    }
}