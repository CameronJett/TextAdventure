import java.util.ArrayList;
import java.util.List;

public class Dialog {
    ArrayList<String> dialog;

    Dialog() {
        dialog = new ArrayList<>();
    }

    Dialog(String line) {
        this();
        addLineOfDialog(line);
    }

    Dialog(List<String> lines) {
        this();
        addLineOfDialog(lines);
    }

    public void addLineOfDialog(String line) {
        dialog.add(line);
    }

    public void addLineOfDialog(List<String> lines) {
        dialog.addAll(lines);
    }

    public void clear() {
        dialog.clear();
    }

    public List<String> readDialog() {
        return dialog;
    }

    @Override
    public boolean equals(Object thatObj) {
        if (this == thatObj) { return true; }

        if (!(thatObj instanceof Dialog)) { return false; }
        Dialog that = (Dialog) thatObj;

        return dialog.equals(that.dialog);
    }

    public Dialog add(Dialog secondHalf) {
        List<String> returnDialog = dialog;
        returnDialog.addAll(secondHalf.dialog);
        return new Dialog(returnDialog);
    }
}
