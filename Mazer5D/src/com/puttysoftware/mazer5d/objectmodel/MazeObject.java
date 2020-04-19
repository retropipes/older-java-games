/*  Maze Reboot
 * A world-solving RPG
 * This code is licensed under the terms of the
 * GPLv3, or at your option, any later version.
 */
package com.puttysoftware.mazer5d.objectmodel;

import java.io.IOException;
import java.util.Objects;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.mazer5d.assets.ColorShader;
import com.puttysoftware.mazer5d.assets.ObjectImageIndex;
import com.puttysoftware.mazer5d.compatibility.abc.RandomGenerationRule;
import com.puttysoftware.mazer5d.compatibility.maze.MazeModel;
import com.puttysoftware.mazer5d.files.io.XDataReader;
import com.puttysoftware.mazer5d.files.io.XDataWriter;
import com.puttysoftware.randomrange.RandomRange;

final class MazeObject implements MazeObjectModel {
    // Properties
    private final MazeObjects uniqueID;
    private final Tile tile;
    private final SolidProperties sp;
    private final VisionProperties vp;
    private final MoveProperties mp;
    private final OtherProperties op;
    private final OtherCounters oc;
    private final CustomCounters cc;
    private final CustomFlags cf;
    private final CustomTexts ct;
    private MazeObjectModel savedObject = null;

    // Constructors
    public MazeObject(final MazeObjects objectID) {
        this.uniqueID = objectID;
        this.tile = null;
        this.sp = new SolidProperties();
        this.vp = new VisionProperties();
        this.mp = new MoveProperties();
        this.op = new OtherProperties();
        this.oc = new OtherCounters();
        this.cc = new CustomCounters();
        this.cf = new CustomFlags();
        this.ct = new CustomTexts();
    }

    public MazeObject(final MazeObjects objectID, final String cacheName,
            final ObjectImageIndex image) {
        this.uniqueID = objectID;
        this.tile = new Tile(new ObjectAppearance(cacheName, image));
        this.sp = new SolidProperties();
        this.vp = new VisionProperties();
        this.mp = new MoveProperties();
        this.op = new OtherProperties();
        this.oc = new OtherCounters();
        this.cc = new CustomCounters();
        this.cf = new CustomFlags();
        this.ct = new CustomTexts();
    }

    public MazeObject(final MazeObjects objectID, final String cacheName,
            final ObjectImageIndex image, final ColorShader shader) {
        this.uniqueID = objectID;
        this.tile = new Tile(new ObjectAppearance(cacheName, image, shader));
        this.sp = new SolidProperties();
        this.vp = new VisionProperties();
        this.mp = new MoveProperties();
        this.op = new OtherProperties();
        this.oc = new OtherCounters();
        this.cc = new CustomCounters();
        this.cf = new CustomFlags();
        this.ct = new CustomTexts();
    }

    // Methods
    @Override
    public int hashCode() {
        return Objects.hash(this.mp, this.oc, this.op, this.sp, this.cc,
                this.cf, this.ct, this.uniqueID);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MazeObject)) {
            return false;
        }
        final MazeObject other = (MazeObject) obj;
        return Objects.equals(this.mp, other.mp) && Objects.equals(this.oc,
                other.oc) && Objects.equals(this.op, other.op) && Objects
                        .equals(this.sp, other.sp) && Objects.equals(this.cc,
                                other.cc) && Objects.equals(this.cf, other.cf)
                && Objects.equals(this.ct, other.ct)
                && this.uniqueID == other.uniqueID;
    }

    @Override
    public MazeObjects getUniqueID() {
        return this.uniqueID;
    }

    @Override
    public BufferedImageIcon getImage() {
        return this.getImageHook();
    }

    @Override
    public BufferedImageIcon getGameImage() {
        return this.getGameImageHook();
    }

    @Override
    public BufferedImageIcon getEditorImage() {
        return this.getEditorImageHook();
    }

    @Override
    public BufferedImageIcon getBattleImage() {
        return this.getBattleImageHook();
    }

    protected BufferedImageIcon getImageHook() {
        if (this.tile != null) {
            return this.tile.getImage();
        }
        return null;
    }

    protected BufferedImageIcon getGameImageHook() {
        if (this.tile != null) {
            return this.tile.getGameImage();
        }
        return null;
    }

    protected BufferedImageIcon getEditorImageHook() {
        if (this.tile != null) {
            return this.tile.getEditorImage();
        }
        return null;
    }

    protected BufferedImageIcon getBattleImageHook() {
        if (this.tile != null) {
            return this.tile.getBattleImage();
        }
        return null;
    }

    protected int customCountersLength() {
        return this.cc.length();
    }

    protected boolean addCustomCounter(final int count) {
        return this.cc.add(count);
    }

    protected void addOneCustomCounter() {
        this.cc.addOne();
    }

    protected void appendCustomCounter(final int count) {
        this.cc.append(count);
    }

    protected void appendOneCustomCounter() {
        this.cc.appendOne();
    }

    protected int getCustomCounter(final int index) {
        return this.cc.get(index);
    }

    protected boolean decrementCustomCounter(final int index) {
        return this.cc.decrement(index);
    }

    protected boolean incrementCustomCounter(final int index) {
        return this.cc.increment(index);
    }

    protected boolean offsetCustomCounter(final int index, final int value) {
        return this.cc.offset(index, value);
    }

    protected boolean setCustomCounter(final int index, final int value) {
        return this.cc.set(index, value);
    }

    protected int customFlagsLength() {
        return this.cf.length();
    }

    protected boolean addCustomFlag(final int count) {
        return this.cf.add(count);
    }

    protected void addOneCustomFlag() {
        this.cf.addOne();
    }

    protected void appendCustomFlag(final int count) {
        this.cf.append(count);
    }

    protected void appendOneCustomFlag() {
        this.cf.appendOne();
    }

    protected boolean getCustomFlag(final int index) {
        return this.cf.get(index);
    }

    protected boolean toggleCustomFlag(final int index) {
        return this.cf.toggle(index);
    }

    protected boolean setCustomFlag(final int index, final boolean value) {
        return this.cf.set(index, value);
    }

    protected int customTextsLength() {
        return this.ct.length();
    }

    protected boolean addCustomText(final int count) {
        return this.ct.add(count);
    }

    protected void addOneCustomText() {
        this.ct.addOne();
    }

    protected void appendCustomText(final int count) {
        this.ct.append(count);
    }

    protected void appendOneCustomText() {
        this.ct.appendOne();
    }

    protected String getCustomText(final int index) {
        return this.ct.get(index);
    }

    protected boolean setCustomText(final int index, final String value) {
        return this.ct.set(index, value);
    }

    @Override
    public boolean isSolid() {
        return this.sp.isSolid();
    }

    @Override
    public boolean isDirectionallySolid(final int dirX, final int dirY) {
        return this.sp.isDirectionallySolid(dirX, dirY);
    }

    @Override
    public boolean isInternallyDirectionallySolid(final int dirX,
            final int dirY) {
        return this.sp.isInternallyDirectionallySolid(dirX, dirY);
    }

    protected void setSolid(final boolean value) {
        this.sp.setSolid(value);
    }

    protected void setDirectionallySolid(final int dir, final boolean value) {
        this.sp.setDirectionallySolid(dir, value);
    }

    protected void setInternallyDirectionallySolid(final int dir,
            final boolean value) {
        this.sp.setInternallyDirectionallySolid(dir, value);
    }

    @Override
    public boolean isSightBlocking() {
        return this.vp.isSightBlocking();
    }

    @Override
    public boolean isDirectionallySightBlocking(final int dirX,
            final int dirY) {
        return this.vp.isDirectionallySightBlocking(dirX, dirY);
    }

    @Override
    public boolean isInternallyDirectionallySightBlocking(final int dirX,
            final int dirY) {
        return this.vp.isInternallyDirectionallySightBlocking(dirX, dirY);
    }

    protected void setSightBlocking(final boolean value) {
        this.vp.setSightBlocking(value);
    }

    protected void setDirectionallySightBlocking(final int dir,
            final boolean value) {
        this.vp.setDirectionallySightBlocking(dir, value);
    }

    protected void setInternallyDirectionallySightBlocking(final int dir,
            final boolean value) {
        this.vp.setInternallyDirectionallySightBlocking(dir, value);
    }

    @Override
    public boolean isPushable() {
        return this.mp.isPushable();
    }

    @Override
    public boolean isDirectionallyPushable(final int dirX, final int dirY) {
        return this.mp.isDirectionallyPushable(dirX, dirY);
    }

    @Override
    public boolean isPullable() {
        return this.mp.isPullable();
    }

    @Override
    public boolean isDirectionallyPullable(final int dirX, final int dirY) {
        return this.mp.isDirectionallyPullable(dirX, dirY);
    }

    @Override
    public boolean isPullableInto() {
        return this.mp.isPullableInto();
    }

    @Override
    public boolean isPushableInto() {
        return this.mp.isPushableInto();
    }

    @Override
    public boolean isDirectionallyPushableInto(final int dirX, final int dirY) {
        return this.mp.isDirectionallyPushableInto(dirX, dirY);
    }

    @Override
    public boolean isDirectionallyPullableInto(final int dirX, final int dirY) {
        return this.mp.isDirectionallyPullableInto(dirX, dirY);
    }

    @Override
    public boolean isPullableOut() {
        return this.mp.isPullableOut();
    }

    @Override
    public boolean isPushableOut() {
        return this.mp.isPushableOut();
    }

    @Override
    public boolean isDirectionallyPushableOut(final int dirX, final int dirY) {
        return this.mp.isDirectionallyPushableOut(dirX, dirY);
    }

    @Override
    public boolean isDirectionallyPullableOut(final int dirX, final int dirY) {
        return this.mp.isDirectionallyPullableOut(dirX, dirY);
    }

    protected void setPushable(final boolean value) {
        this.mp.setPushable(value);
    }

    protected void setDirectionallyPushable(final int dir,
            final boolean value) {
        this.mp.setDirectionallyPushable(dir, value);
    }

    protected void setPullable(final boolean value) {
        this.mp.setPullable(value);
    }

    protected void setDirectionallyPullable(final int dir,
            final boolean value) {
        this.mp.setDirectionallyPullable(dir, value);
    }

    protected void setPushableInto(final boolean value) {
        this.mp.setPushableInto(value);
    }

    protected void setDirectionallyPushableInto(final int dir,
            final boolean value) {
        this.mp.setDirectionallyPushableInto(dir, value);
    }

    protected void setPullableInto(final boolean value) {
        this.mp.setPullableInto(value);
    }

    protected void setDirectionallyPullableInto(final int dir,
            final boolean value) {
        this.mp.setDirectionallyPullableInto(dir, value);
    }

    protected void setPushableOut(final boolean value) {
        this.mp.setPushableOut(value);
    }

    protected void setDirectionallyPushableOut(final int dir,
            final boolean value) {
        this.mp.setDirectionallyPushableOut(dir, value);
    }

    protected void setPullableOut(final boolean value) {
        this.mp.setPullableOut(value);
    }

    protected void setDirectionallyPullableOut(final int dir,
            final boolean value) {
        this.mp.setDirectionallyPullableOut(dir, value);
    }

    @Override
    public boolean hasFriction() {
        return this.op.hasFriction();
    }

    protected void setFriction(final boolean value) {
        this.op.setFriction(value);
    }

    @Override
    public boolean isUsable() {
        return this.op.isUsable();
    }

    protected void setUsable(final boolean value) {
        this.op.setUsable(value);
    }

    @Override
    public int getUses() {
        return this.oc.getUses();
    }

    protected void setUses(final int value) {
        this.oc.setUses(value);
    }

    @Override
    public void use() {
        if (this.isUsable()) {
            this.oc.use();
        }
    }

    @Override
    public boolean isDestroyable() {
        return this.op.isDestroyable();
    }

    protected void setDestroyable(final boolean value) {
        this.op.setDestroyable(value);
    }

    @Override
    public boolean isChainReacting() {
        return this.op.isChainReacting();
    }

    @Override
    public boolean isChainReactingHorizontally() {
        return this.op.isChainReactingHorizontally();
    }

    @Override
    public boolean isChainReactingVertically() {
        return this.op.isChainReactingVertically();
    }

    protected void setChainReactingHorizontally(final boolean value) {
        this.op.setChainReactingHorizontally(value);
    }

    protected void setChainReactingVertically(final boolean value) {
        this.op.setChainReactingVertically(value);
    }

    @Override
    public boolean isCarryable() {
        return this.op.isCarryable();
    }

    protected void setCarryable(final boolean value) {
        this.op.setCarryable(value);
    }

    @Override
    public int getTimerTicks() {
        return this.oc.getTimerTicks();
    }

    protected void setTimerTicks(final int value) {
        this.oc.setTimerTicks(value);
    }

    @Override
    public void tickTimer() {
        this.oc.tickTimer();
    }

    protected int getTimerReset() {
        return this.oc.getTimerReset();
    }

    protected void setTimerReset(final int value) {
        this.oc.setTimerReset(value);
    }

    @Override
    public void resetTimer() {
        this.oc.resetTimer();
    }

    @Override
    public void setGameLook(final String cacheName,
            final ObjectImageIndex image) {
        if (this.tile != null) {
            this.tile.setGameLook(new ObjectAppearance(cacheName, image));
        }
    }

    @Override
    public void setGameLook(final String cacheName,
            final ObjectImageIndex image, final ColorShader shader) {
        if (this.tile != null) {
            this.tile.setGameLook(new ObjectAppearance(cacheName, image,
                    shader));
        }
    }

    @Override
    public void setEditorLook(final String cacheName,
            final ObjectImageIndex image) {
        if (this.tile != null) {
            this.tile.setEditorLook(new ObjectAppearance(cacheName, image));
        }
    }

    @Override
    public void setEditorLook(final String cacheName,
            final ObjectImageIndex image, final ColorShader shader) {
        if (this.tile != null) {
            this.tile.setEditorLook(new ObjectAppearance(cacheName, image,
                    shader));
        }
    }

    @Override
    public void setBattleLook(final String cacheName,
            final ObjectImageIndex image) {
        if (this.tile != null) {
            this.tile.setBattleLook(new ObjectAppearance(cacheName, image));
        }
    }

    @Override
    public void setBattleLook(final String cacheName,
            final ObjectImageIndex image, final ColorShader shader) {
        if (this.tile != null) {
            this.tile.setBattleLook(new ObjectAppearance(cacheName, image,
                    shader));
        }
    }

    @Override
    public MazeObjectModel getSavedObject() {
        return this.savedObject;
    }

    @Override
    public boolean hasSavedObject() {
        return this.savedObject != null;
    }

    @Override
    public void setSavedObject(final MazeObjectModel newSavedObject) {
        this.savedObject = newSavedObject;
    }

    @Override
    public int getLayer() {
        return Layers.OBJECT;
    }

    @Override
    public String getName() {
        return Integer.toString(this.getUniqueID().ordinal());
    }

    @Override
    public boolean shouldGenerateObject(final MazeModel world, final int row,
            final int col, final int floor, final int level, final int layer) {
        if (layer == Layers.OBJECT) {
            // Handle object layer
            // Limit generation of objects to 20%, unless required
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
            // Handle ground layer
            return true;
        }
    }

    @Override
    public int getMinimumRequiredQuantity(final MazeModel world) {
        return RandomGenerationRule.NO_LIMIT;
    }

    @Override
    public int getMaximumRequiredQuantity(final MazeModel world) {
        return RandomGenerationRule.NO_LIMIT;
    }

    @Override
    public boolean isRequired() {
        return false;
    }

    @Override
    public boolean isOfType(final MazeObjectType testType) {
        // FIXME: Stub
        return false;
    }

    @Override
    public void dumpState(final XDataWriter writer) throws IOException {
        writer.writeMazeObjectID(this.getUniqueID());
        if (this.savedObject == null) {
            writer.writeMazeObjectID(MazeObjects._NONE);
        } else {
            this.savedObject.dumpState(writer);
        }
        final int ccl = this.customCountersLength();
        for (int x = 0; x < ccl; x++) {
            final int cx = this.getCustomCounter(x);
            writer.writeInt(cx);
        }
    }

    @Override
    public MazeObjectModel loadState(final XDataReader reader,
            final MazeObjects uid) throws IOException {
        if (uid == this.getUniqueID()) {
            final MazeObjects savedIdent = reader.readMazeObjectID();
            if (savedIdent != MazeObjects._NONE) {
                this.savedObject = new MazeObject(savedIdent);
                this.savedObject = this.savedObject.loadState(reader,
                        savedIdent);
            }
            final int ccl = this.customCountersLength();
            for (int x = 0; x < ccl; x++) {
                final int cx = reader.readInt();
                this.setCustomCounter(x, cx);
            }
            return this;
        } else {
            return null;
        }
    }
}
