/*  DungeonDiver4: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.creatures.party;

import java.io.IOException;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.battle.VictorySpoilsDescription;
import com.puttysoftware.dungeondiver4.creatures.AbstractCreature;
import com.puttysoftware.dungeondiver4.creatures.characterfiles.CharacterLoader;
import com.puttysoftware.dungeondiver4.dungeon.objects.BattleCharacter;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class Party {
    // Fields
    private PartyMember[] members;
    private BattleCharacter[] battlers;
    private int leaderID;
    private int activePCs;

    // Constructors
    private Party() {
        this.members = null;
        this.battlers = null;
        this.leaderID = 0;
        this.activePCs = 0;
    }

    Party(PartyMember[] newMembers) {
        this.members = newMembers;
        this.battlers = null;
        this.leaderID = 0;
        this.activePCs = 0;
    }

    Party(final int maxMembers) {
        this.members = new PartyMember[maxMembers];
        this.leaderID = 0;
        this.activePCs = 0;
    }

    // Methods
    private void generateBattleCharacters() {
        BattleCharacter[] tempChars = new BattleCharacter[this.members.length];
        int nnc = 0;
        for (int x = 0; x < tempChars.length; x++) {
            if (this.members[x] != null) {
                tempChars[x] = new BattleCharacter(this.members[x]);
                nnc++;
            }
        }
        BattleCharacter[] chars = new BattleCharacter[nnc];
        nnc = 0;
        for (int x = 0; x < tempChars.length; x++) {
            if (tempChars[x] != null) {
                chars[nnc] = tempChars[x];
                nnc++;
            }
        }
        this.battlers = chars;
    }

    public BattleCharacter[] getBattleCharacters() {
        if (this.battlers == null) {
            this.generateBattleCharacters();
        }
        return this.battlers;
    }

    public void checkPartyLevelUp() {
        for (int x = 0; x < this.battlers.length; x++) {
            // Level Up Check
            if (this.battlers[x].getTemplate().checkLevelUp()) {
                this.battlers[x].getTemplate().levelUp();
                SoundManager.playSound(SoundConstants.SOUND_LEVEL_UP);
                CommonDialogs.showTitledDialog(this.battlers[x].getTemplate()
                        .getName()
                        + " reached level "
                        + this.battlers[x].getTemplate().getLevel() + "!",
                        "Level Up");
                DungeonDiver4
                        .getApplication()
                        .getGameManager()
                        .addToScore(
                                Math.max(1, (10 * this.battlers[x]
                                        .getTemplate().getLevel() - 1)
                                        / this.activePCs));
            }
        }
    }

    public int getPartyMeanLevel() {
        int sum = 0;
        for (int x = 0; x < this.battlers.length; x++) {
            sum += this.battlers[x].getTemplate().getLevel();
        }
        return sum / this.battlers.length;
    }

    public void stripPartyEffects() {
        for (int x = 0; x < this.members.length; x++) {
            if (this.members[x] != null) {
                // Strip All Effects
                this.members[x].stripAllEffects();
            }
        }
    }

    public void distributeVictorySpoils(VictorySpoilsDescription vsd) {
        int divMod = this.battlers.length;
        int monLen = vsd.getMonsterCount();
        for (int x = 0; x < divMod; x++) {
            // Distribute Victory Spoils
            for (int y = 0; y < monLen; y++) {
                this.battlers[x].getTemplate();
                this.battlers[x].getTemplate().offsetExperience(
                        AbstractCreature.getAdjustedExperience(vsd
                                .getExpPerMonster(y)) / divMod);
            }
            this.battlers[x].getTemplate()
                    .offsetGold(vsd.getGoldWon() / divMod);
        }
    }

    public long getPartyMaxToNextLevel() {
        long largest = Integer.MIN_VALUE;
        for (int x = 0; x < this.members.length; x++) {
            if (this.members[x] != null) {
                if (this.members[x].getToNextLevelValue() > largest) {
                    largest = this.members[x].getToNextLevelValue();
                }
            }
        }
        return largest;
    }

    void writebackMembers() {
        for (int x = 0; x < this.members.length; x++) {
            if (this.members[x] != null) {
                // Writeback Party Member
                CharacterLoader.saveCharacter(this.members[x]);
            }
        }
    }

    public PartyMember getLeader() {
        return this.members[this.leaderID];
    }

    public int getActivePCCount() {
        return this.activePCs;
    }

    public void setLeader(String name) {
        int pos = this.findMember(name, 0, this.members.length);
        if (pos != -1) {
            this.leaderID = pos;
        }
    }

    public boolean isAlive() {
        boolean result = false;
        for (int x = 0; x < this.members.length; x++) {
            if (this.members[x] != null) {
                result = result || this.members[x].isAlive();
            }
        }
        return result;
    }

    public void fireStepActions() {
        for (int x = 0; x < this.members.length; x++) {
            if (this.members[x] != null) {
                this.members[x].getItems().fireStepActions(this.members[x]);
            }
        }
    }

    private String[] buildNameList() {
        String[] tempNames = new String[this.members.length];
        int nnc = 0;
        for (int x = 0; x < tempNames.length; x++) {
            if (this.members[x] != null) {
                tempNames[x] = this.members[x].getName();
                nnc++;
            }
        }
        String[] names = new String[nnc];
        nnc = 0;
        for (int x = 0; x < tempNames.length; x++) {
            if (tempNames[x] != null) {
                names[nnc] = tempNames[x];
                nnc++;
            }
        }
        return names;
    }

    PartyMember pickOnePartyMemberCreate() {
        String[] pickNames = this.buildNameList();
        String response = CommonDialogs.showInputDialog("Pick 1 Party Member",
                "Create Party", pickNames, pickNames[0]);
        if (response != null) {
            int loc = this.findMember(response, 0, this.members.length);
            if (loc != -1) {
                return this.members[loc];
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public PartyMember pickOnePartyMember() {
        String[] pickNames = this.buildNameList();
        return this.pickPartyMemberInternal(pickNames, 1, 1);
    }

    private PartyMember pickPartyMemberInternal(String[] pickNames,
            int current, int number) {
        String response = CommonDialogs.showInputDialog("Pick " + number
                + " Party Member(s) - " + current + " of " + number,
                "DungeonDiver4", pickNames, pickNames[0]);
        if (response != null) {
            int loc = this.findMember(response, 0, this.members.length);
            if (loc != -1) {
                return this.members[loc];
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    boolean addPartyMember(PartyMember member) {
        int pos = this.activePCs;
        if (pos < this.members.length) {
            this.members[pos] = member;
            this.activePCs++;
            this.generateBattleCharacters();
            return true;
        }
        return false;
    }

    private int findMember(String name, int start, int limit) {
        for (int x = start; x < limit; x++) {
            if (this.members[x] != null) {
                if (this.members[x].getName().equals(name)) {
                    return x;
                }
            }
        }
        return -1;
    }

    static Party read(XDataReader worldFile) throws IOException {
        int memCount = worldFile.readInt();
        int lid = worldFile.readInt();
        int apc = worldFile.readInt();
        Party pty = new Party();
        pty.leaderID = lid;
        pty.activePCs = apc;
        pty.members = new PartyMember[memCount];
        for (int z = 0; z < memCount; z++) {
            boolean present = worldFile.readBoolean();
            if (present) {
                pty.members[z] = PartyMember.read(worldFile);
            }
        }
        return pty;
    }

    void write(XDataWriter worldFile) throws IOException {
        worldFile.writeInt(this.members.length);
        worldFile.writeInt(this.leaderID);
        worldFile.writeInt(this.activePCs);
        for (int z = 0; z < this.members.length; z++) {
            if (this.members[z] == null) {
                worldFile.writeBoolean(false);
            } else {
                worldFile.writeBoolean(true);
                this.members[z].write(worldFile);
            }
        }
    }
}
