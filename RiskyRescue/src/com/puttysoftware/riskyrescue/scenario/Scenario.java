/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.scenario;

import java.io.File;

import com.puttysoftware.randomrange.RandomRange;

public class Scenario {
    // Properties
    private final String basePath;

    // Constructors
    public Scenario() {
        final long random = new RandomRange(0, Integer.MAX_VALUE - 1)
                .generate();
        final String randomID = Long.toHexString(random);
        this.basePath = System.getProperty("java.io.tmpdir") + "RiskyRescue"
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