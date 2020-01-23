package net.worldwizard.support.creatures.monsters;

import net.worldwizard.images.BufferedImageIcon;
import net.worldwizard.randomnumbers.RandomRange;
import net.worldwizard.support.creatures.faiths.FaithManager;
import net.worldwizard.support.resourcemanagers.MonsterImageManager;
import net.worldwizard.support.resourcemanagers.MonsterNames;

public abstract class RandomBaseMonster extends BaseMonster {
    // Constructors
    RandomBaseMonster() {
        super();
        this.image = this.getInitialImage();
    }

    @Override
    public boolean random() {
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
            this.setElement(new Element(FaithManager.getRandomFaith()));
            return MonsterImageManager.getMonsterImage(this.getType(),
                    this.getElement());
        }
    }

    @Override
    protected void loadMonster() {
        this.image = this.getInitialImage();
    }
}
