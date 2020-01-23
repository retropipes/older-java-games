/*  DDRemix: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ddremix.maze.objects;

import com.puttysoftware.ddremix.maze.abc.AbstractPassThroughObject;
import com.puttysoftware.ddremix.maze.utilities.TypeConstants;
import com.puttysoftware.ddremix.resourcemanagers.ObjectImageConstants;

public class Darkness extends AbstractPassThroughObject {
    // Constructors
    public Darkness() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_DARKNESS;
    }

    @Override
    public String getName() {
        return "Darkness";
    }

    @Override
    public String getPluralName() {
        return "Squares of Darkness";
    }

    @Override
    public String getDescription() {
        return "Squares of Darkness are what fills areas that cannot be seen.";
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_PASS_THROUGH);
        this.type.set(TypeConstants.TYPE_EMPTY_SPACE);
    }
}