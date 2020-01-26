/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.Application;
import com.puttysoftware.loopchute.LoopChute;
import com.puttysoftware.loopchute.editor.MazeEditor;
import com.puttysoftware.loopchute.game.ObjectInventory;
import com.puttysoftware.loopchute.generic.GenericInvisibleTeleport;
import com.puttysoftware.loopchute.generic.MazeObject;
import com.puttysoftware.loopchute.resourcemanagers.SoundConstants;
import com.puttysoftware.loopchute.resourcemanagers.SoundManager;

public class InvisibleOneShotTeleport extends GenericInvisibleTeleport {
    // Constructors
    public InvisibleOneShotTeleport() {
        super(0, 0, 0, "one_shot");
    }

    public InvisibleOneShotTeleport(final int destinationRow,
            final int destinationColumn, final int destinationFloor) {
        super(destinationRow, destinationColumn, destinationFloor, "one_shot");
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = LoopChute.getApplication();
        app.getGameManager().decay();
        app.getGameManager().updatePositionAbsolute(this.getDestinationRow(),
                this.getDestinationColumn(), this.getDestinationFloor());
        LoopChute.getApplication().showMessage("Invisible Teleport!");
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_TELEPORT);
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
    public MazeObject editorPropertiesHook() {
        final MazeEditor me = LoopChute.getApplication().getEditor();
        return me.editTeleportDestination(
                MazeEditor.TELEPORT_TYPE_INVISIBLE_ONESHOT);
    }

    @Override
    public String getDescription() {
        return "Invisible One-Shot Teleports are a combination of invisible and one-shot teleport behaviors.";
    }
}