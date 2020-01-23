package net.worldwizard.mazerunner;

import javax.swing.ImageIcon;

public class MazeVoid extends MazeGenericWall {
    // Properties - used only for gameRenderHook
    // Serialization
    private static final long serialVersionUID = 3L;

    // Constructors
    public MazeVoid() {
        super("Void", "Void", false, false);
    }

    @Override
    public ImageIcon gameRenderHook(final int x, final int y, final int z,
            final int w) {
        final MazeRunner app = MazeRunner.getApplication();
        String mo1Name, mo2Name, mo3Name, mo4Name, mo6Name, mo7Name, mo8Name, mo9Name, thisName;
        thisName = this.getName();
        final MazeObject mo1 = app.getMazeObject(x - 1, y - 1, z, w);
        try {
            mo1Name = mo1.getName();
        } catch (final NullPointerException np) {
            mo1Name = thisName;
        }
        final MazeObject mo2 = app.getMazeObject(x - 1, y, z, w);
        try {
            mo2Name = mo2.getName();
        } catch (final NullPointerException np) {
            mo2Name = thisName;
        }
        final MazeObject mo3 = app.getMazeObject(x - 1, y + 1, z, w);
        try {
            mo3Name = mo3.getName();
        } catch (final NullPointerException np) {
            mo3Name = thisName;
        }
        final MazeObject mo4 = app.getMazeObject(x, y - 1, z, w);
        try {
            mo4Name = mo4.getName();
        } catch (final NullPointerException np) {
            mo4Name = thisName;
        }
        final MazeObject mo6 = app.getMazeObject(x, y + 1, z, w);
        try {
            mo6Name = mo6.getName();
        } catch (final NullPointerException np) {
            mo6Name = thisName;
        }
        final MazeObject mo7 = app.getMazeObject(x + 1, y - 1, z, w);
        try {
            mo7Name = mo7.getName();
        } catch (final NullPointerException np) {
            mo7Name = thisName;
        }
        final MazeObject mo8 = app.getMazeObject(x + 1, y, z, w);
        try {
            mo8Name = mo8.getName();
        } catch (final NullPointerException np) {
            mo8Name = thisName;
        }
        final MazeObject mo9 = app.getMazeObject(x + 1, y + 1, z, w);
        try {
            mo9Name = mo9.getName();
        } catch (final NullPointerException np) {
            mo9Name = thisName;
        }
        if (!thisName.equals(mo1Name) || !thisName.equals(mo2Name)
                || !thisName.equals(mo3Name) || !thisName.equals(mo4Name)
                || !thisName.equals(mo6Name) || !thisName.equals(mo7Name)
                || !thisName.equals(mo8Name) || !thisName.equals(mo9Name)) {
            return ImageSetManager.getImage(MazeObject.getSet(),
                    MazeObject.getSize(), "SealingWall");
        } else {
            return super.gameRenderHook(x, y, z, w);
        }
    }

    @Override
    public String toString() {
        return "V";
    }

    @Override
    public String getName() {
        return "Void";
    }
}
