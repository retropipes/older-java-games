/*  MazeRunnerII: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.creatures.party;

import com.puttysoftware.mazerunner2.creatures.castes.CasteManager;
import com.puttysoftware.mazerunner2.creatures.faiths.FaithManager;
import com.puttysoftware.mazerunner2.creatures.genders.GenderManager;
import com.puttysoftware.mazerunner2.creatures.personalities.PersonalityManager;
import com.puttysoftware.mazerunner2.creatures.races.RaceManager;

public class NoRPGPartyMember extends PartyMember {
    // Constructors
    NoRPGPartyMember() {
        super(RaceManager.getRace(0), CasteManager.getCaste(0),
                FaithManager.getFaith(0), PersonalityManager.getPersonality(0),
                GenderManager.getGender(0), null);
        // Set initial statistics
        this.setVitality(500);
        this.healPercentage(100);
    }

    // Methods
    @Override
    protected void levelUpHook() {
        // Do nothing
    }
}
