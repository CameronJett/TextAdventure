public class GameEntity {
    private String name;
    private Dialog description;

    public GameEntity(String tempName, Dialog tempDescription) {
        name = tempName;
        description = tempDescription;
    }

    public Dialog getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
