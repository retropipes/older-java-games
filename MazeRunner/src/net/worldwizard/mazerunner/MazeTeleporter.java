package net.worldwizard.mazerunner;

public class MazeTeleporter extends MazeGenericTeleporter {
    // Serialization
    private static final long serialVersionUID = 103L;

    // Constructors
    public MazeTeleporter() {
        super("Teleporter", "Teleporter", 0, 0, 0, 0);
    }

    public MazeTeleporter(final int destinationRow, final int destinationColumn,
            final int destinationFloor, final int destinationLevel) {
        super("Teleporter", "Teleporter", destinationRow, destinationColumn,
                destinationFloor, destinationLevel);
    }

    @Override
    public String toString() {
        return "T\n" + Integer.toString(this.getDestinationColumn()) + "\n"
                + Integer.toString(this.getDestinationRow()) + "\n"
                + Integer.toString(this.getDestinationFloor()) + "\n"
                + Integer.toString(this.getDestinationLevel());
    }

    @Override
    public String getName() {
        return "Teleporter";
    }

    @Override
    public MazeObject editorHook() {
        final MazeObject mo = MazeMaker
                .editTeleporterDestination(MazeMaker.TELEPORTER_TYPE_GENERIC);
        return mo;
    }
}