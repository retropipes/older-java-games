/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.creatures.monsters;

import com.puttysoftware.fantastlex.creatures.faiths.FaithManager;
import com.puttysoftware.images.BufferedImageIcon;

abstract class AbstractFaithRandomMonster extends AbstractMonster {
    // Constructors
    AbstractFaithRandomMonster() {
        super();
        this.element = AbstractFaithRandomMonster.getInitialElement();
    }

    @Override
    public boolean randomAppearance() {
        return false;
    }

    @Override
    public boolean randomFaith() {
        return true;
    }

    @Override
    protected BufferedImageIcon getInitialImage() {
        return null;
    }

    private static Element getInitialElement() {
        return new Element(FaithManager.getRandomFaith());
    }

    @Override
    public void loadMonster() {
        this.image = this.getInitialImage();
    }
}
