/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map.generic;

import com.puttysoftware.dungeondiver3.support.map.Map;
import com.puttysoftware.randomrange.RandomRange;

final class RuleSet implements Cloneable, RandomGenerationRule {
    // Fields
    private int minQuantity;
    private int maxQuantity;
    private boolean percentageFlag;
    private boolean required;
    private int generateQuantity;
    private final RandomRange rng;

    // Constructor
    public RuleSet() {
        this.maxQuantity = 0;
        this.minQuantity = 0;
        this.percentageFlag = false;
        this.required = true;
        this.generateQuantity = 100;
        this.rng = new RandomRange(1, 100);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.generateQuantity;
        result = prime * result + this.maxQuantity;
        result = prime * result + this.minQuantity;
        result = prime * result + (this.percentageFlag ? 1231 : 1237);
        return prime * result + (this.required ? 1231 : 1237);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof RuleSet)) {
            return false;
        }
        final RuleSet other = (RuleSet) obj;
        if (this.generateQuantity != other.generateQuantity) {
            return false;
        }
        if (this.maxQuantity != other.maxQuantity) {
            return false;
        }
        if (this.minQuantity != other.minQuantity) {
            return false;
        }
        if (this.percentageFlag != other.percentageFlag) {
            return false;
        }
        if (this.required != other.required) {
            return false;
        }
        return true;
    }

    // Methods
    @Override
    public RuleSet clone() {
        final RuleSet copy = new RuleSet();
        copy.maxQuantity = this.maxQuantity;
        copy.minQuantity = this.minQuantity;
        copy.percentageFlag = this.percentageFlag;
        copy.required = this.required;
        copy.generateQuantity = this.generateQuantity;
        return copy;
    }

    /**
     * Methods implementing RandomGenerationRule
     */
    @Override
    public int getMaximumRequiredQuantity(final Map map) {
        if (this.percentageFlag) {
            final int base = map.getRows() * map.getColumns();
            final double factor = this.maxQuantity / 100.0;
            return (int) (base * factor);
        } else {
            return this.maxQuantity;
        }
    }

    @Override
    public int getMinimumRequiredQuantity(final Map map) {
        if (this.percentageFlag) {
            final int base = map.getRows() * map.getColumns();
            final double factor = this.minQuantity / 100.0;
            return (int) (base * factor);
        } else {
            return this.minQuantity;
        }
    }

    @Override
    public boolean isRequired() {
        return this.required;
    }

    @Override
    public boolean shouldGenerateObject(final Map map, final int row,
            final int col, final int floor, final int level, final int layer) {
        final int genval = this.rng.generate();
        return genval <= this.generateQuantity;
    }

    @Override
    public int getMaximumRequiredQuantityInBattle(final Map map) {
        if (this.percentageFlag) {
            final int base = map.getRows() * map.getColumns();
            final double factor = this.maxQuantity / 100.0;
            return (int) (base * factor);
        } else {
            return this.maxQuantity;
        }
    }

    @Override
    public int getMinimumRequiredQuantityInBattle(final Map map) {
        if (this.percentageFlag) {
            final int base = map.getRows() * map.getColumns();
            final double factor = this.minQuantity / 100.0;
            return (int) (base * factor);
        } else {
            return this.minQuantity;
        }
    }

    @Override
    public boolean isRequiredInBattle() {
        return this.required;
    }

    @Override
    public boolean shouldGenerateObjectInBattle(final Map map, final int row,
            final int col, final int floor, final int level, final int layer) {
        final int genval = this.rng.generate();
        return genval <= this.generateQuantity;
    }
}
