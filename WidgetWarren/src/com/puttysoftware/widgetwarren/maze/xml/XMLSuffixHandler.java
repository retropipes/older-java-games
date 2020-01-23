package com.puttysoftware.widgetwarren.maze.xml;

import java.io.IOException;

import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class XMLSuffixHandler implements XMLSuffixIO {
    @Override
    public void readSuffix(final XDataReader reader, final int formatVersion)
            throws IOException {
        WidgetWarren.getApplication().getGameManager()
                .loadGameHookXML(reader, formatVersion);
    }

    @Override
    public void writeSuffix(final XDataWriter writer) throws IOException {
        WidgetWarren.getApplication().getGameManager().saveGameHookXML(writer);
    }
}
