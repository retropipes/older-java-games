package net.worldwizard.dungeondiver.creatures;

import javax.swing.ImageIcon;

import net.worldwizard.dungeondiver.DungeonDiver;
import net.worldwizard.dungeondiver.creatures.ai.BossAIRoutine;
import net.worldwizard.dungeondiver.creatures.spells.BossSpellBook;

public class Boss extends Creature {
    // Fields
    private static final long serialVersionUID = 745458684863250L;
    private final int attack;
    private final int defense;
    // Constants
    public static final int FIGHT_LEVEL = 13;
    // Private Constants
    private static final double ATTACK_MULTIPLIER = 1.3;
    private static final double DEFENSE_MULTIPLIER = 1.3;
    private static final double HP_MULTIPLIER = 1.6;
    private static final double MP_MULTIPLIER = 1.3;
    private static final int BONUS_LEVEL = 3;

    // Constructors
    public Boss() {
        super();
        this.level = Boss.FIGHT_LEVEL + Boss.BONUS_LEVEL;
        this.maximumHP = Boss.getInitialMaximumHP();
        this.currentHP = this.maximumHP;
        this.maximumMP = Boss.getInitialMaximumMP();
        this.currentMP = this.maximumMP;
        this.attack = Boss.getInitialAttack();
        this.defense = Boss.getInitialDefense();
        this.gold = 0;
        this.experience = 0;
        this.spellsKnown = new BossSpellBook();
        this.ai = new BossAIRoutine();
    }

    @Override
    public int getAttack() {
        return this.attack;
    }

    @Override
    public int getDefense() {
        return this.defense;
    }

    private static int getInitialAttack() {
        final Player player = DungeonDiver.getHoldingBag().getPlayer();
        return (int) (player.getAttack() * Boss.ATTACK_MULTIPLIER);
    }

    private static int getInitialDefense() {
        final Player player = DungeonDiver.getHoldingBag().getPlayer();
        return (int) (player.getDefense() * Boss.DEFENSE_MULTIPLIER);
    }

    // Helper Methods
    private static int getInitialMaximumHP() {
        final Player player = DungeonDiver.getHoldingBag().getPlayer();
        return (int) (player.getMaximumHP() * Boss.HP_MULTIPLIER);
    }

    private static int getInitialMaximumMP() {
        final Player player = DungeonDiver.getHoldingBag().getPlayer();
        return (int) (player.getMaximumMP() * Boss.MP_MULTIPLIER);
    }

    // Accessors
    @Override
    public String getFightingWhatString() {
        return "You're fighting The Boss";
    }

    @Override
    protected ImageIcon getInitialImage() {
        return GraphicsManager.getBossImage();
    }

    @Override
    public String getName() {
        return "The Boss";
    }
}