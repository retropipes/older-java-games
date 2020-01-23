/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.maze;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class Maze2Filter extends FileFilter {
    @Override
    public boolean accept(final File f) {
	if (f.isDirectory()) {
	    return true;
	}
	final String extension = Maze2Filter.getExtension(f);
	if (extension != null) {
	    if (extension.equals(Extension.getMaze2Extension())) {
		return true;
	    } else {
		return false;
	    }
	}
	return false;
    }

    @Override
    public String getDescription() {
	return "Layered Mazes (" + Extension.getMaze2ExtensionWithPeriod() + ")";
    }

    private static String getExtension(final File f) {
	String ext = null;
	final String s = f.getName();
	final int i = s.lastIndexOf('.');
	if (i > 0 && i < s.length() - 1) {
	    ext = s.substring(i + 1).toLowerCase();
	}
	return ext;
    }
}