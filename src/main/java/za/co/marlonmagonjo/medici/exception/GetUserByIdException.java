package za.co.marlonmagonjo.medici.exception;

public class GetUserByIdException extends RuntimeException {
    public GetUserByIdException(String message) {
        super(message);
    }

    public GetUserByIdException(String message, Throwable cause) {
        super(message, cause);
    }
}