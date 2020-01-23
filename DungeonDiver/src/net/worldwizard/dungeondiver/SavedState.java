package net.worldwizard.dungeondiver;

import java.awt.FileDialog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import net.worldwizard.dungeondiver.creatures.ClassManager;
import net.worldwizard.dungeondiver.creatures.Player;

public class SavedState {
    private static final int CHECKSUM_RADIX_2 = 16;

    private static String getExtension(final File f) {
        String ext = null;
        final String s = f.getName();
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    private static String getExtension(final String s) {
        String ext = null;
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    private static String getNameWithoutExtension(final File f) {
        String ext = null;
        final String s = f.getAbsolutePath();
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(0, i - 1);
        }
        return ext;
    }

    private static String getNameWithoutExtension(final String s) {
        String ext = null;
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(0, i - 1);
        }
        return ext;
    }

    public boolean load() {
        boolean result = false;
        String filename, extension;
        if (System.getProperty("os.name").startsWith("Mac OS X")) {
            final FileDialog fd = new FileDialog(DungeonDiver.getHoldingBag()
                    .getGUIManager().getParentFrame(), "Load", FileDialog.LOAD);
            final SavedStateFilterMacOSX ss = new SavedStateFilterMacOSX();
            fd.setFilenameFilter(ss);
            fd.setVisible(true);
            if (fd.getFile() != null) {
                filename = fd.getDirectory() + fd.getFile();
                extension = SavedState.getExtension(filename);
                if (extension.equals(SavedStateIdentifier.getIdentifier())) {
                    result = SavedState.loadState(filename);
                }
            }
        } else {
            final JFileChooser fc = new JFileChooser();
            final SavedStateFilter ss = new SavedStateFilter();
            fc.setAcceptAllFileFilterUsed(false);
            fc.addChoosableFileFilter(ss);
            fc.setFileFilter(ss);
            final int returnVal = fc.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                final File file = fc.getSelectedFile();
                filename = file.getAbsolutePath();
                extension = SavedState.getExtension(file);
                if (extension.equals(SavedStateIdentifier.getIdentifier())) {
                    result = SavedState.loadState(filename);
                }
            }
        }
        return result;
    }

    private static boolean loadState(final String filename) {
        boolean result = false;
        try (ObjectInputStream stream = new ObjectInputStream(
                new FileInputStream(filename))) {
            Player p;
            final int pAttack = stream.readInt();
            final int pDefense = stream.readInt();
            final int pHP = stream.readInt();
            final int pMP = stream.readInt();
            final int newKills = stream.readInt();
            final int newLevel = stream.readInt();
            final int chp = stream.readInt();
            final int cmp = stream.readInt();
            final int newWeapon = stream.readInt();
            final int newArmor = stream.readInt();
            final int newGold = stream.readInt();
            final int newBank = stream.readInt();
            final long newExperience = stream.readLong();
            final int dl = stream.readInt();
            final int bookID = stream.readInt();
            final String checksumFile = (String) stream.readObject();
            stream.close();
            final Player temp = ClassManager.getNewPlayerInstance(bookID);
            temp.loadPlayer(pAttack, pDefense, pHP, pMP, newKills, newLevel,
                    chp, cmp, newWeapon, newArmor, newGold, newBank,
                    newExperience, dl, bookID);
            final String checksumData = SavedState.getChecksum(temp);
            if (!checksumFile.equals(checksumData)) {
                JOptionPane.showMessageDialog(null, "Cheat attempt detected.",
                        "Load State", JOptionPane.INFORMATION_MESSAGE);
            } else {
                p = ClassManager.getNewPlayerInstance(bookID);
                p.loadPlayer(pAttack, pDefense, pHP, pMP, newKills, newLevel,
                        chp, cmp, newWeapon, newArmor, newGold, newBank,
                        newExperience, dl, bookID);
                DungeonDiver.getHoldingBag().setPlayer(p);
                result = true;
            }
        } catch (final IOException ie) {
            JOptionPane.showMessageDialog(null, "State could not be loaded.",
                    "Load State", JOptionPane.INFORMATION_MESSAGE);
        } catch (final Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Unknown error reading state file.", "Load State",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        return result;
    }

    public void save() {
        String filename, extension;
        if (System.getProperty("os.name").startsWith("Mac OS X")) {
            final FileDialog fd = new FileDialog(DungeonDiver.getHoldingBag()
                    .getGUIManager().getParentFrame(), "Save", FileDialog.SAVE);
            final SavedStateFilterMacOSX ss = new SavedStateFilterMacOSX();
            fd.setFilenameFilter(ss);
            fd.setVisible(true);
            if (fd.getFile() != null) {
                filename = fd.getDirectory() + fd.getFile();
                extension = SavedState.getExtension(filename);
                if (extension != null) {
                    if (!extension.equals(SavedStateIdentifier.getIdentifier())) {
                        filename = SavedState.getNameWithoutExtension(filename)
                                + SavedStateIdentifier
                                        .getIdentifierWithPeriod();
                    }
                } else {
                    filename += SavedStateIdentifier.getIdentifierWithPeriod();
                }
                SavedState.saveState(filename);
            }
        } else {
            final JFileChooser fc = new JFileChooser();
            final SavedStateFilter ss = new SavedStateFilter();
            fc.setAcceptAllFileFilterUsed(false);
            fc.addChoosableFileFilter(ss);
            fc.setFileFilter(ss);
            final int returnVal = fc.showSaveDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                final File file = fc.getSelectedFile();
                extension = SavedState.getExtension(file);
                filename = file.getAbsolutePath();
                if (extension != null) {
                    if (!extension.equals(SavedStateIdentifier.getIdentifier())) {
                        filename = SavedState.getNameWithoutExtension(file)
                                + SavedStateIdentifier
                                        .getIdentifierWithPeriod();
                    }
                } else {
                    filename += SavedStateIdentifier.getIdentifierWithPeriod();
                }
                SavedState.saveState(filename);
            }
        }
    }

    private static void saveState(final String filename) {
        try (ObjectOutputStream stream = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            final Player p = DungeonDiver.getHoldingBag().getPlayer();
            stream.writeInt(p.getPermanentAttackPoints());
            stream.writeInt(p.getPermanentDefensePoints());
            stream.writeInt(p.getPermanentHPPoints());
            stream.writeInt(p.getPermanentMPPoints());
            stream.writeInt(p.getKills());
            stream.writeInt(p.getLevel());
            stream.writeInt(p.getCurrentHP());
            stream.writeInt(p.getCurrentMP());
            stream.writeInt(p.getWeaponPower());
            stream.writeInt(p.getArmorBlock());
            stream.writeInt(p.getGold());
            stream.writeInt(p.getGoldInBank());
            stream.writeLong(p.getExperience());
            stream.writeInt(p.getDungeonLevel());
            stream.writeInt(p.getSpellBook().getID());
            stream.writeObject(SavedState.getChecksum(p));
            stream.close();
        } catch (final IOException ie) {
            JOptionPane.showMessageDialog(null, "State could not be saved.",
                    "Save State", JOptionPane.INFORMATION_MESSAGE);
        } catch (final Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Unknown error writing state file.", "Save State",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static String getChecksum(final Player p) {
        final int pAttack = p.getPermanentAttackPoints();
        final int pDefense = p.getPermanentDefensePoints();
        final int pHP = p.getPermanentHPPoints();
        final int pMP = p.getPermanentMPPoints();
        final int newKills = p.getKills();
        final int newLevel = p.getLevel();
        final int chp = p.getCurrentHP();
        final int cmp = p.getCurrentMP();
        final int newWeapon = p.getWeaponPower();
        final int newArmor = p.getArmorBlock();
        final int newGold = p.getGold();
        final int newBank = p.getGoldInBank();
        final long newExperience = p.getExperience();
        final int dl = p.getDungeonLevel();
        final int bookID = p.getSpellBook().getID();
        final String checksum = Long.toString(dl + 2 * (newExperience + 1) + 3
                * ((long) newBank + 1) + 4 * ((long) newGold + 1) + 5
                * ((long) newArmor + 1) + 6 * ((long) newWeapon + 1) + 7
                * ((long) chp + 1) + 8 * ((long) cmp + 1) + 9
                * ((long) newLevel + 1) + 10 * ((long) newKills + 1) + 11
                * ((long) pMP + 1) + 12 * ((long) pHP + 1) + 13
                * ((long) pDefense + 1) + 14 * ((long) pAttack + 1) + 15
                * ((long) bookID + 1), SavedState.CHECKSUM_RADIX_2);
        return checksum;
    }
}
