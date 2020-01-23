/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.assets;

import com.puttysoftware.riskyrescue.assets.data.ImageDataManager;

public class ObjectImageNames {
    // Fields
    private static String[] CACHE;
    private static boolean CACHE_CREATED = false;

    public static String getName(final ObjectImage oi) {
        if (!ObjectImageNames.CACHE_CREATED) {
            ObjectImageNames.CACHE = ImageDataManager.getObjectImageData();
            ObjectImageNames.CACHE_CREATED = true;
        }
        return ObjectImageNames.CACHE[oi.ordinal()];
    }
}
