package net.worldwizard.map;

import javax.swing.ImageIcon;

public abstract class MapObject implements RandomGenerationRule {
    /**
     *
     */
    private static final long serialVersionUID = 1567264672603346505L;
    // Fields
    private final ImageIcon[] appearances;
    private final boolean solid;
    private final String name;
    private final MapObject prerequisite;
    private static final int GAME_APPEARANCE = 0;
    private static final int EDITOR_APPEARANCE = 1;
    private static final int OTHER_APPEARANCES_OFFSET = 2;
    private static final int MINIMUM_OTHER_APPEARANCES = 1;

    // Constructors
    protected MapObject(final int otherAppearanceCount, final boolean isSolid,
            final String newName, final MapObject prereqObject) {
        int fixedOtherAppearanceCount = otherAppearanceCount;
        if (fixedOtherAppearanceCount < MapObject.MINIMUM_OTHER_APPEARANCES) {
            fixedOtherAppearanceCount = MapObject.MINIMUM_OTHER_APPEARANCES;
        }
        this.appearances = new ImageIcon[MapObject.OTHER_APPEARANCES_OFFSET
                + fixedOtherAppearanceCount];
        this.solid = isSolid;
        this.name = newName;
        this.prerequisite = prereqObject;
    }

    // Methods
    public ImageIcon getGameAppearance() {
        return this.appearances[MapObject.GAME_APPEARANCE];
    }

    public ImageIcon getEditorAppearance() {
        return this.appearances[MapObject.EDITOR_APPEARANCE];
    }

    public final ImageIcon getOtherAppearance(final int ID) {
        return this.appearances[MapObject.OTHER_APPEARANCES_OFFSET + ID];
    }

    public final void setGameAppearance(final ImageIcon newAppearance) {
        this.appearances[MapObject.GAME_APPEARANCE] = newAppearance;
    }

    public final void setEditorAppearance(final ImageIcon newAppearance) {
        this.appearances[MapObject.EDITOR_APPEARANCE] = newAppearance;
    }

    public final void setOtherAppearance(final int ID,
            final ImageIcon newAppearance) {
        this.appearances[MapObject.OTHER_APPEARANCES_OFFSET
                + ID] = newAppearance;
    }

    public final int getOtherAppearanceCount() {
        return this.appearances.length - MapObject.OTHER_APPEARANCES_OFFSET;
    }

    public boolean isSolid() {
        return this.solid;
    }

    public boolean shouldCache() {
        return true;
    }

    public final String getName() {
        return this.name;
    }

    public final boolean hasPrerequisite() {
        return this.prerequisite != null;
    }

    public final MapObject getPrerequisite() {
        return this.prerequisite;
    }

    public final boolean hasNthPrerequisite(final int N) {
        if (this.hasPrerequisite()) {
            final MapObject pr = this.getPrerequisite();
            if (N == 1) {
                return true;
            } else {
                return pr.hasNthPrerequisite(N - 1);
            }
        } else {
            return false;
        }
    }

    public final MapObject getNthPrerequisite(final int N) {
        if (this.hasPrerequisite()) {
            final MapObject pr = this.getPrerequisite();
            if (N == 1) {
                return pr;
            } else {
                return pr.getNthPrerequisite(N - 1);
            }
        } else {
            return null;
        }
    }
}