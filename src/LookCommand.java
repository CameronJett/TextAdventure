public class LookCommand {

    public String getResponse(Interactable entity, String name) {
        if(entity.getName().equals(name) || name.equals(Const.LOOK)) {
            return entity.getDescription();
        } else if (entity.contains(name)) {
            return entity.getPointOfInterest(name);
        }
        return Const.LOOK_NOT_INTERESTING;
    }
}
