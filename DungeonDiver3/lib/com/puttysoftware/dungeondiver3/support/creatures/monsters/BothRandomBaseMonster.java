/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.creatures.monsters;

import com.puttysoftware.dungeondiver3.support.creatures.faiths.FaithManager;
import com.puttysoftware.dungeondiver3.support.resourcemanagers.ImageManager;
import com.puttysoftware.dungeondiver3.support.resourcemanagers.MonsterNames;
import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.randomrange.RandomRange;

abstract class BothRandomBaseMonster extends BaseMonster {
    // Constructors
    BothRandomBaseMonster() {
        super();
        this.element = BothRandomBaseMonster.getInitialElement();
        this.image = this.getInitialImage();
    }

    @Override
    public boolean randomAppearance() {
        return true;
    }

    @Override
    public boolean randomFaith() {
        return true;
    }

    @Override
    protected BufferedImageIcon getInitialImage() {
        if (this.getLevel() == 0) {
            return null;
        } else {
            final String[] types = MonsterNames.getAllNames();
            final RandomRange r = new RandomRange(0, types.length - 1);
            this.setType(types[r.generate()]);
            return ImageManager.getMonsterImage(this.getType(),
                    this.getElement());
        }
    }

    private static Element getInitialElement() {
        return new Element(FaithManager.getRandomFaith());
    }

    @Override
    public void loadMonster() {
        this.image = this.getInitialImage();
    }
}
