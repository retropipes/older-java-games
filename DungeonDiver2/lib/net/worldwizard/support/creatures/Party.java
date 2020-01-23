package net.worldwizard.support.creatures;

import java.io.IOException;
import java.util.ArrayList;

import net.worldwizard.commondialogs.CommonDialogs;
import net.worldwizard.support.map.generic.GameSoundConstants;
import net.worldwizard.support.map.objects.BattleCharacter;
import net.worldwizard.support.resourcemanagers.SoundManager;
import net.worldwizard.support.scripts.game.GameActionCode;
import net.worldwizard.support.scripts.game.GameScript;
import net.worldwizard.support.scripts.game.GameScriptEntry;
import net.worldwizard.support.scripts.game.GameScriptEntryArgument;
import net.worldwizard.xio.XDataReader;
import net.worldwizard.xio.XDataWriter;

public class Party {
    // Fields
    private PartyMember[] members;
    private BattleCharacter[] battlers;
    private final int playerLimit;
    private int leaderID;
    private int activePCs;
    private int activeNPCs;
    private int dungeonLevel;

    // Constructors
    private Party(final int maxPlayers) {
        this.members = null;
        this.battlers = null;
        this.playerLimit = maxPlayers;
        this.leaderID = 0;
        this.activePCs = 0;
        this.activeNPCs = 0;
        this.dungeonLevel = 1;
    }

    Party(final PartyMember[] newMembers) {
        this.members = newMembers;
        this.battlers = null;
        this.playerLimit = newMembers.length;
        this.leaderID = 0;
        this.activePCs = 0;
        this.activeNPCs = 0;
        this.dungeonLevel = 1;
    }

    public Party(final int maxMembers, final int maxPlayers) {
        this.members = new PartyMember[maxMembers];
        this.playerLimit = maxPlayers;
        this.leaderID = 0;
        this.activePCs = 0;
        this.activeNPCs = 0;
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
        this.generateBattleCharacters();
        return this.battlers;
    }

    public ArrayList<GameScript> checkPartyLevelUp() {
        final ArrayList<GameScript> retVal = new ArrayList<>();
        for (final BattleCharacter battler : this.battlers) {
            // Level Up Check
            if (battler.getTemplate().checkLevelUp()) {
                battler.getTemplate().levelUp();
                SoundManager.playSound(GameSoundConstants.SOUND_LEVEL_UP);
                CommonDialogs.showTitledDialog(battler.getTemplate().getName()
                        + " reached level " + battler.getTemplate().getLevel()
                        + "!", "Level Up");
                // Build level up script
                final GameScript levelUpScript = new GameScript();
                final GameScriptEntry act0 = new GameScriptEntry();
                act0.setActionCode(GameActionCode.ADD_TO_SCORE);
                act0.addActionArg(new GameScriptEntryArgument(Math.max(1,
                        (10 * battler.getTemplate().getLevel() - 1)
                                / (this.activeNPCs + this.activePCs))));
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

    public void distributeVictorySpoils(final long exp, final int gold) {
        for (final BattleCharacter battler : this.battlers) {
            // Distribute Victory Spoils
            battler.getTemplate().offsetExperience(exp);
            battler.getTemplate().offsetGold(gold);
        }
    }

    public void hurtPartyPercentage(final int mod) {
        for (final PartyMember member : this.members) {
            if (member != null) {
                // Hurt Party Member
                member.doDamagePercentage(mod);
            }
        }
    }

    public PartyMember getLeader() {
        return this.members[this.leaderID];
    }

    public int getActivePCCount() {
        return this.activePCs;
    }

    public int getActiveNPCCount() {
        return this.activeNPCs;
    }

    public int getDungeonLevel() {
        return this.dungeonLevel;
    }

    public String getDungeonLevelString() {
        return "" + this.dungeonLevel;
    }

    public void increaseDungeonLevel() {
        this.dungeonLevel++;
    }

    public void decreaseDungeonLevel() {
        if (this.dungeonLevel > 0) {
            this.dungeonLevel--;
        }
    }

    public int getMaxPCs() {
        return this.playerLimit;
    }

    public int getMaxNPCs() {
        return this.members.length - this.playerLimit;
    }

    public void setLeader(final String name) {
        final int pos = this.findMember(name, 0, this.members.length);
        if (pos != -1) {
            this.leaderID = pos;
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

    public void fireStepActions() {
        for (final PartyMember member : this.members) {
            if (member != null) {
                member.getItems().fireStepActions(member);
            }
        }
    }

    private String[] buildNameList() {
        final String[] tempNames = new String[this.playerLimit];
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

    public PartyMember pickOnePartyMemberCreate() {
        final String[] pickNames = this.buildNameList();
        final String response = CommonDialogs.showInputDialog(
                "Pick 1 Party Member", "Create Party", pickNames, pickNames[0]);
        if (response != null) {
            final int loc = this.findMember(response, 0, this.playerLimit);
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
        final String[] pickNames = this.buildNameList();
        return this.pickPartyMemberInternal(pickNames, 1, 1);
    }

    private PartyMember pickPartyMemberInternal(final String[] pickNames,
            final int current, final int number) {
        final String response = CommonDialogs.showInputDialog("Pick " + number
                + " Party Member(s) - " + current + " of " + number,
                "DungeonDiverII", pickNames, pickNames[0]);
        if (response != null) {
            final int loc = this.findMember(response, 0, this.playerLimit);
            if (loc != -1) {
                return this.members[loc];
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public boolean addPartyMember(final PartyMember member) {
        final boolean isPC = member.isPlayerCharacter();
        if (isPC) {
            final int pos = this.activePCs;
            if (pos < this.playerLimit) {
                this.members[pos] = member;
                this.activePCs++;
                this.generateBattleCharacters();
                return true;
            }
            return false;
        } else {
            final int pos = this.activeNPCs + this.playerLimit;
            if (pos < this.members.length) {
                this.members[pos] = member;
                this.activeNPCs++;
                this.generateBattleCharacters();
                return true;
            }
            return false;
        }
    }

    private int findMember(final String name, final int start, final int limit) {
        for (int x = start; x < limit; x++) {
            if (this.members[x] != null) {
                if (this.members[x].getName().equals(name)) {
                    return x;
                }
            }
        }
        return -1;
    }

    public static Party read(final XDataReader worldFile) throws IOException {
        final int memCount = worldFile.readInt();
        final int pCount = worldFile.readInt();
        final int lid = worldFile.readInt();
        final int apc = worldFile.readInt();
        final int anpc = worldFile.readInt();
        final int dl = worldFile.readInt();
        final Party pty = new Party(pCount);
        pty.leaderID = lid;
        pty.activePCs = apc;
        pty.activeNPCs = anpc;
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

    public void write(final XDataWriter worldFile) throws IOException {
        worldFile.writeInt(this.members.length);
        worldFile.writeInt(this.playerLimit);
        worldFile.writeInt(this.leaderID);
        worldFile.writeInt(this.activePCs);
        worldFile.writeInt(this.activeNPCs);
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
