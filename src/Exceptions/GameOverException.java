package Exceptions;

public class GameOverException extends GameException {
    private final String reason = "GAME ALREADY OVER";

    public void reason() {
        System.out.println(reason);
    }

}
