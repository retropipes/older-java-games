package net.worldwizard.support.spells;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import net.worldwizard.support.IDGenerator;
import net.worldwizard.support.Identifiable;
import net.worldwizard.support.Support;
import net.worldwizard.support.creatures.BattleTarget;
import net.worldwizard.support.effects.Effect;
import net.worldwizard.support.effects.EffectLoader;
import net.worldwizard.support.variables.Extension;
import net.worldwizard.xio.XDataReader;
import net.worldwizard.xio.XDataWriter;

public class Spell extends Identifiable {
    // Fields
    private Effect effect;
    private int cost;
    private BattleTarget target;
    private int soundEffect;

    // Constructors
    public Spell() {
        super();
    }

    public Spell(final Effect newEffect, final int newCost,
            final BattleTarget newTarget, final int sfx) {
        super(true);
        this.effect = newEffect;
        this.cost = newCost;
        this.target = newTarget;
        this.soundEffect = sfx;
    }

    @Override
    public String getName() {
        return this.effect.getName();
    }

    public Effect getEffect() {
        return this.effect;
    }

    public int getCost() {
        return this.cost;
    }

    public int getCostForPower(final int power) {
        return this.cost * power;
    }

    public BattleTarget getTarget() {
        return this.target;
    }

    public int getSound() {
        return this.soundEffect;
    }

    public void setEffect(final Effect e) {
        this.effect = e;
    }

    public void setCost(final int c) {
        this.cost = c;
    }

    public void setTarget(final BattleTarget bt) {
        this.target = bt;
    }

    public void setSound(final int s) {
        this.soundEffect = s;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.cost;
        result = prime * result
                + (this.effect == null ? 0 : this.effect.hashCode());
        result = prime * result + this.soundEffect;
        result = prime * result
                + (this.target == null ? 0 : this.target.hashCode());
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
        if (!(obj instanceof Spell)) {
            return false;
        }
        final Spell other = (Spell) obj;
        if (this.cost != other.cost) {
            return false;
        }
        if (this.effect == null) {
            if (other.effect != null) {
                return false;
            }
        } else if (!this.effect.equals(other.effect)) {
            return false;
        }
        if (this.soundEffect != other.soundEffect) {
            return false;
        }
        if (this.target != other.target) {
            return false;
        }
        return true;
    }

    @Override
    public BigInteger computeLongHash() {
        BigInteger longHash = BigInteger.ZERO;
        longHash = longHash.add(this.effect.computeLongHash().multiply(
                BigInteger.valueOf(2)));
        longHash = longHash.add(IDGenerator.computeLongLongHash(this.cost)
                .multiply(BigInteger.valueOf(3)));
        longHash = longHash.add(IDGenerator.computeLongLongHash(
                this.soundEffect).multiply(BigInteger.valueOf(4)));
        return longHash;
    }

    public static Spell read(final XDataReader reader) throws IOException {
        final Spell e = new Spell();
        e.effect = EffectLoader.loadEffect(reader.readString());
        e.cost = reader.readInt();
        e.target = BattleTarget.valueOf(reader.readString());
        e.soundEffect = reader.readInt();
        return e;
    }

    public void write(final XDataWriter writer) throws IOException {
        writer.writeString(this.effect.getID());
        writer.writeInt(this.cost);
        writer.writeString(this.target.toString());
        writer.writeInt(this.soundEffect);
    }

    @Override
    public void dumpContentsToFile() throws IOException {
        final File dir = new File(Support.getSystemVariables().getBasePath()
                + File.separator + "spells");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        final XDataWriter writer = new XDataWriter(Support.getSystemVariables()
                .getBasePath()
                + File.separator
                + "spells"
                + File.separator
                + this.getID() + Extension.getSpellExtensionWithPeriod(),
                Extension.getSpellExtension());
        this.write(writer);
        writer.close();
    }

    @Override
    public String getTypeName() {
        return "Spell";
    }

    @Override
    public String getPluralTypeName() {
        return "spells";
    }
}
