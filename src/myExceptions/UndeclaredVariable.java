package myExceptions;

public class UndeclaredVariable extends ProgramStateException {
    public UndeclaredVariable(String message) {
        super(message);
    }
}
