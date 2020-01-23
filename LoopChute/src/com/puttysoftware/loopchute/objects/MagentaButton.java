/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericButton;

public class MagentaButton extends GenericButton {
    public MagentaButton() {
        super(new MagentaWallOff(), new MagentaWallOn(),
                ColorConstants.COLOR_MAGENTA);
    }

    @Override
    public String getName() {
        return "Magenta Button";
    }

    @Override
    public String getPluralName() {
        return "Magenta Buttons";
    }

    @Override
    public String getDescription() {
        return "Magenta Buttons will cause all Magenta Walls Off to become On, and all Magenta Walls On to become Off.";
    }
}
