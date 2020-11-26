package exceptions;

// Exception for methods that use a private key
public class PrivateKeyException extends Exception {

    public PrivateKeyException() {

    }

    //EFFECTS: takes a message that can be displayed when exception is encountered
    public PrivateKeyException(String msg) {
        super(msg);
    }
}
