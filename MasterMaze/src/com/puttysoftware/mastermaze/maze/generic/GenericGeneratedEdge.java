/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.generic;

import com.puttysoftware.mastermaze.maze.MazeConstants;
import com.puttysoftware.mastermaze.maze.objects.GeneratedEdge;

public abstract class GenericGeneratedEdge extends MazeObject {
    // Fields
    private final String name;
    private final int baseName;
    private final String plural;
    private final String source1;
    private final String source2;
    private final String dirName;

    // Constructors
    protected GenericGeneratedEdge(final String n, final int b, final String p,
            final String s1, final String s2, final String d) {
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
    public MazeObject clone() {
        return new GeneratedEdge(this.name, this.baseName, this.plural,
                this.source1, this.source2, this.dirName);
    }

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_GROUND;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_GENERATED);
    }

    @Override
    public int getCustomProperty(final int propID) {
        return MazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        // Do nothing
    }
}
