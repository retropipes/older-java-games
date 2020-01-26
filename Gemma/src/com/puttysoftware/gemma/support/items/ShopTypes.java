/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.items;

public interface ShopTypes {
    int SHOP_TYPE_WEAPONS = 1;
    int SHOP_TYPE_ARMOR = 2;
    int SHOP_TYPE_HEALER = 3;
    int SHOP_TYPE_REGENERATOR = 4;
    int SHOP_TYPE_ITEMS = 6;
    int SHOP_TYPE_ENHANCEMENTS = 8;
    int SHOP_TYPE_FAITH_POWERS = 9;
    String[] SHOP_NAMES = { "Weapons", "Armor", "Healer", "Regenerator",
            "Spells", "Items", "Socks", "Enhancements", "Faith Powers" };
}