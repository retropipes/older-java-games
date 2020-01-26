/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.Application;
import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.editor.MazeEditor;
import com.puttysoftware.rulemazer.game.ObjectInventory;
import com.puttysoftware.rulemazer.generic.GenericTeleport;
import com.puttysoftware.rulemazer.generic.MazeObject;
import com.puttysoftware.rulemazer.resourcemanagers.SoundConstants;
import com.puttysoftware.rulemazer.resourcemanagers.SoundManager;

public class StairsUp extends GenericTeleport {
    // Constructors
    public StairsUp() {
        super(0, 0, 0);
    }

    // For derived classes only
    protected StairsUp(final boolean doesAcceptPushInto) {
        super(doesAcceptPushInto);
    }

    @Override
    public String getName() {
        return "Stairs Up";
    }

    @Override
    public String getPluralName() {
        return "Sets of Stairs Up";
    }

    @Override
    public int getDestinationRow() {
        final Application app = Main.getApplication();
        return app.getGameManager().getPlayerManager().getPlayerLocationX();
    }

    @Override
    public int getDestinationColumn() {
        final Application app = Main.getApplication();
        return app.getGameManager().getPlayerManager().getPlayerLocationY();
    }

    @Override
    public int getDestinationFloor() {
        final Application app = Main.getApplication();
        return app.getGameManager().getPlayerManager().getPlayerLocationZ() + 1;
    }

    @Override
    public int getDestinationLevel() {
        final Application app = Main.getApplication();
        return app.getGameManager().getPlayerManager().getPlayerLocationW();
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = Main.getApplication();
        app.getGameManager().updatePositionAbsoluteNoEvents(
                this.getDestinationRow(), this.getDestinationColumn(),
                this.getDestinationFloor(), this.getDestinationLevel());
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_UP);
    }

    @Override
    public void editorPlaceHook() {
        final MazeEditor me = Main.getApplication().getEditor();
        me.pairStairs(MazeEditor.STAIRS_UP);
    }

    @Override
    public MazeObject editorPropertiesHook() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Stairs Up lead to the floor above.";
    }

    @Override
    public int getCustomFormat() {
        return 0;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return MazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}
