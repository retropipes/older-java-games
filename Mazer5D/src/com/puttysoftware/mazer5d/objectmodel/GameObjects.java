/*  Mazer5D: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objectmodel;

import java.io.IOException;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.mazer5d.files.versions.MazeVersion;
import com.puttysoftware.mazer5d.files.versions.MazeVersions;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public final class GameObjects {
    // Constructor
    private GameObjects() {
    }

    // Methods
    public static MazeObjectModel getEmptySpace() {
        return GameObjects.createObject(MazeObjects.EMPTY);
    }

    public static MazeObjectModel[] getAllObjects() {
        // FIXME: Stub
        return new MazeObjectModel[0];
    }

    public static String[] getAllNames() {
        // FIXME: Stub
        return new String[0];
    }

    public static String[] getAllDescriptions() {
        // FIXME: Stub
        return new String[0];
    }

    public static MazeObjectModel[] getAllObjectsWithRuleSets() {
        // FIXME: Stub
        return new MazeObjectModel[0];
    }

    public static MazeObjectModel[] getAllObjectsWithoutRuleSets() {
        // FIXME: Stub
        return new MazeObjectModel[0];
    }

    public static MazeObjectModel[] getAllGroundLayerObjects() {
        // FIXME: Stub
        return new MazeObjectModel[0];
    }

    public static MazeObjectModel[] getAllObjectLayerObjects() {
        // FIXME: Stub
        return new MazeObjectModel[0];
    }

    public static String[] getAllGroundLayerNames() {
        // FIXME: Stub
        return new String[0];
    }

    public static String[] getAllObjectLayerNames() {
        // FIXME: Stub
        return new String[0];
    }

    public static BufferedImageIcon[] getAllEditorAppearances() {
        // FIXME: Stub
        return new BufferedImageIcon[0];
    }

    public static BufferedImageIcon[] getAllGroundLayerEditorAppearances() {
        // FIXME: Stub
        return new BufferedImageIcon[0];
    }

    public static BufferedImageIcon[] getAllObjectLayerEditorAppearances() {
        // FIXME: Stub
        return new BufferedImageIcon[0];
    }

    public static BufferedImageIcon[] getAllContainableObjectEditorAppearances() {
        // FIXME: Stub
        return new BufferedImageIcon[0];
    }

    public static MazeObjectModel[] getAllContainableObjects() {
        // FIXME: Stub
        return new MazeObjectModel[0];
    }

    public static String[] getAllContainableNames() {
        // FIXME: Stub
        return new String[0];
    }

    public static MazeObjectModel[] getAllInventoryableObjectsMinusSpecial() {
        // FIXME: Stub
        return new MazeObjectModel[0];
    }

    public static String[] getAllInventoryableNamesMinusSpecial() {
        // FIXME: Stub
        return new String[0];
    }

    public static MazeObjectModel[] getAllProgrammableKeys() {
        // FIXME: Stub
        return new MazeObjectModel[0];
    }

    public static String[] getAllProgrammableKeyNames() {
        // FIXME: Stub
        return new String[0];
    }

    public static MazeObjectModel[] getAllUsableObjects() {
        // FIXME: Stub
        return new MazeObjectModel[0];
    }

    public static String[] getAllUsableNamesMinusSpecial() {
        // FIXME: Stub
        return new String[0];
    }

    public static MazeObjectModel[] getAllBows() {
        // FIXME: Stub
        return new MazeObjectModel[0];
    }

    public static String[] getAllBowNames() {
        // FIXME: Stub
        return new String[0];
    }

    public static MazeObjectModel[] getAllRequired(final int layer) {
        // FIXME: Stub
        return new MazeObjectModel[0];
    }

    public static MazeObjectModel[] getAllWithoutPrerequisiteAndNotRequired(
            final int layer) {
        // FIXME: Stub
        return new MazeObjectModel[0];
    }

    public static MazeObjectModel[] getAllRequiredSubset(
            final MazeObjectModel[] objs, final int layer) {
        // FIXME: Stub
        return new MazeObjectModel[0];
    }

    public static MazeObjectModel[] getAllWithoutPrerequisiteAndNotRequiredSubset(
            final MazeObjectModel[] objs, final int layer) {
        // FIXME: Stub
        return new MazeObjectModel[0];
    }

    public static void readRuleSet(final XDataReader reader, final int rsFormat)
            throws IOException {
        // FIXME: Stub
    }

    public static void writeRuleSet(final XDataWriter writer)
            throws IOException {
        // FIXME: Stub
    }

    public static MazeObjectModel createObject(final MazeObjects uid) {
        return new MazeObject(uid);
    }

    public static MazeObjectModel readObject(final XDataReader reader,
            final MazeVersion formatVersion) throws IOException {
        int UID = -1;
        if (formatVersion == MazeVersions.LATEST) {
            UID = reader.readInt();
        }
        MazeObjectModel o = new MazeObject(UID);
        o.loadState(reader, UID);
        return o;
    }
}
