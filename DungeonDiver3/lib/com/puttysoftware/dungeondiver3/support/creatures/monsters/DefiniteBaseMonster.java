/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.creatures.monsters;

import com.puttysoftware.dungeondiver3.support.resourcemanagers.ImageManager;
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
        if (this.getLevel() == 0) {
            return null;
        } else {
            return ImageManager.getMonsterImage(this.getType(),
                    this.getElement());
        }
    }

    @Override
    public void loadMonster() {
        this.image = this.getInitialImage();
    }
}
