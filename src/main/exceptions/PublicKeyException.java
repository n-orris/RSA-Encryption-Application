package exceptions;

// Exception for methods that use a public key
public class PublicKeyException extends Exception {

    public PublicKeyException() {

    }


    //EFFECTS: takes a message that can be displayed when exception is encountered
    public PublicKeyException(String msg) {
        super(msg);
    }
}
