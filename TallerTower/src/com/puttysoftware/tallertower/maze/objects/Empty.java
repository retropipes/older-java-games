/*  TallerTower: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.tallertower.maze.objects;

import com.puttysoftware.tallertower.maze.abc.AbstractPassThroughObject;
import com.puttysoftware.tallertower.maze.utilities.TypeConstants;
import com.puttysoftware.tallertower.resourcemanagers.ObjectImageConstants;

public class Empty extends AbstractPassThroughObject {
    // Constructors
    public Empty() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_EMPTY;
    }

    @Override
    public String getName() {
        return "Empty";
    }

    @Override
    public String getPluralName() {
        return "Squares of Emptiness";
    }

    @Override
    public String getDescription() {
        return "Squares of Emptiness are what fills areas that aren't occupied by other objects.";
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_PASS_THROUGH);
        this.type.set(TypeConstants.TYPE_EMPTY_SPACE);
    }
}