/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.creatures.races;

import com.puttysoftware.gemma.support.descriptionmanagers.RaceDescriptionManager;

public class Race {
    private final int[] data;
    private final int raceID;
    private final String desc;

    Race(final int rid, final int... rdata) {
        if (rdata == null
                || rdata.length != RaceConstants.RACE_ATTRIBUTE_COUNT) {
            throw new IllegalArgumentException(
                    "Exactly " + RaceConstants.RACE_ATTRIBUTE_COUNT
                            + " attributes must be specified!");
        }
        this.raceID = rid;
        this.data = rdata;
        this.desc = RaceDescriptionManager.getRaceDescription(rid);
    }

    public int getAttribute(final int aid) {
        return this.data[aid];
    }

    public String getName() {
        return RaceConstants.getRaceName(this.raceID);
    }

    public String getDescription() {
        return this.desc;
    }

    public int getRaceID() {
        return this.raceID;
    }
}
