/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.GenericSingleKey;

public class PurpleKey extends GenericSingleKey {
    // Constructors
    public PurpleKey() {
        super();
    }

    // Scriptability
    @Override
    public String getName() {
        return "Purple Key";
    }

    @Override
    public String getPluralName() {
        return "Purple Keys";
    }

    @Override
    public String getDescription() {
        return "Purple Keys will unlock Purple Locks, and can only be used once.";
    }
}