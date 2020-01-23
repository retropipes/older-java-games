/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.game.ObjectInventory;
import net.worldwizard.worldz.generic.GenericTeleport;
import net.worldwizard.worldz.generic.WorldObject;

public class OneShotControllableTeleport extends GenericTeleport {
    // Constructors
    public OneShotControllableTeleport() {
        super(0, 0, 0, 0);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        final Application app = Worldz.getApplication();
        if (app.getPrefsManager().getSoundEnabled(
                PreferencesManager.SOUNDS_GAME)) {
            this.playMoveSuccessSound();
        }
        app.getGameManager().controllableTeleport();
        app.getGameManager().decay();
    }

    @Override
    public String getName() {
        return "One-Shot Controllable Teleport";
    }

    @Override
    public String getPluralName() {
        return "One-Shot Controllable Teleports";
    }

    @Override
    public void editorProbeHook() {
        Messager.showMessage(this.getName());
    }

    @Override
    public WorldObject editorPropertiesHook() {
        return null;
    }

    @Override
    public String getMoveSuccessSoundName() {
        return "walk";
    }

    @Override
    public String getDescription() {
        return "One-Shot Controllable Teleports let you choose the place you teleport to, then disappear.";
    }

    @Override
    public int getCustomFormat() {
        return 0;
    }
}