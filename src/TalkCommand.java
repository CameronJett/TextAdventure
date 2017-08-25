
public class TalkCommand {

    public String getResponse(Person person, String option) {
        if (person.getName().equals(option) || option.equals(Const.TALK)) {
            //return dialog choices
            return person.getDialogChoices();
        } else if (person.hasDialog(option)) {
            //return dialog text
            return person.getDialog(option);
        }
        return Const.NO_ONE_TO_TALK_TO;
    }
}
