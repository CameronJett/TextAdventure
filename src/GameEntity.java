public class GameEntity {
    private String name;
    private String description;

    public GameEntity(String tempName, String tempDescription) {
        name = tempName;
        description = tempDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
