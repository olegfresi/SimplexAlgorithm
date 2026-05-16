package Parser.Visitor;

public class ParseException extends RuntimeException {
    private final int line;

    public ParseException(String message, int line) {
        super("Error at line " + line + ": " + message);
        this.line = line;
    }

    public int getLine() {
        return line;
    }
}