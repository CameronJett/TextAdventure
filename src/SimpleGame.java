public class SimpleGame {
    public static void main(String [ ] args) {
        Game myGame = new Game();
        myGame.load(Const.SIMPLE_GAME_LOAD_FILE);
        myGame.run();
    }
}
