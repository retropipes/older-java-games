/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.creatures.faiths;

import java.awt.Color;
import java.util.Arrays;

import com.puttysoftware.gemma.support.datamanagers.FaithDataManager;
import com.puttysoftware.gemma.support.descriptionmanagers.FaithDescriptionManager;

public final class Faith {
    private final int faithID;
    private final double[] multipliers;
    private final String desc;

    Faith(final int fid) {
        this.multipliers = FaithDataManager.getFaithData(fid);
        this.desc = FaithDescriptionManager.getFaithDescription(fid);
        this.faithID = fid;
    }

    public double getMultiplierForOtherFaith(final int fid) {
        return this.multipliers[fid];
    }

    public int getFaithID() {
        return this.faithID;
    }

    public String getName() {
        return FaithConstants.getFaithName(this.faithID);
    }

    public String getDescription() {
        return this.desc;
    }

    public Color getColor() {
        return FaithConstants.getFaithColor(this.faithID);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.faithID;
        return prime * result + Arrays.hashCode(this.multipliers);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Faith)) {
            return false;
        }
        final Faith other = (Faith) obj;
        if (this.faithID != other.faithID) {
            return false;
        }
        if (!Arrays.equals(this.multipliers, other.multipliers)) {
            return false;
        }
        return true;
    }
}
