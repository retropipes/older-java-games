/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.generic;

import com.puttysoftware.loopchute.Application;
import com.puttysoftware.loopchute.LoopChute;
import com.puttysoftware.loopchute.game.ObjectInventory;
import com.puttysoftware.loopchute.resourcemanagers.SoundConstants;
import com.puttysoftware.loopchute.resourcemanagers.SoundManager;

public abstract class GenericInvisibleBlockTeleport
                extends GenericBlockTeleport {
        // Constructors
        protected GenericInvisibleBlockTeleport(final int destinationRow,
                        final int destinationColumn, final int destinationFloor,
                        final String attrName) {
                super(destinationRow, destinationColumn, destinationFloor, attrName);
                this.setTemplateColor(ColorConstants.COLOR_SAND);
                this.setAttributeTemplateColor(
                                ColorConstants.COLOR_INVISIBLE_BLOCK_TELEPORT_ATTRIBUTE);
        }

        @Override
        public void pushIntoAction(final ObjectInventory inv, final MazeObject mo,
                        final int x, final int y, final int z) {
                final Application app = LoopChute.getApplication();
                final GenericMovableObject pushedInto = (GenericMovableObject) mo;
                app.getGameManager().updatePushedIntoPositionAbsolute(
                                this.getDestinationRow(), this.getDestinationColumn(),
                                this.getDestinationFloor(), x, y, z, pushedInto, this);
                app.getGameManager().keepNextMessage();
                app.showMessage("Invisible Teleport!");
                SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                                SoundConstants.SOUND_TELEPORT);
        }

        @Override
        public void pullIntoAction(final ObjectInventory inv, final MazeObject mo,
                        final int x, final int y, final int z) {
                final Application app = LoopChute.getApplication();
                final GenericMovableObject pushedInto = (GenericMovableObject) mo;
                app.getGameManager().updatePushedIntoPositionAbsolute(
                                this.getDestinationRow(), this.getDestinationColumn(),
                                this.getDestinationFloor(), x, y, z, pushedInto, this);
                app.getGameManager().keepNextMessage();
                app.showMessage("Invisible Teleport!");
                SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                                SoundConstants.SOUND_TELEPORT);
        }

        @Override
        protected void setTypes() {
                this.type.set(TypeConstants.TYPE_INVISIBLE_TELEPORT);
                this.type.set(TypeConstants.TYPE_TELEPORT);
        }
}
