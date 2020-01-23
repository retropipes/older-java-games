/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.scenario;

import java.io.File;

import com.puttysoftware.randomrange.RandomRange;

public class Scenario {
    // Properties
    private final String basePath;

    // Constructors
    public Scenario() {
        long random = new RandomRange(0, Integer.MAX_VALUE - 1).generate();
        String randomID = Long.toHexString(random);
        this.basePath = System.getProperty("java.io.tmpdir") + "Gemma"
                + File.separator + randomID
                + Extension.getScenarioExtensionWithPeriod();
        File base = new File(this.basePath);
        if (!base.exists()) {
            base.mkdirs();
        }
    }

    // Methods
    public String getBasePath() {
        return this.basePath;
    }
}