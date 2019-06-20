package Exceptions;


public class InvalidMoveException extends GameException {
    private final String reason = "INVALID MOVE";

    public void reason() {System.out.println(reason); }
}
