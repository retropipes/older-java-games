/*  SceneMaker: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.creatures.castes;

import java.io.File;

import net.worldwizard.support.Support;
import net.worldwizard.support.SystemLoader;
import net.worldwizard.support.variables.Extension;
import net.worldwizard.xio.XDataReader;

public class CasteLoader {
    // Constructors
    private CasteLoader() {
        // Do nothing
    }

    // Methods
    public static Caste loadCaste(final String file) {
        if (file.startsWith("$")) {
            return SystemLoader.loadCaste(file);
        } else {
            try {
                final XDataReader casteFile = new XDataReader(Support
                        .getVariables().getBasePath()
                        + File.separator
                        + "castes"
                        + File.separator
                        + file
                        + Extension.getCasteExtensionWithPeriod(),
                        Extension.getCasteExtension());
                final Caste c = Caste.read(casteFile);
                casteFile.close();
                return c;
            } catch (final Exception ex) {
                Support.getErrorLogger().logError(ex);
                return null;
            }
        }
    }
}
