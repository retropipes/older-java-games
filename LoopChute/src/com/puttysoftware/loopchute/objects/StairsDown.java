/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.Application;
import com.puttysoftware.loopchute.LoopChute;
import com.puttysoftware.loopchute.editor.MazeEditor;
import com.puttysoftware.loopchute.game.ObjectInventory;
import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericTeleport;
import com.puttysoftware.loopchute.generic.MazeObject;
import com.puttysoftware.loopchute.resourcemanagers.SoundConstants;
import com.puttysoftware.loopchute.resourcemanagers.SoundManager;

public class StairsDown extends GenericTeleport {
    // Constructors
    public StairsDown() {
        super(0, 0, 0, false, "");
        this.setTemplateColor(ColorConstants.COLOR_NONE);
        this.setAttributeTemplateColor(ColorConstants.COLOR_NONE);
    }

    // For derived classes only
    protected StairsDown(final boolean doesAcceptPushInto) {
        super(doesAcceptPushInto, "");
        this.setTemplateColor(ColorConstants.COLOR_NONE);
        this.setAttributeTemplateColor(ColorConstants.COLOR_NONE);
    }

    @Override
    public final String getBaseName() {
        return this.getName();
    }

    @Override
    public String getName() {
        return "Stairs Down";
    }

    @Override
    public String getPluralName() {
        return "Sets of Stairs Down";
    }

    @Override
    public int getDestinationRow() {
        final Application app = LoopChute.getApplication();
        return app.getMazeManager().getMaze().getPlayerLocationX();
    }

    @Override
    public int getDestinationColumn() {
        final Application app = LoopChute.getApplication();
        return app.getMazeManager().getMaze().getPlayerLocationY();
    }

    @Override
    public int getDestinationFloor() {
        final Application app = LoopChute.getApplication();
        return app.getMazeManager().getMaze().getPlayerLocationZ() - 1;
    }

    @Override
    public int getDestinationLevel() {
        final Application app = LoopChute.getApplication();
        return app.getMazeManager().getMaze().getPlayerLocationW();
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = LoopChute.getApplication();
        app.getGameManager().updatePositionAbsoluteNoEvents(
                this.getDestinationRow(), this.getDestinationColumn(),
                this.getDestinationFloor(), this.getDestinationLevel());
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_DOWN);
    }

    @Override
    public void editorPlaceHook() {
        final MazeEditor me = LoopChute.getApplication().getEditor();
        me.pairStairs(MazeEditor.STAIRS_DOWN);
    }

    @Override
    public MazeObject editorPropertiesHook() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Stairs Down lead to the floor below.";
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
