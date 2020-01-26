/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.descriptionmanagers;

import java.io.IOException;

import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.creatures.races.RaceConstants;
import net.dynamicdungeon.dynamicdungeon.dungeon.Extension;
import net.dynamicdungeon.fileutils.ResourceStreamReader;

public class RaceDescriptionManager {
    public static String getRaceDescription(final int r) {
        final String name = RaceConstants.getRaceName(r).toLowerCase();
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                RaceDescriptionManager.class.getResourceAsStream(
                        "/net/dynamicdungeon/dynamicdungeon/resources/descriptions/race/"
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
