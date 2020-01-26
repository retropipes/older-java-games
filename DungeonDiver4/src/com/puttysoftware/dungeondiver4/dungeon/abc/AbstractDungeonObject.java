/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.abc;

import java.io.IOException;
import java.util.BitSet;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.FormatConstants;
import com.puttysoftware.dungeondiver4.dungeon.objects.BattleCharacter;
import com.puttysoftware.dungeondiver4.dungeon.objects.GhostAmulet;
import com.puttysoftware.dungeondiver4.dungeon.objects.PasswallBoots;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ArrowTypeConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.RandomGenerationRule;
import com.puttysoftware.dungeondiver4.dungeon.utilities.SolidProperties;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.editor.rulesets.RuleSet;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageManager;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;
import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.llds.CloneableObject;
import com.puttysoftware.randomrange.RandomRange;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public abstract class AbstractDungeonObject extends CloneableObject
        implements TypeConstants, RandomGenerationRule {
    // Properties
    private SolidProperties sp;
    private boolean pushable;
    private boolean pushableInto;
    private boolean pushableOut;
    private boolean pullable;
    private boolean pullableInto;
    private boolean pullableOut;
    private boolean friction;
    private boolean usable;
    private int uses;
    private boolean destroyable;
    private boolean chainReacts;
    private boolean isInventoryable;
    private final boolean blocksLOS;
    private int templateColor;
    private int timerValue;
    private int initialTimerValue;
    private boolean timerActive;
    private int attributeID;
    private int attributeTemplateColor;
    protected BitSet type;
    private RuleSet ruleSet;
    private AbstractDungeonObject saved;
    public static final int DEFAULT_CUSTOM_VALUE = 0;
    protected static final int CUSTOM_FORMAT_MANUAL_OVERRIDE = -1;
    private static final String OBJECT_GROUP = "object";
    private static final String OBJECT_ID_GROUP = "identifier";
    private static final String OBJECT_SETTINGS_GROUP = "settings";
    private static final String OBJECT_SAVED_GROUP = "saved";

    // Constructors
    public AbstractDungeonObject(final boolean isSolid,
            final boolean sightBlock) {
        this.sp = new SolidProperties();
        this.sp.setSolid(isSolid);
        this.pushable = false;
        this.pushableInto = false;
        this.pushableOut = false;
        this.pullable = false;
        this.pullableInto = false;
        this.pullableOut = false;
        this.friction = true;
        this.destroyable = true;
        this.chainReacts = false;
        this.isInventoryable = false;
        this.blocksLOS = sightBlock;
        this.templateColor = ColorConstants.COLOR_NONE;
        this.attributeTemplateColor = ColorConstants.COLOR_NONE;
        this.type = new BitSet(TypeConstants.TYPES_COUNT);
        this.usable = false;
        this.uses = 0;
        this.timerValue = 0;
        this.initialTimerValue = 0;
        this.timerActive = false;
        this.attributeID = ObjectImageConstants.OBJECT_IMAGE_NONE;
        this.setTypes();
    }

    public AbstractDungeonObject(final boolean isSolid,
            final boolean isPushable, final boolean doesAcceptPushInto,
            final boolean doesAcceptPushOut, final boolean isPullable,
            final boolean doesAcceptPullInto, final boolean doesAcceptPullOut,
            final boolean hasFriction, final boolean sightBlock) {
        this.sp = new SolidProperties();
        this.sp.setSolid(isSolid);
        this.pushable = isPushable;
        this.pushableInto = doesAcceptPushInto;
        this.pushableOut = doesAcceptPushOut;
        this.pullable = isPullable;
        this.pullableInto = doesAcceptPullInto;
        this.pullableOut = doesAcceptPullOut;
        this.friction = hasFriction;
        this.destroyable = true;
        this.chainReacts = false;
        this.isInventoryable = false;
        this.blocksLOS = sightBlock;
        this.templateColor = ColorConstants.COLOR_NONE;
        this.attributeTemplateColor = ColorConstants.COLOR_NONE;
        this.type = new BitSet(TypeConstants.TYPES_COUNT);
        this.usable = false;
        this.uses = 0;
        this.timerValue = 0;
        this.initialTimerValue = 0;
        this.timerActive = false;
        this.attributeID = ObjectImageConstants.OBJECT_IMAGE_NONE;
        this.setTypes();
    }

    public AbstractDungeonObject(final boolean isSolid,
            final boolean isPushable, final boolean doesAcceptPushInto,
            final boolean doesAcceptPushOut, final boolean isPullable,
            final boolean doesAcceptPullInto, final boolean doesAcceptPullOut,
            final boolean hasFriction, final boolean isDestroyable,
            final boolean doesChainReact, final boolean sightBlock) {
        this.sp = new SolidProperties();
        this.sp.setSolid(isSolid);
        this.pushable = isPushable;
        this.pushableInto = doesAcceptPushInto;
        this.pushableOut = doesAcceptPushOut;
        this.pullable = isPullable;
        this.pullableInto = doesAcceptPullInto;
        this.pullableOut = doesAcceptPullOut;
        this.friction = hasFriction;
        this.destroyable = isDestroyable;
        this.chainReacts = doesChainReact;
        this.isInventoryable = false;
        this.blocksLOS = sightBlock;
        this.templateColor = ColorConstants.COLOR_NONE;
        this.attributeTemplateColor = ColorConstants.COLOR_NONE;
        this.type = new BitSet(TypeConstants.TYPES_COUNT);
        this.usable = false;
        this.uses = 0;
        this.timerValue = 0;
        this.initialTimerValue = 0;
        this.timerActive = false;
        this.attributeID = ObjectImageConstants.OBJECT_IMAGE_NONE;
        this.setTypes();
    }

    public AbstractDungeonObject(final boolean isSolid,
            final boolean canBeInventoried, final boolean sightBlock) {
        this.sp = new SolidProperties();
        this.sp.setSolid(isSolid);
        this.pushable = false;
        this.pushableInto = false;
        this.pushableOut = false;
        this.pullable = false;
        this.pullableInto = false;
        this.pullableOut = false;
        this.friction = true;
        this.destroyable = true;
        this.chainReacts = false;
        this.isInventoryable = canBeInventoried;
        this.blocksLOS = sightBlock;
        this.templateColor = ColorConstants.COLOR_NONE;
        this.attributeTemplateColor = ColorConstants.COLOR_NONE;
        this.type = new BitSet(TypeConstants.TYPES_COUNT);
        this.usable = false;
        this.uses = 0;
        this.timerValue = 0;
        this.initialTimerValue = 0;
        this.timerActive = false;
        this.attributeID = ObjectImageConstants.OBJECT_IMAGE_NONE;
        this.setTypes();
    }

    public AbstractDungeonObject(final boolean isSolid, final boolean isUsable,
            final int newUses, final boolean canBeInventoried,
            final boolean sightBlock) {
        this.sp = new SolidProperties();
        this.sp.setSolid(isSolid);
        this.pushable = false;
        this.pushableInto = false;
        this.pushableOut = false;
        this.pullable = false;
        this.pullableInto = false;
        this.pullableOut = false;
        this.friction = true;
        this.destroyable = true;
        this.chainReacts = false;
        this.isInventoryable = canBeInventoried;
        this.blocksLOS = sightBlock;
        this.templateColor = ColorConstants.COLOR_NONE;
        this.attributeTemplateColor = ColorConstants.COLOR_NONE;
        this.type = new BitSet(TypeConstants.TYPES_COUNT);
        this.usable = isUsable;
        this.uses = newUses;
        this.timerValue = 0;
        this.initialTimerValue = 0;
        this.timerActive = false;
        this.attributeID = ObjectImageConstants.OBJECT_IMAGE_NONE;
        this.setTypes();
    }

    public AbstractDungeonObject() {
        this.sp = new SolidProperties();
        this.sp.setSolid(true);
        this.pushable = false;
        this.pushableInto = false;
        this.pushableOut = false;
        this.pullable = false;
        this.pullableInto = false;
        this.pullableOut = false;
        this.friction = true;
        this.destroyable = true;
        this.chainReacts = false;
        this.isInventoryable = false;
        this.blocksLOS = false;
        this.templateColor = ColorConstants.COLOR_NONE;
        this.attributeTemplateColor = ColorConstants.COLOR_NONE;
        this.type = new BitSet(TypeConstants.TYPES_COUNT);
        this.usable = false;
        this.uses = 0;
        this.timerValue = 0;
        this.initialTimerValue = 0;
        this.timerActive = false;
        this.attributeID = ObjectImageConstants.OBJECT_IMAGE_NONE;
        this.setTypes();
    }

    // Methods
    @Override
    public AbstractDungeonObject clone() {
        try {
            final AbstractDungeonObject copy = this.getClass().newInstance();
            copy.sp = this.sp.clone();
            copy.pushable = this.pushable;
            copy.pushableInto = this.pushableInto;
            copy.pushableOut = this.pushableOut;
            copy.pullable = this.pullable;
            copy.pullableInto = this.pullableInto;
            copy.pullableOut = this.pullableOut;
            copy.friction = this.friction;
            copy.destroyable = this.destroyable;
            copy.chainReacts = this.chainReacts;
            copy.isInventoryable = this.isInventoryable;
            copy.templateColor = this.templateColor;
            copy.type = (BitSet) this.type.clone();
            copy.usable = this.usable;
            copy.uses = this.uses;
            copy.timerValue = this.timerValue;
            copy.initialTimerValue = this.initialTimerValue;
            copy.timerActive = this.timerActive;
            copy.attributeID = this.attributeID;
            copy.type = (BitSet) this.type.clone();
            if (this.ruleSet != null) {
                copy.ruleSet = this.ruleSet.clone();
            }
            return copy;
        } catch (final InstantiationException e) {
            DungeonDiver4.getErrorLogger().logError(e);
            return null;
        } catch (final IllegalAccessException e) {
            DungeonDiver4.getErrorLogger().logError(e);
            return null;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.chainReacts ? 1231 : 1237);
        result = prime * result + (this.destroyable ? 1231 : 1237);
        result = prime * result + (this.friction ? 1231 : 1237);
        result = prime * result + (this.isInventoryable ? 1231 : 1237);
        result = prime * result + (this.pullable ? 1231 : 1237);
        result = prime * result + (this.pullableInto ? 1231 : 1237);
        result = prime * result + (this.pullableOut ? 1231 : 1237);
        result = prime * result + (this.pushable ? 1231 : 1237);
        result = prime * result + (this.pushableInto ? 1231 : 1237);
        result = prime * result + (this.pushableOut ? 1231 : 1237);
        result = prime * result + (this.sp == null ? 0 : this.sp.hashCode());
        result = prime * result + this.templateColor;
        result = prime * result + this.timerValue;
        result = prime * result + this.initialTimerValue;
        result = prime * result + this.uses;
        result = prime * result + (this.usable ? 1231 : 1237);
        result = prime * result + (this.timerActive ? 1231 : 1237);
        result = prime * result + this.attributeID;
        return prime * result + (this.type == null ? 0 : this.type.hashCode());
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AbstractDungeonObject)) {
            return false;
        }
        final AbstractDungeonObject other = (AbstractDungeonObject) obj;
        if (this.attributeID != other.attributeID) {
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
        if (this.pullable != other.pullable) {
            return false;
        }
        if (this.pullableInto != other.pullableInto) {
            return false;
        }
        if (this.pullableOut != other.pullableOut) {
            return false;
        }
        if (this.pushable != other.pushable) {
            return false;
        }
        if (this.pushableInto != other.pushableInto) {
            return false;
        }
        if (this.pushableOut != other.pushableOut) {
            return false;
        }
        if (this.sp == null) {
            if (other.sp != null) {
                return false;
            }
        } else if (!this.sp.equals(other.sp)) {
            return false;
        }
        if (this.templateColor != other.templateColor) {
            return false;
        }
        if (this.type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!this.type.equals(other.type)) {
            return false;
        }
        if (this.timerActive != other.timerActive) {
            return false;
        }
        if (this.timerValue != other.timerValue) {
            return false;
        }
        if (this.initialTimerValue != other.initialTimerValue) {
            return false;
        }
        if (this.uses != other.uses) {
            return false;
        }
        if (this.usable != other.usable) {
            return false;
        }
        return true;
    }

    public AbstractDungeonObject getSavedObject() {
        if (this.saved == null) {
            throw new NullPointerException("Saved object == NULL!");
        }
        return this.saved;
    }

    public void setSavedObject(final AbstractDungeonObject newSaved) {
        if (newSaved == null) {
            throw new IllegalArgumentException("New saved object == NULL!");
        }
        this.saved = newSaved;
    }

    public static int getBattleAPCost() {
        return 1;
    }

    public boolean hasRuleSet() {
        return this.ruleSet != null;
    }

    public void giveRuleSet() {
        this.ruleSet = new RuleSet();
    }

    public void takeRuleSet() {
        this.ruleSet = null;
    }

    public RuleSet getRuleSet() {
        return this.ruleSet;
    }

    public boolean isConditionallySolid(final DungeonObjectInventory inv) {
        // Handle passwall boots
        if (inv.isItemThere(new PasswallBoots())) {
            return false;
        } else {
            return this.isSolid();
        }
    }

    public boolean isSolid() {
        return this.sp.isSolid();
    }

    public boolean isSolidInBattle() {
        if (this.enabledInBattle()) {
            return this.isSolid();
        } else {
            return false;
        }
    }

    public boolean isDirectionallySolid(final boolean ie, final int dirX,
            final int dirY) {
        return this.sp.isDirectionallySolid(ie, dirX, dirY);
    }

    public boolean isConditionallyDirectionallySolid(final boolean ie,
            final int dirX, final int dirY, final DungeonObjectInventory inv) {
        // Handle ghost amulet and passwall boots
        if (inv.isItemThere(new GhostAmulet())
                || inv.isItemThere(new PasswallBoots())) {
            return false;
        } else {
            return this.sp.isDirectionallySolid(ie, dirX, dirY);
        }
    }

    public boolean isSightBlocking() {
        return this.blocksLOS;
    }

    public boolean isOfType(final int testType) {
        return this.type.get(testType);
    }

    public int[] getAllTypes() {
        int count = 0;
        for (int x = 0; x < TypeConstants.TYPES_COUNT; x++) {
            if (this.isOfType(x)) {
                count++;
            }
        }
        final int[] result = new int[count];
        count = 0;
        for (int x = 0; x < TypeConstants.TYPES_COUNT; x++) {
            if (this.isOfType(x)) {
                result[count] = x;
                count++;
            }
        }
        return result;
    }

    protected abstract void setTypes();

    public boolean isUsable() {
        return this.usable;
    }

    public int getUses() {
        return this.uses;
    }

    public boolean isPushable() {
        return this.pushable;
    }

    public boolean isPullable() {
        return this.pullable;
    }

    public boolean isPullableInto() {
        return this.pullableInto;
    }

    public boolean isPushableInto() {
        return this.pushableInto;
    }

    public boolean isPullableOut() {
        return this.pullableOut;
    }

    public boolean isPushableOut() {
        return this.pushableOut;
    }

    public boolean hasFriction() {
        return this.friction;
    }

    public boolean doesChainReact() {
        return this.chainReacts;
    }

    public boolean isInventoryable() {
        return this.isInventoryable;
    }

    public int getTemplateColor() {
        return this.templateColor;
    }

    public int getAttributeID() {
        return this.attributeID;
    }

    public int getAttributeTemplateColor() {
        return this.attributeTemplateColor;
    }

    public int getGameTemplateColor() {
        return this.templateColor;
    }

    public int getGameAttributeID() {
        return this.attributeID;
    }

    public int getGameAttributeTemplateColor() {
        return this.attributeTemplateColor;
    }

    protected void setDirectionallySolid(final boolean ie, final int dir,
            final boolean value) {
        this.sp.setDirectionallySolid(ie, dir, value);
    }

    protected void setTemplateColor(final int newTC) {
        this.templateColor = newTC;
    }

    protected void setAttributeID(final int newAttrID) {
        this.attributeID = newAttrID;
    }

    protected void setAttributeTemplateColor(final int attrColor) {
        this.attributeTemplateColor = attrColor;
    }

    // Scripting
    /**
     *
     * @param ie
     * @param dirX
     * @param dirY
     * @param inv
     * @return
     */
    public boolean preMoveAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        return true;
    }

    public abstract void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv);

    /**
     *
     * @param active
     */
    public void postMoveBattleAction(final BattleCharacter active) {
        // Do nothing
    }

    /**
     *
     * @param ie
     * @param dirX
     * @param dirY
     * @param inv
     */
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_WALK_FAILED);
        DungeonDiver4.getApplication().showMessage("Can't go that way");
    }

    /**
     *
     * @param dirX
     * @param dirY
     * @param dirZ
     */
    public void chainReactionAction(final int dirX, final int dirY,
            final int dirZ) {
        // Do nothing
    }

    /**
     *
     * @param inv
     * @param moving
     * @return
     */
    public boolean hasFrictionConditionally(final DungeonObjectInventory inv,
            final boolean moving) {
        return this.hasFriction();
    }

    public void editorPlaceHook() {
        // Do nothing
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     */
    public void editorGenerateHook(final int x, final int y, final int z) {
        // Do nothing
    }

    public void editorProbeHook() {
        DungeonDiver4.getApplication().showMessage(this.getName());
    }

    public AbstractDungeonObject editorPropertiesHook() {
        return null;
    }

    /**
     *
     * @param locX
     * @param locY
     * @param locZ
     * @param dirX
     * @param dirY
     * @param arrowType
     * @param inv
     * @return
     */
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int arrowType,
            final DungeonObjectInventory inv) {
        // Stop non-ghost arrows passing through solid objects
        if (arrowType == ArrowTypeConstants.ARROW_TYPE_GHOST) {
            return true;
        } else {
            if (this.isConditionallySolid(inv)) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     *
     * @param mo
     * @param x
     * @param y
     * @param z
     */
    public void useAction(final AbstractDungeonObject mo, final int x,
            final int y, final int z) {
        // Do nothing
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     */
    public void useHelper(final int x, final int y, final int z) {
        // Do nothing
    }

    /**
     *
     * @param inv
     * @param mo
     * @param x
     * @param y
     * @param pushX
     * @param pushY
     */
    public void pushAction(final DungeonObjectInventory inv,
            final AbstractDungeonObject mo, final int x, final int y,
            final int pushX, final int pushY) {
        // Do nothing
    }

    /**
     *
     * @param inv
     * @param pushed
     * @param x
     * @param y
     * @param z
     */
    public void pushIntoAction(final DungeonObjectInventory inv,
            final AbstractDungeonObject pushed, final int x, final int y,
            final int z) {
        // Do nothing
    }

    /**
     *
     * @param inv
     * @param pushed
     * @param x
     * @param y
     * @param z
     */
    public void pushOutAction(final DungeonObjectInventory inv,
            final AbstractDungeonObject pushed, final int x, final int y,
            final int z) {
        // Do nothing
    }

    /**
     *
     * @param inv
     * @param x
     * @param y
     * @param pushX
     * @param pushY
     */
    public void pushFailedAction(final DungeonObjectInventory inv, final int x,
            final int y, final int pushX, final int pushY) {
        // Play push failed sound, if it's enabled
        SoundManager.playSound(SoundConstants.SOUND_ACTION_FAILED);
        DungeonDiver4.getApplication().getGameManager().keepNextMessage();
        DungeonDiver4.getApplication().showMessage("Can't push that");
    }

    /**
     *
     * @param inv
     * @param mo
     * @param x
     * @param y
     * @param pullX
     * @param pullY
     */
    public void pullAction(final DungeonObjectInventory inv,
            final AbstractDungeonObject mo, final int x, final int y,
            final int pullX, final int pullY) {
        // Do nothing
    }

    /**
     *
     * @param inv
     * @param pulled
     * @param x
     * @param y
     * @param z
     */
    public void pullIntoAction(final DungeonObjectInventory inv,
            final AbstractDungeonObject pulled, final int x, final int y,
            final int z) {
        // Do nothing
    }

    /**
     *
     * @param inv
     * @param pulled
     * @param x
     * @param y
     * @param z
     */
    public void pullOutAction(final DungeonObjectInventory inv,
            final AbstractDungeonObject pulled, final int x, final int y,
            final int z) {
        // Do nothing
    }

    /**
     *
     * @param inv
     * @param x
     * @param y
     * @param pullX
     * @param pullY
     */
    public void pullFailedAction(final DungeonObjectInventory inv, final int x,
            final int y, final int pullX, final int pullY) {
        SoundManager.playSound(SoundConstants.SOUND_ACTION_FAILED);
        DungeonDiver4.getApplication().getGameManager().keepNextMessage();
        DungeonDiver4.getApplication().showMessage("Can't pull that");
    }

    public boolean arrowHitBattleCheck() {
        return !this.isSolid();
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    public AbstractDungeonObject gameRenderHook(final int x, final int y,
            final int z) {
        return this;
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    public AbstractDungeonObject editorRenderHook(final int x, final int y,
            final int z) {
        return this;
    }

    public BufferedImageIcon battleRenderHook() {
        return ObjectImageManager.getImage(this.getName(),
                this.getBattleBaseID(), this.getTemplateColor(),
                this.getAttributeID(), this.getAttributeTemplateColor());
    }

    public boolean defersSetProperties() {
        return false;
    }

    public boolean overridesDefaultPostMove() {
        return false;
    }

    public String getGameName() {
        return this.getName();
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     */
    public void determineCurrentAppearance(final int x, final int y,
            final int z) {
        // Do nothing
    }

    public void stepAction() {
        // Do nothing
    }

    public final void activateTimer(final int ticks) {
        this.timerActive = true;
        this.timerValue = ticks;
        this.initialTimerValue = ticks;
    }

    public final void extendTimer(final int ticks) {
        if (this.timerActive) {
            this.timerValue += ticks;
        }
    }

    public final void extendTimerByInitialValue() {
        if (this.timerActive) {
            this.timerValue += this.initialTimerValue;
        }
    }

    public final void resetTimer() {
        if (this.timerActive) {
            this.timerValue = this.initialTimerValue;
        }
    }

    public final void tickTimer(final int dirX, final int dirY) {
        if (this.timerActive) {
            this.timerValue--;
            if (this.timerValue == 0) {
                this.timerActive = false;
                this.initialTimerValue = 0;
                this.timerExpiredAction(dirX, dirY);
            }
        }
    }

    /**
     *
     * @param dirX
     * @param dirY
     */
    public void timerExpiredAction(final int dirX, final int dirY) {
        // Do nothing
    }

    abstract public String getName();

    public abstract int getBaseID();

    public int getGameBaseID() {
        return this.getBaseID();
    }

    public int getBattleBaseID() {
        if (this.enabledInBattle()) {
            return this.getGameBaseID();
        } else {
            return ObjectImageConstants.OBJECT_IMAGE_NONE;
        }
    }

    public boolean canMove() {
        return false;
    }

    public boolean enabledInBattle() {
        return true;
    }

    public final String getIdentifier() {
        return this.getName();
    }

    public String getIdentifierV1() {
        return this.getName();
    }

    abstract public String getPluralName();

    abstract public String getDescription();

    abstract public int getLayer();

    abstract public int getCustomProperty(int propID);

    abstract public void setCustomProperty(int propID, int value);

    public int getCustomFormat() {
        return 0;
    }

    @Override
    public boolean shouldGenerateObject(final Dungeon dungeon, final int row,
            final int col, final int floor, final int level, final int layer) {
        if (layer == DungeonConstants.LAYER_OBJECT) {
            // Handle object layer
            if (!this.isOfType(TypeConstants.TYPE_PASS_THROUGH)) {
                // Limit generation of other objects to 20%, unless required
                if (this.isRequired()) {
                    return true;
                } else {
                    final RandomRange r = new RandomRange(1, 100);
                    if (r.generate() <= 20) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                // Generate pass-through objects at 100%
                return true;
            }
        } else {
            // Handle ground layer
            if (this.isOfType(TypeConstants.TYPE_FIELD)) {
                // Limit generation of fields to 20%
                final RandomRange r = new RandomRange(1, 100);
                if (r.generate() <= 20) {
                    return true;
                } else {
                    return false;
                }
            } else {
                // Generate other ground at 100%
                return true;
            }
        }
    }

    @Override
    public int getMinimumRequiredQuantity(final Dungeon dungeon) {
        return RandomGenerationRule.NO_LIMIT;
    }

    @Override
    public int getMaximumRequiredQuantity(final Dungeon dungeon) {
        return RandomGenerationRule.NO_LIMIT;
    }

    @Override
    public boolean isRequired() {
        return false;
    }

    @Override
    public boolean shouldGenerateObjectInBattle(final Dungeon dungeon,
            final int row, final int col, final int floor, final int level,
            final int layer) {
        if (!this.enabledInBattle()) {
            // Don't generate disabled objects
            return false;
        } else {
            // Generate other objects at 100%
            return true;
        }
    }

    @Override
    public int getMinimumRequiredQuantityInBattle(final Dungeon dungeon) {
        return RandomGenerationRule.NO_LIMIT;
    }

    @Override
    public int getMaximumRequiredQuantityInBattle(final Dungeon dungeon) {
        return RandomGenerationRule.NO_LIMIT;
    }

    @Override
    public boolean isRequiredInBattle() {
        return false;
    }

    public final void writeDungeonObject(final XDataWriter writer)
            throws IOException {
        writer.writeOpeningGroup(AbstractDungeonObject.OBJECT_GROUP);
        writer.writeOpeningGroup(AbstractDungeonObject.OBJECT_ID_GROUP);
        writer.writeString(this.getIdentifier());
        writer.writeClosingGroup(AbstractDungeonObject.OBJECT_ID_GROUP);
        writer.writeOpeningGroup(AbstractDungeonObject.OBJECT_SAVED_GROUP);
        if (this.saved == null) {
            writer.writeString("NULL");
        } else {
            this.saved.writeDungeonObject(writer);
        }
        writer.writeClosingGroup(AbstractDungeonObject.OBJECT_SAVED_GROUP);
        writer.writeOpeningGroup(AbstractDungeonObject.OBJECT_SETTINGS_GROUP);
        final int cc = this.getCustomFormat();
        if (cc == AbstractDungeonObject.CUSTOM_FORMAT_MANUAL_OVERRIDE) {
            this.writeDungeonObjectHook(writer);
        } else {
            for (int x = 0; x < cc; x++) {
                final int cx = this.getCustomProperty(x + 1);
                writer.writeInt(cx);
            }
        }
        writer.writeClosingGroup(AbstractDungeonObject.OBJECT_SETTINGS_GROUP);
        writer.writeClosingGroup(AbstractDungeonObject.OBJECT_GROUP);
    }

    public final AbstractDungeonObject readDungeonObjectV1(
            final XDataReader reader, final String ident) throws IOException {
        if (ident.equals(this.getIdentifier())) {
            reader.readOpeningGroup(AbstractDungeonObject.OBJECT_SAVED_GROUP);
            final String savedIdent = reader.readString();
            reader.readClosingGroup(AbstractDungeonObject.OBJECT_SAVED_GROUP);
            if (!savedIdent.equals("NULL")) {
                this.saved = DungeonDiver4.getApplication().getObjects()
                        .readSavedDungeonObject(reader, savedIdent,
                                FormatConstants.DUNGEON_FORMAT_1);
            }
            reader.readOpeningGroup(
                    AbstractDungeonObject.OBJECT_SETTINGS_GROUP);
            final int cc = this.getCustomFormat();
            if (cc == AbstractDungeonObject.CUSTOM_FORMAT_MANUAL_OVERRIDE) {
                return this.readDungeonObjectHook(reader,
                        FormatConstants.DUNGEON_FORMAT_1);
            } else {
                for (int x = 0; x < cc; x++) {
                    final int cx = reader.readInt();
                    this.setCustomProperty(x + 1, cx);
                }
            }
            reader.readClosingGroup(
                    AbstractDungeonObject.OBJECT_SETTINGS_GROUP);
            return this;
        } else {
            return null;
        }
    }

    /**
     *
     * @param writer
     * @throws IOException
     */
    protected void writeDungeonObjectHook(final XDataWriter writer)
            throws IOException {
        // Do nothing - but let subclasses override
    }

    /**
     *
     * @param reader
     * @param formatVersion
     * @return
     * @throws IOException
     */
    protected AbstractDungeonObject readDungeonObjectHook(
            final XDataReader reader, final int formatVersion)
            throws IOException {
        // Dummy implementation, subclasses can override
        return this;
    }

    public boolean isMoving() {
        return false;
    }

    public boolean isDestroyable() {
        return this.destroyable;
    }
}
