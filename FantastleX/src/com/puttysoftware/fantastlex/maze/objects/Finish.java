/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.Application;
import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.Maze;
import com.puttysoftware.fantastlex.maze.abc.AbstractMazeObject;
import com.puttysoftware.fantastlex.maze.abc.AbstractTeleport;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.maze.utilities.RandomGenerationRule;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

public class Finish extends AbstractTeleport {
    // Constructors
    public Finish() {
        super(0, 0, 0, false, ObjectImageConstants.OBJECT_IMAGE_NONE);
        this.setTemplateColor(ColorConstants.COLOR_WHITE);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        final Application app = FantastleX.getApplication();
        SoundManager.playSound(SoundConstants.SOUND_FINISH);
        app.getGameManager().solvedLevel();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_FINISH;
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
        FantastleX.getApplication().showMessage(this.getName());
    }

    @Override
    public AbstractMazeObject editorPropertiesHook() {
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
    public int getMinimumRequiredQuantity(final Maze maze) {
        return 1;
    }

    @Override
    public int getMaximumRequiredQuantity(final Maze maze) {
        return RandomGenerationRule.NO_LIMIT;
    }
}