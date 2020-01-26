/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.creatures;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver3.support.creatures.castes.Caste;
import com.puttysoftware.dungeondiver3.support.creatures.castes.CasteManager;
import com.puttysoftware.dungeondiver3.support.creatures.characterfiles.CharacterLoader;
import com.puttysoftware.dungeondiver3.support.creatures.characterfiles.CharacterRegistration;
import com.puttysoftware.dungeondiver3.support.creatures.faiths.Faith;
import com.puttysoftware.dungeondiver3.support.creatures.faiths.FaithManager;
import com.puttysoftware.dungeondiver3.support.creatures.genders.Gender;
import com.puttysoftware.dungeondiver3.support.creatures.genders.GenderManager;
import com.puttysoftware.dungeondiver3.support.creatures.personalities.Personality;
import com.puttysoftware.dungeondiver3.support.creatures.personalities.PersonalityManager;
import com.puttysoftware.dungeondiver3.support.creatures.races.Race;
import com.puttysoftware.dungeondiver3.support.creatures.races.RaceManager;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class PartyManager {
    // Fields
    private static Party party;
    private static int bank = 0;
    private static final int PARTY_SIZE = 6;
    private final static String[] buttonNames = new String[] { "Done", "Create",
            "Pick" };

    // Constructors
    private PartyManager() {
        // Do nothing
    }

    // Methods
    public static boolean createParty(final JFrame owner) {
        PartyManager.party = new Party(PartyManager.PARTY_SIZE);
        int mem = 0;
        final PartyMember[] pickMembers = CharacterLoader
                .loadAllRegisteredCharacters();
        Party pickParty = null;
        if (pickMembers != null) {
            pickParty = new Party(pickMembers);
        }
        for (int x = 0; x < PartyManager.PARTY_SIZE; x++) {
            PartyMember pc = null;
            if (pickParty == null) {
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
                    pc = pickParty.pickOnePartyMemberCreate();
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
            final int response = CommonDialogs.showConfirmDialog(
                    "Add another character?", "Create Party");
            if (response != JOptionPane.YES_OPTION) {
                break;
            }
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

    public static void loadGameHook(final XDataReader partyFile)
            throws IOException {
        final boolean containsPCData = partyFile.readBoolean();
        if (containsPCData) {
            final int gib = partyFile.readInt();
            PartyManager.setGoldInBank(gib);
            PartyManager.party = Party.read(partyFile);
        }
    }

    public static void loadGameHookV1(final XDataReader partyFile)
            throws IOException {
        final boolean containsPCData = partyFile.readBoolean();
        if (containsPCData) {
            final int gib = partyFile.readInt();
            PartyManager.setGoldInBank(gib);
            PartyManager.party = Party.readV1(partyFile);
        }
    }

    public static void saveGameHook(final XDataWriter partyFile)
            throws IOException {
        if (PartyManager.party != null) {
            partyFile.writeBoolean(true);
            partyFile.writeInt(PartyManager.getGoldInBank());
            PartyManager.party.write(partyFile);
        } else {
            partyFile.writeBoolean(false);
        }
    }

    public static void writebackCharacters() {
        if (PartyManager.party != null) {
            PartyManager.party.writebackMembers();
        }
    }

    public static void revivePartyFully() {
        if (PartyManager.party != null) {
            PartyManager.party.revivePartyFully();
        }
    }

    public static PartyMember getNewPCInstance(final int r, final int c,
            final int f, final int p, final int g, final String n) {
        final Race race = RaceManager.getRace(r);
        final Caste caste = CasteManager.getCaste(c);
        final Faith faith = FaithManager.getFaith(f);
        final Personality personality = PersonalityManager.getPersonality(p);
        final Gender gender = GenderManager.getGender(g);
        return new PartyMember(race, caste, faith, personality, gender, n);
    }

    private static PartyMember createNewPC(final JFrame owner) {
        final String name = CommonDialogs.showTextInputDialog("Character Name",
                "Create Character");
        if (name != null) {
            final Race race = RaceManager.selectRace(owner);
            if (race != null) {
                final Caste caste = CasteManager.selectCaste(owner);
                if (caste != null) {
                    final Faith faith = FaithManager.selectFaith(owner);
                    if (faith != null) {
                        final Personality personality = PersonalityManager
                                .selectPersonality(owner);
                        if (personality != null) {
                            final Gender gender = GenderManager.selectGender();
                            if (gender != null) {
                                return new PartyMember(race, caste, faith,
                                        personality, gender, name);
                            }
                        }
                    }
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
}
