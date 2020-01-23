package net.worldwizard.mazerunner;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class MazeMaker {
    // Declarations
    private static JFrame outputFrame;
    private static Container outputPane, secondaryPane, borderPane;
    private static GridBagLayout gridbag;
    private static GridBagConstraints c;
    private static JScrollBar vertScroll, horzScroll;
    private static MazeMakerEventHandler mhandler;
    private static MazeTeleporterEventHandler thandler;
    private static int MAX_VIEWING_WINDOW_X;
    private static int MAX_VIEWING_WINDOW_Y;
    private static int editorLocationZ, editorLocationW, editorLocationX,
            editorLocationY, editorCameFromZ, editorCameFromW;
    private static int TELEPORTER_TYPE;
    private static MazeRunner app;
    private static UndoRedoEngine engine;
    // Public constants
    public static final int TELEPORTER_TYPE_GENERIC = 0;
    public static final int TELEPORTER_TYPE_INVISIBLE_GENERIC = 1;
    public static final int TELEPORTER_TYPE_RANDOM = 2;
    public static final int TELEPORTER_TYPE_RANDOM_INVISIBLE = 3;
    public static final int TELEPORTER_TYPE_ONESHOT = 4;
    public static final int TELEPORTER_TYPE_INVISIBLE_ONESHOT = 5;
    public static final int TELEPORTER_TYPE_RANDOM_ONESHOT = 6;
    public static final int TELEPORTER_TYPE_RANDOM_INVISIBLE_ONESHOT = 7;
    public static final int TELEPORTER_TYPE_TWOWAY = 8;
    public static final int STAIRS_UP = 0;
    public static final int STAIRS_DOWN = 1;

    public static void setMaxViewingWindow(final int maxX, final int maxY) {
        MazeMaker.MAX_VIEWING_WINDOW_X = maxX;
        MazeMaker.MAX_VIEWING_WINDOW_Y = maxY;
    }

    public static void updateEditorPosition(final int x, final int y,
            final int z, final int w) {
        MazeMaker.app.setViewingWindowLocation(false,
                MazeMaker.app.getViewingWindowLocation(false) + x);
        MazeMaker.app.setViewingWindowLocation(true,
                MazeMaker.app.getViewingWindowLocation(true) + y);
        MazeMaker.app.setLowerViewingWindowLocation(false,
                MazeMaker.app.getLowerViewingWindowLocation(false) + x);
        MazeMaker.app.setLowerViewingWindowLocation(true,
                MazeMaker.app.getLowerViewingWindowLocation(true) + y);
        MazeMaker.editorLocationZ += z;
        MazeMaker.editorLocationW += w;
        if (MazeMaker.app.getViewingWindowLocation(false) < MazeRunner.MIN_VIEWING_WINDOW_X) {
            MazeMaker.app.setViewingWindowLocation(false,
                    MazeRunner.MIN_VIEWING_WINDOW_X);
            MazeMaker.app.setLowerViewingWindowLocation(false,
                    MazeRunner.MIN_VIEWING_WINDOW_X
                            + MazeRunner.VIEWING_WINDOW_SIZE_X - 1);
        }
        if (MazeMaker.app.getViewingWindowLocation(true) < MazeRunner.MIN_VIEWING_WINDOW_Y) {
            MazeMaker.app.setViewingWindowLocation(true,
                    MazeRunner.MIN_VIEWING_WINDOW_Y);
            MazeMaker.app.setLowerViewingWindowLocation(true,
                    MazeRunner.MIN_VIEWING_WINDOW_Y
                            + MazeRunner.VIEWING_WINDOW_SIZE_Y - 1);
        }
        if (MazeMaker.app.getLowerViewingWindowLocation(false) > MazeMaker.MAX_VIEWING_WINDOW_X) {
            MazeMaker.app.setLowerViewingWindowLocation(false,
                    MazeMaker.MAX_VIEWING_WINDOW_X);
            MazeMaker.app.setViewingWindowLocation(false,
                    MazeMaker.MAX_VIEWING_WINDOW_X
                            - MazeRunner.VIEWING_WINDOW_SIZE_X + 1);
        }
        if (MazeMaker.app.getLowerViewingWindowLocation(true) > MazeMaker.MAX_VIEWING_WINDOW_Y) {
            MazeMaker.app.setLowerViewingWindowLocation(true,
                    MazeMaker.MAX_VIEWING_WINDOW_Y);
            MazeMaker.app.setViewingWindowLocation(true,
                    MazeMaker.MAX_VIEWING_WINDOW_Y
                            - MazeRunner.VIEWING_WINDOW_SIZE_Y + 1);
        }
        if (MazeMaker.editorLocationZ < 0) {
            MazeMaker.editorLocationZ = 0;
            Messager.showMessage("No more floors below");
        }
        if (MazeMaker.editorLocationZ >= MazeMaker.app.getMaze().getFloors()) {
            MazeMaker.editorLocationZ = MazeMaker.app.getMaze().getFloors() - 1;
            Messager.showMessage("No more floors above");
        }
        if (MazeMaker.editorLocationW < 0) {
            MazeMaker.editorLocationW = 0;
            Messager.showMessage("No more levels below");
        }
        if (MazeMaker.editorLocationW >= MazeMaker.app.getMaze().getLevels()) {
            MazeMaker.editorLocationW = MazeMaker.app.getMaze().getLevels() - 1;
            Messager.showMessage("No more levels above");
        }
        MazeMaker.redrawEditor();
    }

    public static void redrawEditor() {
        // Draw the maze in edit mode
        int x, y;
        MazeMaker.borderPane.removeAll();
        MazeMaker.secondaryPane.removeAll();
        for (x = MazeMaker.app.getViewingWindowLocation(false); x <= MazeMaker.app
                .getLowerViewingWindowLocation(false); x++) {
            for (y = MazeMaker.app.getViewingWindowLocation(true); y <= MazeMaker.app
                    .getLowerViewingWindowLocation(true); y++) {
                try {
                    MazeMaker.secondaryPane.add(new JLabel("", MazeMaker.app
                            .getMaze()
                            .getCell(y, x, MazeMaker.editorLocationZ,
                                    MazeMaker.editorLocationW)
                            .getEditorAppearance(), SwingConstants.CENTER));
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    MazeMaker.secondaryPane.add(new JLabel("", ImageSetManager
                            .getImage(MazeObject.getSet(),
                                    MazeObject.getSize(), "Void"),
                            SwingConstants.CENTER));
                }
            }
        }
        MazeMaker.borderPane.add(MazeMaker.outputPane, BorderLayout.CENTER);
        MazeMaker.borderPane.add(MazeMaker.app.getMessageLabel(),
                BorderLayout.SOUTH);
        MazeMaker.outputFrame.pack();
        MazeMaker.outputFrame.setTitle("Maze Maker - Floor "
                + (MazeMaker.editorLocationZ + 1) + " Level "
                + (MazeMaker.editorLocationW + 1));
        MazeMaker.showOutput();
    }

    public static void editObject(final int x, final int y) {
        int z = 0;
        MazeObject mo = null;
        final int xOffset = MazeMaker.vertScroll.getValue()
                - MazeMaker.vertScroll.getMinimum();
        final int yOffset = MazeMaker.horzScroll.getValue()
                - MazeMaker.horzScroll.getMinimum();
        final int gridX = x / MazeObject.getSize()
                + MazeMaker.app.getViewingWindowLocation(false) - xOffset
                + yOffset;
        final int gridY = y / MazeObject.getSize()
                + MazeMaker.app.getViewingWindowLocation(true) + xOffset
                - yOffset;
        try {
            MazeMaker.app.setSavedMazeObject(MazeMaker.app.getMaze().getCell(
                    gridX, gridY, MazeMaker.editorLocationZ,
                    MazeMaker.editorLocationW));
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return;
        }
        final MazeObjectList list = new MazeObjectList();
        final MazeObject[] choices = list.getAllObjects();
        final Object[] userChoices = list.getAllNames();
        final String result = Messager.showInputDialog("Place which object?",
                "Maze Maker", userChoices, new MazeGround().getName());
        if (result != null && result.length() > 0) {
            for (z = 0; z < userChoices.length; z++) {
                if (result.equals(userChoices[z])) {
                    MazeMaker.editorLocationX = gridX;
                    MazeMaker.editorLocationY = gridY;
                    mo = choices[z].editorHook();
                    break;
                }
            }
            try {
                MazeMaker.checkTwoWayTeleporterPair(MazeMaker.editorLocationZ,
                        MazeMaker.editorLocationW);
                MazeMaker.updateUndoHistory(MazeMaker.app.getSavedMazeObject(),
                        gridX, gridY, MazeMaker.editorLocationZ,
                        MazeMaker.editorLocationW);
                MazeMaker.app.getMaze().setCell(mo, gridX, gridY,
                        MazeMaker.editorLocationZ, MazeMaker.editorLocationW);
                MazeMaker.checkStairPair(MazeMaker.editorLocationZ,
                        MazeMaker.editorLocationW);
                MazeMaker.app.setDirty(true);
                MazeMaker.redrawEditor();
            } catch (final NullPointerException np) {
                MazeMaker.app.getMaze().setCell(
                        MazeMaker.app.getSavedMazeObject(), gridX, gridY,
                        MazeMaker.editorLocationZ, MazeMaker.editorLocationW);
                MazeMaker.redrawEditor();
            }
        }
    }

    private static void checkStairPair(final int z, final int w) {
        final MazeObject mo1 = MazeMaker.app.getMaze().getCell(
                MazeMaker.editorLocationX, MazeMaker.editorLocationY, z, w);
        final String name1 = mo1.getName();
        String name2, name3;
        try {
            final MazeObject mo2 = MazeMaker.app.getMaze().getCell(
                    MazeMaker.editorLocationX, MazeMaker.editorLocationY,
                    z + 1, w);
            name2 = mo2.getName();
        } catch (final ArrayIndexOutOfBoundsException e) {
            name2 = "";
        }
        try {
            final MazeObject mo3 = MazeMaker.app.getMaze().getCell(
                    MazeMaker.editorLocationX, MazeMaker.editorLocationY,
                    z - 1, w);
            name3 = mo3.getName();
        } catch (final ArrayIndexOutOfBoundsException e) {
            name3 = "";
        }
        if (!name1.equals("Stairs Up")) {
            if (name2.equals("Stairs Down")) {
                MazeMaker.unpairStairs(MazeMaker.STAIRS_UP, z, w);
            } else if (!name1.equals("Stairs Down")) {
                if (name3.equals("Stairs Up")) {
                    MazeMaker.unpairStairs(MazeMaker.STAIRS_DOWN, z, w);
                }
            }
        }
    }

    private static void reverseCheckStairPair(final int z, final int w) {
        final MazeObject mo1 = MazeMaker.app.getMaze().getCell(
                MazeMaker.editorLocationX, MazeMaker.editorLocationY, z, w);
        final String name1 = mo1.getName();
        String name2, name3;
        try {
            final MazeObject mo2 = MazeMaker.app.getMaze().getCell(
                    MazeMaker.editorLocationX, MazeMaker.editorLocationY,
                    z + 1, w);
            name2 = mo2.getName();
        } catch (final ArrayIndexOutOfBoundsException e) {
            name2 = "";
        }
        try {
            final MazeObject mo3 = MazeMaker.app.getMaze().getCell(
                    MazeMaker.editorLocationX, MazeMaker.editorLocationY,
                    z - 1, w);
            name3 = mo3.getName();
        } catch (final ArrayIndexOutOfBoundsException e) {
            name3 = "";
        }
        if (name1.equals("Stairs Up")) {
            if (!name2.equals("Stairs Down")) {
                MazeMaker.pairStairs(MazeMaker.STAIRS_UP, z, w);
            } else if (name1.equals("Stairs Down")) {
                if (!name3.equals("Stairs Up")) {
                    MazeMaker.pairStairs(MazeMaker.STAIRS_DOWN, z, w);
                }
            }
        }
    }

    public static void pairStairs(final int type) {
        switch (type) {
        case STAIRS_UP:
            try {
                MazeMaker.app.getMaze().setCell(new MazeStairsDown(),
                        MazeMaker.editorLocationX, MazeMaker.editorLocationY,
                        MazeMaker.editorLocationZ + 1,
                        MazeMaker.editorLocationW);
            } catch (final ArrayIndexOutOfBoundsException e) {
                // Do nothing
            }
            break;
        case STAIRS_DOWN:
            try {
                MazeMaker.app.getMaze().setCell(new MazeStairsUp(),
                        MazeMaker.editorLocationX, MazeMaker.editorLocationY,
                        MazeMaker.editorLocationZ - 1,
                        MazeMaker.editorLocationW);
            } catch (final ArrayIndexOutOfBoundsException e) {
                // Do nothing
            }
            break;
        default:
            break;
        }
    }

    private static void pairStairs(final int type, final int z, final int w) {
        switch (type) {
        case STAIRS_UP:
            try {
                MazeMaker.app.getMaze().setCell(new MazeStairsDown(),
                        MazeMaker.editorLocationX, MazeMaker.editorLocationY,
                        z + 1, w);
            } catch (final ArrayIndexOutOfBoundsException e) {
                // Do nothing
            }
            break;
        case STAIRS_DOWN:
            try {
                MazeMaker.app.getMaze().setCell(new MazeStairsUp(),
                        MazeMaker.editorLocationX, MazeMaker.editorLocationY,
                        z - 1, w);
            } catch (final ArrayIndexOutOfBoundsException e) {
                // Do nothing
            }
            break;
        default:
            break;
        }
    }

    private static void unpairStairs(final int type, final int z, final int w) {
        switch (type) {
        case STAIRS_UP:
            try {
                MazeMaker.app.getMaze().setCell(new MazeGround(),
                        MazeMaker.editorLocationX, MazeMaker.editorLocationY,
                        z + 1, w);
            } catch (final ArrayIndexOutOfBoundsException e) {
                // Do nothing
            }
            break;
        case STAIRS_DOWN:
            try {
                MazeMaker.app.getMaze().setCell(new MazeGround(),
                        MazeMaker.editorLocationX, MazeMaker.editorLocationY,
                        z - 1, w);
            } catch (final ArrayIndexOutOfBoundsException e) {
                // Do nothing
            }
            break;
        default:
            break;
        }
    }

    private static void checkTwoWayTeleporterPair(final int z, final int w) {
        final MazeObject mo1 = MazeMaker.app.getMaze().getCell(
                MazeMaker.editorLocationX, MazeMaker.editorLocationY, z, w);
        final String name1 = mo1.getName();
        String name2;
        int destX, destY, destZ, destW;
        if (name1.equals("Two-Way Teleporter")) {
            final MazeTwoWayTeleporter twt = (MazeTwoWayTeleporter) mo1;
            destX = twt.getDestinationRow();
            destY = twt.getDestinationColumn();
            destZ = twt.getDestinationFloor();
            destW = twt.getDestinationLevel();
            final MazeObject mo2 = MazeMaker.app.getMaze().getCell(destX,
                    destY, destZ, destW);
            name2 = mo2.getName();
            if (name2.equals("Two-Way Teleporter")) {
                MazeMaker.unpairTwoWayTeleporter(destX, destY, destZ, destW);
            }
        }
    }

    private static void reverseCheckTwoWayTeleporterPair(final int z,
            final int w) {
        final MazeObject mo1 = MazeMaker.app.getMaze().getCell(
                MazeMaker.editorLocationX, MazeMaker.editorLocationY, z, w);
        final String name1 = mo1.getName();
        String name2;
        int destX, destY, destZ, destW;
        if (name1.equals("Two-Way Teleporter")) {
            final MazeTwoWayTeleporter twt = (MazeTwoWayTeleporter) mo1;
            destX = twt.getDestinationRow();
            destY = twt.getDestinationColumn();
            destZ = twt.getDestinationFloor();
            destW = twt.getDestinationLevel();
            final MazeObject mo2 = MazeMaker.app.getMaze().getCell(destX,
                    destY, destZ, destW);
            name2 = mo2.getName();
            if (!name2.equals("Two-Way Teleporter")) {
                MazeMaker.pairTwoWayTeleporter(destX, destY, destZ, destW);
            }
        }
    }

    public static void pairTwoWayTeleporter(final int destX, final int destY,
            final int destZ, final int destW) {
        MazeMaker.app.getMaze().setCell(
                new MazeTwoWayTeleporter(MazeMaker.editorLocationX,
                        MazeMaker.editorLocationY, MazeMaker.editorCameFromZ,
                        MazeMaker.editorCameFromW), destX, destY, destZ, destW);
    }

    private static void unpairTwoWayTeleporter(final int destX,
            final int destY, final int destZ, final int destW) {
        MazeMaker.app.getMaze().setCell(new MazeGround(), destX, destY, destZ,
                destW);
    }

    public static MazeObject editTeleporterDestination(final int type) {
        String input1 = null, input2 = null;
        MazeMaker.TELEPORTER_TYPE = type;
        int destX = 0, destY = 0;
        switch (type) {
        case TELEPORTER_TYPE_GENERIC:
        case TELEPORTER_TYPE_INVISIBLE_GENERIC:
        case TELEPORTER_TYPE_ONESHOT:
        case TELEPORTER_TYPE_INVISIBLE_ONESHOT:
        case TELEPORTER_TYPE_TWOWAY:
            Messager.showMessage("Click to set teleporter destination");
            break;
        case TELEPORTER_TYPE_RANDOM:
        case TELEPORTER_TYPE_RANDOM_INVISIBLE:
        case TELEPORTER_TYPE_RANDOM_ONESHOT:
        case TELEPORTER_TYPE_RANDOM_INVISIBLE_ONESHOT:
            input1 = JOptionPane.showInputDialog(MazeMaker.outputFrame,
                    "Random row range:", "Maze Maker",
                    JOptionPane.OK_CANCEL_OPTION);
            break;
        default:
            break;
        }
        if (input1 != null) {
            switch (type) {
            case TELEPORTER_TYPE_RANDOM:
            case TELEPORTER_TYPE_RANDOM_INVISIBLE:
            case TELEPORTER_TYPE_RANDOM_ONESHOT:
            case TELEPORTER_TYPE_RANDOM_INVISIBLE_ONESHOT:
                input2 = JOptionPane.showInputDialog(MazeMaker.outputFrame,
                        "Random column range:", "Maze Maker",
                        JOptionPane.OK_CANCEL_OPTION);
                break;
            default:
                break;
            }
            if (input2 != null) {
                try {
                    destX = Integer.parseInt(input1);
                    destY = Integer.parseInt(input2);
                } catch (final NumberFormatException nf) {
                    Messager.showDialog("Row and column ranges must be integers.");
                }
                switch (type) {
                case TELEPORTER_TYPE_RANDOM:
                    return new MazeRandomTeleporter(destX, destY);
                case TELEPORTER_TYPE_RANDOM_INVISIBLE:
                    return new MazeRandomInvisibleTeleporter(destX, destY);
                case TELEPORTER_TYPE_RANDOM_ONESHOT:
                    return new MazeRandomOneShotTeleporter(destX, destY);
                case TELEPORTER_TYPE_RANDOM_INVISIBLE_ONESHOT:
                    return new MazeRandomInvisibleOneShotTeleporter(destX,
                            destY);
                default:
                    break;
                }
            }
        } else {
            switch (type) {
            case TELEPORTER_TYPE_GENERIC:
            case TELEPORTER_TYPE_INVISIBLE_GENERIC:
            case TELEPORTER_TYPE_ONESHOT:
            case TELEPORTER_TYPE_INVISIBLE_ONESHOT:
            case TELEPORTER_TYPE_TWOWAY:
                MazeMaker.horzScroll
                        .removeAdjustmentListener(MazeMaker.mhandler);
                MazeMaker.vertScroll
                        .removeAdjustmentListener(MazeMaker.mhandler);
                MazeMaker.secondaryPane.removeMouseListener(MazeMaker.mhandler);
                MazeMaker.outputFrame.removeKeyListener(MazeMaker.mhandler);
                MazeMaker.horzScroll.addAdjustmentListener(MazeMaker.thandler);
                MazeMaker.vertScroll.addAdjustmentListener(MazeMaker.thandler);
                MazeMaker.secondaryPane.addMouseListener(MazeMaker.thandler);
                MazeMaker.outputFrame.addKeyListener(MazeMaker.thandler);
                MazeMaker.editorCameFromZ = MazeMaker.editorLocationZ;
                MazeMaker.editorCameFromW = MazeMaker.editorLocationW;
                break;
            default:
                break;
            }
        }
        return null;
    }

    public static MazeObject editFinishToDestination() {
        String input1 = null;
        int destW = 0;
        input1 = JOptionPane.showInputDialog(MazeMaker.outputFrame,
                "Destination Level:", "Maze Maker",
                JOptionPane.OK_CANCEL_OPTION);
        if (input1 != null) {
            try {
                destW = Integer.parseInt(input1) - 1;
                return new MazeFinishTo(destW);
            } catch (final NumberFormatException nf) {
                Messager.showDialog("Destination level must be an integer greater than 0.");
            }
        }
        return null;
    }

    public static void setTeleporterDestination(final int x, final int y) {
        final int xOffset = MazeMaker.vertScroll.getValue()
                - MazeMaker.vertScroll.getMinimum();
        final int yOffset = MazeMaker.horzScroll.getValue()
                - MazeMaker.horzScroll.getMinimum();
        final int destX = x / MazeObject.getSize()
                + MazeMaker.app.getViewingWindowLocation(false) - xOffset
                + yOffset;
        final int destY = y / MazeObject.getSize()
                + MazeMaker.app.getViewingWindowLocation(true) + xOffset
                - yOffset;
        final int destZ = MazeMaker.editorLocationZ;
        final int destW = MazeMaker.editorLocationW;
        try {
            MazeMaker.app.getMaze().getCell(destX, destY, destZ, destW);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            MazeMaker.horzScroll.removeAdjustmentListener(MazeMaker.thandler);
            MazeMaker.vertScroll.removeAdjustmentListener(MazeMaker.thandler);
            MazeMaker.secondaryPane.removeMouseListener(MazeMaker.thandler);
            MazeMaker.outputFrame.removeKeyListener(MazeMaker.thandler);
            MazeMaker.horzScroll.addAdjustmentListener(MazeMaker.mhandler);
            MazeMaker.vertScroll.addAdjustmentListener(MazeMaker.mhandler);
            MazeMaker.secondaryPane.addMouseListener(MazeMaker.mhandler);
            MazeMaker.outputFrame.addKeyListener(MazeMaker.mhandler);
            return;
        }
        switch (MazeMaker.TELEPORTER_TYPE) {
        case TELEPORTER_TYPE_GENERIC:
            MazeMaker.app.getMaze().setCell(
                    new MazeTeleporter(destX, destY, destZ, destW),
                    MazeMaker.editorLocationX, MazeMaker.editorLocationY,
                    MazeMaker.editorCameFromZ, MazeMaker.editorCameFromW);
            break;
        case TELEPORTER_TYPE_INVISIBLE_GENERIC:
            MazeMaker.app.getMaze().setCell(
                    new MazeInvisibleTeleporter(destX, destY, destZ, destW),
                    MazeMaker.editorLocationX, MazeMaker.editorLocationY,
                    MazeMaker.editorCameFromZ, MazeMaker.editorCameFromW);
            break;
        case TELEPORTER_TYPE_ONESHOT:
            MazeMaker.app.getMaze().setCell(
                    new MazeOneShotTeleporter(destX, destY, destZ, destW),
                    MazeMaker.editorLocationX, MazeMaker.editorLocationY,
                    MazeMaker.editorCameFromZ, MazeMaker.editorCameFromW);
            break;
        case TELEPORTER_TYPE_INVISIBLE_ONESHOT:
            MazeMaker.app.getMaze().setCell(
                    new MazeInvisibleOneShotTeleporter(destX, destY, destZ,
                            destW), MazeMaker.editorLocationX,
                    MazeMaker.editorLocationY, MazeMaker.editorCameFromZ,
                    MazeMaker.editorCameFromW);
            break;
        case TELEPORTER_TYPE_TWOWAY:
            MazeMaker.app.getMaze().setCell(
                    new MazeTwoWayTeleporter(destX, destY, destZ, destW),
                    MazeMaker.editorLocationX, MazeMaker.editorLocationY,
                    MazeMaker.editorCameFromZ, MazeMaker.editorCameFromW);
            MazeMaker.pairTwoWayTeleporter(destX, destY, destZ, destW);
            break;
        default:
            break;
        }
        MazeMaker.horzScroll.removeAdjustmentListener(MazeMaker.thandler);
        MazeMaker.vertScroll.removeAdjustmentListener(MazeMaker.thandler);
        MazeMaker.secondaryPane.removeMouseListener(MazeMaker.thandler);
        MazeMaker.outputFrame.removeKeyListener(MazeMaker.thandler);
        MazeMaker.horzScroll.addAdjustmentListener(MazeMaker.mhandler);
        MazeMaker.vertScroll.addAdjustmentListener(MazeMaker.mhandler);
        MazeMaker.secondaryPane.addMouseListener(MazeMaker.mhandler);
        MazeMaker.outputFrame.addKeyListener(MazeMaker.mhandler);
        Messager.showMessage("Destination set.");
        MazeMaker.redrawEditor();
    }

    public static void setPlayerLocation() {
        MazeMaker.app.setPlayerLocation(true, true, MazeMaker.editorLocationW);
        MazeMaker.app.setPlayerLocation(true, false, MazeMaker.editorLocationZ);
        MazeMaker.app.setPlayerLocation(false, true, MazeMaker.editorLocationY);
        MazeMaker.app
                .setPlayerLocation(false, false, MazeMaker.editorLocationX);
    }

    public static boolean saveTextMaze(final String filename) {
        int x = 0, y = 0, z = 0, w = 0;
        boolean success = true;
        try {
            try (PrintWriter mazeFile = new PrintWriter(new BufferedWriter(
                    new FileWriter(filename)))) {
                mazeFile.print(MazeMaker.app.getMaze().getColumns());
                mazeFile.print("\n");
                mazeFile.print(MazeMaker.app.getMaze().getRows());
                mazeFile.print("\n");
                mazeFile.print(MazeMaker.app.getMaze().getFloors());
                mazeFile.print("\n");
                mazeFile.print(MazeMaker.app.getMaze().getLevels());
                mazeFile.print("\n");
                for (w = 0; w < MazeMaker.app.getMaze().getLevels(); w++) {
                    for (z = 0; z < MazeMaker.app.getMaze().getFloors(); z++) {
                        for (y = 0; y < MazeMaker.app.getMaze().getRows(); y++) {
                            for (x = 0; x < MazeMaker.app.getMaze()
                                    .getColumns(); x++) {
                                mazeFile.print(MazeMaker.app.getMaze()
                                        .getCell(x, y, z, w).toString());
                                mazeFile.print("\n");
                            }
                        }
                    }
                }
                if (mazeFile.checkError()) {
                    success = false;
                }
            } catch (final IOException ie) {
                throw new InvalidMazeException(
                        "Maze file couldn't be written to.");
            }
        } catch (final InvalidMazeException ime) {
            if (MazeMaker.app
                    .getMessageEnabled(MazeRunner.MESSAGE_SPECIFIC_MAZE_ERRORS)) {
                Messager.showDialog(ime.getMessage());
            } else {
                Messager.showDialog("Maze could not be saved.");
            }
            success = false;
        } catch (final Exception ex) {
            Messager.showDialog("Unknown error writing maze file.");
            success = false;
        }
        return success;
    }

    public static void editMaze() {
        if (MazeMaker.app.getLoaded()) {
            try {
                MazeMaker.app.hideMenu();
                MazeMaker.app.setInGame(false);
                MazeMaker.editorLocationZ = 0;
                MazeMaker.editorLocationW = 0;
                MazeMaker.outputFrame = new JFrame("Maze Maker");
                MazeMaker.outputPane = new Container();
                MazeMaker.secondaryPane = new Container();
                MazeMaker.borderPane = new Container();
                MazeMaker.borderPane.setLayout(new BorderLayout());
                MazeMaker.outputFrame.setContentPane(MazeMaker.borderPane);
                MazeMaker.outputFrame
                        .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                MazeMaker.borderPane.add(MazeMaker.outputPane,
                        BorderLayout.CENTER);
                MazeMaker.borderPane.add(MazeMaker.app.getMessageLabel(),
                        BorderLayout.SOUTH);
                MazeMaker.gridbag = new GridBagLayout();
                MazeMaker.c = new GridBagConstraints();
                MazeMaker.outputPane.setLayout(MazeMaker.gridbag);
                MazeMaker.outputFrame.setResizable(false);
                MazeMaker.outputFrame.setName("OutputFrame");
                MazeMaker.outputPane.setName("OutputPane");
                MazeMaker.secondaryPane.setName("SecondaryPane");
                MazeMaker.c.fill = GridBagConstraints.BOTH;
                MazeMaker.secondaryPane.setLayout(new GridLayout(
                        MazeRunner.VIEWING_WINDOW_SIZE_X,
                        MazeRunner.VIEWING_WINDOW_SIZE_Y));
                MazeMaker.horzScroll = new JScrollBar(Adjustable.HORIZONTAL,
                        MazeRunner.MIN_VIEWING_WINDOW_Y,
                        MazeRunner.VIEWING_WINDOW_SIZE_Y,
                        MazeRunner.MIN_VIEWING_WINDOW_Y,
                        MazeMaker.MAX_VIEWING_WINDOW_Y);
                MazeMaker.vertScroll = new JScrollBar(Adjustable.VERTICAL,
                        MazeRunner.MIN_VIEWING_WINDOW_X,
                        MazeRunner.VIEWING_WINDOW_SIZE_X,
                        MazeRunner.MIN_VIEWING_WINDOW_X,
                        MazeMaker.MAX_VIEWING_WINDOW_X);
                MazeMaker.c.gridx = 0;
                MazeMaker.c.gridy = 0;
                MazeMaker.gridbag.setConstraints(MazeMaker.secondaryPane,
                        MazeMaker.c);
                MazeMaker.outputPane.add(MazeMaker.secondaryPane);
                MazeMaker.c.gridx = 1;
                MazeMaker.c.gridy = 0;
                MazeMaker.c.gridwidth = GridBagConstraints.REMAINDER;
                MazeMaker.gridbag.setConstraints(MazeMaker.vertScroll,
                        MazeMaker.c);
                MazeMaker.outputPane.add(MazeMaker.vertScroll);
                MazeMaker.c.gridx = 0;
                MazeMaker.c.gridy = 1;
                MazeMaker.c.gridwidth = 1;
                MazeMaker.c.gridheight = GridBagConstraints.REMAINDER;
                MazeMaker.gridbag.setConstraints(MazeMaker.horzScroll,
                        MazeMaker.c);
                MazeMaker.outputPane.add(MazeMaker.horzScroll);
                MazeMaker.horzScroll.addAdjustmentListener(MazeMaker.mhandler);
                MazeMaker.vertScroll.addAdjustmentListener(MazeMaker.mhandler);
                MazeMaker.secondaryPane.addMouseListener(MazeMaker.mhandler);
                MazeMaker.outputFrame.addKeyListener(MazeMaker.mhandler);
                MazeMaker.outputFrame.addWindowListener(MazeMaker.app
                        .getEventHandler());
                MazeMaker.clearHistory();
                MazeMaker.redrawEditor();
            } catch (final IllegalArgumentException ia) {
                Messager.showDialog("The maze size specified is too small.");
                MazeMaker.app.setLoaded(false);
                MazeMaker.app.showMenu();
            }
        } else {
            Messager.showDialog("No Maze Opened");
        }
    }

    public static boolean newMaze() {
        int x, y, z, w, mazeSizeX, mazeSizeY, mazeSizeZ, mazeSizeW;
        boolean success = true;
        boolean saved = true;
        int status = 0;
        String input1, input2, input3, input4;
        if (MazeMaker.app.getDirty()) {
            status = MazeMaker.app.showSaveDialog();
            if (status == JOptionPane.YES_OPTION) {
                saved = MazeMaker.app.saveMaze();
            } else if (status == JOptionPane.CANCEL_OPTION) {
                saved = false;
            } else {
                MazeMaker.app.setDirty(false);
            }
        }
        if (saved) {
            input1 = JOptionPane.showInputDialog(null, "Number of rows?",
                    "New Maze", JOptionPane.OK_CANCEL_OPTION);
            if (input1 != null) {
                input2 = JOptionPane.showInputDialog(null,
                        "Number of columns?", "New Maze",
                        JOptionPane.OK_CANCEL_OPTION);
                if (input2 != null) {
                    input3 = JOptionPane.showInputDialog(null,
                            "Number of floors?", "New Maze",
                            JOptionPane.OK_CANCEL_OPTION);
                    if (input3 != null) {
                        input4 = JOptionPane.showInputDialog(null,
                                "Number of levels?", "New Maze",
                                JOptionPane.OK_CANCEL_OPTION);
                        if (input4 != null) {
                            try {
                                mazeSizeX = Integer.parseInt(input1);
                                mazeSizeY = Integer.parseInt(input2);
                                mazeSizeZ = Integer.parseInt(input3);
                                mazeSizeW = Integer.parseInt(input4);
                                if (mazeSizeX < 1 || mazeSizeY < 1
                                        || mazeSizeZ < 1 || mazeSizeW < 1) {
                                    throw new NumberFormatException();
                                }
                                MazeMaker.app.setPlayerLocation(false, false,
                                        -1);
                                MazeMaker.app
                                        .setPlayerLocation(false, true, -1);
                                MazeMaker.app
                                        .setPlayerLocation(true, false, -1);
                                MazeMaker.app.setPlayerLocation(true, true, -1);
                                MazeMaker.app.setMaze(new Maze(mazeSizeX,
                                        mazeSizeY, mazeSizeZ, mazeSizeW));
                                MazeMaker.app
                                        .setViewingWindowLocation(
                                                false,
                                                0 - (MazeRunner.VIEWING_WINDOW_SIZE_X - 1) / 2);
                                MazeMaker.app
                                        .setViewingWindowLocation(
                                                true,
                                                0 - (MazeRunner.VIEWING_WINDOW_SIZE_Y - 1) / 2);
                                MazeMaker.app
                                        .setLowerViewingWindowLocation(
                                                false,
                                                0 + (MazeRunner.VIEWING_WINDOW_SIZE_X - 1) / 2);
                                MazeMaker.app
                                        .setLowerViewingWindowLocation(
                                                true,
                                                0 + (MazeRunner.VIEWING_WINDOW_SIZE_Y - 1) / 2);
                                MazeMaker.MAX_VIEWING_WINDOW_X = mazeSizeX
                                        + MazeRunner.VIEWING_WINDOW_SIZE_X / 2
                                        - 1;
                                MazeMaker.MAX_VIEWING_WINDOW_Y = mazeSizeY
                                        + MazeRunner.VIEWING_WINDOW_SIZE_Y / 2
                                        - 1;
                                for (w = 0; w < mazeSizeW; w++) {
                                    for (z = 0; z < mazeSizeZ; z++) {
                                        for (x = 0; x < mazeSizeX; x++) {
                                            for (y = 0; y < mazeSizeY; y++) {
                                                MazeMaker.app
                                                        .getMaze()
                                                        .setCell(
                                                                new MazeGround(),
                                                                x, y, z, w);
                                            }
                                        }
                                    }
                                }
                                MazeMaker.clearHistory();
                            } catch (final NumberFormatException nf) {
                                Messager.showDialog("Rows, columns, floors, and levels must be integers greater than 0.");
                                success = false;
                            } catch (final OutOfMemoryError om) {
                                Messager.showDialog("The maze size specified is too large.");
                                success = false;
                            }
                        } else {
                            // User cancelled
                            success = false;
                        }
                    } else {
                        // User cancelled
                        success = false;
                    }
                } else {
                    // User cancelled
                    success = false;
                }
            } else {
                // User cancelled
                success = false;
            }
        } else {
            success = false;
        }
        return success;
    }

    public static boolean isOutputVisible() {
        return MazeMaker.outputFrame.isVisible();
    }

    public static void showOutput() {
        MazeMaker.outputFrame.setJMenuBar(MazeMaker.app.getMainMenuBar());
        MazeMaker.app.setEditorMenus();
        MazeMaker.outputFrame.setVisible(true);
    }

    public static void hideOutput() {
        MazeMaker.outputFrame.setVisible(false);
    }

    public static JFrame getOutputFrame() {
        if (MazeMaker.outputFrame != null && MazeMaker.outputFrame.isVisible()) {
            return MazeMaker.outputFrame;
        } else {
            return null;
        }
    }

    public static void undo() {
        MazeMaker.engine.undo();
        final MazeObject obj = MazeMaker.engine.getObject();
        final int x = MazeMaker.engine.getX();
        final int y = MazeMaker.engine.getY();
        final int z = MazeMaker.engine.getZ();
        final int w = MazeMaker.engine.getW();
        MazeMaker.editorLocationX = x;
        MazeMaker.editorLocationY = y;
        MazeMaker.editorCameFromZ = z;
        MazeMaker.editorCameFromW = w;
        if (x != -1 && y != -1 && z != -1 && w != -1) {
            final MazeObject oldObj = MazeMaker.app.getMazeObject(x, y, z, w);
            if (!obj.getName().equals(new MazeStairsUp().getName())
                    && !obj.getName().equals(new MazeStairsDown().getName())) {
                if (obj.getName().equals(new MazeTwoWayTeleporter().getName())) {
                    MazeMaker.app.getMaze().setCell(obj, x, y, z, w);
                    MazeMaker.reverseCheckTwoWayTeleporterPair(z, w);
                    MazeMaker.checkStairPair(z, w);
                } else {
                    MazeMaker.checkTwoWayTeleporterPair(z, w);
                    MazeMaker.app.getMaze().setCell(obj, x, y, z, w);
                    MazeMaker.checkStairPair(z, w);
                }
            } else {
                MazeMaker.app.getMaze().setCell(obj, x, y, z, w);
                MazeMaker.reverseCheckStairPair(z, w);
            }
            MazeMaker.updateRedoHistory(oldObj, x, y, z, w);
            MazeMaker.redrawEditor();
        } else {
            Messager.showMessage("Nothing to undo");
        }
    }

    public static void redo() {
        MazeMaker.engine.redo();
        final MazeObject obj = MazeMaker.engine.getObject();
        final int x = MazeMaker.engine.getX();
        final int y = MazeMaker.engine.getY();
        final int z = MazeMaker.engine.getZ();
        final int w = MazeMaker.engine.getW();
        MazeMaker.editorLocationX = x;
        MazeMaker.editorLocationY = y;
        MazeMaker.editorCameFromZ = z;
        MazeMaker.editorCameFromW = w;
        if (x != -1 && y != -1 && z != -1 && w != -1) {
            final MazeObject oldObj = MazeMaker.app.getMazeObject(x, y, z, w);
            if (!obj.getName().equals(new MazeStairsUp().getName())
                    && !obj.getName().equals(new MazeStairsDown().getName())) {
                if (obj.getName().equals(new MazeTwoWayTeleporter().getName())) {
                    MazeMaker.app.getMaze().setCell(obj, x, y, z, w);
                    MazeMaker.reverseCheckTwoWayTeleporterPair(z, w);
                    MazeMaker.checkStairPair(z, w);
                } else {
                    MazeMaker.checkTwoWayTeleporterPair(z, w);
                    MazeMaker.app.getMaze().setCell(obj, x, y, z, w);
                    MazeMaker.checkStairPair(z, w);
                }
            } else {
                MazeMaker.app.getMaze().setCell(obj, x, y, z, w);
                MazeMaker.reverseCheckStairPair(z, w);
            }
            MazeMaker.updateUndoHistory(oldObj, x, y, z, w);
            MazeMaker.redrawEditor();
        } else {
            Messager.showMessage("Nothing to redo");
        }
    }

    public static void clearHistory() {
        MazeMaker.engine = new UndoRedoEngine();
    }

    private static void updateUndoHistory(final MazeObject obj, final int x,
            final int y, final int z, final int w) {
        MazeMaker.engine.updateUndoHistory(obj, x, y, z, w);
    }

    private static void updateRedoHistory(final MazeObject obj, final int x,
            final int y, final int z, final int w) {
        MazeMaker.engine.updateRedoHistory(obj, x, y, z, w);
    }

    public static void initEditor() {
        MazeMaker.mhandler = new MazeMakerEventHandler();
        MazeMaker.thandler = new MazeTeleporterEventHandler();
        MazeMaker.engine = new UndoRedoEngine();
        MazeMaker.app = MazeRunner.getApplication();
        MazeMaker.editorLocationX = 0;
        MazeMaker.editorLocationY = 0;
        MazeMaker.editorLocationZ = 0;
        MazeMaker.editorLocationW = 0;
    }
}