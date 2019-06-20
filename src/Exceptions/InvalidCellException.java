package Exceptions;

public class InvalidCellException extends GameException {
    private final String reason = "INVALID CELL";

    public void reason() {
        System.out.println(reason);
    }
}
