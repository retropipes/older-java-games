/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.generic;

import java.io.IOException;

import net.worldwizard.io.DataReader;
import net.worldwizard.io.DataWriter;
import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.game.ObjectInventory;
import net.worldwizard.worldz.objects.Empty;

public abstract class GenericContainer extends GenericLock {
    // Fields
    private WorldObject inside;

    // Constructors
    protected GenericContainer(final GenericSingleKey mgk) {
        super(mgk);
        this.inside = new Empty();
    }

    protected GenericContainer(final GenericSingleKey mgk,
            final WorldObject insideObject) {
        super(mgk);
        this.inside = insideObject;
    }

    public WorldObject getInsideObject() {
        return this.inside;
    }

    @Override
    public boolean defersSetProperties() {
        return true;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final GenericContainer other = (GenericContainer) obj;
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
    public GenericContainer clone() {
        final GenericContainer copy = (GenericContainer) super.clone();
        copy.inside = this.inside.clone();
        return copy;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = Worldz.getApplication();
        if (!this.getKey().isInfinite()) {
            inv.removeItem(this.getKey());
        }
        final int pz = app.getGameManager().getPlayerManager()
                .getPlayerLocationZ();
        if (this.inside != null) {
            app.getGameManager().morph(this.inside, dirX, dirY, pz);
        } else {
            app.getGameManager().decay();
        }
        if (app.getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playMoveSuccessSound();
        }
        app.getGameManager().backUpPlayer();
    }

    @Override
    public void editorProbeHook() {
        if (!this.inside.getName().equals("Empty")) {
            Messager.showMessage(
                    this.getName() + ": Contains " + this.inside.getName());
        } else {
            Messager.showMessage(this.getName() + ": Contains Nothing");
        }
    }

    @Override
    public abstract WorldObject editorPropertiesHook();

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_CONTAINER);
        this.type.set(TypeConstants.TYPE_SINGLE_LOCK);
        this.type.set(TypeConstants.TYPE_LOCK);
    }

    @Override
    protected WorldObject readWorldObjectHook(final DataReader reader,
            final int formatVersion) throws IOException {
        final WorldObjectList objectList = Worldz.getApplication().getObjects();
        this.inside = objectList.readWorldObject(reader, formatVersion);
        return this;
    }

    @Override
    protected void writeWorldObjectHook(final DataWriter writer)
            throws IOException {
        this.inside.writeWorldObject(writer);
    }

    @Override
    public int getCustomFormat() {
        return WorldObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
    }
}