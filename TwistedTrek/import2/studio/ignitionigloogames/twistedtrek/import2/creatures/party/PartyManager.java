/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.creatures.party;

import java.io.IOException;

import javax.swing.JFrame;

import studio.ignitionigloogames.commondialogs.CommonDialogs;
import studio.ignitionigloogames.twistedtrek.import2.creatures.castes.Caste;
import studio.ignitionigloogames.twistedtrek.import2.creatures.castes.CasteManager;
import studio.ignitionigloogames.twistedtrek.import2.creatures.characterfiles.CharacterLoader;
import studio.ignitionigloogames.twistedtrek.import2.creatures.characterfiles.CharacterRegistration;
import studio.ignitionigloogames.twistedtrek.import2.creatures.faiths.Faith;
import studio.ignitionigloogames.twistedtrek.import2.creatures.faiths.FaithManager;
import studio.ignitionigloogames.twistedtrek.import2.creatures.genders.Gender;
import studio.ignitionigloogames.twistedtrek.import2.creatures.genders.GenderManager;
import studio.ignitionigloogames.twistedtrek.import2.creatures.personalities.Personality;
import studio.ignitionigloogames.twistedtrek.import2.creatures.personalities.PersonalityManager;
import studio.ignitionigloogames.twistedtrek.import2.creatures.races.Race;
import studio.ignitionigloogames.twistedtrek.import2.creatures.races.RaceManager;
import studio.ignitionigloogames.xio.XDataReader;
import studio.ignitionigloogames.xio.XDataWriter;

public class PartyManager {
	// Fields
	private static Party party;
	private static int bank = 0;
	private static final int PARTY_SIZE = 1;
	private final static String[] buttonNames = new String[] { "Done", "Create", "Pick" };

	// Constructors
	private PartyManager() {
		// Do nothing
	}

	// Methods
	public static boolean createParty(final JFrame owner) {
		PartyManager.party = new Party();
		int mem = 0;
		final PartyMember[] pickMembers = CharacterLoader.loadAllRegisteredCharacters();
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
				final int response = CommonDialogs.showCustomDialog("Pick, Create, or Done?", "Create Party",
						PartyManager.buttonNames, PartyManager.buttonNames[2]);
				if (response == 2) {
					pc = PartyManager.pickOnePartyMemberCreate(pickMembers);
				} else if (response == 1) {
					pc = PartyManager.createNewPC(owner);
					if (pc != null) {
						CharacterRegistration.autoregisterCharacter(pc.getName());
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

	public static void loadGameHook(final XDataReader partyFile) throws IOException {
		final boolean containsPCData = partyFile.readBoolean();
		if (containsPCData) {
			final int gib = partyFile.readInt();
			PartyManager.setGoldInBank(gib);
			PartyManager.party = Party.read(partyFile);
		}
	}

	public static void saveGameHook(final XDataWriter partyFile) throws IOException {
		if (PartyManager.party != null) {
			partyFile.writeBoolean(true);
			partyFile.writeInt(PartyManager.getGoldInBank());
			PartyManager.party.write(partyFile);
		} else {
			partyFile.writeBoolean(false);
		}
	}

	public static PartyMember getNewPCInstance(final int r, final int c, final int f, final int p, final int g,
			final String n) {
		final Race race = RaceManager.getRace(r);
		final Caste caste = CasteManager.getCaste(c);
		final Faith faith = FaithManager.getFaith(f);
		final Personality personality = PersonalityManager.getPersonality(p);
		final Gender gender = GenderManager.getGender(g);
		return new PartyMember(race, caste, faith, personality, gender, n);
	}

	public static void updatePostKill() {
		final PartyMember leader = PartyManager.getParty().getLeader();
		leader.initPostKill(leader.getRace(), leader.getCaste(), leader.getFaith(), leader.getPersonality(),
				leader.getGender());
	}

	private static PartyMember createNewPC(final JFrame owner) {
		final String name = CommonDialogs.showTextInputDialog("Character Name", "Create Character");
		if (name != null) {
			final Race race = RaceManager.selectRace(owner);
			if (race != null) {
				final Caste caste = CasteManager.selectCaste(owner);
				if (caste != null) {
					final Faith faith = FaithManager.selectFaith(owner);
					if (faith != null) {
						final Personality personality = PersonalityManager.selectPersonality(owner);
						if (personality != null) {
							final Gender gender = GenderManager.selectGender();
							if (gender != null) {
								return new PartyMember(race, caste, faith, personality, gender, name);
							}
						}
					}
				}
			}
		}
		return null;
	}

	public static String showCreationDialog(final JFrame owner, final String labelText, final String title,
			final String[] input, final String[] descriptions) {
		return ListWithDescDialog.showDialog(owner, null, labelText, title, input, input[0], descriptions[0],
				descriptions);
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

	private static PartyMember pickOnePartyMemberCreate(final PartyMember[] members) {
		final String[] pickNames = PartyManager.buildNameList(members);
		final String response = CommonDialogs.showInputDialog("Pick 1 Party Member", "Create Party", pickNames,
				pickNames[0]);
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
