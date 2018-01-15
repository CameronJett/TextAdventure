public class HelpCommand implements Command {

    public String getResponse(Room room, String name) {
        return Const.HELP_RESPONSE;
    }
}
