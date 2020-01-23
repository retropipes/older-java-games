/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.battle;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import net.worldwizard.support.IDGenerator;
import net.worldwizard.support.Identifiable;
import net.worldwizard.support.Support;
import net.worldwizard.support.creatures.PartyManager;
import net.worldwizard.support.creatures.monsters.BaseMonster;
import net.worldwizard.support.creatures.monsters.MonsterManager;
import net.worldwizard.support.map.objects.BattleCharacter;
import net.worldwizard.support.variables.Extension;
import net.worldwizard.xio.XDataWriter;

public class Battle extends Identifiable {
    // Fields
    private String name;
    private final BaseMonster[] monsterArray;
    public static final int MAX_MONSTERS = 90;

    // Constructors
    public Battle() {
        super();
        this.name = "Battle";
        this.monsterArray = new BaseMonster[Battle.MAX_MONSTERS];
        // Fill array with monsters
        final int numMonsters = PartyManager.getParty().getActivePCCount()
                + PartyManager.getParty().getActiveNPCCount();
        for (int x = 0; x < numMonsters; x++) {
            this.monsterArray[x] = MonsterManager.getNewMonsterInstance(true,
                    true, false);
        }
    }

    // Methods
    @Override
    public String getName() {
        return this.name;
    }

    public void setName(final String newName) {
        this.name = newName;
    }

    private BaseMonster[] compactMonsterArray() {
        final BaseMonster[] temp = new BaseMonster[this.monsterArray.length];
        System.arraycopy(this.monsterArray, 0, temp, 0,
                this.monsterArray.length);
        for (int x = 0; x < temp.length; x++) {
            if (temp[x] != null) {
            } else {
                if (x < temp.length - 1) {
                    temp[x] = temp[x + 1];
                }
            }
        }
        return temp;
    }

    public BattleCharacter[] getBattlers() {
        final BaseMonster[] compacted = this.compactMonsterArray();
        final BattleCharacter[] battlerArray = new BattleCharacter[compacted.length];
        for (int x = 0; x < battlerArray.length; x++) {
            if (compacted[x] != null) {
                battlerArray[x] = new BattleCharacter(compacted[x]);
            }
        }
        return battlerArray;
    }

    public void write(final XDataWriter writer) throws IOException {
        final BaseMonster[] compacted = this.compactMonsterArray();
        writer.writeString(this.name);
        writer.writeInt(compacted.length);
        for (final BaseMonster element : compacted) {
            writer.writeString(element.getID());
        }
    }

    @Override
    public BigInteger computeLongHash() {
        BigInteger longHash = BigInteger.ZERO;
        longHash = longHash.add(IDGenerator.computeStringLongHash(this.name)
                .multiply(BigInteger.valueOf(2)));
        for (int x = 0; x < Battle.MAX_MONSTERS; x++) {
            longHash = longHash.add(this.monsterArray[x].computeLongHash())
                    .multiply(BigInteger.valueOf(x + 3));
        }
        return longHash;
    }

    @Override
    public void dumpContentsToFile() throws IOException {
        final File dir = new File(Support.getSystemVariables().getBasePath()
                + File.separator + "battles");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        final XDataWriter writer = new XDataWriter(Support.getSystemVariables()
                .getBasePath()
                + File.separator
                + "battles"
                + File.separator
                + this.getID() + Extension.getBattleExtensionWithPeriod(),
                Extension.getBattleExtension());
        this.write(writer);
        writer.close();
    }

    @Override
    public String getTypeName() {
        return "Battle";
    }

    @Override
    public String getPluralTypeName() {
        return "battles";
    }
}
