package net.worldwizard.support.effects;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

import net.worldwizard.randomnumbers.RandomRange;
import net.worldwizard.support.IDGenerator;
import net.worldwizard.support.Identifiable;
import net.worldwizard.support.Support;
import net.worldwizard.support.creatures.Creature;
import net.worldwizard.support.creatures.StatConstants;
import net.worldwizard.support.variables.Extension;
import net.worldwizard.xio.XDataReader;
import net.worldwizard.xio.XDataWriter;

public class Effect extends Identifiable implements StatConstants {
    // Fields
    protected Creature sourceCreature;
    protected String name;
    protected double[][] initialEffect;
    protected double[][] effect;
    protected double effectScaleFactor;
    protected int effectScaleStat;
    protected double[] effectDecayRate;
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

    // Constructors
    public Effect() {
        super();
        this.name = "Un-named";
        this.messages = new String[Effect.MAX_MESSAGES];
        this.effect = new double[Effect.MAX_EFFECT_TYPES][StatConstants.MAX_STATS];
        this.initialEffect = new double[Effect.MAX_EFFECT_TYPES][StatConstants.MAX_STATS];
        this.effectScaleFactor = Effect.DEFAULT_SCALE_FACTOR;
        this.effectScaleStat = Effect.DEFAULT_SCALE_STAT;
        this.effectDecayRate = new double[StatConstants.MAX_STATS];
        int x, y;
        for (x = 0; x < Effect.MAX_EFFECT_TYPES; x++) {
            for (y = 0; y < StatConstants.MAX_STATS; y++) {
                if (x == Effect.EFFECT_ADD) {
                    this.effect[x][y] = Effect.DEFAULT_ADDITION;
                    this.initialEffect[x][y] = Effect.DEFAULT_ADDITION;
                } else if (x == Effect.EFFECT_MULTIPLY) {
                    this.effect[x][y] = Effect.DEFAULT_MULTIPLIER;
                    this.initialEffect[x][y] = Effect.DEFAULT_MULTIPLIER;
                } else {
                    this.effect[x][y] = 0;
                    this.initialEffect[x][y] = 0;
                }
            }
        }
        for (x = 0; x < Effect.MAX_MESSAGES; x++) {
            this.messages[x] = "";
        }
        this.rounds = 0;
        this.initialRounds = 0;
        this.roundsScaleFactor = Effect.DEFAULT_SCALE_FACTOR;
        this.roundsScaleStat = Effect.DEFAULT_SCALE_STAT;
    }

    public Effect(final String effectName, final int newRounds) {
        super(true);
        this.name = effectName;
        this.messages = new String[Effect.MAX_MESSAGES];
        this.effect = new double[Effect.MAX_EFFECT_TYPES][StatConstants.MAX_STATS];
        this.initialEffect = new double[Effect.MAX_EFFECT_TYPES][StatConstants.MAX_STATS];
        this.effectScaleFactor = Effect.DEFAULT_SCALE_FACTOR;
        this.effectScaleStat = Effect.DEFAULT_SCALE_STAT;
        this.effectDecayRate = new double[StatConstants.MAX_STATS];
        int x, y;
        for (x = 0; x < Effect.MAX_EFFECT_TYPES; x++) {
            for (y = 0; y < StatConstants.MAX_STATS; y++) {
                if (x == Effect.EFFECT_ADD) {
                    this.effect[x][y] = Effect.DEFAULT_ADDITION;
                    this.initialEffect[x][y] = Effect.DEFAULT_ADDITION;
                } else if (x == Effect.EFFECT_MULTIPLY) {
                    this.effect[x][y] = Effect.DEFAULT_MULTIPLIER;
                    this.initialEffect[x][y] = Effect.DEFAULT_MULTIPLIER;
                } else {
                    this.effect[x][y] = 0;
                    this.initialEffect[x][y] = 0;
                }
            }
        }
        for (x = 0; x < Effect.MAX_MESSAGES; x++) {
            this.messages[x] = "";
        }
        this.rounds = newRounds;
        this.initialRounds = newRounds;
        this.roundsScaleFactor = Effect.DEFAULT_SCALE_FACTOR;
        this.roundsScaleStat = Effect.DEFAULT_SCALE_STAT;
    }

    public Effect(final String effectName, final int newMinRounds,
            final int newMaxRounds) {
        super(true);
        this.name = effectName;
        this.messages = new String[Effect.MAX_MESSAGES];
        this.effect = new double[Effect.MAX_EFFECT_TYPES][StatConstants.MAX_STATS];
        this.initialEffect = new double[Effect.MAX_EFFECT_TYPES][StatConstants.MAX_STATS];
        this.effectScaleFactor = Effect.DEFAULT_SCALE_FACTOR;
        this.effectScaleStat = Effect.DEFAULT_SCALE_STAT;
        this.effectDecayRate = new double[StatConstants.MAX_STATS];
        int x, y;
        for (x = 0; x < Effect.MAX_EFFECT_TYPES; x++) {
            for (y = 0; y < StatConstants.MAX_STATS; y++) {
                if (x == Effect.EFFECT_ADD) {
                    this.effect[x][y] = Effect.DEFAULT_ADDITION;
                    this.initialEffect[x][y] = Effect.DEFAULT_ADDITION;
                } else if (x == Effect.EFFECT_MULTIPLY) {
                    this.effect[x][y] = Effect.DEFAULT_MULTIPLIER;
                    this.initialEffect[x][y] = Effect.DEFAULT_MULTIPLIER;
                } else {
                    this.effect[x][y] = 0;
                    this.initialEffect[x][y] = 0;
                }
            }
        }
        for (x = 0; x < Effect.MAX_MESSAGES; x++) {
            this.messages[x] = "";
        }
        final RandomRange r = new RandomRange(newMinRounds, newMaxRounds);
        this.rounds = r.generate();
        this.initialRounds = this.rounds;
        this.roundsScaleFactor = Effect.DEFAULT_SCALE_FACTOR;
        this.roundsScaleStat = Effect.DEFAULT_SCALE_STAT;
    }

    public void customExtendLogic() {
        // Do nothing
    }

    public void customTerminateLogic() {
        // Do nothing
    }

    /**
     *
     * @param source
     * @param target
     */
    public void customUseLogic(final Creature source, final Creature target) {
        // Do nothing
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(this.effect);
        long temp;
        result = prime * result + Arrays.hashCode(this.effectDecayRate);
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
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Effect)) {
            return false;
        }
        final Effect other = (Effect) obj;
        if (!Arrays.equals(this.effect, other.effect)) {
            return false;
        }
        if (!Arrays.equals(this.effectDecayRate, other.effectDecayRate)) {
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
        return true;
    }

    public void extendEffect(final int additionalRounds) {
        this.customExtendLogic();
        this.rounds += additionalRounds;
    }

    public int getScaleStat() {
        return this.effectScaleStat;
    }

    public int getRoundsScaleStat() {
        return this.roundsScaleStat;
    }

    public double getScaleFactor() {
        return this.effectScaleFactor;
    }

    public double getRoundsScaleFactor() {
        return this.roundsScaleFactor;
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
        if (this.rounds == this.initialRounds) {
            if (!this.messages[Effect.MESSAGE_INITIAL]
                    .equals(Effect.getNullMessage())) {
                msg += this.messages[Effect.MESSAGE_INITIAL] + "\n";
            }
        }
        if (!this.messages[Effect.MESSAGE_SUBSEQUENT]
                .equals(Effect.getNullMessage())) {
            msg += this.messages[Effect.MESSAGE_SUBSEQUENT] + "\n";
        }
        if (this.rounds == 1) {
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

    public void setRounds(final int value) {
        this.rounds = value;
        this.initialRounds = value;
    }

    public void setRoundsScaleFactor(final double value) {
        this.roundsScaleFactor = value;
    }

    public void setRoundsScaleStat(final int value) {
        this.roundsScaleStat = value;
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

    public void restoreEffect(final Creature scaleTo) {
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

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(final String n) {
        this.name = n;
    }

    public int getRounds() {
        return this.rounds;
    }

    public boolean areRoundsInfinite() {
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
        int x;
        for (x = 0; x < Effect.MAX_EFFECT_TYPES; x++) {
            this.effect[x] = this.initialEffect[x];
        }
    }

    public void useEffect(final Creature target) {
        this.customUseLogic(this.sourceCreature, target);
        if (!this.areRoundsInfinite()) {
            this.rounds--;
            if (this.rounds < 0) {
                this.rounds = 0;
            }
            if (this.rounds == 0) {
                this.customTerminateLogic();
            }
        }
        this.decayEffect();
    }

    public double getEffect(final int type, final int stat) {
        return this.effect[type][stat];
    }

    public void scaleEffect(final int type, final Creature scaleTo) {
        for (int stat = 0; stat < StatConstants.MAX_STATS; stat++) {
            final double base = this.effect[type][stat];
            final int scst = this.effectScaleStat;
            if (scst != StatConstants.STAT_NONE) {
                final double factor = this.effectScaleFactor;
                final int scstVal = scaleTo.getStat(scst);
                this.effect[type][stat] = scstVal * base * factor;
            }
        }
    }

    public void setScaleFactor(final double factor) {
        this.effectScaleFactor = factor;
    }

    public void setScaleStat(final int scaleStat) {
        this.effectScaleStat = scaleStat;
    }

    public void setEffect(final int type, final int stat, final double value,
            final double factor, final int scaleStat) {
        this.effect[type][stat] = value;
        this.initialEffect[type][stat] = value;
        this.effectScaleFactor = factor;
        this.effectScaleStat = scaleStat;
    }

    public void setEffect(final int type, final int stat, final double value) {
        this.effect[type][stat] = value;
        this.initialEffect[type][stat] = value;
    }

    public void modifyEffectForPower(final double value) {
        for (int type = 0; type < Effect.MAX_EFFECT_TYPES; type++) {
            for (int stat = 0; stat < StatConstants.MAX_STATS; stat++) {
                this.effect[type][stat] *= value;
            }
        }
    }

    public void modifyEffect(final int type, final int stat, final double value,
            final double factor, final int scaleStat) {
        this.effect[type][stat] = value;
        this.effectScaleFactor = factor;
        this.effectScaleStat = scaleStat;
    }

    public double getDecayRate(final int stat) {
        return this.effectDecayRate[stat];
    }

    public void setDecayRate(final int stat, final double value) {
        this.effectDecayRate[stat] = value;
    }

    public void decayEffect() {
        for (int stat = 0; stat < StatConstants.MAX_STATS; stat++) {
            double currVal = 0.0;
            currVal += this.getEffect(Effect.EFFECT_ADD, stat);
            final double currDecay = this.getDecayRate(stat);
            final double modVal = currVal - currDecay;
            final int scst = this.effectScaleStat;
            final double factor = this.effectScaleFactor;
            this.modifyEffect(Effect.EFFECT_ADD, stat, modVal, factor, scst);
        }
    }

    public static Effect read(final XDataReader reader) throws IOException {
        final Effect e = new Effect();
        e.name = reader.readString();
        for (int x = 0; x < e.initialEffect.length; x++) {
            for (int y = 0; y < e.initialEffect[x].length; y++) {
                e.initialEffect[x][y] = reader.readDouble();
                e.effect[x][y] = reader.readDouble();
            }
        }
        e.effectScaleFactor = reader.readDouble();
        e.effectScaleStat = reader.readInt();
        for (int z = 0; z < e.effectDecayRate.length; z++) {
            e.effectDecayRate[z] = reader.readDouble();
        }
        e.rounds = reader.readInt();
        e.initialRounds = reader.readInt();
        e.roundsScaleFactor = reader.readDouble();
        e.roundsScaleStat = reader.readInt();
        for (int x = 0; x < e.messages.length; x++) {
            e.messages[x] = reader.readString();
        }
        return e;
    }

    public void write(final XDataWriter writer) throws IOException {
        writer.writeString(this.name);
        for (int x = 0; x < this.initialEffect.length; x++) {
            for (int y = 0; y < this.initialEffect[x].length; y++) {
                writer.writeDouble(this.initialEffect[x][y]);
                writer.writeDouble(this.effect[x][y]);
            }
        }
        writer.writeDouble(this.effectScaleFactor);
        writer.writeInt(this.effectScaleStat);
        for (final double element : this.effectDecayRate) {
            writer.writeDouble(element);
        }
        writer.writeInt(this.rounds);
        writer.writeInt(this.initialRounds);
        writer.writeDouble(this.roundsScaleFactor);
        writer.writeInt(this.roundsScaleStat);
        for (final String message : this.messages) {
            writer.writeString(message);
        }
    }

    @Override
    public BigInteger computeLongHash() {
        BigInteger longHash = BigInteger.ZERO;
        longHash = longHash.add(
                IDGenerator.computeStringLongHash(this.getClass().getName())
                        .multiply(BigInteger.valueOf(2)));
        longHash = longHash.add(IDGenerator.computeStringLongHash(this.name)
                .multiply(BigInteger.valueOf(3)));
        longHash = longHash
                .add(IDGenerator.computeDoubleLongHash(this.effectScaleFactor)
                        .multiply(BigInteger.valueOf(4)));
        longHash = longHash
                .add(IDGenerator.computeLongLongHash(this.effectScaleStat)
                        .multiply(BigInteger.valueOf(5)));
        longHash = longHash
                .add(IDGenerator.computeDoubleLongHash(this.roundsScaleFactor)
                        .multiply(BigInteger.valueOf(6)));
        longHash = longHash
                .add(IDGenerator.computeLongLongHash(this.roundsScaleStat)
                        .multiply(BigInteger.valueOf(7)));
        for (int x = 0; x < Effect.MAX_MESSAGES; x++) {
            longHash = longHash
                    .add(IDGenerator.computeStringLongHash(this.messages[x])
                            .multiply(BigInteger.valueOf(x + 8)));
        }
        return longHash;
    }

    @Override
    public void dumpContentsToFile() throws IOException {
        final File dir = new File(Support.getSystemVariables().getBasePath()
                + File.separator + "effects");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        final XDataWriter writer = new XDataWriter(
                Support.getSystemVariables().getBasePath() + File.separator
                        + "effects" + File.separator + this.getID()
                        + Extension.getEffectExtensionWithPeriod(),
                Extension.getEffectExtension());
        this.write(writer);
        writer.close();
    }

    @Override
    public String getTypeName() {
        return "Effect";
    }

    @Override
    public String getPluralTypeName() {
        return "effects";
    }
}