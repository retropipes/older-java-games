package studio.ignitionigloogames.dungeondiver1.dungeon.objects;

public abstract class GenericRequiredObject extends DungeonObject {
    // Serialization
    private static final long serialVersionUID = 403448605235302L;

    public GenericRequiredObject(final boolean solid, final String name) {
        super(solid, name, null);
    }

    @Override
    public boolean isRequired() {
        return true;
    }
}
