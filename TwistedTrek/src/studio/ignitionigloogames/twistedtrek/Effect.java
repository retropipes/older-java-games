/* Twisted Trek: A Dual-World Action RPG */
package studio.ignitionigloogames.twistedtrek;

import java.io.IOException;

import studio.ignitionigloogames.xio.XDataReader;
import studio.ignitionigloogames.xio.XDataWriter;

public final class Effect {
    // Fields
    private int duration;
    private int attackModifier;
    private int defenseModifier;
    private int hpModifierOngoing;
    private int manaModifierOngoing;
    private String hpModifierOngoingMessage;
    private String itemMessage;
    private String itemMessageEnd;
    private int visionModifier;
    private int xpModifierOngoing;
    private int regenHpModifier;
    private int regenManaModifier;
    private boolean hpToMana;
    private boolean blink;
    private boolean summonBats;
    private boolean detect;
    private final Item item;
    private static StuffFactory FACTORY;

    public Effect(final Item newItem) {
	// Create an empty effect to be populated later
	this.item = newItem;
    }

    public Effect(final int newDuration, final int newAttackModifier, final int newDefenseModifier,
	    final int newHpModifierOngoing, final int newManaModifierOngoing, final String newHpModifierOngoingMessage,
	    final Item newItem, final String newItemMessage, final int newVisionModifier,
	    final int newXpModifierOngoing, final int newRegenHpModifier, final int newRegenManaModifier,
	    final boolean newHpToMana, final boolean newBlink, final boolean newSummonBats, final boolean newDetect,
	    final String newItemMessageEnd) {
	this.duration = newDuration;
	this.attackModifier = newAttackModifier;
	this.defenseModifier = newDefenseModifier;
	this.hpModifierOngoing = newHpModifierOngoing;
	this.manaModifierOngoing = newManaModifierOngoing;
	this.hpModifierOngoingMessage = newHpModifierOngoingMessage;
	this.item = newItem;
	this.itemMessage = newItemMessage;
	this.visionModifier = newVisionModifier;
	this.xpModifierOngoing = newXpModifierOngoing;
	this.regenHpModifier = newRegenHpModifier;
	this.regenManaModifier = newRegenManaModifier;
	this.hpToMana = newHpToMana;
	this.blink = newBlink;
	this.summonBats = newSummonBats;
	this.detect = newDetect;
	this.itemMessageEnd = newItemMessageEnd;
    }

    public Effect(final Effect other) {
	this.duration = other.duration;
	this.attackModifier = other.attackModifier;
	this.defenseModifier = other.defenseModifier;
	this.hpModifierOngoing = other.hpModifierOngoing;
	this.manaModifierOngoing = other.manaModifierOngoing;
	this.hpModifierOngoingMessage = other.hpModifierOngoingMessage;
	this.item = other.item;
	this.itemMessage = other.itemMessage;
	this.visionModifier = other.visionModifier;
	this.xpModifierOngoing = other.xpModifierOngoing;
	this.regenHpModifier = other.regenHpModifier;
	this.regenManaModifier = other.regenManaModifier;
	this.hpToMana = other.hpToMana;
	this.blink = other.blink;
	this.summonBats = other.summonBats;
	this.detect = other.detect;
	this.itemMessageEnd = other.itemMessageEnd;
    }

    public static void setStuffFactory(final StuffFactory stuff) {
	Effect.FACTORY = stuff;
    }

    public boolean isDone() {
	return this.duration < 1;
    }

    public void update(final Creature creature) {
	this.duration--;
	if (!this.hpToMana) {
	    if (this.hpModifierOngoing != 0 && this.hpModifierOngoingMessage != null) {
		if (this.hpModifierOngoing > 0 && creature.hp() < creature.maxHp()) {
		    creature.modifyHp(this.hpModifierOngoing, this.hpModifierOngoingMessage);
		} else if (this.hpModifierOngoing < 0 && creature.hp() > 0) {
		    creature.modifyHp(this.hpModifierOngoing, this.hpModifierOngoingMessage);
		}
	    }
	    if (this.manaModifierOngoing != 0) {
		if (this.manaModifierOngoing > 0 && creature.mana() < creature.maxMana()) {
		    creature.modifyMana(this.manaModifierOngoing);
		} else if (this.manaModifierOngoing < 0 && creature.mana() > 0) {
		    creature.modifyMana(this.manaModifierOngoing);
		}
	    }
	}
	if (this.xpModifierOngoing != 0) {
	    creature.modifyXp(creature.level() * this.xpModifierOngoing);
	}
    }

    public void start(final Creature creature) {
	if (this.item != null && this.itemMessage != null) {
	    creature.doAction(this.item, this.itemMessage);
	}
	if (this.hpToMana && this.hpModifierOngoingMessage != null) {
	    final int amount = Math.min(creature.hp() - 1, creature.maxMana() - creature.mana());
	    creature.modifyHp(-amount, this.hpModifierOngoingMessage);
	    creature.modifyMana(amount);
	}
	if (this.blink) {
	    creature.doAction("fade out");
	    int mx = 0;
	    int my = 0;
	    do {
		mx = (int) (Math.random() * 11) - 5;
		my = (int) (Math.random() * 11) - 5;
	    } while (!creature.canEnter(creature.x + mx, creature.y + my, creature.z)
		    && creature.canSee(creature.x + mx, creature.y + my, creature.z));
	    creature.moveBy(mx, my, 0);
	    creature.doAction("fade in");
	}
	if (this.summonBats) {
	    for (int ox = -1; ox < 2; ox++) {
		for (int oy = -1; oy < 2; oy++) {
		    final int nx = creature.x + ox;
		    final int ny = creature.y + oy;
		    if (ox == 0 && oy == 0 || creature.creature(nx, ny, creature.z) != null) {
			continue;
		    }
		    final Creature bat = Effect.FACTORY.newBat(0);
		    if (!bat.canEnter(nx, ny, creature.z)) {
			Effect.FACTORY.getWorld().remove(bat);
			continue;
		    }
		    bat.x = nx;
		    bat.y = ny;
		    bat.z = creature.z;
		    creature.summon(bat);
		}
	    }
	}
	if (this.detect) {
	    creature.doAction("look far off into the distance");
	    creature.modifyDetectCreatures(1);
	}
	if (this.attackModifier != 0) {
	    creature.modifyAttackValue(this.attackModifier);
	}
	if (this.defenseModifier != 0) {
	    creature.modifyDefenseValue(this.defenseModifier);
	}
	if (this.visionModifier != 0) {
	    creature.modifyVisionRadius(this.visionModifier);
	}
	if (this.regenHpModifier != 0) {
	    creature.modifyRegenHpPer1000(this.regenHpModifier);
	}
	if (this.regenManaModifier != 0) {
	    creature.modifyRegenManaPer1000(this.regenManaModifier);
	}
    }

    public void end(final Creature creature) {
	if (this.itemMessageEnd != null) {
	    creature.doAction(this.itemMessageEnd);
	}
	if (this.detect) {
	    creature.modifyDetectCreatures(-1);
	}
	if (this.attackModifier != 0) {
	    creature.modifyAttackValue(-this.attackModifier);
	}
	if (this.defenseModifier != 0) {
	    creature.modifyDefenseValue(-this.defenseModifier);
	}
	if (this.visionModifier != 0) {
	    creature.modifyVisionRadius(-this.visionModifier);
	}
	if (this.regenHpModifier != 0) {
	    creature.modifyRegenHpPer1000(-this.regenHpModifier);
	}
	if (this.regenManaModifier != 0) {
	    creature.modifyRegenManaPer1000(-this.regenManaModifier);
	}
    }

    public void loadEffect(final XDataReader reader) throws IOException {
	reader.readOpeningGroup("effect");
	this.duration = reader.readCustomInt("duration");
	this.attackModifier = reader.readCustomInt("attackModifier");
	this.defenseModifier = reader.readCustomInt("defenseModifier");
	this.hpModifierOngoing = reader.readCustomInt("healthModifierOngoing");
	this.manaModifierOngoing = reader.readCustomInt("manaModifierOngoing");
	final boolean hpModOMPresent = reader.readCustomBoolean("hpModOMPresent");
	if (hpModOMPresent) {
	    this.hpModifierOngoingMessage = reader.readCustomString("healthModifierOngoingMessage");
	}
	final boolean imPresent = reader.readCustomBoolean("imPresent");
	if (imPresent) {
	    this.itemMessage = reader.readCustomString("itemMessage");
	}
	this.visionModifier = reader.readCustomInt("visionModifier");
	this.regenHpModifier = reader.readCustomInt("regenHealthModifier");
	this.regenManaModifier = reader.readCustomInt("regenManaModifier");
	this.hpToMana = reader.readCustomBoolean("hpToManaFlag");
	this.blink = reader.readCustomBoolean("blinkFlag");
	this.summonBats = reader.readCustomBoolean("summonBatsFlag");
	this.detect = reader.readCustomBoolean("detectFlag");
	final boolean imePresent = reader.readCustomBoolean("imePresent");
	if (imePresent) {
	    this.itemMessageEnd = reader.readCustomString("itemMessageEnd");
	}
	reader.readClosingGroup("effect");
    }

    public void saveEffect(final XDataWriter writer) throws IOException {
	writer.writeOpeningGroup("effect");
	writer.writeCustomInt(this.duration, "duration");
	writer.writeCustomInt(this.attackModifier, "attackModifier");
	writer.writeCustomInt(this.defenseModifier, "defenseModifier");
	writer.writeCustomInt(this.hpModifierOngoing, "healthModifierOngoing");
	writer.writeCustomInt(this.manaModifierOngoing, "manaModifierOngoing");
	final boolean hpModOMPresent = this.hpModifierOngoingMessage != null;
	writer.writeCustomBoolean(hpModOMPresent, "hpModOMPresent");
	if (hpModOMPresent) {
	    writer.writeCustomString(this.hpModifierOngoingMessage, "healthModifierOngoingMessage");
	}
	final boolean imPresent = this.itemMessage != null;
	writer.writeCustomBoolean(imPresent, "imPresent");
	if (imPresent) {
	    writer.writeCustomString(this.itemMessage, "itemMessage");
	}
	writer.writeCustomInt(this.visionModifier, "visionModifier");
	writer.writeCustomInt(this.regenHpModifier, "regenHealthModifier");
	writer.writeCustomInt(this.regenManaModifier, "regenManaModifier");
	writer.writeCustomBoolean(this.hpToMana, "hpToManaFlag");
	writer.writeCustomBoolean(this.blink, "blinkFlag");
	writer.writeCustomBoolean(this.summonBats, "summonBatsFlag");
	writer.writeCustomBoolean(this.detect, "detectFlag");
	final boolean imePresent = this.itemMessageEnd != null;
	writer.writeCustomBoolean(imPresent, "imePresent");
	if (imePresent) {
	    writer.writeCustomString(this.itemMessageEnd, "itemMessageEnd");
	}
	writer.writeClosingGroup("effect");
    }
}
