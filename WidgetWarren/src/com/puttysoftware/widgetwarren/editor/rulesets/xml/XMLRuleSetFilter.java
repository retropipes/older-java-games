/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.editor.rulesets.xml;

import java.io.File;
import java.io.FilenameFilter;

import com.puttysoftware.widgetwarren.maze.xml.XMLExtension;

public class XMLRuleSetFilter implements FilenameFilter {
    @Override
    public boolean accept(final File dir, final String name) {
        final String extension = XMLRuleSetFilter.getExtension(name);
        if (extension != null) {
            if (extension.equals(XMLExtension.getXMLRuleSetExtension())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private static String getExtension(final String s) {
        String ext = null;
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }
}
