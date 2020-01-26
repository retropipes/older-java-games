/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.Application;
import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.editor.MazeEditor;
import com.puttysoftware.widgetwarren.game.ObjectInventory;
import com.puttysoftware.widgetwarren.generic.GenericRandomTeleport;
import com.puttysoftware.widgetwarren.generic.MazeObject;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundConstants;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundManager;

public class RandomOneShotTeleport extends GenericRandomTeleport {
    // Constructors
    public RandomOneShotTeleport() {
        super(0, 0);
    }

    public RandomOneShotTeleport(final int newRandomRangeY,
            final int newRandomRangeX) {
        super(newRandomRangeY, newRandomRangeX);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = WidgetWarren.getApplication();
        app.getGameManager().decay();
        int dr, dc;
        do {
            dr = this.getDestinationRow();
            dc = this.getDestinationColumn();
        } while (!app.getGameManager().tryUpdatePositionRelative(dr, dc));
        app.getGameManager().updatePositionRelative(dr, dc);
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_TELEPORT);
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
    public MazeObject editorPropertiesHook() {
        final MazeEditor me = WidgetWarren.getApplication().getEditor();
        final MazeObject mo = me.editTeleportDestination(
                MazeEditor.TELEPORT_TYPE_RANDOM_ONESHOT);
        return mo;
    }

    @Override
    public String getDescription() {
        return "Random One-Shot Teleports are random, and only work once.";
    }
}