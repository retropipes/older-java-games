package net.worldwizard.support.creatures.faiths;

import java.awt.Color;
import java.util.Arrays;

import net.worldwizard.support.datamanagers.FaithDataManager;

public final class Faith {
    private final int faithID;
    private final double[] multipliers;

    Faith(final int fid) {
        this.multipliers = FaithDataManager.getFaithData(fid);
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

    public String getDamageType() {
        return FaithConstants.getFaithDamageType(this.faithID);
    }

    public Color getColor() {
        return FaithConstants.getFaithColor(this.faithID);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.faithID;
        result = prime * result + Arrays.hashCode(this.multipliers);
        return result;
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
