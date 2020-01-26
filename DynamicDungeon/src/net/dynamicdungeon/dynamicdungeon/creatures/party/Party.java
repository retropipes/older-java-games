/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.creatures.party;

import java.io.IOException;

import net.dynamicdungeon.dbio.DatabaseReader;
import net.dynamicdungeon.dbio.DatabaseWriter;
import net.dynamicdungeon.dynamicdungeon.dungeon.Dungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.BattleCharacter;

public class Party {
    // Fields
    private PartyMember members;
    private BattleCharacter battlers;
    private int leaderID;
    private int activePCs;
    private int towerLevel;

    // Constructors
    public Party() {
        this.members = null;
        this.leaderID = 0;
        this.activePCs = 0;
        this.towerLevel = 0;
    }

    // Methods
    private void generateBattleCharacters() {
        this.battlers = new BattleCharacter(this.members);
    }

    public BattleCharacter getBattleCharacters() {
        if (this.battlers == null) {
            this.generateBattleCharacters();
        }
        return this.battlers;
    }

    public long getPartyMaxToNextLevel() {
        return this.members.getToNextLevelValue();
    }

    public int getDungeonLevel() {
        return this.towerLevel;
    }

    void resetDungeonLevel() {
        this.towerLevel = 0;
    }

    public void offsetDungeonLevel(final int offset) {
        if (this.towerLevel + offset > Dungeon.getMaxLevels()
                || this.towerLevel + offset < 0) {
            return;
        }
        this.towerLevel += offset;
    }

    public String getDungeonLevelString() {
        return "Dungeon Level: " + (this.towerLevel + 1);
    }

    public PartyMember getLeader() {
        return this.members;
    }

    public boolean isAlive() {
        return this.members.isAlive();
    }

    boolean addPartyMember(final PartyMember member) {
        this.members = member;
        return true;
    }

    static Party read(final DatabaseReader worldFile) throws IOException {
        worldFile.readInt();
        final int lid = worldFile.readInt();
        final int apc = worldFile.readInt();
        final int lvl = worldFile.readInt();
        final Party pty = new Party();
        pty.leaderID = lid;
        pty.activePCs = apc;
        pty.towerLevel = lvl;
        final boolean present = worldFile.readBoolean();
        if (present) {
            pty.members = PartyMember.read(worldFile);
        }
        return pty;
    }

    void write(final DatabaseWriter worldFile) throws IOException {
        worldFile.writeInt(1);
        worldFile.writeInt(this.leaderID);
        worldFile.writeInt(this.activePCs);
        worldFile.writeInt(this.towerLevel);
        if (this.members == null) {
            worldFile.writeBoolean(false);
        } else {
            worldFile.writeBoolean(true);
            this.members.write(worldFile);
        }
    }
}
