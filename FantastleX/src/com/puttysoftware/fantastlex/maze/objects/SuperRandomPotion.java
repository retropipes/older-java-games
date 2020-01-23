/*  FantastleX: A Maze/RPG Hybrid Game
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

Any questions should be directed to the author via email at: fantastlex@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.creatures.StatConstants;
import com.puttysoftware.fantastlex.maze.abc.AbstractPotion;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class SuperRandomPotion extends AbstractPotion {
    // Fields
    private static final int MIN_EFFECT = -99;
    private static final int MAX_EFFECT = 99;

    // Constructors
    public SuperRandomPotion() {
        super(StatConstants.STAT_CURRENT_MP, true,
                SuperRandomPotion.MIN_EFFECT, SuperRandomPotion.MAX_EFFECT);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_SUPER_RANDOM_POTION;
    }

    @Override
    public String getName() {
        return "Super Random Potion";
    }

    @Override
    public String getPluralName() {
        return "Super Random Potions";
    }

    @Override
    public String getDescription() {
        return "Super Random Potions might regenerate your magic almost fully or drain your magic almost fully when picked up.";
    }
}