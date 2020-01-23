/*  SceneMaker: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.spells;

import java.io.File;

import net.worldwizard.support.Support;
import net.worldwizard.support.SystemLoader;
import net.worldwizard.support.variables.Extension;
import net.worldwizard.xio.XDataReader;

public class SpellBookLoader {
    // Constructors
    private SpellBookLoader() {
        // Do nothing
    }

    // Methods
    public static SpellBook loadSpellBook(final String file) {
        if (file.startsWith("$")) {
            return SystemLoader.loadSpellBook(file);
        } else {
            try {
                final XDataReader spellBookFile = new XDataReader(Support
                        .getVariables().getBasePath()
                        + File.separator
                        + "spellbooks"
                        + File.separator
                        + file
                        + Extension.getSpellBookExtensionWithPeriod(),
                        Extension.getSpellBookExtension());
                final SpellBook sb = SpellBook.read(spellBookFile);
                spellBookFile.close();
                return sb;
            } catch (final Exception ex) {
                Support.getErrorLogger().logError(ex);
                return null;
            }
        }
    }
}
