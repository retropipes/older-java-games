/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericConditionalTeleport;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class InvisibleConditionalTeleport extends GenericConditionalTeleport {
    // Constructors
    public InvisibleConditionalTeleport() {
        super();
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


    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.INVISIBLE_CONDITIONAL_TELEPORT;
    }}