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

public abstract class GenericBlockTeleport extends GenericTeleport {
        // Constructors
        protected GenericBlockTeleport(final int destinationRow,
                        final int destinationColumn, final int destinationFloor,
                        final String attrName) {
                super(destinationRow, destinationColumn, destinationFloor, true,
                                attrName);
                this.setTemplateColor(ColorConstants.COLOR_ORANGE);
                this.setAttributeTemplateColor(ColorConstants.COLOR_PURPLE);
        }

        @Override
        public void postMoveAction(final boolean ie, final int dirX, final int dirY,
                        final ObjectInventory inv) {
                SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                                SoundConstants.SOUND_WALK);
        }

        @Override
        public void pushIntoAction(final ObjectInventory inv, final MazeObject mo,
                        final int x, final int y, final int z) {
                final Application app = BrainMaze.getApplication();
                final GenericMovableObject pushedInto = (GenericMovableObject) mo;
                app.getGameManager().updatePushedIntoPositionAbsolute(
                                this.getDestinationRow(), this.getDestinationColumn(),
                                this.getDestinationFloor(), x, y, z, pushedInto, this);
                SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                                SoundConstants.SOUND_TELEPORT);
        }

        @Override
        public void pullIntoAction(final ObjectInventory inv, final MazeObject mo,
                        final int x, final int y, final int z) {
                final Application app = BrainMaze.getApplication();
                final GenericMovableObject pushedInto = (GenericMovableObject) mo;
                app.getGameManager().updatePushedIntoPositionAbsolute(
                                this.getDestinationRow(), this.getDestinationColumn(),
                                this.getDestinationFloor(), x, y, z, pushedInto, this);
                SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                                SoundConstants.SOUND_TELEPORT);
        }
}
