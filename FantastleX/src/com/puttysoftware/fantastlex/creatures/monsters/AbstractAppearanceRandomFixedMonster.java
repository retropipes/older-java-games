/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.creatures.monsters;

abstract class AbstractAppearanceRandomFixedMonster
        extends AbstractAppearanceRandomMonster {
    // Constructors
    AbstractAppearanceRandomFixedMonster() {
        super();
    }

    @Override
    public boolean scales() {
        return false;
    }
}
