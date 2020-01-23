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

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.creatures.party.PartyManager;
import com.puttysoftware.dungeondiver4.creatures.party.PartyMember;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractBoots;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class ExperienceBoots extends AbstractBoots {
    // Constants
    private static final int EXP_AMOUNT = 1;

    // Constructors
    public ExperienceBoots() {
        super(ColorConstants.COLOR_ORANGE);
    }

    @Override
    public String getName() {
        return "Experience Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of Experience Boots";
    }

    @Override
    public String getDescription() {
        return "Experience Boots give you experience as you walk. Note that you can only wear one pair of boots at once.";
    }

    @Override
    public void stepAction() {
        PartyMember playerCharacter = PartyManager.getParty().getLeader();
        playerCharacter.offsetExperience(ExperienceBoots.EXP_AMOUNT);
        DungeonDiver4.getApplication().getGameManager()
                .addToScore(ExperienceBoots.EXP_AMOUNT);
        // Level Up Check
        if (playerCharacter.checkLevelUp()) {
            playerCharacter.levelUp();
            DungeonDiver4.getApplication().getGameManager().keepNextMessage();
            DungeonDiver4.getApplication().showMessage(
                    "You reached level " + playerCharacter.getLevel() + ".");
        }
    }
}
