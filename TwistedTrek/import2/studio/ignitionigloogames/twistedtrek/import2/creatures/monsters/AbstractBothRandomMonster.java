/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.creatures.monsters;

import studio.ignitionigloogames.images.BufferedImageIcon;
import studio.ignitionigloogames.randomrange.RandomRange;
import studio.ignitionigloogames.twistedtrek.import2.creatures.faiths.FaithManager;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.MonsterImageManager;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.MonsterNames;

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
	    return MonsterImageManager.getImage(this.getType(), this.getElement());
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
