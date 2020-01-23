/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractButton;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class RedButton extends AbstractButton {
    public RedButton() {
        super(new RedWallOff(), new RedWallOn(), ColorConstants.COLOR_RED);
    }

    @Override
    public String getName() {
        return "Red Button";
    }

    @Override
    public String getPluralName() {
        return "Red Buttons";
    }

    @Override
    public String getDescription() {
        return "Red Buttons will cause all Red Walls Off to become On, and all Red Walls On to become Off.";
    }
}
