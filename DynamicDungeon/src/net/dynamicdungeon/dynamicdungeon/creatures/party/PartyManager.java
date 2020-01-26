/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.creatures.party;

import java.io.IOException;

import javax.swing.JFrame;

import net.dynamicdungeon.commondialogs.CommonDialogs;
import net.dynamicdungeon.dbio.DatabaseReader;
import net.dynamicdungeon.dbio.DatabaseWriter;
import net.dynamicdungeon.dynamicdungeon.creatures.castes.Caste;
import net.dynamicdungeon.dynamicdungeon.creatures.castes.CasteManager;
import net.dynamicdungeon.dynamicdungeon.creatures.characterfiles.CharacterLoader;
import net.dynamicdungeon.dynamicdungeon.creatures.characterfiles.CharacterRegistration;
import net.dynamicdungeon.dynamicdungeon.creatures.races.Race;
import net.dynamicdungeon.dynamicdungeon.creatures.races.RaceManager;

public class PartyManager {
    // Fields
    private static Party party;
    private static int bank = 0;
    private static final int PARTY_SIZE = 1;
    private final static String[] buttonNames = new String[] { "Done", "Create",
            "Pick" };

    // Constructors
    private PartyManager() {
        // Do nothing
    }

    // Methods
    public static boolean createParty(final JFrame owner) {
        PartyManager.party = new Party();
        int mem = 0;
        final PartyMember[] pickMembers = CharacterLoader
                .loadAllRegisteredCharacters();
        for (int x = 0; x < PartyManager.PARTY_SIZE; x++) {
            PartyMember pc = null;
            if (pickMembers == null) {
                // No characters registered - must create one
                pc = PartyManager.createNewPC(owner);
                if (pc != null) {
                    CharacterRegistration.autoregisterCharacter(pc.getName());
                    CharacterLoader.saveCharacter(pc);
                }
            } else {
                final int response = CommonDialogs.showCustomDialog(
                        "Pick, Create, or Done?", "Create Party",
                        PartyManager.buttonNames, PartyManager.buttonNames[2]);
                if (response == 2) {
                    pc = PartyManager.pickOnePartyMemberCreate(pickMembers);
                } else if (response == 1) {
                    pc = PartyManager.createNewPC(owner);
                    if (pc != null) {
                        CharacterRegistration
                                .autoregisterCharacter(pc.getName());
                        CharacterLoader.saveCharacter(pc);
                    }
                }
            }
            if (pc == null) {
                break;
            }
            PartyManager.party.addPartyMember(pc);
            mem++;
        }
        if (mem == 0) {
            return false;
        }
        return true;
    }

    public static Party getParty() {
        return PartyManager.party;
    }

    public static void addGoldToBank(final int newGold) {
        PartyManager.bank += newGold;
    }

    public static int getGoldInBank() {
        return PartyManager.bank;
    }

    public static void removeGoldFromBank(final int cost) {
        PartyManager.bank -= cost;
        if (PartyManager.bank < 0) {
            PartyManager.bank = 0;
        }
    }

    private static void setGoldInBank(final int newGold) {
        PartyManager.bank = newGold;
    }

    public static void loadGameHook(final DatabaseReader partyFile)
            throws IOException {
        final boolean containsPCData = partyFile.readBoolean();
        if (containsPCData) {
            final int gib = partyFile.readInt();
            PartyManager.setGoldInBank(gib);
            PartyManager.party = Party.read(partyFile);
        }
    }

    public static void saveGameHook(final DatabaseWriter partyFile)
            throws IOException {
        if (PartyManager.party != null) {
            partyFile.writeBoolean(true);
            partyFile.writeInt(PartyManager.getGoldInBank());
            PartyManager.party.write(partyFile);
        } else {
            partyFile.writeBoolean(false);
        }
    }

    public static PartyMember getNewPCInstance(final int r, final int c,
            final String n) {
        final Race race = RaceManager.getRace(r);
        final Caste caste = CasteManager.getCaste(c);
        return new PartyMember(race, caste, n);
    }

    public static void updatePostKill() {
        final PartyMember leader = PartyManager.getParty().getLeader();
        leader.initPostKill(leader.getRace(), leader.getCaste());
    }

    private static PartyMember createNewPC(final JFrame owner) {
        final String name = CommonDialogs.showTextInputDialog("Character Name",
                "Create Character");
        if (name != null) {
            final Race race = RaceManager.selectRace(owner);
            if (race != null) {
                final Caste caste = CasteManager.selectCaste(owner);
                if (caste != null) {
                    return new PartyMember(race, caste, name);
                }
            }
        }
        return null;
    }

    public static String showCreationDialog(final JFrame owner,
            final String labelText, final String title, final String[] input,
            final String[] descriptions) {
        return ListWithDescDialog.showDialog(owner, null, labelText, title,
                input, input[0], descriptions[0], descriptions);
    }

    private static String[] buildNameList(final PartyMember[] members) {
        final String[] tempNames = new String[1];
        int nnc = 0;
        for (int x = 0; x < tempNames.length; x++) {
            if (members != null) {
                tempNames[x] = members[x].getName();
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

    private static PartyMember pickOnePartyMemberCreate(
            final PartyMember[] members) {
        final String[] pickNames = PartyManager.buildNameList(members);
        final String response = CommonDialogs.showInputDialog(
                "Pick 1 Party Member", "Create Party", pickNames, pickNames[0]);
        if (response != null) {
            for (final PartyMember member : members) {
                if (member.getName().equals(response)) {
                    return member;
                }
            }
            return null;
        } else {
            return null;
        }
    }
}
