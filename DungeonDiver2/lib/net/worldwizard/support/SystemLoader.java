/*  SceneMaker: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support;

import java.io.File;

import net.worldwizard.support.creatures.castes.Caste;
import net.worldwizard.support.creatures.races.Race;
import net.worldwizard.support.effects.Effect;
import net.worldwizard.support.items.combat.CombatUsableItem;
import net.worldwizard.support.spells.Spell;
import net.worldwizard.support.spells.SpellBook;
import net.worldwizard.support.variables.Extension;
import net.worldwizard.xio.XDataReader;

public class SystemLoader {
    // Constructors
    private SystemLoader() {
        // Do nothing
    }

    // Methods
    public static Caste loadCaste(final String file) {
        try {
            final XDataReader casteFile = new XDataReader(Support
                    .getSystemVariables().getBasePath()
                    + File.separator
                    + "castes"
                    + File.separator
                    + file
                    + Extension.getCasteExtensionWithPeriod(),
                    Extension.getCasteExtension());
            final Caste c = Caste.read(casteFile);
            c.setSystemObject(true);
            casteFile.close();
            return c;
        } catch (final Exception ex) {
            Support.getErrorLogger().logError(ex);
            return null;
        }
    }

    public static Effect loadEffect(final String file) {
        try {
            final XDataReader effectFile = new XDataReader(Support
                    .getSystemVariables().getBasePath()
                    + File.separator
                    + "effects"
                    + File.separator
                    + file
                    + Extension.getEffectExtensionWithPeriod(),
                    Extension.getEffectExtension());
            final Effect e = Effect.read(effectFile);
            e.setSystemObject(true);
            effectFile.close();
            return e;
        } catch (final Exception ex) {
            Support.getErrorLogger().logError(ex);
            return null;
        }
    }

    public static CombatUsableItem loadCombatItem(final String file) {
        try {
            final XDataReader itemFile = new XDataReader(Support
                    .getSystemVariables().getBasePath()
                    + File.separator
                    + "items"
                    + File.separator
                    + file
                    + Extension.getItemExtensionWithPeriod(),
                    Extension.getItemExtension());
            final CombatUsableItem i = CombatUsableItem.read(itemFile);
            i.setSystemObject(true);
            itemFile.close();
            return i;
        } catch (final Exception ex) {
            Support.getErrorLogger().logError(ex);
            return null;
        }
    }

    public static CombatUsableItem[] loadAllItems() {
        final File itemsFolder = new File(Support.getSystemVariables()
                .getBasePath() + File.separator + "items");
        final String[] fileList = itemsFolder.list(new ItemFilter());
        if (fileList != null && fileList.length > 0) {
            // Strip extension
            for (int x = 0; x < fileList.length; x++) {
                fileList[x] = fileList[x].split("\\.")[0];
            }
            final CombatUsableItem[] retVal = new CombatUsableItem[fileList.length];
            for (int x = 0; x < retVal.length; x++) {
                retVal[x] = SystemLoader.loadCombatItem(fileList[x]);
            }
            return retVal;
        } else {
            return null;
        }
    }

    public static Race loadRace(final String file) {
        try {
            final XDataReader raceFile = new XDataReader(Support
                    .getSystemVariables().getBasePath()
                    + File.separator
                    + "races"
                    + File.separator
                    + file
                    + Extension.getRaceExtensionWithPeriod(),
                    Extension.getRaceExtension());
            final Race r = Race.read(raceFile);
            r.setSystemObject(true);
            raceFile.close();
            return r;
        } catch (final Exception ex) {
            Support.getErrorLogger().logError(ex);
            return null;
        }
    }

    public static Spell loadSpell(final String file) {
        try {
            final XDataReader spellFile = new XDataReader(Support
                    .getSystemVariables().getBasePath()
                    + File.separator
                    + "spells"
                    + File.separator
                    + file
                    + Extension.getSpellExtensionWithPeriod(),
                    Extension.getSpellExtension());
            final Spell s = Spell.read(spellFile);
            s.setSystemObject(true);
            spellFile.close();
            return s;
        } catch (final Exception ex) {
            Support.getErrorLogger().logError(ex);
            return null;
        }
    }

    public static SpellBook loadSpellBook(final String file) {
        try {
            final XDataReader spellBookFile = new XDataReader(Support
                    .getSystemVariables().getBasePath()
                    + File.separator
                    + "spellbooks"
                    + File.separator
                    + file
                    + Extension.getSpellBookExtensionWithPeriod(),
                    Extension.getSpellBookExtension());
            final SpellBook sb = SpellBook.read(spellBookFile);
            sb.setSystemObject(true);
            spellBookFile.close();
            return sb;
        } catch (final Exception ex) {
            Support.getErrorLogger().logError(ex);
            return null;
        }
    }
}
