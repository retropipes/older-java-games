package studio.ignitionigloogames.dungeondiver1.dungeon.objects;

public class Player extends GenericRONSObject {
    // Serialization
    private static final long serialVersionUID = -329523532523463L;

    // Constructors
    public Player() {
        super(false, "Player");
    }

    @Override
    public void moveOntoHook() {
        // Do nothing
    }
}
