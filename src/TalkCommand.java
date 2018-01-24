public class TalkCommand implements Command {

    @Override
    public Dialog getResponse(Room room, String option) {
        if (room.hasPerson()) {
            if (room.getPerson().getName().toLowerCase().equalsIgnoreCase(option)
                    || option.equalsIgnoreCase(Const.TALK)) {
                //return dialog choices
                return room.getPerson().getDialogChoices();
            } else if (room.getPerson().hasDialog(option)) {
                //return dialog text
                return room.getPerson().getDialog(option);
            } else if (room.getPerson().hasDialog(Integer.parseInt(option))) {
                return room.getPerson().getDialog(Integer.parseInt(option));
            }
        }

        return new Dialog(Const.NO_ONE_TO_TALK_TO);
    }
}
