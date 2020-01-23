/*  LTRemix: An Arena-Solving Game
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ltremix.arena;

import java.io.IOException;

import com.puttysoftware.ltremix.LTRemix;
import com.puttysoftware.ltremix.utilities.FormatConstants;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class SuffixHandler implements AbstractSuffixIO {
    @Override
    public void readSuffix(final XDataReader reader, final int formatVersion)
            throws IOException {
        if (FormatConstants.isFormatVersionValidGeneration1(formatVersion)) {
            LTRemix.getApplication().getGameManager().loadGameHookG1(reader);
        } else if (FormatConstants
                .isFormatVersionValidGeneration2(formatVersion)) {
            LTRemix.getApplication().getGameManager().loadGameHookG2(reader);
        } else if (FormatConstants
                .isFormatVersionValidGeneration3(formatVersion)) {
            LTRemix.getApplication().getGameManager().loadGameHookG3(reader);
        } else if (FormatConstants
                .isFormatVersionValidGeneration4(formatVersion)) {
            LTRemix.getApplication().getGameManager().loadGameHookG4(reader);
        } else if (FormatConstants
                .isFormatVersionValidGeneration5(formatVersion)) {
            LTRemix.getApplication().getGameManager().loadGameHookG5(reader);
        } else if (FormatConstants
                .isFormatVersionValidGeneration6(formatVersion)) {
            LTRemix.getApplication().getGameManager().loadGameHookG6(reader);
        } else if (FormatConstants
                .isFormatVersionValidGeneration7(formatVersion)) {
            LTRemix.getApplication().getGameManager().loadGameHookG7(reader);
        }
    }

    @Override
    public void writeSuffix(final XDataWriter writer) throws IOException {
        LTRemix.getApplication().getGameManager().saveGameHook(writer);
    }
}
