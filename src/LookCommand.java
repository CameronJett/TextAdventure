
public class LookCommand {

    public String getResponse(Interactable entity, String name) {
        if(entity.getName().equals(name)) {
            return entity.getDescription();
        } else if (entity.contains(name)) {
            return entity.getPointOfInterest(name);
        }
        return "That isn't very interesting.";
    }
}
