/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.creatures.monsters;

import net.dynamicdungeon.dynamicdungeon.creatures.faiths.FaithManager;
import net.dynamicdungeon.dynamicdungeon.creatures.party.PartyManager;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.MonsterImageManager;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.MonsterNames;
import net.dynamicdungeon.images.BufferedImageIcon;
import net.dynamicdungeon.randomrange.RandomRange;

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
            final int tl = PartyManager.getParty().getDungeonLevel();
            final String[] types = MonsterNames.getAllDisplayNamesForLevel(tl);
            final RandomRange r = new RandomRange(0, types.length - 1);
            final int index = r.generate();
            this.setType(types[index]);
            final String[] filenames = MonsterNames.getAllFileNamesForLevel(tl);
            return MonsterImageManager.getImage(filenames[index], tl + 1,
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
