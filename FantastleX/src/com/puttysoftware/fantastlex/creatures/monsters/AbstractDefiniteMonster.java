/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.creatures.monsters;

import com.puttysoftware.fantastlex.resourcemanagers.MonsterImageManager;
import com.puttysoftware.images.BufferedImageIcon;

abstract class AbstractDefiniteMonster extends AbstractMonster {
    // Constructors
    AbstractDefiniteMonster() {
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
            return MonsterImageManager.getImage(this.getType(),
                    this.getElement());
        }
    }

    @Override
    public void loadMonster() {
        this.image = this.getInitialImage();
    }
}
