/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.GenericWall;
import com.puttysoftware.widgetwarren.generic.TypeConstants;

public class OneWaySouthWall extends GenericWall {
    public OneWaySouthWall() {
        super(true, false, true, true, true, false, true, true);
    }

    @Override
    public String getName() {
        return "One-Way South Wall";
    }

    @Override
    public String getPluralName() {
        return "One-Way South Walls";
    }

    @Override
    public String getDescription() {
        return "One-Way South Walls allow movement through them only South.";
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_PLAIN_WALL);
        this.type.set(TypeConstants.TYPE_WALL);
    }
}
