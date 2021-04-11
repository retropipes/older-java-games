package studio.ignitionigloogames.dungeondiver1.dungeon.objects;

import studio.ignitionigloogames.dungeondiver1.dungeon.GraphicsManager;
import studio.ignitionigloogames.dungeondiver1.utilities.MapObject;

public abstract class DungeonObject extends MapObject {
    /**
     *
     */
    private static final long serialVersionUID = -7639751583274645572L;
    // Fields
    private boolean appearanceValid;

    // Constructors
    public DungeonObject(final boolean solid, final String name,
            final MapObject prereq) {
        super(1, solid, name, prereq);
        this.setGameAppearance(GraphicsManager.getDungeonImage(name));
        this.appearanceValid = true;
    }

    // Methods
    public void updateAppearance() {
        if (!this.appearanceValid) {
            this.setGameAppearance(
                    GraphicsManager.getDungeonImage(this.getName()));
            this.appearanceValid = true;
        }
    }

    public void invalidateAppearance() {
        this.appearanceValid = false;
    }

    public abstract void moveOntoHook();

    /**
     *
     * @param xLoc
     * @param yLoc
     */
    public void playerMoveHook(final int xLoc, final int yLoc) {
        // Do nothing
    }
}
