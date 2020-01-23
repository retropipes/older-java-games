package net.worldwizard.dungeondiver.dungeon.buffs;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JLabel;

import net.worldwizard.dungeondiver.DungeonDiver;

public class DungeonBuffManager {
    // Fields
    private final int[] savedRounds;
    private final DungeonBuff[] buffs;
    private final Container effectsPane;
    private final JLabel[] effects;
    private static final int BUFF_CONFUSED = 0;
    private static final int BUFF_ROTATED = 1;
    private static final int BUFF_DIZZY = 2;
    private static final int BUFF_SHINING = 3;
    private static final int BUFF_SHINING_BRIGHTLY = 4;
    private static final int BUFF_PARTLY_BLINDED = 5;
    private static final int BUFF_BLINDED = 6;
    private static final int MAX_BUFFS = 7;
    private static final int FIRST_HANDLED_BUFF = 3;

    // Constructors
    public DungeonBuffManager() {
        this.savedRounds = new int[DungeonBuffManager.MAX_BUFFS];
        this.buffs = new DungeonBuff[DungeonBuffManager.MAX_BUFFS];
        this.effectsPane = new Container();
        this.effectsPane.setLayout(new GridLayout(DungeonBuffManager.MAX_BUFFS,
                1));
        this.effects = new JLabel[DungeonBuffManager.MAX_BUFFS];
        int x;
        for (x = 0; x < DungeonBuffManager.MAX_BUFFS; x++) {
            this.effects[x] = new JLabel();
            this.effects[x].setOpaque(true);
            this.effectsPane.add(this.effects[x]);
        }
    }

    // Methods
    private boolean isConfused() {
        return this.buffs[DungeonBuffManager.BUFF_CONFUSED] != null;
    }

    public void setConfused(final int rounds) {
        if (this.isConfused()) {
            this.buffs[DungeonBuffManager.BUFF_CONFUSED]
                    .extendDungeonBuff(rounds);
        } else {
            if (this.isDizzy()) {
                this.buffs[DungeonBuffManager.BUFF_DIZZY].setRounds(0);
                this.buffs[DungeonBuffManager.BUFF_DIZZY] = null;
            }
            this.buffs[DungeonBuffManager.BUFF_CONFUSED] = new Confusion(rounds);
        }
    }

    private boolean isDizzy() {
        return this.buffs[DungeonBuffManager.BUFF_DIZZY] != null;
    }

    public void setDizzy(final int rounds) {
        if (this.isDizzy()) {
            this.buffs[DungeonBuffManager.BUFF_DIZZY].extendDungeonBuff(rounds);
        } else {
            if (this.isConfused()) {
                this.buffs[DungeonBuffManager.BUFF_CONFUSED].setRounds(0);
                this.buffs[DungeonBuffManager.BUFF_CONFUSED] = null;
            }
            this.buffs[DungeonBuffManager.BUFF_DIZZY] = new Dizzy(rounds);
        }
    }

    private boolean isRotated() {
        return this.buffs[DungeonBuffManager.BUFF_ROTATED] != null;
    }

    public void setRotated(final int rounds, final int state) {
        if (this.isRotated()) {
            final int currState = this.buffs[DungeonBuffManager.BUFF_ROTATED]
                    .getPower();
            if (currState == state) {
                // Extend Rotated
                this.buffs[DungeonBuffManager.BUFF_ROTATED]
                        .extendDungeonBuff(rounds);
            } else {
                // Clear Rotated
                this.buffs[DungeonBuffManager.BUFF_ROTATED].setRounds(0);
                this.buffs[DungeonBuffManager.BUFF_ROTATED] = null;
            }
        } else {
            // Set Rotated
            this.buffs[DungeonBuffManager.BUFF_ROTATED] = new Rotated(rounds,
                    state);
        }
    }

    private boolean isShining() {
        return this.buffs[DungeonBuffManager.BUFF_SHINING] != null;
    }

    public void setShining(final int rounds) {
        if (this.isShiningBrightly()) {
            // Extend Shining Brightly
            this.setShiningBrightly(rounds);
        } else if (this.isShining()) {
            // Save rounds of Shining
            this.savedRounds[DungeonBuffManager.BUFF_SHINING] = this.buffs[DungeonBuffManager.BUFF_SHINING]
                    .getRounds();
            // Get rid of Shining
            this.buffs[DungeonBuffManager.BUFF_SHINING].setRounds(0);
            this.buffs[DungeonBuffManager.BUFF_SHINING].customUseLogic(0);
            this.buffs[DungeonBuffManager.BUFF_SHINING] = null;
            // Set Shining Brightly
            this.setShiningBrightly(rounds);
        } else {
            if (this.isBlinded()) {
                // Get rid of Blinded
                this.buffs[DungeonBuffManager.BUFF_BLINDED].setRounds(0);
                this.buffs[DungeonBuffManager.BUFF_BLINDED].customUseLogic(0);
                this.buffs[DungeonBuffManager.BUFF_BLINDED] = null;
            } else if (this.isPartlyBlinded()) {
                // Get rid of Partly Blinded
                this.buffs[DungeonBuffManager.BUFF_PARTLY_BLINDED].setRounds(0);
                this.buffs[DungeonBuffManager.BUFF_PARTLY_BLINDED]
                        .customUseLogic(0);
                this.buffs[DungeonBuffManager.BUFF_PARTLY_BLINDED] = null;
            } else {
                // Activate Shining
                this.buffs[DungeonBuffManager.BUFF_SHINING] = new Shining(
                        rounds);
                DungeonDiver.getHoldingBag().getDungeonGUI()
                        .increaseVisibility();
            }
        }
    }

    void setShiningOnExpiry() {
        // Activate Shining
        final int rounds = this.savedRounds[DungeonBuffManager.BUFF_SHINING];
        if (rounds > 0) {
            this.buffs[DungeonBuffManager.BUFF_SHINING] = new Shining(rounds);
            DungeonDiver.getHoldingBag().getDungeonGUI().increaseVisibility();
        }
    }

    private boolean isShiningBrightly() {
        return this.buffs[DungeonBuffManager.BUFF_SHINING_BRIGHTLY] != null;
    }

    private void setShiningBrightly(final int rounds) {
        if (this.isShiningBrightly()) {
            this.buffs[DungeonBuffManager.BUFF_SHINING_BRIGHTLY]
                    .extendDungeonBuff(rounds);
        } else {
            this.buffs[DungeonBuffManager.BUFF_SHINING_BRIGHTLY] = new ShiningBrightly(
                    rounds);
            DungeonDiver.getHoldingBag().getDungeonGUI().increaseVisibility();
            DungeonDiver.getHoldingBag().getDungeonGUI().increaseVisibility();
        }
    }

    private boolean isPartlyBlinded() {
        return this.buffs[DungeonBuffManager.BUFF_PARTLY_BLINDED] != null;
    }

    public void setPartlyBlinded(final int rounds) {
        if (this.isBlinded()) {
            // Extend Blinded
            this.setBlinded(rounds);
        } else if (this.isPartlyBlinded()) {
            // Save rounds of Partly Blinded
            this.savedRounds[DungeonBuffManager.BUFF_PARTLY_BLINDED] = this.buffs[DungeonBuffManager.BUFF_PARTLY_BLINDED]
                    .getRounds();
            // Get rid of Partly Blinded
            this.buffs[DungeonBuffManager.BUFF_PARTLY_BLINDED].setRounds(0);
            this.buffs[DungeonBuffManager.BUFF_PARTLY_BLINDED]
                    .customUseLogic(0);
            this.buffs[DungeonBuffManager.BUFF_PARTLY_BLINDED] = null;
            // Set Blinded
            this.setBlinded(rounds);
        } else {
            if (this.isShiningBrightly()) {
                // Get rid of Shining Brightly
                this.buffs[DungeonBuffManager.BUFF_SHINING_BRIGHTLY]
                        .setRounds(0);
                this.buffs[DungeonBuffManager.BUFF_SHINING_BRIGHTLY]
                        .customUseLogic(0);
                this.buffs[DungeonBuffManager.BUFF_SHINING_BRIGHTLY] = null;
            } else if (this.isShining()) {
                // Get rid of Shining
                this.buffs[DungeonBuffManager.BUFF_SHINING].setRounds(0);
                this.buffs[DungeonBuffManager.BUFF_SHINING].customUseLogic(0);
                this.buffs[DungeonBuffManager.BUFF_SHINING] = null;
            } else {
                // Activate Partly Blinded
                this.buffs[DungeonBuffManager.BUFF_PARTLY_BLINDED] = new PartlyBlinded(
                        rounds);
                DungeonDiver.getHoldingBag().getDungeonGUI()
                        .decreaseVisibility();
            }
        }
    }

    void setPartlyBlindedOnExpiry() {
        // Activate Partly Blinded
        final int rounds = this.savedRounds[DungeonBuffManager.BUFF_PARTLY_BLINDED];
        if (rounds > 0) {
            this.buffs[DungeonBuffManager.BUFF_PARTLY_BLINDED] = new PartlyBlinded(
                    rounds);
            DungeonDiver.getHoldingBag().getDungeonGUI().decreaseVisibility();
        }
    }

    private boolean isBlinded() {
        return this.buffs[DungeonBuffManager.BUFF_BLINDED] != null;
    }

    private void setBlinded(final int rounds) {
        if (this.isBlinded()) {
            this.buffs[DungeonBuffManager.BUFF_BLINDED]
                    .extendDungeonBuff(rounds);
        } else {
            this.buffs[DungeonBuffManager.BUFF_BLINDED] = new Blinded(rounds);
            DungeonDiver.getHoldingBag().getDungeonGUI().decreaseVisibility();
            DungeonDiver.getHoldingBag().getDungeonGUI().decreaseVisibility();
        }
    }

    public int modifyMove(final int arg) {
        if (this.isDizzy()) {
            if (this.isRotated()) {
                final int diz = this.buffs[DungeonBuffManager.BUFF_DIZZY]
                        .useDungeonBuff(arg);
                return this.buffs[DungeonBuffManager.BUFF_ROTATED]
                        .useDungeonBuff(diz);
            } else {
                return this.buffs[DungeonBuffManager.BUFF_DIZZY]
                        .useDungeonBuff(arg);
            }
        } else {
            if (this.isConfused() && this.isRotated()) {
                final int conf = this.buffs[DungeonBuffManager.BUFF_CONFUSED]
                        .useDungeonBuff(arg);
                return this.buffs[DungeonBuffManager.BUFF_ROTATED]
                        .useDungeonBuff(conf);
            } else if (this.isConfused() && !this.isRotated()) {
                return this.buffs[DungeonBuffManager.BUFF_CONFUSED]
                        .useDungeonBuff(arg);
            } else if (!this.isConfused() && this.isRotated()) {
                return this.buffs[DungeonBuffManager.BUFF_ROTATED]
                        .useDungeonBuff(arg);
            } else {
                return arg;
            }
        }
    }

    public void cullInactiveBuffs() {
        int x;
        for (x = 0; x < this.buffs.length; x++) {
            if (this.buffs[x] != null) {
                if (!this.buffs[x].isActive()) {
                    this.buffs[x] = null;
                }
            }
        }
    }

    public void useBuffs() {
        int x;
        for (x = DungeonBuffManager.FIRST_HANDLED_BUFF; x < this.buffs.length; x++) {
            if (this.buffs[x] != null) {
                this.buffs[x].useDungeonBuff(0);
            }
        }
    }

    public Container updateEffects() {
        String s = "";
        final DungeonBuff[] tempBuffs = new DungeonBuff[this.buffs.length];
        int counter = 0;
        int x;
        for (x = 0; x < this.buffs.length; x++) {
            if (this.buffs[x] != null) {
                tempBuffs[counter] = this.buffs[x];
                counter++;
            }
        }
        this.effectsPane.removeAll();
        this.effectsPane.setLayout(new GridLayout(counter, 1));
        for (x = 0; x < counter; x++) {
            s = tempBuffs[x].getDungeonBuffString();
            this.effectsPane.add(this.effects[x]);
            this.effects[x].setText(s);
        }
        return this.effectsPane;
    }

    public int encodeMovement(final int arg1, final int arg2) {
        if (arg1 == 0 && arg2 == -1) {
            return DungeonBuff.MOVE_UP;
        } else if (arg1 == 0 && arg2 == 1) {
            return DungeonBuff.MOVE_DOWN;
        } else if (arg1 == -1 && arg2 == 0) {
            return DungeonBuff.MOVE_LEFT;
        } else if (arg1 == 1 && arg2 == 0) {
            return DungeonBuff.MOVE_RIGHT;
        }
        return 0;
    }

    public int[] decodeMovement(final int arg) {
        final int[] arr = new int[2];
        if (arg == DungeonBuff.MOVE_UP) {
            arr[0] = 0;
            arr[1] = -1;
        } else if (arg == DungeonBuff.MOVE_DOWN) {
            arr[0] = 0;
            arr[1] = 1;
        } else if (arg == DungeonBuff.MOVE_LEFT) {
            arr[0] = -1;
            arr[1] = 0;
        } else if (arg == DungeonBuff.MOVE_RIGHT) {
            arr[0] = 1;
            arr[1] = 0;
        }
        return arr;
    }
}
