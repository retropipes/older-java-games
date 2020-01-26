/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.scenario;

import java.io.File;

import com.puttysoftware.randomrange.RandomRange;

public class Scenario {
    // Properties
    private final String basePath;

    // Constructors
    public Scenario() {
        final long random = new RandomRange(0, Long.MAX_VALUE).generateLong();
        final String randomID = Long.toHexString(random);
        this.basePath = System.getProperty("java.io.tmpdir") + "DungeonDiver3"
                + File.separator + randomID
                + Extension.getScenarioExtensionWithPeriod();
        final File base = new File(this.basePath);
        if (!base.exists()) {
            base.mkdirs();
        }
    }

    // Methods
    public String getBasePath() {
        return this.basePath;
    }
}