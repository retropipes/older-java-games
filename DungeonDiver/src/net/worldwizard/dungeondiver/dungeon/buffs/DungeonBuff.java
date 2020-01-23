package net.worldwizard.dungeondiver.dungeon.buffs;

public abstract class DungeonBuff {
    // Fields
    protected String name;
    protected int power;
    protected int what;
    protected int rounds;
    protected int initialRounds;
    public static final int WHAT_MOVEMENT = 1;
    public static final int WHAT_VISION = 2;
    public static final int MOVE_UP = 1;
    public static final int MOVE_DOWN = 2;
    public static final int MOVE_LEFT = 3;
    public static final int MOVE_RIGHT = 4;

    // Constructor
    public DungeonBuff(final String buffName, final int newPower,
            final int newWhat, final int newRounds) {
        this.name = buffName;
        this.power = newPower;
        this.what = newWhat;
        this.rounds = newRounds;
        this.initialRounds = newRounds;
    }

    public abstract int customUseLogic(final int arg);

    public void extendDungeonBuff(final int additionalRounds) {
        this.rounds += additionalRounds;
    }

    public String getDungeonBuffString() {
        if (this.name.equals("")) {
            return "";
        } else {
            return this.name + " (" + this.rounds + ")";
        }
    }

    public static String getNullDungeonBuffString() {
        return "";
    }

    public int getInitialRounds() {
        return this.initialRounds;
    }

    public String getName() {
        return this.name;
    }

    public int getPower() {
        return this.power;
    }

    public int getRounds() {
        return this.rounds;
    }

    public int getWhat() {
        return this.what;
    }

    public boolean isActive() {
        return this.rounds > 0;
    }

    public void restoreDungeonBuff() {
        this.rounds = this.initialRounds;
    }

    public void setRounds(final int newRounds) {
        this.rounds = newRounds;
    }

    public int useDungeonBuff(final int arg) {
        this.rounds--;
        if (this.rounds < 0) {
            this.rounds = 0;
        }
        return this.customUseLogic(arg);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.initialRounds;
        result = prime * result
                + (this.name == null ? 0 : this.name.hashCode());
        result = prime * result + this.power;
        result = prime * result + this.rounds;
        result = prime * result + this.what;
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof DungeonBuff)) {
            return false;
        }
        final DungeonBuff other = (DungeonBuff) obj;
        if (this.initialRounds != other.initialRounds) {
            return false;
        }
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
        if (this.power != other.power) {
            return false;
        }
        if (this.rounds != other.rounds) {
            return false;
        }
        if (this.what != other.what) {
            return false;
        }
        return true;
    }
}