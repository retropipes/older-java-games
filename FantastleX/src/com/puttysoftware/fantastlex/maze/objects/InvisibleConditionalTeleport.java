/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractInvisibleConditionalTeleport;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class InvisibleConditionalTeleport extends
        AbstractInvisibleConditionalTeleport {
    // Constructors
    public InvisibleConditionalTeleport() {
        super(ObjectImageConstants.OBJECT_IMAGE_CONDITIONAL);
    }

    @Override
    public String getName() {
        return "Invisible Conditional Teleport";
    }

    @Override
    public String getPluralName() {
        return "Invisible Conditional Teleports";
    }

    @Override
    public String getDescription() {
        return "Invisible Conditional Teleports send you to one of two predetermined destinations when stepped on, depending on how many Sun or Moon Stones are in your inventory, and cannot be seen.";
    }

    @Override
    public String getGameName() {
        return "Empty";
    }
}