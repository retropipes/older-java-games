package com.puttysoftware.mazer5d.objectmodel;

import java.io.IOException;

import com.puttysoftware.mazer5d.assets.ColorShader;
import com.puttysoftware.mazer5d.assets.ObjectImageIndex;
import com.puttysoftware.mazer5d.compatibility.abc.RandomGenerationRule;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public interface MazeObjectModel
        extends ObjectModel, RandomGenerationRule {
    void setGameLook(String cacheName, ObjectImageIndex image);

    void setGameLook(String cacheName, ObjectImageIndex image,
            ColorShader shader);

    void setEditorLook(String cacheName, ObjectImageIndex image);

    void setEditorLook(String cacheName, ObjectImageIndex image,
            ColorShader shader);

    void setBattleLook(String cacheName, ObjectImageIndex image);

    void setBattleLook(String cacheName, ObjectImageIndex image,
            ColorShader shader);

    MazeObjectModel getSavedObject();

    boolean hasSavedObject();

    void setSavedObject(MazeObjectModel inNewSavedObject);

    String getName();

    int getLayer();

    void writeObject(XDataWriter writer) throws IOException;

    MazeObjectModel readObject(XDataReader reader, int uid)
            throws IOException;
}