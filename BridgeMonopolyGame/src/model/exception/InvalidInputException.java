package model.exception;

public class InvalidInputException extends Exception {

    private final String line;

    public InvalidInputException(final String line) {
        this.line = line;
    }

    public String getLine() {
        return this.line;
    }

    @Override
    public String toString() {
        return "Invalid input : " + line;
    }

}
