package net.worldwizard.map;

import java.io.Serializable;

public interface RandomGenerationRule extends Serializable {
    public static final int NO_MINIMUM = 0;
    public static final int NO_MAXIMUM = -1;

    public boolean shouldGenerateObject(NDimensionalLocation loc,
            NDimensionalMap map);

    public int getMinimumRequiredQuantity(NDimensionalMap map);

    public int getMaximumRequiredQuantity(NDimensionalMap map);

    public boolean isRequired();
}
