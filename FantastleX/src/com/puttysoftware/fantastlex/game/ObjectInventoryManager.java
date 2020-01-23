/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.game;

import java.io.IOException;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.fantastlex.Application;
import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.Maze;
import com.puttysoftware.fantastlex.maze.MazeConstants;
import com.puttysoftware.fantastlex.maze.abc.AbstractBow;
import com.puttysoftware.fantastlex.maze.abc.AbstractMazeObject;
import com.puttysoftware.fantastlex.maze.objects.AnnihilationWand;
import com.puttysoftware.fantastlex.maze.objects.Bow;
import com.puttysoftware.fantastlex.maze.objects.DarkGem;
import com.puttysoftware.fantastlex.maze.objects.DarkWand;
import com.puttysoftware.fantastlex.maze.objects.DisarmTrapWand;
import com.puttysoftware.fantastlex.maze.objects.Empty;
import com.puttysoftware.fantastlex.maze.objects.FinishMakingWand;
import com.puttysoftware.fantastlex.maze.objects.LightGem;
import com.puttysoftware.fantastlex.maze.objects.LightWand;
import com.puttysoftware.fantastlex.maze.objects.Player;
import com.puttysoftware.fantastlex.maze.objects.TeleportWand;
import com.puttysoftware.fantastlex.maze.objects.WallBreakingWand;
import com.puttysoftware.fantastlex.maze.objects.WallMakingWand;
import com.puttysoftware.fantastlex.maze.utilities.ArrowTypeConstants;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectList;
import com.puttysoftware.fantastlex.maze.utilities.TypeConstants;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

class ObjectInventoryManager {
    // Fields
    private MazeObjectInventory objectInv, savedObjectInv;
    private int lastUsedBowIndex;
    private AbstractBow activeBow;
    private AbstractMazeObject objectBeingUsed;
    private int lastUsedObjectIndex;
    private boolean using;
    private int activeArrowType;
    private boolean arrowActive;

    // Constructor
    ObjectInventoryManager() {
        this.objectInv = new MazeObjectInventory();
        this.using = false;
        this.activeBow = new Bow();
        this.activeArrowType = ArrowTypeConstants.ARROW_TYPE_PLAIN;
        this.arrowActive = false;
    }

    // Methods
    boolean usingAnItem() {
        return this.using;
    }

    void setUsingAnItem(final boolean isUsing) {
        this.using = isUsing;
    }

    boolean isArrowActive() {
        return this.arrowActive;
    }

    void resetObjectInventory() {
        this.objectInv = new MazeObjectInventory();
    }

    void saveObjectInventory() {
        this.savedObjectInv = this.objectInv.clone();
    }

    void restoreObjectInventory() {
        if (this.savedObjectInv != null) {
            this.objectInv = this.savedObjectInv.clone();
        } else {
            this.objectInv = new MazeObjectInventory();
        }
    }

    void fireStepActions() {
        this.objectInv.fireStepActions();
    }

    MazeObjectInventory getObjectInventory() {
        return this.objectInv;
    }

    void fireArrow(final int x, final int y) {
        if (this.getObjectInventory().getUses(this.activeBow) == 0) {
            FantastleX.getApplication().showMessage("You're out of arrows!");
        } else {
            final GameArrowTask at = new GameArrowTask(x, y,
                    this.activeArrowType);
            this.arrowActive = true;
            at.start();
        }
    }

    void arrowDone() {
        this.arrowActive = false;
        this.objectInv.useBow(this.activeBow);
    }

    void showUseDialog() {
        int x;
        final MazeObjectList list = FantastleX.getApplication().getObjects();
        final AbstractMazeObject[] choices = list.getAllUsableObjects();
        final String[] userChoices = this.objectInv.generateUseStringArray();
        final String result = CommonDialogs.showInputDialog(
                "Use which object?", "FantastleX", userChoices,
                userChoices[this.lastUsedObjectIndex]);
        try {
            for (x = 0; x < choices.length; x++) {
                if (result.equals(userChoices[x])) {
                    this.lastUsedObjectIndex = x;
                    this.objectBeingUsed = choices[x];
                    if (this.objectInv.getUses(this.objectBeingUsed) == 0) {
                        FantastleX.getApplication().showMessage(
                                "That item has no more uses left.");
                        this.setUsingAnItem(false);
                    } else {
                        FantastleX.getApplication().showMessage(
                                "Click to set target");
                        this.setUsingAnItem(true);
                    }
                    return;
                }
            }
        } catch (final NullPointerException np) {
            this.setUsingAnItem(false);
        }
    }

    void showSwitchBowDialog() {
        int x;
        final MazeObjectList list = FantastleX.getApplication().getObjects();
        final AbstractMazeObject[] choices = list.getAllBows();
        final String[] userChoices = this.objectInv.generateBowStringArray();
        final String result = CommonDialogs.showInputDialog(
                "Switch to which bow?", "FantastleX", userChoices,
                userChoices[this.lastUsedBowIndex]);
        try {
            for (x = 0; x < choices.length; x++) {
                if (result.equals(userChoices[x])) {
                    this.lastUsedBowIndex = x;
                    this.activeBow = (AbstractBow) choices[x];
                    this.activeArrowType = this.activeBow.getArrowType();
                    if (this.objectInv.getUses(this.activeBow) == 0) {
                        FantastleX.getApplication().showMessage(
                                "That bow is out of arrows!");
                    } else {
                        FantastleX.getApplication().showMessage(
                                this.activeBow.getName() + " activated.");
                    }
                    return;
                }
            }
        } catch (final NullPointerException np) {
            // Do nothing
        }
    }

    void useItemHandler(final int x, final int y) {
        final Application app = FantastleX.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        final int z = m.getPlayerLocationZ();
        if (this.usingAnItem() && app.getMode() == Application.STATUS_GAME) {
            final boolean visible = m.isSquareVisible(m.getPlayerLocationX(),
                    m.getPlayerLocationY(), x, y);
            try {
                final AbstractMazeObject target = m.getCell(x, y, z,
                        MazeConstants.LAYER_OBJECT);
                final String name = this.objectBeingUsed.getName();
                if ((target.isSolid() || !visible)
                        && name.equals(new TeleportWand().getName())) {
                    this.setUsingAnItem(false);
                    FantastleX.getApplication().showMessage(
                            "Can't teleport there");
                }
                if (target.getName().equals(new Player().getName())) {
                    this.setUsingAnItem(false);
                    FantastleX.getApplication().showMessage(
                            "Don't aim at yourself!");
                }
                if (!target.isDestroyable()
                        && name.equals(new AnnihilationWand().getName())) {
                    this.setUsingAnItem(false);
                    FantastleX.getApplication().showMessage(
                            "Can't destroy that");
                }
                if (!target.isDestroyable()
                        && name.equals(new WallMakingWand().getName())) {
                    this.setUsingAnItem(false);
                    FantastleX.getApplication().showMessage(
                            "Can't create a wall there");
                }
                if (!target.isDestroyable()
                        && name.equals(new FinishMakingWand().getName())) {
                    this.setUsingAnItem(false);
                    FantastleX.getApplication().showMessage(
                            "Can't create a finish there");
                }
                if ((!target.isDestroyable() || !target
                        .isOfType(TypeConstants.TYPE_WALL))
                        && name.equals(new WallBreakingWand().getName())) {
                    this.setUsingAnItem(false);
                    FantastleX.getApplication().showMessage("Aim at a wall");
                }
                if ((!target.isDestroyable() || !target
                        .isOfType(TypeConstants.TYPE_TRAP))
                        && name.equals(new DisarmTrapWand().getName())) {
                    this.setUsingAnItem(false);
                    FantastleX.getApplication().showMessage("Aim at a trap");
                }
                if (!target.getName().equals(new Empty().getName())
                        && !target.getName().equals(new DarkGem().getName())
                        && name.equals(new LightWand().getName())) {
                    this.setUsingAnItem(false);
                    FantastleX.getApplication().showMessage(
                            "Aim at either an empty space or a Dark Gem");
                }
                if (!target.getName().equals(new Empty().getName())
                        && !target.getName().equals(new LightGem().getName())
                        && name.equals(new DarkWand().getName())) {
                    this.setUsingAnItem(false);
                    FantastleX.getApplication().showMessage(
                            "Aim at either an empty space or a Light Gem");
                }
            } catch (final ArrayIndexOutOfBoundsException ae) {
                this.setUsingAnItem(false);
                FantastleX.getApplication().showMessage("Aim within the maze");
            } catch (final NullPointerException np) {
                this.setUsingAnItem(false);
            }
        }
        if (this.usingAnItem()) {
            this.getObjectInventory().use(this.objectBeingUsed, x, y, z);
        }
    }

    void readObjectInventory(final XDataReader mazeFile, final int formatVersion)
            throws IOException {
        this.objectInv = MazeObjectInventory.readInventory(mazeFile,
                formatVersion);
        this.savedObjectInv = MazeObjectInventory.readInventory(mazeFile,
                formatVersion);
    }

    void writeObjectInventory(final XDataWriter mazeFile) throws IOException {
        this.objectInv.writeInventory(mazeFile);
        this.savedObjectInv.writeInventory(mazeFile);
    }
}
