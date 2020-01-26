package net.worldwizard.mazerunner;

public interface Scriptable {
    void preMoveAction(Inventory inv);

    void preMoveAction(boolean ie, int dirX, int dirY, Inventory inv);

    void postMoveAction(Inventory inv);

    void postMoveAction(boolean ie, int dirX, int dirY, Inventory inv);

    boolean isConditionallySolid(Inventory inv);

    boolean isConditionallyDirectionallySolid(boolean ie, int dirX, int dirY,
            Inventory inv);

    MazeObject editorHook();

    void playSound();

    void pushAction(Inventory inv, MazeObject mo, int x, int y, int pushX,
            int pushY);

    void pushIntoAction(Inventory inv, MazeObject pushed, int x, int y, int z,
            int w);

    void pushOutAction(Inventory inv);

    void pullAction(Inventory inv, MazeObject mo, int x, int y, int pullX,
            int pullY);

    void pullIntoAction(Inventory inv);

    void pullOutAction(Inventory inv);

    void useAction(MazeObject mo, int x, int y, int z, int w);

    void chainReactionAction(int x, int y, int z, int w);
}