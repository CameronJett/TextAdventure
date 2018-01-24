public class Item extends GameEntity{
    private Room useLocation;
    private Room useExit;
    private boolean addsExit;

    public Item(String item_name, Dialog item_description) {
        super(item_name, item_description);
        useLocation = new Room("", new Dialog());
        addsExit = false;
    }

    public Item(String item_name, String item_description) {
        this(item_name, new Dialog(item_description));
    }

    public void addUseLocation(Room room) {
        useLocation = room;
    }

    public Room getUseLocation() {
        return useLocation;
    }

    public void addExitAfterUse(Room room) {
        useExit = room;
        addsExit = true;
    }

    public Room getExit() {
        return useExit;
    }

    public boolean addsExit() {
        return addsExit;
    }
}
