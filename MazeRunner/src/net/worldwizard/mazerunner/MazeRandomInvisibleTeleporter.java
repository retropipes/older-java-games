package net.worldwizard.mazerunner;

import java.util.Random;

public class MazeRandomInvisibleTeleporter
        extends MazeGenericInvisibleTeleporter {
    // Fields
    private final int randomRangeX;
    private final int randomRangeY;
    private final Random generator;
    // Serialization
    private static final long serialVersionUID = 106L;

    // Constructors
    public MazeRandomInvisibleTeleporter() {
        super("Ground", "RandomInvisibleTeleporter", 0, 0, 0, 0);
        this.randomRangeX = 0;
        this.randomRangeY = 0;
        this.generator = new Random();
    }

    public MazeRandomInvisibleTeleporter(final int newRandomRangeY,
            final int newRandomRangeX) {
        super("Ground", "RandomInvisibleTeleporter", 0, 0, 0, 0);
        this.randomRangeX = newRandomRangeX;
        this.randomRangeY = newRandomRangeY;
        this.generator = new Random();
    }

    public MazeRandomInvisibleTeleporter(final String gameAppearance,
            final String editorAppearance, final int newRandomRangeY,
            final int newRandomRangeX) {
        super(gameAppearance, editorAppearance, 0, 0, 0, 0);
        this.randomRangeX = newRandomRangeX;
        this.randomRangeY = newRandomRangeY;
        this.generator = new Random();
    }

    // Accessor methods
    @Override
    public int getDestinationRow() {
        int sign = this.generator.nextInt(2);
        final int value = this.generator.nextInt(this.randomRangeY + 1);
        if (sign == 0) {
            sign = -1;
        }
        return sign * value;
    }

    @Override
    public int getDestinationColumn() {
        int sign = this.generator.nextInt(2);
        final int value = this.generator.nextInt(this.randomRangeX + 1);
        if (sign == 0) {
            sign = -1;
        }
        return sign * value;
    }

    @Override
    public int getDestinationFloor() {
        final MazeRunner app = MazeRunner.getApplication();
        return app.getPlayerLocation(true, false);
    }

    @Override
    public int getDestinationLevel() {
        final MazeRunner app = MazeRunner.getApplication();
        return app.getPlayerLocation(true, true);
    }

    public int getRandomRowRange() {
        return this.randomRangeY;
    }

    public int getRandomColumnRange() {
        return this.randomRangeX;
    }

    // Scriptability
    @Override
    public void postMoveAction(final Inventory inv) {
        final MazeRunner app = MazeRunner.getApplication();
        app.updatePosition(this.getDestinationRow(),
                this.getDestinationColumn());
        // Display invisible teleporter message, if it's enabled
        if (app.getMessageEnabled(MazeRunner.MESSAGE_INVISIBLE_TELEPORTER)) {
            Messager.showMessage("Invisible Teleporter!");
        }
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        final MazeRunner app = MazeRunner.getApplication();
        app.updatePosition(this.getDestinationRow(),
                this.getDestinationColumn());
        // Display invisible teleporter message, if it's enabled
        if (app.getMessageEnabled(MazeRunner.MESSAGE_INVISIBLE_TELEPORTER)) {
            Messager.showMessage("Invisible Teleporter!");
        }
    }

    @Override
    public String toString() {
        return "RIT\n" + Integer.toString(this.randomRangeY) + "\n"
                + Integer.toString(this.randomRangeX);
    }

    @Override
    public String getName() {
        return "Random Invisible Teleporter";
    }

    @Override
    public boolean equals(final Object obj) {
        final MazeRandomInvisibleTeleporter mgt = (MazeRandomInvisibleTeleporter) obj;
        if (super.equals(mgt) && this.randomRangeX == mgt.randomRangeX
                && this.randomRangeY == mgt.randomRangeY) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public MazeObject editorHook() {
        final MazeObject mo = MazeMaker.editTeleporterDestination(
                MazeMaker.TELEPORTER_TYPE_RANDOM_INVISIBLE);
        return mo;
    }
}