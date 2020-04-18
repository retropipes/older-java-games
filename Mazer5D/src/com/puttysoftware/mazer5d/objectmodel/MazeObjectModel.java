package com.puttysoftware.mazer5d.objectmodel;

import java.io.IOException;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.mazer5d.assets.ColorShader;
import com.puttysoftware.mazer5d.assets.ObjectImageIndex;
import com.puttysoftware.mazer5d.compatibility.abc.RandomGenerationRule;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public interface MazeObjectModel extends RandomGenerationRule {
    int getUniqueID();

    BufferedImageIcon getImage();

    BufferedImageIcon getGameImage();

    BufferedImageIcon getEditorImage();

    BufferedImageIcon getBattleImage();

    boolean isSolid();

    boolean isDirectionallySolid(int dirX, int dirY);

    boolean isInternallyDirectionallySolid(int dirX, int dirY);

    boolean isPushable();

    boolean isDirectionallyPushable(int dirX, int dirY);

    boolean isPullable();

    boolean isDirectionallyPullable(int dirX, int dirY);

    boolean isPullableInto();

    boolean isPushableInto();

    boolean isDirectionallyPushableInto(int dirX, int dirY);

    boolean isDirectionallyPullableInto(int dirX, int dirY);

    boolean isPullableOut();

    boolean isPushableOut();

    boolean isDirectionallyPushableOut(int dirX, int dirY);

    boolean isDirectionallyPullableOut(int dirX, int dirY);

    boolean hasFriction();

    boolean isUsable();

    int getUses();

    void use();

    boolean isDestroyable();

    boolean isChainReacting();

    boolean isChainReactingHorizontally();

    boolean isChainReactingVertically();

    boolean isCarryable();

    boolean isSightBlocking();

    boolean isDirectionallySightBlocking(int inDirX, int inDirY);

    boolean isInternallyDirectionallySightBlocking(int inDirX, int inDirY);

    int getTimerTicks();

    void tickTimer();

    void resetTimer();

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

    boolean isOfType(MazeObjectType testType);

    void dumpState(XDataWriter writer) throws IOException;

    MazeObjectModel loadState(XDataReader reader, int uid) throws IOException;
}