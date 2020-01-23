/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.creatures.monsters;

import com.puttysoftware.gemma.support.resourcemanagers.ImageManager;
import com.puttysoftware.images.BufferedImageIcon;

abstract class DefiniteBaseMonster extends BaseMonster {
    // Constructors
    DefiniteBaseMonster() {
        super();
    }

    @Override
    public boolean randomAppearance() {
        return false;
    }

    @Override
    public boolean randomFaith() {
        return false;
    }

    // Helper Methods
    @Override
    protected BufferedImageIcon getInitialImage() {
        return ImageManager.getBossImage();
    }

    @Override
    public void loadMonster() {
        this.image = this.getInitialImage();
    }
}
