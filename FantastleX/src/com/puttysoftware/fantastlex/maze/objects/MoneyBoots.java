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

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.creatures.party.PartyManager;
import com.puttysoftware.fantastlex.maze.abc.AbstractBoots;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class MoneyBoots extends AbstractBoots {
    // Constants
    private static final int GOLD_AMOUNT = 1;

    // Constructors
    public MoneyBoots() {
        super(ColorConstants.COLOR_GREEN);
    }

    @Override
    public String getName() {
        return "Money Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of Money Boots";
    }

    @Override
    public String getDescription() {
        return "Money Boots give you money as you walk. Note that you can only wear one pair of boots at once.";
    }

    @Override
    public void stepAction() {
        PartyManager.getParty().getLeader().offsetGold(MoneyBoots.GOLD_AMOUNT);
        FantastleX.getApplication().getGameManager()
                .addToScore(MoneyBoots.GOLD_AMOUNT);
    }
}
