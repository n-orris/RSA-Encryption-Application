package exceptions;

public class InvalidKeyPairException extends Exception {

    public InvalidKeyPairException() {

    }

    public InvalidKeyPairException(String msg) {
        super(msg);
    }
}
