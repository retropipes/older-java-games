/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.Application;
import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.editor.MazeEditor;
import com.puttysoftware.weaselweb.game.ObjectInventory;
import com.puttysoftware.weaselweb.maze.generic.MazeObject;
import com.puttysoftware.weaselweb.resourcemanagers.SoundConstants;
import com.puttysoftware.weaselweb.resourcemanagers.SoundManager;

public class RandomInvisibleOneShotTeleport extends RandomInvisibleTeleport {
    // Constructors
    public RandomInvisibleOneShotTeleport() {
        super();
        this.getAttributeGroup().unlinkGame();
        this.getAttributeGroup().getGameBase().setImageName(this, "Empty");
    }

    public RandomInvisibleOneShotTeleport(final int newRandomRangeY,
            final int newRandomRangeX) {
        super(newRandomRangeY, newRandomRangeX);
        this.getAttributeGroup().unlinkGame();
        this.getAttributeGroup().getGameBase().setImageName(this, "Empty");
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = WeaselWeb.getApplication();
        app.getGameManager().decay();
        int dr, dc;
        do {
            dr = this.getDestinationRow();
            dc = this.getDestinationColumn();
        } while (!app.getGameManager().tryUpdatePositionRelative(dr, dc));
        app.getGameManager().updatePositionRelative(dr, dc);
        WeaselWeb.getApplication().showMessage("Invisible Teleport!");
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public String getName() {
        return "Random Invisible One-Shot Teleport";
    }

    @Override
    public String getPluralName() {
        return "Random Invisible One-Shot Teleports";
    }

    @Override
    public MazeObject editorPropertiesHook() {
        final MazeEditor me = WeaselWeb.getApplication().getEditor();
        final MazeObject mo = me.editTeleportDestination(
                MazeEditor.TELEPORT_TYPE_RANDOM_INVISIBLE_ONESHOT);
        return mo;
    }

    @Override
    public String getDescription() {
        return "Random Invisible One-Shot Teleports are random, invisible, and only work once.";
    }
}