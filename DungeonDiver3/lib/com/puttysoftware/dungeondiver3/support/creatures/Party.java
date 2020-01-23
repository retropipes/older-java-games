/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.creatures;

import java.io.IOException;
import java.util.ArrayList;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver3.support.battle.VictorySpoilsDescription;
import com.puttysoftware.dungeondiver3.support.creatures.characterfiles.CharacterLoader;
import com.puttysoftware.dungeondiver3.support.map.objects.BattleCharacter;
import com.puttysoftware.dungeondiver3.support.resourcemanagers.GameSoundConstants;
import com.puttysoftware.dungeondiver3.support.resourcemanagers.SoundManager;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScript;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScriptActionCode;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScriptEntry;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScriptEntryArgument;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class Party {
    // Fields
    private PartyMember[] members;
    private BattleCharacter[] battlers;
    private int leaderID;
    private int activePCs;
    private int dungeonLevel;

    // Constructors
    private Party() {
        this.members = null;
        this.battlers = null;
        this.leaderID = 0;
        this.activePCs = 0;
        this.dungeonLevel = 1;
    }

    Party(PartyMember[] newMembers) {
        this.members = newMembers;
        this.battlers = null;
        this.leaderID = 0;
        this.activePCs = 0;
        this.dungeonLevel = 1;
    }

    Party(final int maxMembers) {
        this.members = new PartyMember[maxMembers];
        this.leaderID = 0;
        this.activePCs = 0;
        this.dungeonLevel = 1;
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

    public ArrayList<InternalScript> checkPartyLevelUp() {
        ArrayList<InternalScript> retVal = new ArrayList<>();
        for (int x = 0; x < this.battlers.length; x++) {
            // Level Up Check
            if (this.battlers[x].getTemplate().checkLevelUp()) {
                this.battlers[x].getTemplate().levelUp();
                SoundManager.playSound(GameSoundConstants.SOUND_LEVEL_UP);
                CommonDialogs.showTitledDialog(this.battlers[x].getTemplate()
                        .getName()
                        + " reached level "
                        + this.battlers[x].getTemplate().getLevel() + "!",
                        "Level Up");
                InternalScript levelUpScript = new InternalScript();
                InternalScriptEntry act0 = new InternalScriptEntry();
                act0.setActionCode(InternalScriptActionCode.ADD_TO_SCORE);
                act0.addActionArg(new InternalScriptEntryArgument(Math.max(1,
                        (10 * this.battlers[x].getTemplate().getLevel() - 1)
                                / this.activePCs)));
                act0.finalizeActionArgs();
                levelUpScript.addAction(act0);
                levelUpScript.finalizeActions();
                retVal.add(levelUpScript);
            }
        }
        return retVal;
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
                        Creature.getAdjustedExperience(vsd.getExpPerMonster(y))
                                / divMod);
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

    public void hurtPartyPercentage(int mod) {
        for (int x = 0; x < this.members.length; x++) {
            if (this.members[x] != null) {
                // Hurt Party Member
                this.members[x].doDamagePercentage(mod);
            }
        }
    }

    void revivePartyFully() {
        for (int x = 0; x < this.members.length; x++) {
            if (this.members[x] != null) {
                // Revive Party Member
                this.members[x].healAndRegenerateFully();
            }
        }
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

    public int getDungeonLevel() {
        return this.dungeonLevel;
    }

    public String getDungeonLevelString() {
        return "" + this.dungeonLevel;
    }

    public void increaseDungeonLevel() {
        if (this.dungeonLevel < Creature.getMaximumLevel()) {
            this.dungeonLevel++;
        }
    }

    public void decreaseDungeonLevel() {
        if (this.dungeonLevel > 0) {
            this.dungeonLevel--;
        }
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
                "DungeonDiver3", pickNames, pickNames[0]);
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
        int dl = worldFile.readInt();
        Party pty = new Party();
        pty.leaderID = lid;
        pty.activePCs = apc;
        pty.members = new PartyMember[memCount];
        pty.dungeonLevel = dl;
        for (int z = 0; z < memCount; z++) {
            boolean present = worldFile.readBoolean();
            if (present) {
                pty.members[z] = PartyMember.read(worldFile);
            }
        }
        return pty;
    }

    static Party readV1(XDataReader worldFile) throws IOException {
        int memCount = worldFile.readInt();
        int lid = worldFile.readInt();
        int apc = worldFile.readInt();
        int dl = worldFile.readInt();
        Party pty = new Party();
        pty.leaderID = lid;
        pty.activePCs = apc;
        pty.members = new PartyMember[memCount];
        pty.dungeonLevel = dl;
        for (int z = 0; z < memCount; z++) {
            boolean present = worldFile.readBoolean();
            if (present) {
                pty.members[z] = PartyMember.readV1(worldFile);
            }
        }
        return pty;
    }

    void write(XDataWriter worldFile) throws IOException {
        worldFile.writeInt(this.members.length);
        worldFile.writeInt(this.leaderID);
        worldFile.writeInt(this.activePCs);
        worldFile.writeInt(this.dungeonLevel);
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
