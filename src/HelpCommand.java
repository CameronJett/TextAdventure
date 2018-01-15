public class HelpCommand implements Command {

    @Override
    public String getResponse(Room room, String name) {
        return Const.HELP_RESPONSE;
    }
}
