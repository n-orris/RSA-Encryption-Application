package exceptions;

// Exception for methods checking encryption key pairs
public class InvalidKeyPairException extends Exception {

    public InvalidKeyPairException() {

    }

    //EFFECTS: takes a message that can be displayed when exception is encountered
    public InvalidKeyPairException(String msg) {
        super(msg);
    }
}
