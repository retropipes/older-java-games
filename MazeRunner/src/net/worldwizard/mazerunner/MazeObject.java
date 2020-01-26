package net.worldwizard.mazerunner;

import java.io.Serializable;

import javax.swing.ImageIcon;

public abstract class MazeObject implements Scriptable, Serializable {
    // Properties
    private final boolean solidXN;
    private final boolean solidXS;
    private final boolean solidXE;
    private final boolean solidXW;
    private final boolean solidIN;
    private final boolean solidIE;
    private final boolean solidIS;
    private final boolean solidIW;
    private ImageIcon gameAppearance;
    private ImageIcon editorAppearance;
    private final boolean isPushable;
    private final boolean acceptsPushInto;
    private final boolean acceptsPushOut;
    private final boolean isPullable;
    private final boolean acceptsPullInto;
    private final boolean acceptsPullOut;
    private final boolean friction;
    private final boolean usable;
    private final int uses;
    private final boolean destroyable;
    private final boolean chainReacts;
    private static String set;
    private static int size;
    private String storedGameAppearance;
    private String storedEditorAppearance;
    private final boolean isInventoryable;
    // Serialization
    private static final long serialVersionUID = 1L;

    // Constructors
    public MazeObject(final boolean isSolid, final String newGameAppearance,
            final String newEditorAppearance) {
        this.solidXN = isSolid;
        this.solidXS = isSolid;
        this.solidXE = isSolid;
        this.solidXW = isSolid;
        this.solidIN = isSolid;
        this.solidIS = isSolid;
        this.solidIE = isSolid;
        this.solidIW = isSolid;
        this.gameAppearance = ImageSetManager.getImage(MazeObject.set,
                MazeObject.size, newGameAppearance);
        this.editorAppearance = ImageSetManager.getImage(MazeObject.set,
                MazeObject.size, newEditorAppearance);
        this.isPushable = false;
        this.acceptsPushInto = false;
        this.acceptsPushOut = false;
        this.isPullable = false;
        this.acceptsPullInto = false;
        this.acceptsPullOut = false;
        this.friction = true;
        this.usable = false;
        this.uses = 0;
        this.destroyable = true;
        this.chainReacts = false;
        this.storedGameAppearance = newGameAppearance;
        this.storedEditorAppearance = newEditorAppearance;
        this.isInventoryable = false;
    }

    public MazeObject(final boolean isSolid, final String newGameAppearance,
            final String newEditorAppearance, final boolean isDestroyable) {
        this.solidXN = isSolid;
        this.solidXS = isSolid;
        this.solidXE = isSolid;
        this.solidXW = isSolid;
        this.solidIN = isSolid;
        this.solidIS = isSolid;
        this.solidIE = isSolid;
        this.solidIW = isSolid;
        this.gameAppearance = ImageSetManager.getImage(MazeObject.set,
                MazeObject.size, newGameAppearance);
        this.editorAppearance = ImageSetManager.getImage(MazeObject.set,
                MazeObject.size, newEditorAppearance);
        this.isPushable = false;
        this.acceptsPushInto = false;
        this.acceptsPushOut = false;
        this.isPullable = false;
        this.acceptsPullInto = false;
        this.acceptsPullOut = false;
        this.friction = true;
        this.usable = false;
        this.uses = 0;
        this.destroyable = isDestroyable;
        this.chainReacts = false;
        this.storedGameAppearance = newGameAppearance;
        this.storedEditorAppearance = newEditorAppearance;
        this.isInventoryable = false;
    }

    public MazeObject(final boolean isSolidXN, final boolean isSolidXS,
            final boolean isSolidXE, final boolean isSolidXW,
            final boolean isSolidIN, final boolean isSolidIS,
            final boolean isSolidIE, final boolean isSolidIW,
            final String newGameAppearance, final String newEditorAppearance) {
        this.solidXN = isSolidXN;
        this.solidXS = isSolidXS;
        this.solidXE = isSolidXE;
        this.solidXW = isSolidXW;
        this.solidIN = isSolidIN;
        this.solidIS = isSolidIS;
        this.solidIE = isSolidIE;
        this.solidIW = isSolidIW;
        this.gameAppearance = ImageSetManager.getImage(MazeObject.set,
                MazeObject.size, newGameAppearance);
        this.editorAppearance = ImageSetManager.getImage(MazeObject.set,
                MazeObject.size, newEditorAppearance);
        this.isPushable = false;
        this.acceptsPushInto = false;
        this.acceptsPushOut = false;
        this.isPullable = false;
        this.acceptsPullInto = false;
        this.acceptsPullOut = false;
        this.friction = true;
        this.usable = false;
        this.uses = 0;
        this.destroyable = true;
        this.chainReacts = false;
        this.isInventoryable = false;
    }

    public MazeObject(final boolean isSolidXN, final boolean isSolidXS,
            final boolean isSolidXE, final boolean isSolidXW,
            final boolean isSolidIN, final boolean isSolidIS,
            final boolean isSolidIE, final boolean isSolidIW,
            final String newGameAppearance, final String newEditorAppearance,
            final boolean isDestroyable) {
        this.solidXN = isSolidXN;
        this.solidXS = isSolidXS;
        this.solidXE = isSolidXE;
        this.solidXW = isSolidXW;
        this.solidIN = isSolidIN;
        this.solidIS = isSolidIS;
        this.solidIE = isSolidIE;
        this.solidIW = isSolidIW;
        this.gameAppearance = ImageSetManager.getImage(MazeObject.set,
                MazeObject.size, newGameAppearance);
        this.editorAppearance = ImageSetManager.getImage(MazeObject.set,
                MazeObject.size, newEditorAppearance);
        this.isPushable = false;
        this.acceptsPushInto = false;
        this.acceptsPushOut = false;
        this.isPullable = false;
        this.acceptsPullInto = false;
        this.acceptsPullOut = false;
        this.friction = true;
        this.usable = false;
        this.uses = 0;
        this.destroyable = isDestroyable;
        this.chainReacts = false;
        this.storedGameAppearance = newGameAppearance;
        this.storedEditorAppearance = newEditorAppearance;
        this.isInventoryable = false;
    }

    public MazeObject(final boolean isSolidXN, final boolean isSolidXS,
            final boolean isSolidXE, final boolean isSolidXW,
            final boolean isSolidIN, final boolean isSolidIS,
            final boolean isSolidIE, final boolean isSolidIW,
            final String newGameAppearance, final String newEditorAppearance,
            final boolean pushable, final boolean doesAcceptPushInto,
            final boolean doesAcceptPushOut, final boolean pullable,
            final boolean doesAcceptPullInto, final boolean doesAcceptPullOut,
            final boolean hasFriction, final boolean isUsable,
            final int newUses) {
        this.solidXN = isSolidXN;
        this.solidXS = isSolidXS;
        this.solidXE = isSolidXE;
        this.solidXW = isSolidXW;
        this.solidIN = isSolidIN;
        this.solidIS = isSolidIS;
        this.solidIE = isSolidIE;
        this.solidIW = isSolidIW;
        this.gameAppearance = ImageSetManager.getImage(MazeObject.set,
                MazeObject.size, newGameAppearance);
        this.editorAppearance = ImageSetManager.getImage(MazeObject.set,
                MazeObject.size, newEditorAppearance);
        this.isPushable = pushable;
        this.acceptsPushInto = doesAcceptPushInto;
        this.acceptsPushOut = doesAcceptPushOut;
        this.isPullable = pullable;
        this.acceptsPullInto = doesAcceptPullInto;
        this.acceptsPullOut = doesAcceptPullOut;
        this.friction = hasFriction;
        this.usable = isUsable;
        this.uses = newUses;
        this.destroyable = true;
        this.chainReacts = false;
        this.storedGameAppearance = newGameAppearance;
        this.storedEditorAppearance = newEditorAppearance;
        this.isInventoryable = false;
    }

    public MazeObject(final boolean isSolidXN, final boolean isSolidXS,
            final boolean isSolidXE, final boolean isSolidXW,
            final boolean isSolidIN, final boolean isSolidIS,
            final boolean isSolidIE, final boolean isSolidIW,
            final String newGameAppearance, final String newEditorAppearance,
            final boolean pushable, final boolean doesAcceptPushInto,
            final boolean doesAcceptPushOut, final boolean pullable,
            final boolean doesAcceptPullInto, final boolean doesAcceptPullOut,
            final boolean hasFriction, final boolean isUsable,
            final int newUses, final boolean isDestroyable,
            final boolean doesChainReact) {
        this.solidXN = isSolidXN;
        this.solidXS = isSolidXS;
        this.solidXE = isSolidXE;
        this.solidXW = isSolidXW;
        this.solidIN = isSolidIN;
        this.solidIS = isSolidIS;
        this.solidIE = isSolidIE;
        this.solidIW = isSolidIW;
        this.gameAppearance = ImageSetManager.getImage(MazeObject.set,
                MazeObject.size, newGameAppearance);
        this.editorAppearance = ImageSetManager.getImage(MazeObject.set,
                MazeObject.size, newEditorAppearance);
        this.isPushable = pushable;
        this.acceptsPushInto = doesAcceptPushInto;
        this.acceptsPushOut = doesAcceptPushOut;
        this.isPullable = pullable;
        this.acceptsPullInto = doesAcceptPullInto;
        this.acceptsPullOut = doesAcceptPullOut;
        this.friction = hasFriction;
        this.usable = isUsable;
        this.uses = newUses;
        this.destroyable = isDestroyable;
        this.chainReacts = doesChainReact;
        this.storedGameAppearance = newGameAppearance;
        this.storedEditorAppearance = newEditorAppearance;
        this.isInventoryable = false;
    }

    public MazeObject(final boolean isSolid, final String newGameAppearance,
            final String newEditorAppearance, final boolean isUsable,
            final int newUses, final boolean canBeInventoried) {
        this.solidXN = isSolid;
        this.solidXS = isSolid;
        this.solidXE = isSolid;
        this.solidXW = isSolid;
        this.solidIN = isSolid;
        this.solidIS = isSolid;
        this.solidIE = isSolid;
        this.solidIW = isSolid;
        this.gameAppearance = ImageSetManager.getImage(MazeObject.set,
                MazeObject.size, newGameAppearance);
        this.editorAppearance = ImageSetManager.getImage(MazeObject.set,
                MazeObject.size, newEditorAppearance);
        this.isPushable = false;
        this.acceptsPushInto = false;
        this.acceptsPushOut = false;
        this.isPullable = false;
        this.acceptsPullInto = false;
        this.acceptsPullOut = false;
        this.friction = true;
        this.usable = isUsable;
        this.uses = newUses;
        this.destroyable = true;
        this.chainReacts = false;
        this.storedGameAppearance = newGameAppearance;
        this.storedEditorAppearance = newEditorAppearance;
        this.isInventoryable = canBeInventoried;
    }

    // Accessors
    public boolean isSolid() {
        return this.solidXN && this.solidXS && this.solidXE && this.solidXW
                && this.solidIN && this.solidIS && this.solidIE && this.solidIW;
    }

    public boolean isDirectionallySolid(final boolean ie, final int dirX,
            final int dirY) {
        final MazeRunner app = MazeRunner.getApplication();
        final int px = app.getPlayerLocation(false, false);
        final int py = app.getPlayerLocation(false, true);
        final int diffX = (int) Math.signum(px - dirX);
        final int diffY = (int) Math.signum(py - dirY);
        if (ie) {
            if (diffX == 0 && diffY == -1) {
                return this.solidXN;
            } else if (diffX == 0 && diffY == 1) {
                return this.solidXS;
            } else if (diffX == -1 && diffY == 0) {
                return this.solidXW;
            } else if (diffX == 1 && diffY == 0) {
                return this.solidXE;
            } else {
                return true;
            }
        } else {
            if (diffX == 0 && diffY == -1) {
                return this.solidIN;
            } else if (diffX == 0 && diffY == 1) {
                return this.solidIS;
            } else if (diffX == -1 && diffY == 0) {
                return this.solidIW;
            } else if (diffX == 1 && diffY == 0) {
                return this.solidIE;
            } else {
                return true;
            }
        }
    }

    public ImageIcon getGameAppearance() {
        return this.gameAppearance;
    }

    public ImageIcon getEditorAppearance() {
        return this.editorAppearance;
    }

    public boolean isPushable() {
        return this.isPushable;
    }

    public boolean doesAcceptPushInto() {
        return this.acceptsPushInto;
    }

    public boolean doesAcceptPushOut() {
        return this.acceptsPushOut;
    }

    public boolean isPullable() {
        return this.isPullable;
    }

    public boolean doesAcceptPullInto() {
        return this.acceptsPullInto;
    }

    public boolean doesAcceptPullOut() {
        return this.acceptsPullOut;
    }

    public boolean hasFriction() {
        return this.friction;
    }

    public boolean isUsable() {
        return this.usable;
    }

    public int getUses() {
        return this.uses;
    }

    public boolean isDestroyable() {
        return this.destroyable;
    }

    public boolean doesChainReact() {
        return this.chainReacts;
    }

    public static String getSet() {
        return MazeObject.set;
    }

    public static int getSize() {
        return MazeObject.size;
    }

    public boolean isInventoryable() {
        return this.isInventoryable;
    }

    // Transformers
    public static void setSet(final String newSet) {
        MazeObject.set = newSet;
    }

    public static void setSize(final int newSize) {
        MazeObject.size = newSize;
    }

    public void updateGraphics() {
        this.gameAppearance = ImageSetManager.getImage(MazeObject.set,
                MazeObject.size, this.storedGameAppearance);
        this.editorAppearance = ImageSetManager.getImage(MazeObject.set,
                MazeObject.size, this.storedEditorAppearance);
    }

    // Scriptability
    @Override
    public void preMoveAction(final Inventory inv) {
        // Do nothing
    }

    @Override
    public void preMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        // Do nothing
    }

    @Override
    public void postMoveAction(final Inventory inv) {
        // Do nothing
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        // Do nothing
    }

    @Override
    public boolean isConditionallySolid(final Inventory inv) {
        return this.isSolid();
    }

    @Override
    public boolean isConditionallyDirectionallySolid(final boolean ie,
            final int dirX, final int dirY, final Inventory inv) {
        return this.isDirectionallySolid(ie, dirX, dirY);
    }

    @Override
    public MazeObject editorHook() {
        return this;
    }

    @Override
    public void playSound() {
        // Do nothing
    }

    @Override
    public void pushAction(final Inventory inv, final MazeObject mo,
            final int x, final int y, final int pushX, final int pushY) {
        // Do nothing
    }

    @Override
    public void pushIntoAction(final Inventory inv, final MazeObject pushed,
            final int x, final int y, final int z, final int w) {
        // Do nothing
    }

    @Override
    public void pushOutAction(final Inventory inv) {
        // Do nothing
    }

    @Override
    public void pullAction(final Inventory inv, final MazeObject mo,
            final int x, final int y, final int pullX, final int pullY) {
        // Do nothing
    }

    @Override
    public void pullIntoAction(final Inventory inv) {
        // Do nothing
    }

    @Override
    public void pullOutAction(final Inventory inv) {
        // Do nothing
    }

    @Override
    public void useAction(final MazeObject mo, final int x, final int y,
            final int z, final int w) {
        // Do nothing
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     * @param w
     */
    protected void useHelper(final int x, final int y, final int z,
            final int w) {
        // Do nothing
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     * @param w
     * @return
     */
    public ImageIcon gameRenderHook(final int x, final int y, final int z,
            final int w) {
        return this.gameAppearance;
    }

    @Override
    public void chainReactionAction(final int x, final int y, final int z,
            final int w) {
        // Do nothing
    }

    @Override
    abstract public String toString();

    abstract public String getName();

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.acceptsPullInto ? 1231 : 1237);
        result = prime * result + (this.acceptsPullOut ? 1231 : 1237);
        result = prime * result + (this.acceptsPushInto ? 1231 : 1237);
        result = prime * result + (this.acceptsPushOut ? 1231 : 1237);
        result = prime * result + (this.chainReacts ? 1231 : 1237);
        result = prime * result + (this.destroyable ? 1231 : 1237);
        result = prime * result + (this.friction ? 1231 : 1237);
        result = prime * result + (this.isInventoryable ? 1231 : 1237);
        result = prime * result + (this.isPullable ? 1231 : 1237);
        result = prime * result + (this.isPushable ? 1231 : 1237);
        result = prime * result + (this.solidIE ? 1231 : 1237);
        result = prime * result + (this.solidIN ? 1231 : 1237);
        result = prime * result + (this.solidIS ? 1231 : 1237);
        result = prime * result + (this.solidIW ? 1231 : 1237);
        result = prime * result + (this.solidXE ? 1231 : 1237);
        result = prime * result + (this.solidXN ? 1231 : 1237);
        result = prime * result + (this.solidXS ? 1231 : 1237);
        result = prime * result + (this.solidXW ? 1231 : 1237);
        result = prime * result + (this.storedEditorAppearance == null ? 0
                : this.storedEditorAppearance.hashCode());
        result = prime * result + (this.storedGameAppearance == null ? 0
                : this.storedGameAppearance.hashCode());
        result = prime * result + (this.usable ? 1231 : 1237);
        result = prime * result + this.uses;
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof MazeObject)) {
            return false;
        }
        final MazeObject other = (MazeObject) obj;
        if (this.acceptsPullInto != other.acceptsPullInto) {
            return false;
        }
        if (this.acceptsPullOut != other.acceptsPullOut) {
            return false;
        }
        if (this.acceptsPushInto != other.acceptsPushInto) {
            return false;
        }
        if (this.acceptsPushOut != other.acceptsPushOut) {
            return false;
        }
        if (this.chainReacts != other.chainReacts) {
            return false;
        }
        if (this.destroyable != other.destroyable) {
            return false;
        }
        if (this.friction != other.friction) {
            return false;
        }
        if (this.isInventoryable != other.isInventoryable) {
            return false;
        }
        if (this.isPullable != other.isPullable) {
            return false;
        }
        if (this.isPushable != other.isPushable) {
            return false;
        }
        if (this.solidIE != other.solidIE) {
            return false;
        }
        if (this.solidIN != other.solidIN) {
            return false;
        }
        if (this.solidIS != other.solidIS) {
            return false;
        }
        if (this.solidIW != other.solidIW) {
            return false;
        }
        if (this.solidXE != other.solidXE) {
            return false;
        }
        if (this.solidXN != other.solidXN) {
            return false;
        }
        if (this.solidXS != other.solidXS) {
            return false;
        }
        if (this.solidXW != other.solidXW) {
            return false;
        }
        if (this.storedEditorAppearance == null) {
            if (other.storedEditorAppearance != null) {
                return false;
            }
        } else if (!this.storedEditorAppearance
                .equals(other.storedEditorAppearance)) {
            return false;
        }
        if (this.storedGameAppearance == null) {
            if (other.storedGameAppearance != null) {
                return false;
            }
        } else if (!this.storedGameAppearance
                .equals(other.storedGameAppearance)) {
            return false;
        }
        if (this.usable != other.usable) {
            return false;
        }
        if (this.uses != other.uses) {
            return false;
        }
        return true;
    }
}