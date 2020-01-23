package net.worldwizard.map;

public class RulelessMapObject extends MapObject {
    // Serialization
    private static final long serialVersionUID = 3523334646585632L;

    // Constructors
    public RulelessMapObject(final int otherAppearanceCount,
            final boolean isSolid, final String newName) {
        super(otherAppearanceCount, isSolid, newName, null);
    }

    // Methods
    @Override
    public final boolean shouldGenerateObject(final NDimensionalLocation loc,
            final NDimensionalMap map) {
        return true;
    }

    @Override
    public final int getMinimumRequiredQuantity(final NDimensionalMap map) {
        return RandomGenerationRule.NO_MINIMUM;
    }

    @Override
    public final int getMaximumRequiredQuantity(final NDimensionalMap map) {
        return RandomGenerationRule.NO_MAXIMUM;
    }

    @Override
    public final boolean isRequired() {
        return false;
    }
}
