package net.worldwizard.mazerunner;

public class MazeExplodingWall extends MazeGenericWall {
    // Serialization
    private static final long serialVersionUID = 50L;

    // Constructors
    public MazeExplodingWall() {
        super("Wall", "ExplodingWall", true, true);
    }

    @Override
    public void preMoveAction(final Inventory inv) {
        Messager.showMessage("BOOM!");
    }

    @Override
    public void preMoveAction(final boolean ie, final int dirX, final int dirY,
            final Inventory inv) {
        Messager.showMessage("BOOM!");
    }

    @Override
    public void chainReactionAction(final int x, final int y, final int z,
            final int w) {
        // Explode this wall, and any exploding walls next to this wall as well
        String mo2Name, mo4Name, mo6Name, mo8Name, invalidName, thisName;
        invalidName = new MazeVoid().getName();
        final MazeRunner app = MazeRunner.getApplication();
        thisName = this.getName();
        final MazeObject mo2 = app.getMazeObject(x - 1, y, z, w);
        try {
            mo2Name = mo2.getName();
        } catch (final NullPointerException np) {
            mo2Name = invalidName;
        }
        final MazeObject mo4 = app.getMazeObject(x, y - 1, z, w);
        try {
            mo4Name = mo4.getName();
        } catch (final NullPointerException np) {
            mo4Name = invalidName;
        }
        final MazeObject mo6 = app.getMazeObject(x, y + 1, z, w);
        try {
            mo6Name = mo6.getName();
        } catch (final NullPointerException np) {
            mo6Name = invalidName;
        }
        final MazeObject mo8 = app.getMazeObject(x + 1, y, z, w);
        try {
            mo8Name = mo8.getName();
        } catch (final NullPointerException np) {
            mo8Name = invalidName;
        }
        if (mo2Name.equals(thisName)) {
            app.morph(new MazeGround(), x, y, z, w, "BOOM!");
            this.chainReactionAction(x - 1, y, z, w);
        }
        if (mo4Name.equals(thisName)) {
            app.morph(new MazeGround(), x, y, z, w, "BOOM!");
            this.chainReactionAction(x, y - 1, z, w);
        }
        if (mo6Name.equals(thisName)) {
            app.morph(new MazeGround(), x, y, z, w, "BOOM!");
            this.chainReactionAction(x, y + 1, z, w);
        }
        if (mo8Name.equals(thisName)) {
            app.morph(new MazeGround(), x, y, z, w, "BOOM!");
            this.chainReactionAction(x + 1, y, z, w);
        }
        app.morph(new MazeGround(), x, y, z, w, "BOOM!");
    }

    @Override
    public String toString() {
        return "EW";
    }

    @Override
    public String getName() {
        return "Exploding Wall";
    }
}