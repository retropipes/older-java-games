/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.creatures.monsters;

class AppearanceRandomScalingStaticMonster
        extends AbstractAppearanceRandomScalingMonster {
    // Constructors
    AppearanceRandomScalingStaticMonster() {
        super();
    }

    @Override
    public boolean dynamic() {
        return false;
    }
}
