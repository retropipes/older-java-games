/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.tallertower.creatures.monsters;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.randomrange.RandomRange;
import com.puttysoftware.tallertower.creatures.faiths.FaithManager;
import com.puttysoftware.tallertower.resourcemanagers.MonsterImageManager;
import com.puttysoftware.tallertower.resourcemanagers.MonsterNames;

abstract class AbstractBothRandomMonster extends AbstractMonster {
    // Constructors
    AbstractBothRandomMonster() {
        super();
        this.element = AbstractBothRandomMonster.getInitialElement();
        this.image = this.getInitialImage();
    }

    @Override
    protected BufferedImageIcon getInitialImage() {
        if (this.getLevel() == 0) {
            return null;
        } else {
            final String[] types = MonsterNames.getAllNames();
            final RandomRange r = new RandomRange(0, types.length - 1);
            this.setType(types[r.generate()]);
            return MonsterImageManager.getImage(this.getType(),
                    this.getElement());
        }
    }

    private static Element getInitialElement() {
        return new Element(FaithManager.getRandomFaith());
    }

    @Override
    public void loadCreature() {
        this.image = this.getInitialImage();
    }
}
