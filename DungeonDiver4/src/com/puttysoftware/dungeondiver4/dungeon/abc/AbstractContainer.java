/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: dungeonr5d@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.abc;

import java.io.IOException;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.effects.DungeonEffectConstants;
import com.puttysoftware.dungeondiver4.dungeon.objects.Empty;
import com.puttysoftware.dungeondiver4.dungeon.objects.PasswallBoots;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectList;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public abstract class AbstractContainer extends AbstractLock {
    // Fields
    private AbstractDungeonObject inside;

    // Constructors
    protected AbstractContainer(final AbstractSingleKey mgk) {
        super(mgk);
        this.inside = new Empty();
    }

    protected AbstractContainer(final AbstractSingleKey mgk,
            final AbstractDungeonObject insideObject) {
        super(mgk);
        this.inside = insideObject;
    }

    public AbstractDungeonObject getInsideObject() {
        return this.inside;
    }

    @Override
    public boolean defersSetProperties() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final AbstractContainer other = (AbstractContainer) obj;
        if (this.inside != other.inside
                && (this.inside == null || !this.inside.equals(other.inside))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + (this.inside != null ? this.inside.hashCode() : 0);
        return hash;
    }

    @Override
    public AbstractContainer clone() {
        AbstractContainer copy = (AbstractContainer) super.clone();
        copy.inside = this.inside.clone();
        return copy;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        Application app = DungeonDiver4.getApplication();
        if (!app.getGameManager().isEffectActive(
                DungeonEffectConstants.EFFECT_GHOSTLY)
                && !inv.isItemThere(new PasswallBoots())) {
            if (!this.getKey().isInfinite()) {
                inv.removeItem(this.getKey());
            }
            int pz = app.getDungeonManager().getDungeon().getPlayerLocationZ();
            if (this.inside != null) {
                app.getGameManager().morph(this.inside, dirX, dirY, pz);
            } else {
                app.getGameManager().decay();
            }
            SoundManager.playSound(SoundConstants.SOUND_UNLOCK);
            app.getGameManager().backUpPlayer(this);
            DungeonDiver4.getApplication().getGameManager()
                    .addToScore(AbstractLock.SCORE_UNLOCK);
        } else {
            SoundManager.playSound(SoundConstants.SOUND_WALK);
        }
    }

    @Override
    public void editorProbeHook() {
        if (!this.inside.getName().equals("Empty")) {
            DungeonDiver4.getApplication().showMessage(
                    this.getName() + ": Contains " + this.inside.getName());
        } else {
            DungeonDiver4.getApplication().showMessage(
                    this.getName() + ": Contains Nothing");
        }
    }

    @Override
    public abstract AbstractDungeonObject editorPropertiesHook();

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_CONTAINER);
        this.type.set(TypeConstants.TYPE_SINGLE_LOCK);
        this.type.set(TypeConstants.TYPE_LOCK);
    }

    @Override
    protected AbstractDungeonObject readDungeonObjectHook(XDataReader reader,
            int formatVersion) throws IOException {
        DungeonObjectList objectList = DungeonDiver4.getApplication()
                .getObjects();
        this.inside = objectList.readDungeonObject(reader, formatVersion);
        return this;
    }

    @Override
    protected void writeDungeonObjectHook(XDataWriter writer)
            throws IOException {
        if (this.inside == null) {
            new Empty().writeDungeonObject(writer);
        } else {
            this.inside.writeDungeonObject(writer);
        }
    }

    @Override
    public int getCustomFormat() {
        return AbstractDungeonObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
    }
}