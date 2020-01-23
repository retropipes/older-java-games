package net.worldwizard.mazerunner;

public class MazeInvisibleTeleporter extends MazeGenericInvisibleTeleporter {
    // Serialization
    private static final long serialVersionUID = 203L;

    // Constructors
    public MazeInvisibleTeleporter() {
        super("Ground", "InvisibleTeleporter", 0, 0, 0, 0);
    }

    public MazeInvisibleTeleporter(final int destinationRow,
            final int destinationColumn, final int destinationFloor,
            final int destinationLevel) {
        super("Ground", "InvisibleTeleporter", destinationRow,
                destinationColumn, destinationFloor, destinationLevel);
    }

    // Scriptability
    @Override
    public String toString() {
        return "IT\n" + Integer.toString(this.getDestinationColumn()) + "\n"
                + Integer.toString(this.getDestinationRow()) + "\n"
                + Integer.toString(this.getDestinationFloor()) + "\n"
                + Integer.toString(this.getDestinationLevel());
    }

    @Override
    public String getName() {
        return "Invisible Teleporter";
    }

    @Override
    public MazeObject editorHook() {
        final MazeObject mo = MazeMaker
                .editTeleporterDestination(MazeMaker.TELEPORTER_TYPE_INVISIBLE_GENERIC);
        return mo;
    }
}