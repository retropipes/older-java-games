/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.variables;

import java.io.File;
import java.io.IOException;

import net.worldwizard.randomnumbers.RandomRange;
import net.worldwizard.support.IDGenerator;
import net.worldwizard.xio.XDataReader;
import net.worldwizard.xio.XDataWriter;

public class Variables {
    // Properties
    private String basePath;
    private String variablesID;

    // Constructors
    public Variables(final boolean system) {
        if (system) {
            this.basePath = System.getProperty("java.io.tmpdir")
                    + File.separator + "Support" + File.separator + "$"
                    + Extension.getVariablesExtensionWithPeriod();
            final File base = new File(this.basePath);
            if (!base.exists()) {
                base.mkdirs();
            }
            this.variablesID = "$";
        } else {
            final long random = new RandomRange(0, Long.MAX_VALUE)
                    .generateLong();
            final String randomID = Long.toHexString(random);
            this.basePath = System.getProperty("java.io.tmpdir")
                    + File.separator + "Support" + File.separator + randomID
                    + Extension.getVariablesExtensionWithPeriod();
            final File base = new File(this.basePath);
            if (!base.exists()) {
                base.mkdirs();
            }
            this.variablesID = IDGenerator.generateRandomID();
        }
    }

    // Static Methods
    public static String getTempFolder() {
        return System.getProperty("java.io.tmpdir") + File.separator
                + "Support";
    }

    // Methods
    public String getBasePath() {
        return this.basePath;
    }

    public String getVariablesID() {
        return this.variablesID;
    }

    public void read() throws IOException {
        final XDataReader reader = new XDataReader(
                this.basePath + File.separator + "metadata.xml",
                Extension.getVariablesExtension());
        this.variablesID = reader.readString();
        reader.close();
    }

    public void write() throws IOException {
        final XDataWriter writer = new XDataWriter(
                this.basePath + File.separator + "metadata.xml",
                Extension.getVariablesExtension());
        writer.writeString(this.variablesID);
        writer.close();
    }
}
