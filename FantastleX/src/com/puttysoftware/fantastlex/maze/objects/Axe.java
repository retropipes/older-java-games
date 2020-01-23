/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractInfiniteKey;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class Axe extends AbstractInfiniteKey {
    // Constructors
    public Axe() {
        super();
        this.setTemplateColor(ColorConstants.COLOR_BRIDGE);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_AXE;
    }

    @Override
    public String getName() {
        return "Axe";
    }

    @Override
    public String getPluralName() {
        return "Axe";
    }

    @Override
    public String getDescription() {
        return "With an Axe, Trees can be cut down. Axes never lose their ability to cut trees.";
    }
}