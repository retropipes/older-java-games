/*  DDRemix: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ddremix.creatures.monsters;

import com.puttysoftware.ddremix.creatures.faiths.FaithManager;
import com.puttysoftware.ddremix.creatures.party.PartyManager;
import com.puttysoftware.ddremix.resourcemanagers.MonsterImageManager;
import com.puttysoftware.ddremix.resourcemanagers.MonsterNames;
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
