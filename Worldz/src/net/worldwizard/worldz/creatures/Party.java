package net.worldwizard.worldz.creatures;

import java.io.IOException;

import net.worldwizard.io.DataReader;
import net.worldwizard.io.DataWriter;
import net.worldwizard.randomnumbers.RandomRange;
import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.objects.BattleCharacter;
import net.worldwizard.worldz.resourcemanagers.SoundManager;

public class Party {
    // Fields
    private PartyMember[] members;
    private BattleCharacter[] battlers;
    private final int playerLimit;
    private int leaderID;
    private int activePCs;
    private int activeNPCs;

    // Constructors
    private Party(final int maxPlayers) {
        this.members = null;
        this.battlers = null;
        this.playerLimit = maxPlayers;
        this.leaderID = 0;
        this.activePCs = 0;
        this.activeNPCs = 0;
    }

    Party(final PartyMember[] newMembers) {
        this.members = newMembers;
        this.battlers = null;
        this.playerLimit = newMembers.length;
        this.leaderID = 0;
        this.activePCs = 0;
        this.activeNPCs = 0;
    }

    public Party(final int maxMembers, final int maxPlayers) {
        this.members = new PartyMember[maxMembers];
        this.playerLimit = maxPlayers;
        this.leaderID = 0;
        this.activePCs = 0;
        this.activeNPCs = 0;
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

    public int getPartyLevel() {
        int min = Integer.MAX_VALUE;
        for (int x = 0; x < this.battlers.length - 1; x++) {
            final int newMin = Math.min(
                    this.battlers[x].getTemplate().getLevel(),
                    this.battlers[x + 1].getTemplate().getLevel());
            min = Math.min(min, newMin);
        }
        if (min == Integer.MAX_VALUE) {
            min = this.battlers[0].getTemplate().getLevel();
        }
        return min;
    }

    public void checkPartyLevelUp() {
        for (final BattleCharacter battler : this.battlers) {
            // Level Up Check
            if (battler.getTemplate().checkLevelUp()) {
                battler.getTemplate().levelUp();
                if (Worldz.getApplication().getPrefsManager()
                        .getSoundEnabled(PreferencesManager.SOUNDS_BATTLE)) {
                    SoundManager.playSound("levelup");
                }
                Messager.showTitledDialog(
                        battler.getTemplate().getName() + " reached level "
                                + battler.getTemplate().getLevel() + "!",
                        "Level Up");
            }
        }
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

    public void healParty(final int mod) {
        for (final PartyMember member : this.members) {
            if (member != null) {
                // Heal Party Member
                member.heal(mod);
            }
        }
    }

    public void healPartyFully() {
        for (final PartyMember member : this.members) {
            if (member != null) {
                // Heal Party Member Fully
                member.healFully();
            }
        }
    }

    public void hurtParty(final int mod) {
        for (final PartyMember member : this.members) {
            if (member != null) {
                // Hurt Party Member
                member.doDamage(mod);
            }
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

    public void healAndRegenerateFully() {
        for (final PartyMember member : this.members) {
            if (member != null) {
                member.healAndRegenerateFully();
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

    public PartyMember pickOnePartyMemberRandomly() {
        final String[] pickNames = this.buildNameList();
        final RandomRange r = new RandomRange(0, pickNames.length - 1);
        final int res = this.findMember(pickNames[r.generate()], 0,
                this.members.length);
        if (res != -1) {
            return this.members[res];
        } else {
            return null;
        }
    }

    public PartyMember pickOnePartyMemberCreate() {
        final String[] pickNames = this.buildNameList();
        final String response = Messager.showInputDialog("Pick 1 Party Member",
                "Create Party", pickNames, pickNames[0]);
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
        final String response = Messager
                .showInputDialog(
                        "Pick " + number + " Party Member(s) - " + current
                                + " of " + number,
                        "Worldz", pickNames, pickNames[0]);
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

    public PartyMember[] pickMultiplePartyMembers(final int number) {
        final String[] pickNames = this.buildNameList();
        final PartyMember[] result = new PartyMember[number];
        for (int x = 0; x < number; x++) {
            final PartyMember member = this.pickPartyMemberInternal(pickNames,
                    x + 1, number);
            result[x] = member;
        }
        return result;
    }

    public PartyMember[] pickMultiplePartyMembersRandomly(final int number) {
        final PartyMember[] result = new PartyMember[number];
        for (int x = 0; x < number; x++) {
            final PartyMember member = this.pickOnePartyMemberRandomly();
            result[x] = member;
        }
        return result;
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

    public boolean removePartyMember(final PartyMember member) {
        final boolean isPC = member.isPlayerCharacter();
        if (isPC) {
            final int pos = this.findMember(member.getName(), 0,
                    this.playerLimit);
            if (pos != -1) {
                this.fixParty(pos, this.playerLimit);
                this.activePCs--;
                this.generateBattleCharacters();
                return true;
            }
            return false;
        } else {
            final int pos = this.findMember(member.getName(), this.playerLimit,
                    this.members.length);
            if (pos != -1) {
                this.fixParty(pos, this.members.length);
                this.activeNPCs--;
                this.generateBattleCharacters();
                return true;
            }
            return false;
        }
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

    private void fixParty(final int delpos, final int limit) {
        for (int x = delpos; x < limit - 1; x++) {
            this.members[x] = this.members[x + 1];
        }
        this.members[limit - 1] = null;
    }

    public boolean isPartyMemberPresent(final String name) {
        final int pos = this.findMember(name, 0, this.members.length);
        if (pos != -1) {
            return true;
        }
        return false;
    }

    public PartyMember getPartyMember(final String name) {
        final int pos = this.findMember(name, 0, this.members.length);
        if (pos != -1) {
            return this.members[pos];
        }
        return null;
    }

    public boolean setPartyMember(final PartyMember member) {
        final int pos = this.findMember(member.getName(), 0,
                this.members.length);
        if (pos != -1) {
            this.members[pos] = member;
            this.generateBattleCharacters();
            return true;
        }
        return false;
    }

    public static Party read(final DataReader worldFile) throws IOException {
        final int memCount = worldFile.readInt();
        final int pCount = worldFile.readInt();
        final int lid = worldFile.readInt();
        final int apc = worldFile.readInt();
        final int anpc = worldFile.readInt();
        final Party pty = new Party(pCount);
        pty.leaderID = lid;
        pty.activePCs = apc;
        pty.activeNPCs = anpc;
        pty.members = new PartyMember[memCount];
        for (int z = 0; z < memCount; z++) {
            final boolean present = worldFile.readBoolean();
            if (present) {
                pty.members[z] = PartyMember.read(worldFile);
            }
        }
        return pty;
    }

    public void write(final DataWriter worldFile) throws IOException {
        worldFile.writeInt(this.members.length);
        worldFile.writeInt(this.playerLimit);
        worldFile.writeInt(this.leaderID);
        worldFile.writeInt(this.activePCs);
        worldFile.writeInt(this.activeNPCs);
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
