/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell


 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.creatures;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.gemma.support.creatures.characterfiles.CharacterLoader;
import com.puttysoftware.gemma.support.creatures.characterfiles.CharacterRegistration;
import com.puttysoftware.gemma.support.creatures.faiths.Faith;
import com.puttysoftware.gemma.support.creatures.faiths.FaithManager;
import com.puttysoftware.gemma.support.creatures.genders.Gender;
import com.puttysoftware.gemma.support.creatures.genders.GenderManager;
import com.puttysoftware.gemma.support.creatures.personalities.Personality;
import com.puttysoftware.gemma.support.creatures.personalities.PersonalityManager;
import com.puttysoftware.gemma.support.creatures.races.Race;
import com.puttysoftware.gemma.support.creatures.races.RaceManager;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class PartyManager {
    // Fields
    private static Party party;
    private static final int PARTY_SIZE = 1;
    private final static String[] buttonNames = new String[] { "Done", "Create",
            "Pick" };

    // Constructors
    private PartyManager() {
        // Do nothing
    }

    // Methods
    public static boolean createParty(JFrame owner) {
        PartyManager.party = new Party(PartyManager.PARTY_SIZE);
        int mem = 0;
        PartyMember[] pickMembers = CharacterLoader
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
                int response = CommonDialogs.showCustomDialog(
                        "Pick, Create, or Done?", "Create Party", buttonNames,
                        buttonNames[2]);
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
            if (mem < PartyManager.PARTY_SIZE) {
                int response = CommonDialogs.showConfirmDialog(
                        "Add another character?", "Create Party");
                if (response != JOptionPane.YES_OPTION) {
                    break;
                }
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

    public static void loadGameHook(XDataReader partyFile) throws IOException {
        boolean containsPCData = partyFile.readBoolean();
        if (containsPCData) {
            PartyManager.party = Party.read(partyFile);
        }
    }

    public static void saveGameHook(XDataWriter partyFile) throws IOException {
        if (PartyManager.party != null) {
            partyFile.writeBoolean(true);
            PartyManager.party.write(partyFile);
        } else {
            partyFile.writeBoolean(false);
        }
    }

    public static void revivePartyFully() {
        if (PartyManager.party != null) {
            PartyManager.party.revivePartyFully();
        }
    }

    public static PartyMember getNewPCInstance(int r, int f, int p, int g,
            String n) {
        Race race = RaceManager.getRace(r);
        Faith faith = FaithManager.getFaith(f);
        Personality personality = PersonalityManager.getPersonality(p);
        Gender gender = GenderManager.getGender(g);
        return new PartyMember(race, faith, personality, gender, n);
    }

    private static PartyMember createNewPC(JFrame owner) {
        String name = CommonDialogs.showTextInputDialog("Character Name",
                "Create Character");
        if (name != null) {
            Race race = RaceManager.selectRace(owner);
            if (race != null) {
                Faith faith = FaithManager.selectFaith(owner);
                if (faith != null) {
                    Personality personality = PersonalityManager
                            .selectPersonality(owner);
                    if (personality != null) {
                        Gender gender = GenderManager.selectGender();
                        if (gender != null) {
                            return new PartyMember(race, faith, personality,
                                    gender, name);
                        }
                    }
                }
            }
        }
        return null;
    }

    public static String showCreationDialog(JFrame owner, String labelText,
            String title, String[] input, String[] descriptions) {
        return ListWithDescDialog.showDialog(owner, null, labelText, title,
                input, input[0], descriptions[0], descriptions);
    }
}
