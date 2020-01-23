package net.worldwizard.dungeondiver.creatures;

public class ClassManager implements PlayerClasses {
    public static Player getNewPlayerInstance(final int classID) {
        switch (classID) {
        case PlayerClasses.CLASS_FIGHTER:
            return new Fighter();
        case PlayerClasses.CLASS_MAGE:
            return new Mage();
        case PlayerClasses.CLASS_THIEF:
            return new Thief();
        default:
            return null;
        }
    }

    public static Player getPlayerInstancePostKill(final int classID,
            final int pAtk, final int pDef, final int pHP, final int pMP,
            final int k) {
        switch (classID) {
        case PlayerClasses.CLASS_FIGHTER:
            return new Fighter(pAtk, pDef, pHP, pMP, k);
        case PlayerClasses.CLASS_MAGE:
            return new Mage(pAtk, pDef, pHP, pMP, k);
        case PlayerClasses.CLASS_THIEF:
            return new Thief(pAtk, pDef, pHP, pMP, k);
        default:
            return null;
        }
    }
}
