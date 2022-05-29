package model.domain.exception;

public class InvalidInputException extends Exception {

    private final String line;

    public InvalidInputException(final String line) {
        this.line = line;
    }

    public String getLine() {
        return this.line;
    }

}
