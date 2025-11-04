package MyExceptions;

public class MyException extends RuntimeException {
    public MyException(String message) {
        super(message);
    }
    @Override
    public String getMessage() {
        return "\033[0;31m"+ super.getMessage()+"\033[0m";
    }
}
