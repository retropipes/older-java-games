package net.worldwizard.dungeondiver.creatures.buffs;

import java.io.Serializable;
import java.util.Arrays;

import net.worldwizard.dungeondiver.creatures.Creature;
import net.worldwizard.dungeondiver.creatures.StatConstants;

public class Buff implements Serializable, StatConstants {
    // Fields
    private static final long serialVersionUID = 2529253463406L;
    protected String name;
    protected double[] initialEffect;
    protected double[] effect;
    protected int statAffected;
    protected double effectScaleFactor;
    protected int effectScaleStat;
    protected double effectDecayRate;
    protected int rounds;
    protected int initialRounds;
    protected double roundsScaleFactor;
    protected int roundsScaleStat;
    protected String[] messages;
    public static final double DEFAULT_ADDITION = 0;
    public static final double DEFAULT_MULTIPLIER = 1;
    public static final int EFFECT_ADD = 0;
    public static final int EFFECT_MULTIPLY = 1;
    public static final double DEFAULT_SCALE_FACTOR = 1.0;
    public static final int DEFAULT_SCALE_STAT = StatConstants.STAT_NONE;
    public static final double DEFAULT_DECAY_RATE = 0.0;
    public static final int ROUNDS_INFINITE = -1;
    public static final int MESSAGE_INITIAL = 0;
    public static final int MESSAGE_SUBSEQUENT = 1;
    public static final int MESSAGE_WEAR_OFF = 2;
    protected static final int MAX_EFFECT_TYPES = 2;
    protected static final int MAX_MESSAGES = 3;

    // Constructor
    public Buff(final String buffName, final int newRounds) {
        this.name = buffName;
        this.messages = new String[Buff.MAX_MESSAGES];
        this.effect = new double[Buff.MAX_EFFECT_TYPES];
        this.initialEffect = new double[Buff.MAX_EFFECT_TYPES];
        this.effectScaleFactor = Buff.DEFAULT_SCALE_FACTOR;
        this.effectScaleStat = Buff.DEFAULT_SCALE_STAT;
        this.effectDecayRate = Buff.DEFAULT_DECAY_RATE;
        int x;
        for (x = 0; x < Buff.MAX_EFFECT_TYPES; x++) {
            if (x == Buff.EFFECT_ADD) {
                this.effect[x] = Buff.DEFAULT_ADDITION;
                this.initialEffect[x] = Buff.DEFAULT_ADDITION;
            } else if (x == Buff.EFFECT_MULTIPLY) {
                this.effect[x] = Buff.DEFAULT_MULTIPLIER;
                this.initialEffect[x] = Buff.DEFAULT_MULTIPLIER;
            } else {
                this.effect[x] = 0;
                this.initialEffect[x] = 0;
            }
        }
        for (x = 0; x < Buff.MAX_MESSAGES; x++) {
            this.messages[x] = "";
        }
        this.rounds = newRounds;
        this.initialRounds = newRounds;
        this.roundsScaleFactor = Buff.DEFAULT_SCALE_FACTOR;
        this.roundsScaleStat = Buff.DEFAULT_SCALE_STAT;
    }

    public Buff(final String buffName, final int newRounds,
            final double rScaleFactor, final int rScaleStat) {
        this.name = buffName;
        this.messages = new String[Buff.MAX_MESSAGES];
        this.effect = new double[Buff.MAX_EFFECT_TYPES];
        this.initialEffect = new double[Buff.MAX_EFFECT_TYPES];
        this.effectScaleFactor = Buff.DEFAULT_SCALE_FACTOR;
        this.effectScaleStat = Buff.DEFAULT_SCALE_STAT;
        this.effectDecayRate = Buff.DEFAULT_DECAY_RATE;
        int x;
        for (x = 0; x < Buff.MAX_EFFECT_TYPES; x++) {
            if (x == Buff.EFFECT_ADD) {
                this.effect[x] = Buff.DEFAULT_ADDITION;
                this.initialEffect[x] = Buff.DEFAULT_ADDITION;
            } else if (x == Buff.EFFECT_MULTIPLY) {
                this.effect[x] = Buff.DEFAULT_MULTIPLIER;
                this.initialEffect[x] = Buff.DEFAULT_MULTIPLIER;
            } else {
                this.effect[x] = 0;
                this.initialEffect[x] = 0;
            }
        }
        for (x = 0; x < Buff.MAX_MESSAGES; x++) {
            this.messages[x] = "";
        }
        this.rounds = newRounds;
        this.initialRounds = newRounds;
        this.roundsScaleFactor = rScaleFactor;
        this.roundsScaleStat = rScaleStat;
    }

    /**
     *
     * @param c
     */
    public void customUseLogic(final Creature c) {
        // Do nothing
    }

    public void extendBuff(final int additionalRounds) {
        this.rounds += additionalRounds;
    }

    public int getAffectedStat() {
        return this.statAffected;
    }

    public String getBuffString() {
        if (this.name.equals("")) {
            return "";
        } else {
            if (this.areRoundsInfinite()) {
                return this.name;
            } else {
                return this.name + " (" + this.rounds + " Rounds Left)";
            }
        }
    }

    public String getCurrentMessage() {
        String msg = Buff.getNullMessage();
        if (this.rounds == this.initialRounds) {
            if (!this.messages[Buff.MESSAGE_INITIAL]
                    .equals(Buff.getNullMessage())) {
                msg += this.messages[Buff.MESSAGE_INITIAL] + "\n";
            }
        }
        if (!this.messages[Buff.MESSAGE_SUBSEQUENT]
                .equals(Buff.getNullMessage())) {
            msg += this.messages[Buff.MESSAGE_SUBSEQUENT] + "\n";
        }
        if (this.rounds == 1) {
            if (!this.messages[Buff.MESSAGE_WEAR_OFF]
                    .equals(Buff.getNullMessage())) {
                msg += this.messages[Buff.MESSAGE_WEAR_OFF] + "\n";
            }
        }
        // Strip final newline character, if it exists
        if (!msg.equals(Buff.getNullMessage())) {
            msg = msg.substring(0, msg.length() - 1);
        }
        return msg;
    }

    public String getMessage(final int which) {
        return this.messages[which];
    }

    public void setMessage(final int which, final String newMessage) {
        this.messages[which] = newMessage;
    }

    public static String getNullBuffString() {
        return "";
    }

    public static String getNullMessage() {
        return "";
    }

    public int getInitialRounds() {
        return this.initialRounds;
    }

    public void restoreBuff(final Creature scaleTo) {
        if (!this.areRoundsInfinite()) {
            final int scst = this.roundsScaleStat;
            if (scst != StatConstants.STAT_NONE) {
                final int base = this.initialRounds;
                final double factor = this.roundsScaleFactor;
                final int scstVal = scaleTo.getStat(scst);
                this.rounds = (int) (scstVal * base * factor);
            } else {
                this.rounds = this.initialRounds;
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public int getRounds() {
        return this.rounds;
    }

    public boolean areRoundsInfinite() {
        return this.rounds == Buff.ROUNDS_INFINITE;
    }

    public boolean isActive() {
        if (this.areRoundsInfinite()) {
            return true;
        } else {
            return this.rounds > 0;
        }
    }

    public void resetEffect() {
        int x;
        for (x = 0; x < Buff.MAX_EFFECT_TYPES; x++) {
            this.effect[x] = this.initialEffect[x];
        }
    }

    public void useBuff(final Creature c) {
        this.customUseLogic(c);
        if (!this.areRoundsInfinite()) {
            this.rounds--;
            if (this.rounds < 0) {
                this.rounds = 0;
            }
        }
        this.decayEffect(c);
    }

    public double getEffect(final int type, final Creature scaleTo) {
        final double base = this.effect[type];
        final int scst = this.effectScaleStat;
        if (scst == StatConstants.STAT_NONE) {
            return base;
        } else {
            final double factor = this.effectScaleFactor;
            final int scstVal = scaleTo.getStat(scst);
            return scstVal * base * factor;
        }
    }

    public void setAffectedStat(final int newStat) {
        this.statAffected = newStat;
    }

    public void setEffect(final int type, final double value,
            final double factor, final int scaleStat) {
        this.effect[type] = value;
        this.initialEffect[type] = value;
        this.effectScaleFactor = factor;
        this.effectScaleStat = scaleStat;
    }

    public void modifyEffect(final int type, final double value,
            final double factor, final int scaleStat) {
        this.effect[type] = value;
        this.effectScaleFactor = factor;
        this.effectScaleStat = scaleStat;
    }

    public double getDecayRate() {
        return this.effectDecayRate;
    }

    public void setDecayRate(final double value) {
        this.effectDecayRate = value;
    }

    public void decayEffect(final Creature scaleTo) {
        double currVal = 0.0;
        currVal += this.getEffect(Buff.EFFECT_ADD, scaleTo);
        final double currDecay = this.getDecayRate();
        final double modVal = currVal - currDecay;
        final int scst = this.effectScaleStat;
        final double factor = this.effectScaleFactor;
        this.modifyEffect(Buff.EFFECT_ADD, modVal, factor, scst);
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
        result = prime * result + Arrays.hashCode(this.effect);
        long temp;
        temp = Double.doubleToLongBits(this.effectDecayRate);
        result = prime * result + (int) (temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.effectScaleFactor);
        result = prime * result + (int) (temp ^ temp >>> 32);
        result = prime * result + this.effectScaleStat;
        result = prime * result + Arrays.hashCode(this.initialEffect);
        result = prime * result + this.initialRounds;
        result = prime * result + Arrays.hashCode(this.messages);
        result = prime * result
                + (this.name == null ? 0 : this.name.hashCode());
        result = prime * result + this.rounds;
        temp = Double.doubleToLongBits(this.roundsScaleFactor);
        result = prime * result + (int) (temp ^ temp >>> 32);
        result = prime * result + this.roundsScaleStat;
        result = prime * result + this.statAffected;
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
        if (!(obj instanceof Buff)) {
            return false;
        }
        final Buff other = (Buff) obj;
        if (!Arrays.equals(this.effect, other.effect)) {
            return false;
        }
        if (Double.doubleToLongBits(this.effectDecayRate) != Double
                .doubleToLongBits(other.effectDecayRate)) {
            return false;
        }
        if (Double.doubleToLongBits(this.effectScaleFactor) != Double
                .doubleToLongBits(other.effectScaleFactor)) {
            return false;
        }
        if (this.effectScaleStat != other.effectScaleStat) {
            return false;
        }
        if (!Arrays.equals(this.initialEffect, other.initialEffect)) {
            return false;
        }
        if (this.initialRounds != other.initialRounds) {
            return false;
        }
        if (!Arrays.equals(this.messages, other.messages)) {
            return false;
        }
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
        if (this.rounds != other.rounds) {
            return false;
        }
        if (Double.doubleToLongBits(this.roundsScaleFactor) != Double
                .doubleToLongBits(other.roundsScaleFactor)) {
            return false;
        }
        if (this.roundsScaleStat != other.roundsScaleStat) {
            return false;
        }
        if (this.statAffected != other.statAffected) {
            return false;
        }
        return true;
    }
}