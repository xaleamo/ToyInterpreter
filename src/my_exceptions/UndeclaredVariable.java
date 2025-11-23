package my_exceptions;

public class UndeclaredVariable extends ProgramStateException {
    public UndeclaredVariable(String message) {
        super(message);
    }
}
