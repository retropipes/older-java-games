/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.creatures.monsters;

class FaithRandomFixedDynamicMonster extends AbstractFaithRandomFixedMonster {
    // Constructors
    FaithRandomFixedDynamicMonster() {
        super();
    }

    @Override
    public boolean dynamic() {
        return true;
    }
}