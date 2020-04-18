/*  Maze Reboot
 * A world-solving RPG
 * This code is licensed under the terms of the
 * GPLv3, or at your option, any later version.
 */
package com.puttysoftware.mazer5d.objectmodel;

import java.io.IOException;

import com.puttysoftware.mazer5d.assets.ColorShader;
import com.puttysoftware.mazer5d.assets.ObjectImageIndex;
import com.puttysoftware.mazer5d.compatibility.abc.RandomGenerationRule;
import com.puttysoftware.mazer5d.compatibility.maze.MazeModel;
import com.puttysoftware.randomrange.RandomRange;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

final class MazeObject extends GameObject implements MazeObjectModel {
    // Properties
    private MazeObjectModel savedObject = null;

    // Constructors
    public MazeObject(final int objectID) {
        super(objectID);
    }

    public MazeObject(final int objectID, final String cacheName,
            final ObjectImageIndex image) {
        super(objectID, new ObjectAppearance(cacheName, image));
    }

    public MazeObject(final int objectID, final String cacheName,
            final ObjectImageIndex image, final ColorShader shader) {
        super(objectID, new ObjectAppearance(cacheName, image, shader));
    }

    // Methods
    @Override
    public void setGameLook(final String cacheName,
            final ObjectImageIndex image) {
        super.setGameLook(new ObjectAppearance(cacheName, image));
    }

    @Override
    public void setGameLook(final String cacheName,
            final ObjectImageIndex image, final ColorShader shader) {
        super.setGameLook(new ObjectAppearance(cacheName, image, shader));
    }

    @Override
    public void setEditorLook(final String cacheName,
            final ObjectImageIndex image) {
        super.setEditorLook(new ObjectAppearance(cacheName, image));
    }

    @Override
    public void setEditorLook(final String cacheName,
            final ObjectImageIndex image, final ColorShader shader) {
        super.setEditorLook(new ObjectAppearance(cacheName, image, shader));
    }

    @Override
    public void setBattleLook(final String cacheName,
            final ObjectImageIndex image) {
        super.setBattleLook(new ObjectAppearance(cacheName, image));
    }

    @Override
    public void setBattleLook(final String cacheName,
            final ObjectImageIndex image, final ColorShader shader) {
        super.setBattleLook(new ObjectAppearance(cacheName, image, shader));
    }

    @Override
    public final MazeObjectModel getSavedObject() {
        return this.savedObject;
    }

    @Override
    public final boolean hasSavedObject() {
        return this.savedObject != null;
    }

    @Override
    public final void setSavedObject(final MazeObjectModel newSavedObject) {
        this.savedObject = newSavedObject;
    }

    @Override
    public int getLayer() {
        return Layers.OBJECT;
    }

    @Override
    public String getName() {
        return Integer.toString(this.getUniqueID());
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
    public final void dumpState(final XDataWriter writer) throws IOException {
        writer.writeInt(this.getUniqueID());
        if (this.savedObject == null) {
            writer.writeInt(-1);
        } else {
            this.savedObject.dumpState(writer);
        }
        final int cc = this.customCountersLength();
        for (int x = 0; x < cc; x++) {
            final int cx = this.getCustomCounter(x);
            writer.writeInt(cx);
        }
    }

    @Override
    public final MazeObjectModel loadState(final XDataReader reader,
            final int uid) throws IOException {
        if (uid == this.getUniqueID()) {
            final int savedIdent = reader.readInt();
            if (savedIdent != -1) {
                this.savedObject = new MazeObject(savedIdent);
                this.savedObject = this.savedObject.loadState(reader,
                        savedIdent);
            }
            final int cc = this.customCountersLength();
            for (int x = 0; x < cc; x++) {
                final int cx = reader.readInt();
                this.setCustomCounter(x, cx);
            }
            return this;
        } else {
            return null;
        }
    }
}
