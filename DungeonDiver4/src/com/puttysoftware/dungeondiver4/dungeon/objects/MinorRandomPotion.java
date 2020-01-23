/*  Fantastle: A Dungeon-Solving Game
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
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.creatures.StatConstants;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractPotion;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class MinorRandomPotion extends AbstractPotion {
    // Fields
    private static final int MIN_EFFECT = -3;
    private static final int MAX_EFFECT = 3;

    // Constructors
    public MinorRandomPotion() {
        super(StatConstants.STAT_CURRENT_MP, true,
                MinorRandomPotion.MIN_EFFECT, MinorRandomPotion.MAX_EFFECT);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_MINOR_RANDOM_POTION;
    }

    @Override
    public String getName() {
        return "Minor Random Potion";
    }

    @Override
    public String getPluralName() {
        return "Minor Random Potions";
    }

    @Override
    public String getDescription() {
        return "Minor Random Potions might regenerate your magic or drain your magic slightly when picked up.";
    }
}