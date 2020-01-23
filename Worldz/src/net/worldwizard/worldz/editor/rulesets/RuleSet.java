package net.worldwizard.worldz.editor.rulesets;

import java.io.IOException;

import net.worldwizard.io.DataReader;
import net.worldwizard.io.DataWriter;
import net.worldwizard.worldz.generic.RandomGenerationRule;
import net.worldwizard.worldz.world.World;

public final class RuleSet implements Cloneable, RandomGenerationRule {
    // Fields
    private int minQuantity;
    private int maxQuantity;
    private boolean percentageFlag;

    // Constructor
    public RuleSet() {
        this.maxQuantity = 0;
        this.minQuantity = 0;
        this.percentageFlag = false;
    }

    // Methods
    @Override
    public RuleSet clone() {
        final RuleSet copy = new RuleSet();
        copy.maxQuantity = this.maxQuantity;
        copy.minQuantity = this.minQuantity;
        copy.percentageFlag = this.percentageFlag;
        return copy;
    }

    public void setQuantityAbsolute(final int min, final int max) {
        // Check for valid arguments
        if (min < 0) {
            throw new IllegalArgumentException("Minimum must be at least zero");
        }
        if (max < 0) {
            throw new IllegalArgumentException("Maximum must be at least zero");
        }
        if (max < min) {
            throw new IllegalArgumentException(
                    "Minimum must be less than Maximum");
        }
        this.minQuantity = min;
        this.maxQuantity = max;
        this.percentageFlag = false;
    }

    public void setQuantityRelative(final int min, final int max) {
        // Check for valid arguments
        if (min < 0) {
            throw new IllegalArgumentException("Minimum must be at least zero");
        }
        if (max < 0) {
            throw new IllegalArgumentException("Maximum must be at least zero");
        }
        if (max < min) {
            throw new IllegalArgumentException(
                    "Minimum must be less than Maximum");
        }
        if (min > 100) {
            throw new IllegalArgumentException(
                    "Minimum must not be more than 100%");
        }
        if (max > 100) {
            throw new IllegalArgumentException(
                    "Maximum must not be more than 100%");
        }
        if (max - min > 100) {
            throw new IllegalArgumentException(
                    "Difference between Maximum and Minimum must not be more than 100%");
        }
        this.minQuantity = min;
        this.maxQuantity = max;
        this.percentageFlag = true;
    }

    public boolean getPercentageFlag() {
        return this.percentageFlag;
    }

    public void readRuleSet(final DataReader reader) throws IOException {
        this.maxQuantity = reader.readInt();
        this.minQuantity = reader.readInt();
        this.percentageFlag = reader.readBoolean();
    }

    public void writeRuleSet(final DataWriter writer) throws IOException {
        writer.writeInt(this.maxQuantity);
        writer.writeInt(this.minQuantity);
        writer.writeBoolean(this.percentageFlag);
    }

    public int getMaximumRequiredQuantity() {
        return this.maxQuantity;
    }

    public int getMinimumRequiredQuantity() {
        return this.minQuantity;
    }

    /**
     * Methods implementing RandomGenerationRule
     */
    @Override
    public int getMaximumRequiredQuantity(final World world) {
        if (this.percentageFlag) {
            final int base = world.getRows() * world.getColumns();
            final double factor = this.maxQuantity / 100.0;
            return (int) (base * factor);
        } else {
            return this.maxQuantity;
        }
    }

    @Override
    public int getMinimumRequiredQuantity(final World world) {
        if (this.percentageFlag) {
            final int base = world.getRows() * world.getColumns();
            final double factor = this.minQuantity / 100.0;
            return (int) (base * factor);
        } else {
            return this.minQuantity;
        }
    }

    @Override
    public boolean isRequired() {
        return true;
    }

    @Override
    public boolean shouldGenerateObject(final World world, final int row,
            final int col, final int floor, final int level, final int layer) {
        return true;
    }
}
