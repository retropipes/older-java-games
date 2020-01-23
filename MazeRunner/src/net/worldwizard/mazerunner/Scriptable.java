package net.worldwizard.mazerunner;

public interface Scriptable {
    public void preMoveAction(Inventory inv);

    public void preMoveAction(boolean ie, int dirX, int dirY, Inventory inv);

    public void postMoveAction(Inventory inv);

    public void postMoveAction(boolean ie, int dirX, int dirY, Inventory inv);

    public boolean isConditionallySolid(Inventory inv);

    public boolean isConditionallyDirectionallySolid(boolean ie, int dirX,
            int dirY, Inventory inv);

    public MazeObject editorHook();

    public void playSound();

    public void pushAction(Inventory inv, MazeObject mo, int x, int y,
            int pushX, int pushY);

    public void pushIntoAction(Inventory inv, MazeObject pushed, int x, int y,
            int z, int w);

    public void pushOutAction(Inventory inv);

    public void pullAction(Inventory inv, MazeObject mo, int x, int y,
            int pullX, int pullY);

    public void pullIntoAction(Inventory inv);

    public void pullOutAction(Inventory inv);

    public void useAction(MazeObject mo, int x, int y, int z, int w);

    public void chainReactionAction(int x, int y, int z, int w);
}