package net.worldwizard.dungeondiver.creatures;

import java.io.Serializable;

import javax.swing.ImageIcon;

import net.worldwizard.dungeondiver.creatures.ai.AIRoutine;
import net.worldwizard.dungeondiver.creatures.buffs.Buff;
import net.worldwizard.dungeondiver.creatures.spells.SpellBook;

public abstract class Creature implements Serializable, StatConstants {
    // Fields
    private static final long serialVersionUID = 34205356346346L;
    protected int level;
    protected int maximumHP;
    protected int currentHP;
    protected int maximumMP;
    protected int currentMP;
    protected int gold;
    protected long experience;
    protected Buff[] buffList;
    protected SpellBook spellsKnown;
    protected ImageIcon image;
    protected AIRoutine ai;
    protected static final int MAX_BUFFS = 100;
    public static final int FULL_HEAL_PERCENTAGE = 100;

    // Constructor
    protected Creature() {
        this.level = 1;
        this.maximumHP = 1;
        this.currentHP = 1;
        this.maximumMP = 0;
        this.currentMP = 0;
        this.gold = 0;
        this.experience = 0;
        this.buffList = new Buff[Creature.MAX_BUFFS];
        this.spellsKnown = null;
        this.image = this.getInitialImage();
        this.ai = null;
    }

    public void applyBuff(final Buff b) {
        int x;
        for (x = 0; x < this.buffList.length; x++) {
            if (this.get(x) == null) {
                this.set(x, b);
                return;
            }
        }
    }

    public void cullInactiveBuffs() {
        int x;
        for (x = 0; x < this.buffList.length; x++) {
            try {
                final Buff b = this.get(x);
                if (!b.isActive()) {
                    this.set(x, null);
                }
            } catch (final NullPointerException np) {
                // Do nothing
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
    }

    public void doDamage(final int damage) {
        this.currentHP -= damage;
        if (this.currentHP < 0) {
            this.currentHP = 0;
        }
    }

    public void drain(final int cost) {
        this.currentMP -= cost;
        if (this.currentMP < 0) {
            this.currentMP = 0;
        }
    }

    public void extendBuff(final Buff b, final int rounds) {
        final int index = this.indexOf(b);
        if (index != -1) {
            this.get(index).extendBuff(rounds);
        }
    }

    protected Buff get(final int x) {
        return this.buffList[x];
    }

    public AIRoutine getAI() {
        return this.ai;
    }

    abstract public int getAttack();

    public String getBuffName(final Buff b) {
        try {
            return this.get(this.indexOf(b)).getName();
        } catch (final NullPointerException np) {
            return null;
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            return null;
        }
    }

    public int getBuffRounds(final Buff b) {
        try {
            return this.get(this.indexOf(b)).getRounds();
        } catch (final NullPointerException np) {
            return 0;
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            return 0;
        }
    }

    public String getBuffMessage(final Buff b, final int which) {
        try {
            return this.get(this.indexOf(b)).getMessage(which);
        } catch (final NullPointerException np) {
            return null;
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            return null;
        }
    }

    public String getBuffString(final Buff b) {
        try {
            return this.get(this.indexOf(b)).getBuffString();
        } catch (final NullPointerException np) {
            return null;
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            return null;
        }
    }

    public double getBuffedStat(final int stat) {
        int x, s, p;
        s = this.getStat(stat);
        p = this.getStat(stat);
        for (x = 0; x < this.buffList.length; x++) {
            try {
                final Buff b = this.get(x);
                if (b.getAffectedStat() == stat) {
                    p *= b.getEffect(Buff.EFFECT_MULTIPLY, this);
                }
            } catch (final NullPointerException np) {
                // Do nothing
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
        for (x = 0; x < this.buffList.length; x++) {
            try {
                final Buff b = this.get(x);
                if (b.getAffectedStat() == stat) {
                    s += b.getEffect(Buff.EFFECT_ADD, this);
                }
            } catch (final NullPointerException np) {
                // Do nothing
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
        return p + s;
    }

    public String getCompleteBuffString() {
        int x;
        String s = "";
        for (x = 0; x < this.buffList.length; x++) {
            try {
                s += this.get(x).getBuffString() + "\n";
            } catch (final NullPointerException np) {
                // Do nothing
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
        // Strip final newline character, if it exists
        if (!s.equals("")) {
            s = s.substring(0, s.length() - 1);
        } else {
            s = Buff.getNullBuffString();
        }
        return s;
    }

    public String getAllCurrentBuffMessages() {
        int x;
        String s = Buff.getNullMessage();
        for (x = 0; x < this.buffList.length; x++) {
            try {
                s += this.get(x).getCurrentMessage() + "\n";
            } catch (final NullPointerException np) {
                // Do nothing
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
        // Strip final newline character, if it exists
        if (!s.equals("")) {
            s = s.substring(0, s.length() - 1);
        } else {
            s = Buff.getNullMessage();
        }
        return s;
    }

    public String getAllBuffMessages(final int which) {
        int x;
        String s = "";
        for (x = 0; x < this.buffList.length; x++) {
            try {
                s += this.get(x).getMessage(which) + "\n";
            } catch (final NullPointerException np) {
                // Do nothing
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
        // Strip final newline character, if it exists
        if (!s.equals("")) {
            s = s.substring(0, s.length() - 1);
        } else {
            s = Buff.getNullMessage();
        }
        return s;
    }

    public int getCurrentHP() {
        return this.currentHP;
    }

    public int getCurrentMP() {
        return this.currentMP;
    }

    abstract public int getDefense();

    public long getExperience() {
        return this.experience;
    }

    public String getFightingWhatString() {
        final String enemyName = this.getName();
        final boolean vowel = this.isFirstLetterVowel(enemyName);
        String fightingWhat = null;
        if (vowel) {
            fightingWhat = "You're fighting an " + enemyName;
        } else {
            fightingWhat = "You're fighting a " + enemyName;
        }
        return fightingWhat;
    }

    public int getGold() {
        return this.gold;
    }

    public String getHPString() {
        return this.currentHP + "/" + this.maximumHP;
    }

    protected abstract ImageIcon getInitialImage();

    // Methods
    public int getLevel() {
        return this.level;
    }

    public int getMaximumHP() {
        return this.maximumHP;
    }

    public int getMaximumMP() {
        return this.maximumMP;
    }

    public String getMPString() {
        return this.currentMP + "/" + this.maximumMP;
    }

    public ImageIcon getImage() {
        return this.image;
    }

    public abstract String getName();

    public SpellBook getSpellBook() {
        return this.spellsKnown;
    }

    public int getStat(final int stat) {
        switch (stat) {
        case StatConstants.STAT_ATTACK:
            return this.getAttack();
        case StatConstants.STAT_DEFENSE:
            return this.getDefense();
        case StatConstants.STAT_CURRENT_HP:
            return this.getCurrentHP();
        case StatConstants.STAT_MAXIMUM_HP:
            return this.getMaximumHP();
        case StatConstants.STAT_GOLD:
            return this.getGold();
        case StatConstants.STAT_CURRENT_MP:
            return this.getCurrentMP();
        case StatConstants.STAT_MAXIMUM_MP:
            return this.getMaximumMP();
        case StatConstants.STAT_LEVEL:
            return this.getLevel();
        default:
            return 0;
        }
    }

    public double getStatBuff(final Buff b, final int stat) {
        int s, p;
        s = (int) Buff.DEFAULT_ADDITION;
        p = (int) Buff.DEFAULT_MULTIPLIER;
        final int index = this.indexOf(b);
        if (index != -1) {
            final Buff rb = this.get(index);
            if (rb.getAffectedStat() == stat) {
                p *= rb.getEffect(Buff.EFFECT_MULTIPLY, this);
                s += rb.getEffect(Buff.EFFECT_ADD, this);
            }
        }
        return p + s;
    }

    public void heal(final int amount) {
        this.currentHP += amount;
        if (this.currentHP > this.maximumHP) {
            this.currentHP = this.maximumHP;
        }
    }

    public void healPercentage(final int percent) {
        int fP = percent;
        if (fP > Creature.FULL_HEAL_PERCENTAGE) {
            fP = Creature.FULL_HEAL_PERCENTAGE;
        }
        if (fP < 0) {
            fP = 0;
        }
        final double fPMultiplier = fP / (double) Creature.FULL_HEAL_PERCENTAGE;
        final int difference = this.maximumHP - this.currentHP;
        this.currentHP += (int) (difference * fPMultiplier);
    }

    protected int indexOf(final Buff b) {
        int x;
        for (x = 0; x < this.buffList.length; x++) {
            final Buff lb = this.get(x);
            if (lb != null) {
                if (b.equals(lb)) {
                    return x;
                }
            } else {
                return -1;
            }
        }
        return -1;
    }

    public boolean isAlive() {
        return this.currentHP > 0;
    }

    public boolean isBuffActive(final Buff b) {
        final int index = this.indexOf(b);
        if (index != -1) {
            return this.get(index).isActive();
        } else {
            return false;
        }
    }

    protected boolean isFirstLetterVowel(final String s) {
        final String firstLetter = s.substring(0, 1);
        if (firstLetter.equalsIgnoreCase("A")
                || firstLetter.equalsIgnoreCase("E")
                || firstLetter.equalsIgnoreCase("I")
                || firstLetter.equalsIgnoreCase("O")
                || firstLetter.equalsIgnoreCase("U")) {
            return true;
        } else {
            return false;
        }
    }

    public void regenerate(final int amount) {
        this.currentMP += amount;
        if (this.currentMP > this.maximumMP) {
            this.currentMP = this.maximumMP;
        }
    }

    public void regeneratePercentage(final int percent) {
        int fP = percent;
        if (fP > Creature.FULL_HEAL_PERCENTAGE) {
            fP = Creature.FULL_HEAL_PERCENTAGE;
        }
        if (fP < 0) {
            fP = 0;
        }
        final double fPMultiplier = fP / (double) Creature.FULL_HEAL_PERCENTAGE;
        final int difference = this.maximumMP - this.currentMP;
        this.currentMP += (int) (difference * fPMultiplier);
    }

    public void restoreBuff(final Buff b) {
        final int index = this.indexOf(b);
        if (index != -1) {
            this.get(index).restoreBuff(this);
        }
    }

    protected void set(final int x, final Buff b) {
        this.buffList[x] = b;
    }

    public void setSpellBook(final SpellBook book) {
        this.spellsKnown = book;
    }

    public void stripAllBuffs() {
        int x;
        for (x = 0; x < this.buffList.length; x++) {
            this.set(x, null);
        }
    }

    public void stripBuff(final Buff b) {
        final int x = this.indexOf(b);
        if (x != -1) {
            this.set(x, null);
        }
    }

    public void useBuffs() {
        int x;
        for (x = 0; x < this.buffList.length; x++) {
            try {
                this.get(x).useBuff(this);
            } catch (final NullPointerException np) {
                // Do nothing
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
    }
}
