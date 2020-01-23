/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractButton;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class PurpleButton extends AbstractButton {
    public PurpleButton() {
        super(new PurpleWallOff(), new PurpleWallOn(),
                ColorConstants.COLOR_PURPLE);
    }

    @Override
    public String getName() {
        return "Purple Button";
    }

    @Override
    public String getPluralName() {
        return "Purple Buttons";
    }

    @Override
    public String getDescription() {
        return "Purple Buttons will cause all Purple Walls Off to become On, and all Purple Walls On to become Off.";
    }
}
