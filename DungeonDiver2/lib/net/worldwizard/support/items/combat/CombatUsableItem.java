package net.worldwizard.support.items.combat;

import java.io.IOException;

import net.worldwizard.support.creatures.BattleTarget;
import net.worldwizard.support.effects.Effect;
import net.worldwizard.support.effects.EffectLoader;
import net.worldwizard.support.items.Item;
import net.worldwizard.xio.XDataReader;
import net.worldwizard.xio.XDataWriter;

public class CombatUsableItem extends Item {
    // Fields
    private final BattleTarget target;
    protected Effect e;
    protected int sound;

    // Constructors
    public CombatUsableItem(final String itemName, final int itemBuyPrice,
            final BattleTarget itemTarget) {
        super(itemName, 1, 0);
        this.setCombatUsable(true);
        this.setBuyPrice(itemBuyPrice);
        this.target = itemTarget;
        this.defineFields();
    }

    // Methods
    public BattleTarget getTarget() {
        return this.target;
    }

    public Effect getEffect() {
        return this.e;
    }

    public int getSound() {
        return this.sound;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (this.e == null ? 0 : this.e.hashCode());
        result = prime * result + this.sound;
        result = prime * result
                + (this.target == null ? 0 : this.target.hashCode());
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
        if (!(obj instanceof CombatUsableItem)) {
            return false;
        }
        final CombatUsableItem other = (CombatUsableItem) obj;
        if (this.e == null) {
            if (other.e != null) {
                return false;
            }
        } else if (!this.e.equals(other.e)) {
            return false;
        }
        if (this.sound != other.sound) {
            return false;
        }
        if (this.target != other.target) {
            return false;
        }
        return true;
    }

    protected void defineFields() {
        // Do nothing
    }

    public static CombatUsableItem read(final XDataReader dr)
            throws IOException {
        final Item i = Item.read(dr);
        final BattleTarget bt = BattleTarget.valueOf(dr.readString());
        final CombatUsableItem cui = new CombatUsableItem(i.getName(),
                i.getBuyPrice(), bt);
        cui.e = EffectLoader.loadEffect(dr.readString());
        cui.sound = dr.readInt();
        return cui;
    }

    @Override
    public void write(final XDataWriter dw) throws IOException {
        super.write(dw);
        dw.writeString(this.target.toString());
        dw.writeString(this.e.getID());
        dw.writeInt(this.sound);
    }
}