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
import net.worldwizard.fantastle5.generic.GenericWand;
import net.worldwizard.fantastle5.generic.MazeObject;

public class TeleportWand extends GenericWand {
    public TeleportWand() {
        super();
    }

    @Override
    public String getName() {
        return "Teleport Wand";
    }

    @Override
    public String getPluralName() {
        return "Teleport Wands";
    }

    @Override
    public void useHelper(final int x, final int y, final int z, final int w) {
        this.useAction(null, x, y, z, w);
        if (Fantastle5.getApplication().getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playUseSound();
        }
    }

    @Override
    public void useAction(final MazeObject mo, final int x, final int y,
            final int z, final int w) {
        final Application app = Fantastle5.getApplication();
        app.getGameManager().updatePositionAbsolute(x, y, z, w);
    }

    @Override
    public String getUseSoundName() {
        return "teleport";
    }

    @Override
    public byte getObjectID() {
        return (byte) 3;
    }

    @Override
    public String getDescription() {
        return "Teleport Wands will teleport you to the target square when used. You cannot teleport to areas you cannot see.";
    }
}
