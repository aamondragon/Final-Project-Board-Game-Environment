package Exceptions;

public abstract class GameException extends Exception {

    private final String reason = "GAME ERROR";

    public void reason() {System.out.println(reason); }



}
