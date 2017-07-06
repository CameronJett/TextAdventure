
public class TalkCommand {

    public String getResponse(Person person, String option) {
        if (person.getName().equals(option)) {
            //return dialog choices
            return person.getDialogChoices();
        } else if (person.hasDialog(option)) {
            //return dialog text
            return person.getDialog(option);
        }
        return "There is no one to talk to.";
    }
}
