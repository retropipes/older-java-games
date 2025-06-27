package net.worldwizard.support.creatures.monsters;

import net.worldwizard.randomnumbers.RandomRange;
import net.worldwizard.support.creatures.PartyManager;
import net.worldwizard.support.creatures.StatConstants;
import net.worldwizard.support.items.Equipment;
import net.worldwizard.support.items.EquipmentFactory;
import net.worldwizard.support.items.EquipmentSlotConstants;

public abstract class RandomScalingBaseMonster extends RandomBaseMonster {
        // Constructors
        RandomScalingBaseMonster() {
                super();
                final int newLevel = PartyManager.getParty().getDungeonLevel();
                this.setLevel(newLevel);
                this.setVitality(this.getInitialVitality());
                this.setCurrentHP(this.getMaximumHP());
                this.setIntelligence(this.getInitialIntelligence());
                this.setCurrentMP(this.getMaximumMP());
                this.setStrength(this.getInitialStrength());
                this.setBlock(this.getInitialBlock());
                this.setAgility(this.getInitialAgility());
                this.setLuck(this.getInitialLuck());
                this.setGold(this.getInitialGold());
                this.setExperience(this.getInitialExperience());
                this.setAttacksPerRound(1);
                this.setSpellsPerRound(1);
                // Create equipment
                final Equipment weapon = EquipmentFactory.createMonsterWeapon(newLevel);
                final Equipment helmet = EquipmentFactory.createArmor(newLevel,
                                EquipmentSlotConstants.SLOT_HEAD);
                final Equipment necklace = EquipmentFactory.createArmor(newLevel,
                                EquipmentSlotConstants.SLOT_NECK);
                final Equipment shield = EquipmentFactory.createArmor(newLevel,
                                EquipmentSlotConstants.SLOT_OFFHAND);
                final Equipment robe = EquipmentFactory.createArmor(newLevel,
                                EquipmentSlotConstants.SLOT_BODY);
                final Equipment cape = EquipmentFactory.createArmor(newLevel,
                                EquipmentSlotConstants.SLOT_BACK);
                final Equipment shirt = EquipmentFactory.createArmor(newLevel,
                                EquipmentSlotConstants.SLOT_UPPER_TORSO);
                final Equipment bracers = EquipmentFactory.createArmor(newLevel,
                                EquipmentSlotConstants.SLOT_ARMS);
                final Equipment gloves = EquipmentFactory.createArmor(newLevel,
                                EquipmentSlotConstants.SLOT_HANDS);
                final Equipment ring = EquipmentFactory.createArmor(newLevel,
                                EquipmentSlotConstants.SLOT_FINGERS);
                final Equipment belt = EquipmentFactory.createArmor(newLevel,
                                EquipmentSlotConstants.SLOT_LOWER_TORSO);
                final Equipment pants = EquipmentFactory.createArmor(newLevel,
                                EquipmentSlotConstants.SLOT_LEGS);
                final Equipment boots = EquipmentFactory.createArmor(newLevel,
                                EquipmentSlotConstants.SLOT_FEET);
                // Equip it
                this.getItems().equipOneHandedWeapon(weapon, true);
                this.getItems().equipArmor(helmet);
                this.getItems().equipArmor(necklace);
                this.getItems().equipArmor(shield);
                this.getItems().equipArmor(robe);
                this.getItems().equipArmor(cape);
                this.getItems().equipArmor(shirt);
                this.getItems().equipArmor(bracers);
                this.getItems().equipArmor(gloves);
                this.getItems().equipArmor(ring);
                this.getItems().equipArmor(belt);
                this.getItems().equipArmor(pants);
                this.getItems().equipArmor(boots);
        }

        @Override
        protected void loadMonster() {
                super.loadMonster();
                final int newLevel = this.getLevel();
                this.setLevel(newLevel);
                this.setVitality(this.getInitialVitality());
                this.setCurrentHP(this.getMaximumHP());
                this.setIntelligence(this.getInitialIntelligence());
                this.setCurrentMP(this.getMaximumMP());
                this.setStrength(this.getInitialStrength());
                this.setBlock(this.getInitialBlock());
                this.setAgility(this.getInitialAgility());
                this.setLuck(this.getInitialLuck());
                this.setGold(this.getInitialGold());
                this.setExperience(this.getInitialExperience());
                this.setAttacksPerRound(1);
                this.setSpellsPerRound(1);
                this.image = this.getInitialImage();
                // Create equipment
                final Equipment weapon = EquipmentFactory.createMonsterWeapon(newLevel);
                final Equipment helmet = EquipmentFactory.createArmor(newLevel,
                                EquipmentSlotConstants.SLOT_HEAD);
                final Equipment necklace = EquipmentFactory.createArmor(newLevel,
                                EquipmentSlotConstants.SLOT_NECK);
                final Equipment shield = EquipmentFactory.createArmor(newLevel,
                                EquipmentSlotConstants.SLOT_OFFHAND);
                final Equipment robe = EquipmentFactory.createArmor(newLevel,
                                EquipmentSlotConstants.SLOT_BODY);
                final Equipment cape = EquipmentFactory.createArmor(newLevel,
                                EquipmentSlotConstants.SLOT_BACK);
                final Equipment shirt = EquipmentFactory.createArmor(newLevel,
                                EquipmentSlotConstants.SLOT_UPPER_TORSO);
                final Equipment bracers = EquipmentFactory.createArmor(newLevel,
                                EquipmentSlotConstants.SLOT_ARMS);
                final Equipment gloves = EquipmentFactory.createArmor(newLevel,
                                EquipmentSlotConstants.SLOT_HANDS);
                final Equipment ring = EquipmentFactory.createArmor(newLevel,
                                EquipmentSlotConstants.SLOT_FINGERS);
                final Equipment belt = EquipmentFactory.createArmor(newLevel,
                                EquipmentSlotConstants.SLOT_LOWER_TORSO);
                final Equipment pants = EquipmentFactory.createArmor(newLevel,
                                EquipmentSlotConstants.SLOT_LEGS);
                final Equipment boots = EquipmentFactory.createArmor(newLevel,
                                EquipmentSlotConstants.SLOT_FEET);
                // Equip it
                this.getItems().equipOneHandedWeapon(weapon, true);
                this.getItems().equipArmor(helmet);
                this.getItems().equipArmor(necklace);
                this.getItems().equipArmor(shield);
                this.getItems().equipArmor(robe);
                this.getItems().equipArmor(cape);
                this.getItems().equipArmor(shirt);
                this.getItems().equipArmor(bracers);
                this.getItems().equipArmor(gloves);
                this.getItems().equipArmor(ring);
                this.getItems().equipArmor(belt);
                this.getItems().equipArmor(pants);
                this.getItems().equipArmor(boots);
        }

        @Override
        public boolean scales() {
                return true;
        }

        private int getInitialStrength() {
                final RandomRange r = new RandomRange(1,
                                this.getLevel() * StatConstants.GAIN_STRENGTH);
                return r.generate();
        }

        private int getInitialBlock() {
                final RandomRange r = new RandomRange(0,
                                this.getLevel() * StatConstants.GAIN_BLOCK);
                return r.generate();
        }

        private long getInitialExperience() {
                int minvar, maxvar;
                minvar = (int) (this.getLevel()
                                * BaseMonster.MINIMUM_EXPERIENCE_RANDOM_VARIANCE);
                maxvar = (int) (this.getLevel()
                                * BaseMonster.MAXIMUM_EXPERIENCE_RANDOM_VARIANCE);
                final RandomRange r = new RandomRange(minvar, maxvar);
                final long exp = this.getToughness() + r.generate();
                if (exp < 0L) {
                        return 0L;
                } else {
                        return exp;
                }
        }

        private int getToughness() {
                return this.getStrength() + this.getBlock() + this.getAgility()
                                + this.getVitality() + this.getIntelligence() + this.getLuck();
        }

        private int getInitialGold() {
                final int min = 0;
                final int max = this.getToughness()
                                * BaseMonster.GOLD_TOUGHNESS_MULTIPLIER;
                final RandomRange r = new RandomRange(min, max);
                return r.generate();
        }

        private int getInitialAgility() {
                final RandomRange r = new RandomRange(1,
                                this.getLevel() * StatConstants.GAIN_AGILITY);
                return r.generate();
        }

        private int getInitialVitality() {
                final RandomRange r = new RandomRange(1,
                                this.getLevel() * StatConstants.GAIN_VITALITY);
                return r.generate();
        }

        private int getInitialIntelligence() {
                final RandomRange r = new RandomRange(0,
                                this.getLevel() * StatConstants.GAIN_INTELLIGENCE);
                return r.generate();
        }

        private int getInitialLuck() {
                final RandomRange r = new RandomRange(0,
                                this.getLevel() * StatConstants.GAIN_LUCK);
                return r.generate();
        }
}
