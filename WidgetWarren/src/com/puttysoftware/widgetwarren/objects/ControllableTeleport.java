/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.Application;
import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.game.ObjectInventory;
import com.puttysoftware.widgetwarren.generic.GenericTeleport;
import com.puttysoftware.widgetwarren.generic.MazeObject;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundConstants;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundManager;

public class ControllableTeleport extends GenericTeleport {
    // Constructors
    public ControllableTeleport() {
        super(0, 0, 0);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        final Application app = WidgetWarren.getApplication();
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_WALK);
        app.getGameManager().controllableTeleport();
    }

    @Override
    public String getName() {
        return "Controllable Teleport";
    }

    @Override
    public String getPluralName() {
        return "Controllable Teleports";
    }

    @Override
    public void editorProbeHook() {
        WidgetWarren.getApplication().showMessage(this.getName());
    }

    @Override
    public MazeObject editorPropertiesHook() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Controllable Teleports let you choose the place you teleport to.";
    }

    @Override
    public int getCustomFormat() {
        return 0;
    }
}