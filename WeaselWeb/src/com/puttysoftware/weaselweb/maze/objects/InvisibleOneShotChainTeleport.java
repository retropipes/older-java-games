/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.Application;
import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.editor.MazeEditor;
import com.puttysoftware.weaselweb.game.ObjectInventory;
import com.puttysoftware.weaselweb.maze.generic.GenericInvisibleTeleport;
import com.puttysoftware.weaselweb.maze.generic.MazeObject;
import com.puttysoftware.weaselweb.resourcemanagers.SoundConstants;
import com.puttysoftware.weaselweb.resourcemanagers.SoundManager;

public class InvisibleOneShotChainTeleport extends GenericInvisibleTeleport {
    // Constructors
    public InvisibleOneShotChainTeleport() {
        super(0, 0, 0);
        this.getAttributeGroup().unlinkGame();
        this.getAttributeGroup().getGameBase().setImageName(this, "Empty");
    }

    public InvisibleOneShotChainTeleport(final int destinationRow,
            final int destinationColumn, final int destinationFloor) {
        super(destinationRow, destinationColumn, destinationFloor);
        this.getAttributeGroup().unlinkGame();
        this.getAttributeGroup().getGameBase().setImageName(this, "Empty");
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        final Application app = WeaselWeb.getApplication();
        app.getGameManager().decay();
        app.getGameManager().updatePositionAbsoluteNoEvents(
                this.getDestinationRow(), this.getDestinationColumn(),
                this.getDestinationFloor(), this.getDestinationLevel());
        WeaselWeb.getApplication().showMessage("Invisible Teleport!");
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public String getName() {
        return "Invisible One-Shot Chain Teleport";
    }

    @Override
    public String getPluralName() {
        return "Invisible One-Shot Chain Teleports";
    }

    @Override
    public MazeObject editorPropertiesHook() {
        final MazeEditor me = WeaselWeb.getApplication().getEditor();
        final MazeObject mo = me
                .editTeleportDestination(MazeEditor.TELEPORT_TYPE_INVISIBLE_ONESHOT);
        return mo;
    }

    @Override
    public String getDescription() {
        return "Invisible One-Shot Chain Teleports are a combination of invisible, one-shot, and chain teleport behaviors.";
    }
}