package net.worldwizard.worldz.editor;

import javax.swing.JOptionPane;

import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.generic.WorldObject;
import net.worldwizard.worldz.scripts.ActionCode;
import net.worldwizard.worldz.scripts.Script;
import net.worldwizard.worldz.scripts.ScriptEntry;
import net.worldwizard.worldz.scripts.ScriptEntryArgument;
import net.worldwizard.worldz.world.WorldConstants;

public class ScriptEditor {
    // Constants
    private static final String[] ACTION_CODES = new String[] { "Do Nothing",
            "Show Message", "Play Sound", "Move Player", "End Game",
            "Modify Object", "Delete Script", "Random Chance", "Battle" };
    private static final String[] ACTION_STRINGS = new String[] {
            ActionCode.NONE.toString(), ActionCode.MESSAGE.toString(),
            ActionCode.SOUND.toString(), ActionCode.SHOP.toString(),
            ActionCode.END_GAME.toString(), ActionCode.MODIFY.toString(),
            ActionCode.DELETE_SCRIPT.toString(),
            ActionCode.RANDOM_CHANCE.toString(), ActionCode.BATTLE.toString() };

    // Constructors
    private ScriptEditor() {
        // Do nothing
    }

    // Methods
    public static void editScript(final int x, final int y, final int z) {
        final Application app = Worldz.getApplication();
        try {
            final String[] choices = ScriptEditor.ACTION_CODES;
            String defaultChoice = choices[0];
            final WorldObject mo = app.getWorldManager().getWorld()
                    .getCell(x, y, z, WorldConstants.LAYER_OBJECT);
            if (mo.hasCustomScript()) {
                defaultChoice = choices[mo.getCustomScript().getAction(0)
                        .getActionCode().ordinal()];
            }
            final Script scpt = new Script();
            boolean addAnother = true;
            while (addAnother) {
                final String action = Messager.showInputDialog(
                        "Action to Perform", "Script Editor", choices,
                        defaultChoice);
                if (action != null) {
                    final ScriptEntry se = new ScriptEntry();
                    int index = -1;
                    for (int u = 0; u < choices.length; u++) {
                        if (choices[u].equals(action)) {
                            index = u;
                            break;
                        }
                    }
                    final String code = ScriptEditor.ACTION_STRINGS[index];
                    se.setActionCode(ActionCode.valueOf(code));
                    String arg = "";
                    int counter = 1;
                    while (arg != null) {
                        arg = Messager.showTextInputDialog("Argument "
                                + counter + " for action (ESC when done)",
                                "Script Editor");
                        counter++;
                        if (arg != null) {
                            double dVal = 0;
                            int iVal = 0;
                            boolean bVal = false;
                            try {
                                // Try integer
                                iVal = Integer.parseInt(arg);
                                se.addActionArg(new ScriptEntryArgument(iVal));
                            } catch (final Exception e1) {
                                try {
                                    // Try double
                                    dVal = Double.parseDouble(arg);
                                    se.addActionArg(new ScriptEntryArgument(
                                            dVal));
                                } catch (final Exception e2) {
                                    if (arg.equalsIgnoreCase("true")
                                            || arg.equalsIgnoreCase("false")) {
                                        // Boolean
                                        bVal = Boolean.parseBoolean(arg);
                                        se.addActionArg(new ScriptEntryArgument(
                                                bVal));
                                    } else {
                                        // String
                                        se.addActionArg(new ScriptEntryArgument(
                                                arg));
                                    }
                                }
                            }
                        } else {
                            counter--;
                        }
                    }
                    if (counter != 0) {
                        se.finalizeActionArgs();
                    }
                    scpt.addAction(se);
                    final int response = Messager.showConfirmDialog(
                            "Add another action?", "Script Editor");
                    if (response != JOptionPane.YES_OPTION) {
                        addAnother = false;
                    }
                } else {
                    addAnother = false;
                }
            }
            scpt.finalizeActions();
            mo.setCustomScript(scpt);
            app.getWorldManager().setDirty(true);
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            // Do nothing
        }
    }

    public static void probeScript(final int x, final int y, final int z) {
        final Application app = Worldz.getApplication();
        final WorldObject mo = app.getWorldManager().getWorld()
                .getCell(x, y, z, WorldConstants.LAYER_OBJECT);
        if (mo.hasCustomScript()) {
            final Script scpt = mo.getCustomScript();
            final int actionCount = scpt.getActionCount();
            Messager.showMessage(mo.getName()
                    + " has a script attached to it, with " + actionCount
                    + " action(s) in it.");
        } else {
            Messager.showMessage(mo.getName()
                    + " does NOT have a script attached to it.");
        }
    }

    public static void deleteScript(final int x, final int y, final int z) {
        final Application app = Worldz.getApplication();
        final WorldObject mo = app.getWorldManager().getWorld()
                .getCell(x, y, z, WorldConstants.LAYER_OBJECT);
        if (mo.hasCustomScript()) {
            mo.setCustomScript(null);
        }
        Messager.showMessage("Script deleted.");
        app.getWorldManager().setDirty(true);
    }
}
