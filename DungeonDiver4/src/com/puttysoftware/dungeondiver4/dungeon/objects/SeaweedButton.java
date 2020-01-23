/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractButton;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class SeaweedButton extends AbstractButton {
    public SeaweedButton() {
        super(new SeaweedWallOff(), new SeaweedWallOn(),
                ColorConstants.COLOR_SEAWEED);
    }

    @Override
    public String getName() {
        return "Seaweed Button";
    }

    @Override
    public String getPluralName() {
        return "Seaweed Buttons";
    }

    @Override
    public String getDescription() {
        return "Seaweed Buttons will cause all Seaweed Walls Off to become On, and all Seaweed Walls On to become Off.";
    }
}
