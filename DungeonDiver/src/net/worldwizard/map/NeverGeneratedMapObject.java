package net.worldwizard.map;

public class NeverGeneratedMapObject extends MapObject {
    // Serialization
    private static final long serialVersionUID = 4363404364372L;

    // Constructors
    public NeverGeneratedMapObject(final int otherAppearanceCount,
            final boolean isSolid, final String newName) {
        super(otherAppearanceCount, isSolid, newName, null);
    }

    // Methods
    @Override
    public final boolean shouldGenerateObject(final NDimensionalLocation loc,
            final NDimensionalMap map) {
        return false;
    }

    @Override
    public final int getMinimumRequiredQuantity(final NDimensionalMap map) {
        return 0;
    }

    @Override
    public final int getMaximumRequiredQuantity(final NDimensionalMap map) {
        return 0;
    }

    @Override
    public final boolean isRequired() {
        return false;
    }
}
