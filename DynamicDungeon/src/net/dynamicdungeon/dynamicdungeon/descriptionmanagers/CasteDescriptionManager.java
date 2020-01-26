/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.descriptionmanagers;

import java.io.IOException;

import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.creatures.castes.CasteConstants;
import net.dynamicdungeon.dynamicdungeon.dungeon.Extension;
import net.dynamicdungeon.fileutils.ResourceStreamReader;

public class CasteDescriptionManager {
    public static String getCasteDescription(final int c) {
        final String name = CasteConstants.CASTE_NAMES[c].toLowerCase();
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                CasteDescriptionManager.class.getResourceAsStream(
                        "/net/dynamicdungeon/dynamicdungeon/resources/descriptions/caste/"
                                + name + Extension
                                        .getInternalDataExtensionWithPeriod()))) {
            // Fetch description
            final String desc = rsr.readString();
            return desc;
        } catch (final IOException e) {
            DynamicDungeon.getErrorLogger().logError(e);
            return null;
        }
    }
}
