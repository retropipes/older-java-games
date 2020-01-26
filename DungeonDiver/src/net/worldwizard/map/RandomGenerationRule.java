package net.worldwizard.map;

import java.io.Serializable;

public interface RandomGenerationRule extends Serializable {
    int NO_MINIMUM = 0;
    int NO_MAXIMUM = -1;

    boolean shouldGenerateObject(NDimensionalLocation loc, NDimensionalMap map);

    int getMinimumRequiredQuantity(NDimensionalMap map);

    int getMaximumRequiredQuantity(NDimensionalMap map);

    boolean isRequired();
}
