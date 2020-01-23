/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.abc;

import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.objects.GeneratedEdge;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;

public abstract class AbstractGeneratedEdge extends AbstractDungeonObject {
    // Fields
    private String name;
    private int baseName;
    private String plural;
    private String source1;
    private String source2;
    private String dirName;

    // Constructors
    protected AbstractGeneratedEdge(final String n, final int b,
            final String p, final String s1, final String s2, final String d) {
        super(false, false);
        this.setTemplateColor(ColorConstants.COLOR_NONE);
        this.name = n;
        this.baseName = b;
        this.plural = p;
        this.source1 = s1;
        this.source2 = s2;
        this.dirName = d;
    }

    // Methods
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getBaseID() {
        return this.baseName;
    }

    @Override
    public String getPluralName() {
        return this.plural;
    }

    public String getSource1() {
        return this.source1;
    }

    public String getSource2() {
        return this.source2;
    }

    public String getDirectionName() {
        return this.dirName;
    }

    @Override
    public String getDescription() {
        return this.plural + " are transitions in the " + this.dirName
                + " direction, between " + this.source1 + " and "
                + this.source2 + ".";
    }

    @Override
    public AbstractDungeonObject clone() {
        return new GeneratedEdge(this.name, this.baseName, this.plural,
                this.source1, this.source2, this.dirName);
    }

    @Override
    public boolean shouldGenerateObject(Dungeon dungeon, int row, int col,
            int floor, int level, int layer) {
        // Blacklist object
        return false;
    }

    @Override
    public int getLayer() {
        return DungeonConstants.LAYER_GROUND;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_GENERATED);
    }

    @Override
    public int getCustomProperty(int propID) {
        return AbstractDungeonObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(int propID, int value) {
        // Do nothing
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        // Do nothing
    }
}
