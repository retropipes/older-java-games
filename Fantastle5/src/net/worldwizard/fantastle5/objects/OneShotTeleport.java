/*  Fantastle: A Maze-Solving Game
    Copyright (C) 2008-2010 Eric Ahnell

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

    Any questions should be directed to the author via email at: fantastle@worldwizard.net
 */
package net.worldwizard.fantastle5.objects;

import net.worldwizard.fantastle5.Application;
import net.worldwizard.fantastle5.Fantastle5;
import net.worldwizard.fantastle5.PreferencesManager;
import net.worldwizard.fantastle5.editor.MazeEditor;
import net.worldwizard.fantastle5.game.ObjectInventory;
import net.worldwizard.fantastle5.generic.GenericTeleport;
import net.worldwizard.fantastle5.generic.MazeObject;

public class OneShotTeleport extends GenericTeleport {
    // Constructors
    public OneShotTeleport() {
        super(0, 0, 0, 0);
    }

    public OneShotTeleport(final int destinationRow,
            final int destinationColumn, final int destinationFloor,
            final int destinationLevel) {
        super(destinationRow, destinationColumn, destinationFloor,
                destinationLevel);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = Fantastle5.getApplication();
        app.getGameManager().decay();
        app.getGameManager().updatePositionAbsolute(this.getDestinationRow(),
                this.getDestinationColumn(), this.getDestinationFloor(),
                this.getDestinationLevel());
        if (app.getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playMoveSuccessSound();
        }
    }

    @Override
    public String getName() {
        return "One-Shot Teleport";
    }

    @Override
    public String getPluralName() {
        return "One-Shot Teleports";
    }

    @Override
    public MazeObject editorPropertiesHook() {
        final MazeEditor me = Fantastle5.getApplication().getEditor();
        final MazeObject mo = me
                .editTeleportDestination(MazeEditor.TELEPORT_TYPE_ONESHOT);
        return mo;
    }

    @Override
    public byte getObjectID() {
        return (byte) 3;
    }

    @Override
    public String getDescription() {
        return "One-Shot Teleports behave like regular Teleports, except they only work once.";
    }
}