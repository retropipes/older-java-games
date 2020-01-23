package net.worldwizard.support.items;

import net.worldwizard.commondialogs.CommonDialogs;
import net.worldwizard.support.creatures.Creature;

public final class Socks extends Equipment {
    // Fields
    private final int actionType;
    private final int actionAmount;
    public static final int STEP_ACTION_HEAL = 1;
    public static final int STEP_ACTION_REGENERATE = 2;
    public static final int STEP_ACTION_XP = 3;
    public static final int STEP_ACTION_MONEY = 4;

    // Constructors
    Socks(final String itemName, final int price, final int action,
            final int amount) {
        super(itemName, price);
        this.actionType = action;
        this.actionAmount = amount;
    }

    // Methods
    public final void stepAction(final Creature wearer) {
        switch (this.actionType) {
        case Socks.STEP_ACTION_HEAL:
            wearer.heal(this.actionAmount);
            break;
        case Socks.STEP_ACTION_REGENERATE:
            wearer.regenerate(this.actionAmount);
            break;
        case Socks.STEP_ACTION_XP:
            wearer.offsetExperience(this.actionAmount);
            if (wearer.checkLevelUp()) {
                wearer.levelUp();
                CommonDialogs.showDialog(wearer.getName() + " reached level "
                        + wearer.getLevel() + ".");
            }
            break;
        case Socks.STEP_ACTION_MONEY:
            wearer.offsetGold(this.actionAmount);
            break;
        default:
            break;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + this.actionAmount;
        result = prime * result + this.actionType;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof Socks)) {
            return false;
        }
        final Socks other = (Socks) obj;
        if (this.actionAmount != other.actionAmount) {
            return false;
        }
        if (this.actionType != other.actionType) {
            return false;
        }
        return true;
    }
}
