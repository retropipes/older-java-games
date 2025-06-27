/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.creatures;

import java.io.IOException;

import studio.ignitionigloogames.twistedtrek.import1.creatures.castes.Caste;
import studio.ignitionigloogames.twistedtrek.import1.creatures.castes.CasteManager;
import studio.ignitionigloogames.twistedtrek.import1.creatures.faiths.Faith;
import studio.ignitionigloogames.twistedtrek.import1.creatures.faiths.FaithManager;
import studio.ignitionigloogames.twistedtrek.import1.creatures.genders.Gender;
import studio.ignitionigloogames.twistedtrek.import1.creatures.genders.GenderManager;
import studio.ignitionigloogames.twistedtrek.import1.creatures.personalities.Personality;
import studio.ignitionigloogames.twistedtrek.import1.creatures.personalities.PersonalityManager;
import studio.ignitionigloogames.twistedtrek.import1.creatures.races.Race;
import studio.ignitionigloogames.twistedtrek.import1.creatures.races.RaceManager;
import studio.ignitionigloogames.twistedtrek.import1.items.ItemInventory;
import studio.ignitionigloogames.xio.XDataReader;
import studio.ignitionigloogames.xio.XDataWriter;

public class PCManager {
	// Fields
	private static PlayerCharacter playerCharacter;

	// Constructors
	private PCManager() {
		// Do nothing
	}

	// Methods
	public static PlayerCharacter getPlayer() {
		return PCManager.playerCharacter;
	}

	public static void setPlayer(final PlayerCharacter newPlayer) {
		PCManager.playerCharacter = newPlayer;
	}

	public static void loadGameHook(final XDataReader mazeFile) throws IOException {
		final int strength = mazeFile.readInt();
		final int block = mazeFile.readInt();
		final int agility = mazeFile.readInt();
		final int vitality = mazeFile.readInt();
		final int intelligence = mazeFile.readInt();
		final int luck = mazeFile.readInt();
		final int pAtk = mazeFile.readInt();
		final int pDef = mazeFile.readInt();
		final int pHP = mazeFile.readInt();
		final int pMP = mazeFile.readInt();
		final int k = mazeFile.readInt();
		final int lvl = mazeFile.readInt();
		final int cHP = mazeFile.readInt();
		final int cMP = mazeFile.readInt();
		final int gld = mazeFile.readInt();
		final int gib = mazeFile.readInt();
		final long exp = mazeFile.readLong();
		final int ml = mazeFile.readInt();
		final int r = mazeFile.readInt();
		final int c = mazeFile.readInt();
		final int f = mazeFile.readInt();
		final int g = mazeFile.readInt();
		final int p = mazeFile.readInt();
		final int max = mazeFile.readInt();
		final boolean[] known = new boolean[max];
		for (int x = 0; x < max; x++) {
			known[x] = mazeFile.readBoolean();
		}
		PCManager.playerCharacter = PCManager.getNewPCInstance(r, c, f, g, p);
		PCManager.playerCharacter.setStrength(strength);
		PCManager.playerCharacter.setBlock(block);
		PCManager.playerCharacter.setAgility(agility);
		PCManager.playerCharacter.setVitality(vitality);
		PCManager.playerCharacter.setIntelligence(intelligence);
		PCManager.playerCharacter.setLuck(luck);
		PCManager.playerCharacter.setItems(ItemInventory.readItemInventory(mazeFile));
		PCManager.playerCharacter.loadPlayer(pAtk, pDef, pHP, pMP, k, lvl, cHP, cMP, gld, gib, exp, ml, c, known);
	}

	public static void saveGameHook(final XDataWriter mazeFile) throws IOException {
		mazeFile.writeInt(PCManager.playerCharacter.getStrength());
		mazeFile.writeInt(PCManager.playerCharacter.getBlock());
		mazeFile.writeInt(PCManager.playerCharacter.getAgility());
		mazeFile.writeInt(PCManager.playerCharacter.getVitality());
		mazeFile.writeInt(PCManager.playerCharacter.getIntelligence());
		mazeFile.writeInt(PCManager.playerCharacter.getLuck());
		mazeFile.writeInt(PCManager.playerCharacter.getPermanentAttackPoints());
		mazeFile.writeInt(PCManager.playerCharacter.getPermanentDefensePoints());
		mazeFile.writeInt(PCManager.playerCharacter.getPermanentHPPoints());
		mazeFile.writeInt(PCManager.playerCharacter.getPermanentMPPoints());
		mazeFile.writeInt(PCManager.playerCharacter.getKills());
		mazeFile.writeInt(PCManager.playerCharacter.getLevel());
		mazeFile.writeInt(PCManager.playerCharacter.getCurrentHP());
		mazeFile.writeInt(PCManager.playerCharacter.getCurrentMP());
		mazeFile.writeInt(PCManager.playerCharacter.getGold());
		mazeFile.writeInt(PCManager.playerCharacter.getGoldInBank());
		mazeFile.writeLong(PCManager.playerCharacter.getExperience());
		mazeFile.writeInt(PCManager.playerCharacter.getMonsterLevel());
		mazeFile.writeInt(PCManager.playerCharacter.getRace().getRaceID());
		mazeFile.writeInt(PCManager.playerCharacter.getCaste().getCasteID());
		mazeFile.writeInt(PCManager.playerCharacter.getFaith().getFaithID());
		mazeFile.writeInt(PCManager.playerCharacter.getGender().getGenderID());
		mazeFile.writeInt(PCManager.playerCharacter.getPersonality().getPersonalityID());
		final int max = PCManager.playerCharacter.getSpellBook().getSpellCount();
		mazeFile.writeInt(max);
		for (int x = 0; x < max; x++) {
			mazeFile.writeBoolean(PCManager.playerCharacter.getSpellBook().isSpellKnown(x));
		}
		PCManager.playerCharacter.getItems().writeItemInventory(mazeFile);
	}

	private static PlayerCharacter getNewPCInstance(final int r, final int c, final int f, final int g, final int p) {
		final Race race = RaceManager.getRace(r);
		final Caste caste = CasteManager.getCaste(c);
		final Faith faith = FaithManager.getFaith(f);
		final Gender gender = GenderManager.getGender(g);
		final Personality personality = PersonalityManager.getPersonality(p);
		return new PlayerCharacter(race, caste, faith, gender, personality);
	}

	public static boolean createNewPC() {
		final Race race = RaceManager.selectRace();
		if (race != null) {
			final Caste caste = CasteManager.selectCaste();
			if (caste != null) {
				final Faith faith = FaithManager.selectFaith();
				if (faith != null) {
					final Gender gender = GenderManager.selectGender();
					if (gender != null) {
						final Personality personality = PersonalityManager.selectPersonality();
						if (personality != null) {
							PCManager.playerCharacter = new PlayerCharacter(race, caste, faith, gender, personality);
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public static void createNewPCPostKill(final int pAttack, final int pDefense, final int pHP, final int pMP,
			final int k) {
		final Race race = RaceManager.selectRace();
		if (race != null) {
			final Caste caste = CasteManager.selectCaste();
			if (caste != null) {
				final Faith faith = FaithManager.selectFaith();
				if (faith != null) {
					final Gender gender = GenderManager.selectGender();
					if (gender != null) {
						final Personality personality = PersonalityManager.selectPersonality();
						if (personality != null) {
							PCManager.playerCharacter = new PlayerCharacter(race, caste, faith, gender, personality,
									pAttack, pDefense, pHP, pMP, k);
							return;
						}
					}
				}
			}
		}
		final Race r = PCManager.playerCharacter.getRace();
		final Caste c = PCManager.playerCharacter.getCaste();
		final Faith f = PCManager.playerCharacter.getFaith();
		final Gender g = PCManager.playerCharacter.getGender();
		final Personality p = PCManager.playerCharacter.getPersonality();
		PCManager.playerCharacter = new PlayerCharacter(r, c, f, g, p, pAttack, pDefense, pHP, pMP, k);
	}
}
