/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.generic;

import com.puttysoftware.brainmaze.Application;
import com.puttysoftware.brainmaze.BrainMaze;
import com.puttysoftware.brainmaze.game.ObjectInventory;
import com.puttysoftware.brainmaze.resourcemanagers.SoundConstants;
import com.puttysoftware.brainmaze.resourcemanagers.SoundManager;

public abstract class GenericInvisibleTeleport extends GenericTeleport {
        // Constructors
        protected GenericInvisibleTeleport(final int destinationRow,
                        final int destinationColumn, final int destinationFloor,
                        final String attrName) {
                super(destinationRow, destinationColumn, destinationFloor, true,
                                attrName);
                this.setTemplateColor(ColorConstants.COLOR_CYAN);
                this.setAttributeTemplateColor(
                                ColorConstants.COLOR_INVISIBLE_TELEPORT_ATTRIBUTE);
        }

        // Scriptability
        @Override
        public void postMoveAction(final boolean ie, final int dirX, final int dirY,
                        final ObjectInventory inv) {
                final Application app = BrainMaze.getApplication();
                app.getGameManager().updatePositionAbsolute(this.getDestinationRow(),
                                this.getDestinationColumn(), this.getDestinationFloor());
                app.showMessage("Invisible Teleport!");
                SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                                SoundConstants.SOUND_TELEPORT);
        }

        @Override
        public abstract String getName();

        @Override
        protected void setTypes() {
                this.type.set(TypeConstants.TYPE_INVISIBLE_TELEPORT);
                this.type.set(TypeConstants.TYPE_TELEPORT);
        }
}