/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.editor.WorldEditor;
import net.worldwizard.worldz.game.ObjectInventory;
import net.worldwizard.worldz.generic.GenericTeleport;
import net.worldwizard.worldz.generic.WorldObject;

public class StairsDown extends GenericTeleport {
    // Constructors
    public StairsDown() {
        super(0, 0, 0, 0);
    }

    // For derived classes only
    protected StairsDown(final boolean doesAcceptPushInto) {
        super(doesAcceptPushInto);
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
        final Application app = Worldz.getApplication();
        return app.getGameManager().getPlayerManager().getPlayerLocationX();
    }

    @Override
    public int getDestinationColumn() {
        final Application app = Worldz.getApplication();
        return app.getGameManager().getPlayerManager().getPlayerLocationY();
    }

    @Override
    public int getDestinationFloor() {
        final Application app = Worldz.getApplication();
        return app.getGameManager().getPlayerManager().getPlayerLocationZ() - 1;
    }

    @Override
    public int getDestinationLevel() {
        final Application app = Worldz.getApplication();
        return app.getGameManager().getPlayerManager().getPlayerLocationW();
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        final Application app = Worldz.getApplication();
        app.getGameManager().updatePositionAbsoluteNoEvents(
                this.getDestinationRow(), this.getDestinationColumn(),
                this.getDestinationFloor(), this.getDestinationLevel());
        if (app.getPrefsManager().getSoundEnabled(
                PreferencesManager.SOUNDS_GAME)) {
            this.playMoveSuccessSound();
        }
    }

    @Override
    public void editorPlaceHook() {
        final WorldEditor me = Worldz.getApplication().getEditor();
        me.pairStairs(WorldEditor.STAIRS_DOWN);
    }

    @Override
    public WorldObject editorPropertiesHook() {
        return null;
    }

    @Override
    public String getMoveSuccessSoundName() {
        return "down";
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
        return WorldObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}
