/*  BrainMaze: A Maze-Solving Game
    Copyright (C) 2008-2012 Eric Ahnell

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

    Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.objects;

import com.puttysoftware.brainmaze.Application;
import com.puttysoftware.brainmaze.BrainMaze;
import com.puttysoftware.brainmaze.editor.MazeEditor;
import com.puttysoftware.brainmaze.game.ObjectInventory;
import com.puttysoftware.brainmaze.generic.GenericTeleport;
import com.puttysoftware.brainmaze.generic.MazeObject;
import com.puttysoftware.brainmaze.resourcemanagers.SoundConstants;
import com.puttysoftware.brainmaze.resourcemanagers.SoundManager;

public class OneShotChainTeleport extends GenericTeleport {
    // Constructors
    public OneShotChainTeleport() {
        super(0, 0, 0, true, "one_shot_chain");
    }

    public OneShotChainTeleport(final int destinationRow,
            final int destinationColumn, final int destinationFloor) {
        super(destinationRow, destinationColumn, destinationFloor, true,
                "one_shot_chain");
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        final Application app = BrainMaze.getApplication();
        app.getGameManager().decay();
        app.getGameManager().updatePositionAbsoluteNoEvents(
                this.getDestinationRow(), this.getDestinationColumn(),
                this.getDestinationFloor(), this.getDestinationLevel());
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public String getName() {
        return "One-Shot Chain Teleport";
    }

    @Override
    public String getPluralName() {
        return "One-Shot Chain Teleports";
    }

    @Override
    public MazeObject editorPropertiesHook() {
        final MazeEditor me = BrainMaze.getApplication().getEditor();
        return me
                .editTeleportDestination(MazeEditor.TELEPORT_TYPE_ONESHOT_CHAIN);
    }

    @Override
    public String getDescription() {
        return "One-Shot Chain Teleports behave like regular Teleports, except they only work once.";
    }
}