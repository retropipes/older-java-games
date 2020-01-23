/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell


 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.descriptionmanagers;

import com.puttysoftware.fileutils.ResourceStreamReader;
import com.puttysoftware.gemma.support.creatures.faiths.FaithConstants;
import com.puttysoftware.gemma.support.scenario.Extension;

public class FaithDescriptionManager {
    public static String getFaithDescription(final int f) {
        String name = FaithConstants.getFaithName(f).toLowerCase();
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                FaithDescriptionManager.class.getResourceAsStream(
                        "/com/puttysoftware/gemma/support/resources/descriptions/faith/"
                                + name + Extension
                                        .getDescriptionExtensionWithPeriod()))) {
            // Fetch description
            String desc = rsr.readString();
            return desc;
        } catch (final Exception e) {
            return null;
        }
    }
}
