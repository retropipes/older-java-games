/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.utilities;

public class TypeConstants {
    public static final int TYPE_GROUND = 0;
    public static final int TYPE_FIELD = 1;
    public static final int TYPE_CHARACTER = 2;
    public static final int TYPE_PASS_THROUGH = 3;
    public static final int TYPE_TELEPORT = 4;
    public static final int TYPE_WALL = 5;
    public static final int TYPE_PLAIN_WALL = 6;
    public static final int TYPE_EMPTY_SPACE = 7;
    public static final int TYPE_SHOP = 8;
    public static final int TYPE_TRAP = 9;
    public static final int TYPE_DUNGEON = 10;
    public static final int TYPE_MP_MODIFIER = 11;
    public static final int TYPE_TRIGGER = 12;
    public static final int TYPE_ITEM = 13;
    public static final int TYPES_COUNT = 14;

    private TypeConstants() {
        // Do nothing
    }
}
