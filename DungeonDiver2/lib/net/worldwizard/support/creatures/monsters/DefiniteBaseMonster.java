package net.worldwizard.support.creatures.monsters;

import net.worldwizard.images.BufferedImageIcon;
import net.worldwizard.support.resourcemanagers.MonsterImageManager;

public abstract class DefiniteBaseMonster extends BaseMonster {
    // Constructors
    DefiniteBaseMonster() {
        super();
    }

    @Override
    public boolean random() {
        return false;
    }

    // Helper Methods
    @Override
    protected BufferedImageIcon getInitialImage() {
        if (this.getLevel() == 0) {
            return null;
        } else {
            return MonsterImageManager.getMonsterImage(this.getType(),
                    this.getElement());
        }
    }

    @Override
    protected void loadMonster() {
        this.image = this.getInitialImage();
    }
}
