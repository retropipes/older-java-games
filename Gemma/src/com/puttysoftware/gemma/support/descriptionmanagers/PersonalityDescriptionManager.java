/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell


 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.descriptionmanagers;

import com.puttysoftware.fileutils.ResourceStreamReader;
import com.puttysoftware.gemma.support.creatures.personalities.PersonalityConstants;
import com.puttysoftware.gemma.support.scenario.Extension;

public class PersonalityDescriptionManager {
    public static String getPersonalityDescription(final int p) {
        final String name = PersonalityConstants.getPersonalityName(p)
                .toLowerCase();
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                PersonalityDescriptionManager.class.getResourceAsStream(
                        "/com/puttysoftware/gemma/support/resources/descriptions/personality/"
                                + name + Extension
                                        .getDescriptionExtensionWithPeriod()))) {
            // Fetch description
            final String desc = rsr.readString();
            rsr.close();
            return desc;
        } catch (final Exception e) {
            return null;
        }
    }
}
