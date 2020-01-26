/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell


Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.creatures;

import java.io.IOException;

import javax.swing.JOptionPane;

import net.worldwizard.io.DataReader;
import net.worldwizard.io.DataWriter;
import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.creatures.castes.Caste;
import net.worldwizard.worldz.creatures.castes.CasteManager;
import net.worldwizard.worldz.creatures.faiths.Faith;
import net.worldwizard.worldz.creatures.faiths.FaithManager;
import net.worldwizard.worldz.creatures.genders.Gender;
import net.worldwizard.worldz.creatures.genders.GenderManager;
import net.worldwizard.worldz.creatures.personalities.Personality;
import net.worldwizard.worldz.creatures.personalities.PersonalityManager;
import net.worldwizard.worldz.creatures.races.Race;
import net.worldwizard.worldz.creatures.races.RaceManager;

public class PartyManager {
    // Fields
    private static Party party;
    private static int bank = 0;
    private static final int PARTY_SIZE = 10;
    private static final int PARTY_PLAYER_LIMIT = 6;

    // Constructors
    private PartyManager() {
        // Do nothing
    }

    // Methods
    public static boolean createParty() {
        PartyManager.party = new Party(PartyManager.PARTY_SIZE,
                PartyManager.PARTY_PLAYER_LIMIT);
        int mem = 0;
        final String[] buttonNames = new String[] { "Done", "Create", "Pick" };
        final PartyMember[] pickMembers = CharacterLoader
                .loadAllRegisteredCharacters();
        Party pickParty = null;
        if (pickMembers != null) {
            pickParty = new Party(pickMembers);
        }
        for (int x = 0; x < PartyManager.PARTY_PLAYER_LIMIT; x++) {
            PartyMember pc = null;
            if (pickParty == null) {
                // No characters registered - must create one
                pc = PartyManager.createNewPC();
                if (pc != null) {
                    CharacterRegistration.autoregisterCharacter(pc.getName());
                    CharacterLoader.saveCharacter(pc);
                }
            } else {
                final int response = Messager.showCustomDialog(
                        "Pick, Create, or Done?", "Create Party", buttonNames,
                        buttonNames[2]);
                if (response == 2) {
                    pc = pickParty.pickOnePartyMemberCreate();
                } else if (response == 1) {
                    pc = PartyManager.createNewPC();
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
            final int response = Messager.showConfirmDialog(
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

    public static void setGoldInBank(final int newGold) {
        PartyManager.bank = newGold;
    }

    public static void loadGameHook(final DataReader worldFile)
            throws IOException {
        final int gib = worldFile.readInt();
        PartyManager.setGoldInBank(gib);
        PartyManager.party = Party.read(worldFile);
    }

    public static void saveGameHook(final DataWriter worldFile)
            throws IOException {
        worldFile.writeInt(PartyManager.getGoldInBank());
        PartyManager.party.write(worldFile);
    }

    public static PartyMember getNewPCInstance(final int r, final int c,
            final int f, final int g, final int p, final String n) {
        final Race race = RaceManager.getRace(r);
        final Caste caste = CasteManager.getCaste(c);
        final Faith faith = FaithManager.getFaith(f);
        final Gender gender = GenderManager.getGender(g);
        final Personality personality = PersonalityManager.getPersonality(p);
        return new PartyMember(race, caste, faith, gender, personality, n);
    }

    public static PartyMember getNewNPCInstance(final int r, final int c,
            final int f, final int g, final int p, final String n,
            final int level) {
        final Race race = RaceManager.getRace(r);
        final Caste caste = CasteManager.getCaste(c);
        final Faith faith = FaithManager.getFaith(f);
        final Gender gender = GenderManager.getGender(g);
        final Personality personality = PersonalityManager.getPersonality(p);
        return new PartyMember(race, caste, faith, gender, personality, n,
                level);
    }

    public static PartyMember createNewPC() {
        final String name = Messager.showTextInputDialog("Character Name",
                "Create Character");
        if (name != null) {
            final Race race = RaceManager.selectRace();
            if (race != null) {
                final Caste caste = CasteManager.selectCaste();
                if (caste != null) {
                    final Faith faith = FaithManager.selectFaith();
                    if (faith != null) {
                        final Gender gender = GenderManager.selectGender();
                        if (gender != null) {
                            final Personality personality = PersonalityManager
                                    .selectPersonality();
                            if (personality != null) {
                                return new PartyMember(race, caste, faith,
                                        gender, personality, name);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public static PartyMember createNewNPC() {
        final String name = Messager.showTextInputDialog("Character Name",
                "Create Character");
        if (name != null) {
            final Race race = RaceManager.selectRace();
            if (race != null) {
                final Caste caste = CasteManager.selectCaste();
                if (caste != null) {
                    final Faith faith = FaithManager.selectFaith();
                    if (faith != null) {
                        final Gender gender = GenderManager.selectGender();
                        if (gender != null) {
                            final Personality personality = PersonalityManager
                                    .selectPersonality();
                            if (personality != null) {
                                final String levelString = Messager
                                        .showTextInputDialog("Starting Level",
                                                "Create Character");
                                int level = 1;
                                try {
                                    level = Integer.parseInt(levelString);
                                } catch (final NumberFormatException nfe) {
                                    // Ignore
                                }
                                return new PartyMember(race, caste, faith,
                                        gender, personality, name, level);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
