/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.creatures;

import java.io.IOException;
import java.util.ArrayList;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.gemma.support.battle.VictorySpoilsDescription;
import com.puttysoftware.gemma.support.creatures.characterfiles.CharacterLoader;
import com.puttysoftware.gemma.support.map.objects.BattleCharacter;
import com.puttysoftware.gemma.support.resourcemanagers.GameSoundConstants;
import com.puttysoftware.gemma.support.resourcemanagers.SoundManager;
import com.puttysoftware.gemma.support.scripts.internal.InternalScript;
import com.puttysoftware.gemma.support.scripts.internal.InternalScriptActionCode;
import com.puttysoftware.gemma.support.scripts.internal.InternalScriptEntry;
import com.puttysoftware.gemma.support.scripts.internal.InternalScriptEntryArgument;
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

    Party(final PartyMember[] newMembers) {
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
        final BattleCharacter[] tempChars = new BattleCharacter[this.members.length];
        int nnc = 0;
        for (int x = 0; x < tempChars.length; x++) {
            if (this.members[x] != null) {
                tempChars[x] = new BattleCharacter(this.members[x]);
                nnc++;
            }
        }
        final BattleCharacter[] chars = new BattleCharacter[nnc];
        nnc = 0;
        for (final BattleCharacter tempChar : tempChars) {
            if (tempChar != null) {
                chars[nnc] = tempChar;
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
        final ArrayList<InternalScript> retVal = new ArrayList<>();
        for (final BattleCharacter battler : this.battlers) {
            // Level Up Check
            if (battler.getTemplate().checkLevelUp()) {
                final InternalScript scpt = battler.getTemplate().levelUp();
                if (scpt != null) {
                    retVal.add(scpt);
                }
                SoundManager.playSound(GameSoundConstants.SOUND_LEVEL_UP);
                CommonDialogs.showTitledDialog(
                        battler.getTemplate().getName() + " reached level "
                                + battler.getTemplate().getLevel() + "!",
                        "Level Up");
                final InternalScript levelUpScript = new InternalScript();
                final InternalScriptEntry act0 = new InternalScriptEntry();
                act0.setActionCode(InternalScriptActionCode.ADD_TO_SCORE);
                act0.addActionArg(new InternalScriptEntryArgument(
                        Math.max(1, (10 * battler.getTemplate().getLevel() - 1)
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
        for (final PartyMember member : this.members) {
            if (member != null) {
                // Strip All Effects
                member.stripAllEffects();
            }
        }
    }

    public void distributeVictorySpoils(final VictorySpoilsDescription vsd,
            final int otherLevel) {
        final int divMod = this.battlers.length;
        final int monLen = vsd.getMonsterCount();
        for (int x = 0; x < divMod; x++) {
            // Distribute Victory Spoils
            for (int y = 0; y < monLen; y++) {
                this.battlers[x].getTemplate();
                this.battlers[x].getTemplate()
                        .offsetExperience(Creature.getAdjustedExperience(
                                vsd.getExpPerMonster(y),
                                this.getLeader().getLevel(), otherLevel)
                                / divMod);
            }
            this.battlers[x].getTemplate()
                    .offsetGold(vsd.getGoldWon() / divMod);
        }
    }

    public long getPartyMaxToNextLevel() {
        long largest = Integer.MIN_VALUE;
        for (final PartyMember member : this.members) {
            if (member != null) {
                if (member.getToNextLevelValue() > largest) {
                    largest = member.getToNextLevelValue();
                }
            }
        }
        return largest;
    }

    public void hurtPartyPercentage(final int mod) {
        for (final PartyMember member : this.members) {
            if (member != null) {
                // Hurt Party Member
                member.doDamagePercentage(mod);
            }
        }
    }

    void revivePartyFully() {
        for (final PartyMember member : this.members) {
            if (member != null) {
                // Revive Party Member
                member.healAndRegenerateFully();
            }
        }
    }

    void writebackMembers() {
        for (final PartyMember member : this.members) {
            if (member != null) {
                // Writeback Party Member
                CharacterLoader.saveCharacter(member);
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

    public boolean isAlive() {
        boolean result = false;
        for (final PartyMember member : this.members) {
            if (member != null) {
                result = result || member.isAlive();
            }
        }
        return result;
    }

    private String[] buildNameList() {
        final String[] tempNames = new String[this.members.length];
        int nnc = 0;
        for (int x = 0; x < tempNames.length; x++) {
            if (this.members[x] != null) {
                tempNames[x] = this.members[x].getName();
                nnc++;
            }
        }
        final String[] names = new String[nnc];
        nnc = 0;
        for (final String tempName : tempNames) {
            if (tempName != null) {
                names[nnc] = tempName;
                nnc++;
            }
        }
        return names;
    }

    PartyMember pickOnePartyMemberCreate() {
        final String[] pickNames = this.buildNameList();
        final String response = CommonDialogs.showInputDialog(
                "Pick 1 Party Member", "Create Party", pickNames, pickNames[0]);
        if (response != null) {
            final int loc = this.findMember(response, 0, this.members.length);
            if (loc != -1) {
                return this.members[loc];
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    boolean addPartyMember(final PartyMember member) {
        final int pos = this.activePCs;
        if (pos < this.members.length) {
            this.members[pos] = member;
            this.activePCs++;
            this.generateBattleCharacters();
            return true;
        }
        return false;
    }

    private int findMember(final String name, final int start,
            final int limit) {
        for (int x = start; x < limit; x++) {
            if (this.members[x] != null) {
                if (this.members[x].getName().equals(name)) {
                    return x;
                }
            }
        }
        return -1;
    }

    static Party read(final XDataReader worldFile) throws IOException {
        final int memCount = worldFile.readInt();
        final int lid = worldFile.readInt();
        final int apc = worldFile.readInt();
        final int dl = worldFile.readInt();
        final Party pty = new Party();
        pty.leaderID = lid;
        pty.activePCs = apc;
        pty.members = new PartyMember[memCount];
        pty.dungeonLevel = dl;
        for (int z = 0; z < memCount; z++) {
            final boolean present = worldFile.readBoolean();
            if (present) {
                pty.members[z] = PartyMember.read(worldFile);
            }
        }
        return pty;
    }

    void write(final XDataWriter worldFile) throws IOException {
        worldFile.writeInt(this.members.length);
        worldFile.writeInt(this.leaderID);
        worldFile.writeInt(this.activePCs);
        worldFile.writeInt(this.dungeonLevel);
        for (final PartyMember member : this.members) {
            if (member == null) {
                worldFile.writeBoolean(false);
            } else {
                worldFile.writeBoolean(true);
                member.write(worldFile);
            }
        }
    }
}
