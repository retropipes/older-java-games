/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.creatures.monsters;

import com.puttysoftware.fantastlex.creatures.faiths.FaithManager;
import com.puttysoftware.fantastlex.resourcemanagers.MonsterImageManager;
import com.puttysoftware.fantastlex.resourcemanagers.MonsterNames;
import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.randomrange.RandomRange;

abstract class AbstractBothRandomMonster extends AbstractMonster {
    // Constructors
    AbstractBothRandomMonster() {
        super();
        this.element = AbstractBothRandomMonster.getInitialElement();
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
            return MonsterImageManager.getImage(this.getType(),
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
