/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.effects;

import java.util.Objects;

import com.puttysoftware.gemma.support.creatures.Creature;
import com.puttysoftware.gemma.support.creatures.StatConstants;

public class Effect {
    // Fields
    private Creature sourceCreature;
    private final String name;
    private boolean isMultiply;
    private final Fraction initialValue;
    private final Fraction value;
    private final Fraction scaleFactor;
    private int scaleStat;
    private int affectedStat;
    private int rounds;
    private final int initialRounds;
    private final String[] messages;
    public static final int DEFAULT_SCALE_FACTOR = 1;
    public static final int DEFAULT_SCALE_STAT = StatConstants.STAT_NONE;
    private static final int ROUNDS_INFINITE = -1;
    public static final int MESSAGE_INITIAL = 0;
    public static final int MESSAGE_SUBSEQUENT = 1;
    public static final int MESSAGE_WEAR_OFF = 2;
    private static final int MAX_MESSAGES = 3;

    // Constructors
    public Effect() {
        super();
        this.name = "Un-named";
        this.messages = new String[Effect.MAX_MESSAGES];
        this.initialValue = new Fraction();
        this.value = new Fraction();
        this.scaleFactor = new Fraction();
        this.scaleStat = Effect.DEFAULT_SCALE_STAT;
        int x;
        for (x = 0; x < Effect.MAX_MESSAGES; x++) {
            this.messages[x] = "";
        }
        this.rounds = 0;
        this.initialRounds = 0;
    }

    public Effect(final String effectName, final int newRounds) {
        super();
        this.name = effectName;
        this.messages = new String[Effect.MAX_MESSAGES];
        this.initialValue = new Fraction();
        this.value = new Fraction();
        this.scaleFactor = new Fraction();
        this.scaleStat = Effect.DEFAULT_SCALE_STAT;
        int x;
        for (x = 0; x < Effect.MAX_MESSAGES; x++) {
            this.messages[x] = "";
        }
        this.rounds = newRounds;
        this.initialRounds = newRounds;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.initialValue);
        hash = 29 * hash + Objects.hashCode(this.value);
        hash = 29 * hash + Objects.hashCode(this.scaleFactor);
        hash = 29 * hash + this.scaleStat;
        hash = 29 * hash + this.rounds;
        hash = 29 * hash + this.initialRounds;
        hash = 29 * hash + this.affectedStat;
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Effect other = (Effect) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.initialValue, other.initialValue)) {
            return false;
        }
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        if (!Objects.equals(this.scaleFactor, other.scaleFactor)) {
            return false;
        }
        if (this.scaleStat != other.scaleStat) {
            return false;
        }
        if (this.rounds != other.rounds) {
            return false;
        }
        if (this.initialRounds != other.initialRounds) {
            return false;
        }
        if (this.affectedStat != other.affectedStat) {
            return false;
        }
        return true;
    }

    public void extendEffect(final int additionalRounds) {
        this.rounds += additionalRounds;
    }

    public String getEffectString() {
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
        String msg = Effect.getNullMessage();
        if (this.rounds == this.initialRounds - 1) {
            if (!this.messages[Effect.MESSAGE_INITIAL]
                    .equals(Effect.getNullMessage())) {
                msg += this.messages[Effect.MESSAGE_INITIAL] + "\n";
            }
        }
        if (!this.messages[Effect.MESSAGE_SUBSEQUENT]
                .equals(Effect.getNullMessage())) {
            msg += this.messages[Effect.MESSAGE_SUBSEQUENT] + "\n";
        }
        if (this.rounds == 0) {
            if (!this.messages[Effect.MESSAGE_WEAR_OFF]
                    .equals(Effect.getNullMessage())) {
                msg += this.messages[Effect.MESSAGE_WEAR_OFF] + "\n";
            }
        }
        // Strip final newline character, if it exists
        if (!msg.equals(Effect.getNullMessage())) {
            msg = msg.substring(0, msg.length() - 1);
        }
        return msg;
    }

    public void setMessage(final int which, final String newMessage) {
        this.messages[which] = newMessage;
    }

    public void setSource(final Creature newSource) {
        this.sourceCreature = newSource;
    }

    public static String getNullMessage() {
        return "";
    }

    public int getInitialRounds() {
        return this.initialRounds;
    }

    public void restoreEffect() {
        if (!this.areRoundsInfinite()) {
            this.rounds = this.initialRounds;
        }
    }

    public boolean isMultiply() {
        return this.isMultiply;
    }

    public void setMultiply(final boolean mult) {
        this.isMultiply = mult;
    }

    public String getName() {
        return this.name;
    }

    public int getAffectedStat() {
        return this.affectedStat;
    }

    public void setAffectedStat(final int newAffectedStat) {
        this.affectedStat = newAffectedStat;
    }

    private boolean areRoundsInfinite() {
        return this.rounds == Effect.ROUNDS_INFINITE;
    }

    public boolean isActive() {
        if (this.areRoundsInfinite()) {
            return true;
        } else {
            return this.rounds > 0;
        }
    }

    public void resetEffect() {
        this.value.setNumerator(this.initialValue.getNumerator());
        this.value.setDenominator(this.initialValue.getDenominator());
    }

    public void useEffect(final Creature target) {
        final boolean affectsCHP = this.affectedStat == StatConstants.STAT_CURRENT_HP;
        final boolean affectsCMP = this.affectedStat == StatConstants.STAT_CURRENT_MP;
        double hpEffect = 0;
        if (affectsCHP) {
            hpEffect = this.getEffect();
        }
        double mpEffect = 0;
        if (affectsCMP) {
            mpEffect = this.getEffect();
        }
        if (hpEffect < 0) {
            if (target.isAlive()) {
                final int srcfid = this.sourceCreature.getFaith().getFaithID();
                final double dmgmult = target.getFaith()
                        .getMultiplierForOtherFaith(srcfid);
                final double damage = hpEffect * dmgmult;
                target.doDamage((int) -damage);
            }
        } else if (hpEffect > 0) {
            target.heal((int) hpEffect);
        }
        if (mpEffect < 0) {
            if (target.isAlive()) {
                target.drain((int) -mpEffect);
            }
        } else if (mpEffect > 0) {
            target.regenerate((int) mpEffect);
        }
        if (!this.areRoundsInfinite()) {
            this.rounds--;
            if (this.rounds < 0) {
                this.rounds = 0;
            }
        }
    }

    public double getEffect() {
        return this.value.toDouble();
    }

    public void scaleEffect(final Creature scaleTo) {
        final int scst = this.scaleStat;
        final Fraction scstVal = new Fraction(scaleTo.getStat(scst), 1);
        final Fraction effectiveVal = scstVal.multiply(this.scaleFactor);
        this.value.add(effectiveVal);
    }

    public void setEffect(final int n, final int d) {
        this.value.setNumerator(n);
        this.value.setDenominator(d);
        this.initialValue.setNumerator(n);
        this.initialValue.setDenominator(d);
    }

    public void setScaleFactor(final int n, final int d) {
        this.scaleFactor.setNumerator(n);
        this.scaleFactor.setDenominator(d);
    }

    public void setScaleStat(final int newScaleStat) {
        this.scaleStat = newScaleStat;
    }
}