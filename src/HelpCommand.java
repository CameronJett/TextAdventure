public class HelpCommand implements Command {

    @Override
    public Dialog getResponse(Room room, String name) {
        return new Dialog(Const.HELP_RESPONSE);
    }
}
