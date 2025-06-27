package studio.ignitionigloogames.dungeondiver1.creatures.spells;

import javax.swing.JOptionPane;

import studio.ignitionigloogames.dungeondiver1.DungeonDiver;
import studio.ignitionigloogames.dungeondiver1.creatures.Creature;
import studio.ignitionigloogames.dungeondiver1.creatures.buffs.Buff;

public class SpellBookManager {
    // Fields
    private static boolean NO_SPELLS_FLAG = false;

    // Private Constructor
    private SpellBookManager() {
        // Do nothing
    }

    public static boolean selectAndCastSpell(final Creature caster) {
        boolean result = false;
        SpellBookManager.NO_SPELLS_FLAG = false;
        final Spell s = SpellBookManager.selectSpell(caster);
        if (s != null) {
            result = SpellBookManager.castSpell(s, caster);
            if (!result && !SpellBookManager.NO_SPELLS_FLAG) {
                JOptionPane.showMessageDialog(null,
                        "You try to cast a spell, but realize you don't have enough MP!",
                        "Select Spell", JOptionPane.WARNING_MESSAGE);
            }
        }
        return result;
    }

    private static Spell selectSpell(final Creature caster) {
        final SpellBook book = caster.getSpellBook();
        if (book != null) {
            final String[] names = book.getAllSpellNames();
            final String[] displayNames = book.getAllSpellNamesWithCosts();
            String dialogResult = null;
            dialogResult = (String) JOptionPane.showInputDialog(null,
                    "Select a Spell to Cast", "Select Spell",
                    JOptionPane.QUESTION_MESSAGE, null, displayNames,
                    displayNames[0]);
            if (dialogResult != null) {
                int index;
                for (index = 0; index < displayNames.length; index++) {
                    if (dialogResult.equals(displayNames[index])) {
                        break;
                    }
                }
                final Spell s = book.getSpellByName(names[index]);
                return s;
            } else {
                return null;
            }
        } else {
            SpellBookManager.NO_SPELLS_FLAG = true;
            JOptionPane.showMessageDialog(null,
                    "You try to cast a spell, but realize you don't know any!",
                    "Select Spell", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    public static boolean castSpell(final Spell cast, final Creature caster) {
        if (cast != null) {
            final int casterMP = caster.getCurrentMP();
            final int cost = cast.getCost();
            if (casterMP >= cost) {
                // Cast Spell
                caster.drain(cost);
                final Buff b = cast.getEffect();
                b.resetEffect();
                final Creature target = SpellBookManager.resolveTarget(cast);
                if (target.isBuffActive(b)) {
                    target.extendBuff(b, b.getInitialRounds());
                } else {
                    b.restoreBuff(target);
                    target.applyBuff(b);
                }
                return true;
            } else {
                // Not enough MP
                return false;
            }
        } else {
            return false;
        }
    }

    private static Creature resolveTarget(final Spell cast) {
        final char target = cast.getTarget();
        switch (target) {
            case 'P':
                return DungeonDiver.getHoldingBag().getPlayer();
            case 'E':
                return DungeonDiver.getHoldingBag().getBattle().getEnemy();
            default:
                return null;
        }
    }

    public static SpellBook getSpellBookByID(final int ID) {
        switch (ID) {
            case 0:
                return null;
            case 1:
                return new FighterSpellBook();
            case 2:
                return new MageSpellBook();
            case 3:
                return new ThiefSpellBook();
            default:
                return null;
        }
    }

    public static SpellBook getEnemySpellBookByID(final int ID) {
        switch (ID) {
            case 0:
                return null;
            case 1:
                return new LowLevelSpellBook();
            case 2:
                return new MidLevelSpellBook();
            case 3:
                return new HighLevelSpellBook();
            case 4:
                return new ToughLevelSpellBook();
            default:
                return null;
        }
    }

    public static int selectClass() {
        final String[] names = { "Fighter", "Mage", "Thief" };
        String dialogResult = null;
        dialogResult = (String) JOptionPane.showInputDialog(null,
                "Select a Class", "Select Class", JOptionPane.QUESTION_MESSAGE,
                null, names, names[0]);
        if (dialogResult != null) {
            int index;
            for (index = 0; index < names.length; index++) {
                if (dialogResult.equals(names[index])) {
                    break;
                }
            }
            return index + 1;
        } else {
            return 0;
        }
    }
}
