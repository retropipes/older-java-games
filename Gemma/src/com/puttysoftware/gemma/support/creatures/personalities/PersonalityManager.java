/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.creatures.personalities;

import javax.swing.JFrame;

import com.puttysoftware.gemma.support.creatures.PartyManager;

public class PersonalityManager {
    private static boolean CACHE_CREATED = false;
    private static Personality[] CACHE;
    private static String[] DESC_CACHE;

    public static Personality selectPersonality(JFrame owner) {
        PersonalityManager.createCache();
        final String[] names = PersonalityConstants.getPersonalityNames();
        String dialogResult = null;
        dialogResult = PartyManager.showCreationDialog(owner,
                "Select a Personality", "Create Character", names,
                PersonalityManager.DESC_CACHE);
        if (dialogResult != null) {
            int index;
            for (index = 0; index < names.length; index++) {
                if (dialogResult.equals(names[index])) {
                    break;
                }
            }
            return PersonalityManager.getPersonality(index);
        } else {
            return null;
        }
    }

    public static Personality getPersonality(int personalityID) {
        PersonalityManager.createCache();
        return PersonalityManager.CACHE[personalityID];
    }

    private static void createCache() {
        if (!PersonalityManager.CACHE_CREATED) {
            if (!PersonalityConstants.personalitiesReady()) {
                PersonalityConstants.initPersonalities();
            }
            // Create cache
            if (!PersonalityConstants.personalitiesReady()) {
                PersonalityConstants.initPersonalities();
            }
            int pc = PersonalityConstants.getPersonalitiesCount();
            PersonalityManager.CACHE = new Personality[pc];
            PersonalityManager.DESC_CACHE = new String[pc];
            for (int x = 0; x < pc; x++) {
                PersonalityManager.CACHE[x] = new Personality(x);
                PersonalityManager.DESC_CACHE[x] = PersonalityManager.CACHE[x]
                        .getDescription();
            }
            PersonalityManager.CACHE_CREATED = true;
        }
    }
}
