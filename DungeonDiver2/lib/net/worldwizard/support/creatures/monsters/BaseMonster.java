package net.worldwizard.support.creatures.monsters;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import net.worldwizard.support.IDGenerator;
import net.worldwizard.support.Support;
import net.worldwizard.support.ai.AIRoutine;
import net.worldwizard.support.ai.RandomAIRoutinePicker;
import net.worldwizard.support.creatures.Creature;
import net.worldwizard.support.creatures.faiths.Faith;
import net.worldwizard.support.creatures.faiths.FaithManager;
import net.worldwizard.support.variables.Extension;
import net.worldwizard.xio.XDataWriter;

public abstract class BaseMonster extends Creature {
    // Fields
    private String type;
    private Element element;
    private int teamID;
    protected static final double MINIMUM_EXPERIENCE_RANDOM_VARIANCE = -5.0 / 2.0;
    protected static final double MAXIMUM_EXPERIENCE_RANDOM_VARIANCE = 5.0 / 2.0;
    protected static final int GOLD_TOUGHNESS_MULTIPLIER = 5;

    // Constructors
    BaseMonster() {
        super();
        this.setAI(BaseMonster.getInitialAI());
        this.setElement(new Element(FaithManager.getFaith(0)));
        this.setTeamID(1);
        this.setSpellBook(new MonsterSpellBook());
    }

    // Methods
    public int getTeamID() {
        return this.teamID;
    }

    public void setTeamID(final int team) {
        this.teamID = team;
    }

    @Override
    public String getName() {
        return this.element.getName() + " " + this.type;
    }

    @Override
    public Faith getFaith() {
        return this.element.getFaith();
    }

    @Override
    public boolean checkLevelUp() {
        return false;
    }

    @Override
    public void levelUp() {
        // Do nothing
    }

    public String getType() {
        return this.type;
    }

    public Element getElement() {
        return this.element;
    }

    public void setType(final String newType) {
        this.type = newType;
    }

    public void setElement(final Element newElement) {
        this.element = newElement;
    }

    public abstract boolean random();

    public abstract boolean scales();

    public abstract boolean dynamic();

    protected abstract void loadMonster();

    public void write(final XDataWriter worldFile) throws IOException {
        worldFile.writeBoolean(this.random());
        worldFile.writeBoolean(this.scales());
        worldFile.writeBoolean(this.dynamic());
        if (!this.scales()) {
            worldFile.writeInt(this.getStrength());
            worldFile.writeInt(this.getBlock());
            worldFile.writeInt(this.getAgility());
            worldFile.writeInt(this.getVitality());
            worldFile.writeInt(this.getIntelligence());
            worldFile.writeInt(this.getLuck());
            worldFile.writeInt(this.getLevel());
            worldFile.writeInt(this.getCurrentHP());
            worldFile.writeInt(this.getCurrentMP());
            worldFile.writeInt(this.getGold());
            worldFile.writeInt(this.getAttacksPerRound());
            worldFile.writeInt(this.getSpellsPerRound());
            worldFile.writeLong(this.getExperience());
        }
        if (!this.random()) {
            worldFile.writeString(this.getType());
            worldFile.writeInt(this.getFaith().getFaithID());
            worldFile.writeString(this.getSpellBook().getID());
            this.getItems().writeItemInventoryXML(worldFile);
        }
        if (this.dynamic()) {
            for (int x = 0; x < this.getDynamism().length; x++) {
                worldFile.writeInt(this.getDynamism()[x]);
            }
        }
        worldFile.writeInt(this.teamID);
    }

    // Helper Methods
    private static AIRoutine getInitialAI() {
        return RandomAIRoutinePicker.getNextRoutine();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + (this.element == null ? 0 : this.element.hashCode());
        result = prime * result + this.teamID;
        result = prime * result
                + (this.type == null ? 0 : this.type.hashCode());
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
        if (!(obj instanceof BaseMonster)) {
            return false;
        }
        final BaseMonster other = (BaseMonster) obj;
        if (this.element == null) {
            if (other.element != null) {
                return false;
            }
        } else if (!this.element.equals(other.element)) {
            return false;
        }
        if (this.teamID != other.teamID) {
            return false;
        }
        if (this.type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!this.type.equals(other.type)) {
            return false;
        }
        return true;
    }

    @Override
    public BigInteger computeLongHash() {
        BigInteger longHash = BigInteger.ZERO;
        longHash = longHash.add(IDGenerator.computeStringLongHash(this.type)
                .multiply(BigInteger.valueOf(2)));
        return longHash;
    }

    @Override
    public void dumpContentsToFile() throws IOException {
        final File dir = new File(Support.getSystemVariables().getBasePath()
                + File.separator + "monsters");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        final XDataWriter writer = new XDataWriter(Support.getSystemVariables()
                .getBasePath()
                + File.separator
                + "monsters"
                + File.separator
                + this.getID() + Extension.getMonsterExtensionWithPeriod(),
                Extension.getMonsterExtension());
        this.write(writer);
        writer.close();
    }

    @Override
    public String getTypeName() {
        return "Monster";
    }

    @Override
    public String getPluralTypeName() {
        return "monsters";
    }
}
