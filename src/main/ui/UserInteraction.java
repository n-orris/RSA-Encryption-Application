package ui;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import model.CipherObj;
import org.w3c.dom.ls.LSOutput;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Scanner;

public class UserInteraction {
    private Scanner consoleScanner = new Scanner(System.in);
    private CipherObj cipherObj;
    private CipherObj test2;
    private Cipher cipher;


    public void consoleInput() throws Exception {
        //welcomeArt();
        //keyGenPrompt();




    }







    public void welcomeArt() {
        System.out.println("   ______");
        System.out.println("  /  __  \\     _    _    _   _");
        System.out.println(" |  |  |  \\___| |__| |__| | / | ");
        System.out.println(" |  |__|   ___________________|");
        System.out.println("  \\______/");
        System.out.println();
        System.out.println("-------WELCOME TO ULOCK-------");
        System.out.println();
    }

    public void keyGenPrompt() throws Exception {
        System.out.println("Would you like to generate a new keyset? (Y/N):");
        while (true) {
            // Takes user string input
            String response = consoleScanner.nextLine();

            if (response.toUpperCase().equals("Y") || response.toUpperCase().equals("YES")) {
                cipherObj = new CipherObj();
                System.out.println("New Public/Private keypair generated");
                keyOptions();
                break;
            } else if (response.toUpperCase().equals("N") || response.toUpperCase().equals("NO")) {
                System.out.println();
                keyOptions();
                break;
            } else {
                System.out.println("Please enter either Y or N");
            }
        }
    }

    public void keyOptions() throws Exception {
        System.out.println();
        System.out.println("Options: ");
        System.out.println("1: Encrypt Message ");
        System.out.println("2: Decrypt Message ");
        System.out.println("3: View your Keys");
        System.out.println();
        System.out.println("Please select a number:");

        int choice = consoleScanner.nextInt();
        consoleScanner.nextLine();

        // Encrypts text and then outputs encryption to console
        if (choice == 1 && cipherObj != null) {
            encrypt();
        } else if (choice == 2) {
            decrypt();
        } else if (choice == 3 && cipherObj != null) {
            viewKeys();
        } else {
            System.out.println("Invalid Number, program ending");
        }
    }

    //MODIFIES: Assigns encrypted value to cipherObj instance
    //EFFECTS:  Takes user message, encrypts it with copherObj instance and displays
    //          encrypted message in console
    public void encrypt() throws Exception {
        System.out.println("Please enter Message you would like to encrypt:");
        String msg = consoleScanner.nextLine();

        byte[] ciphertext = cipherObj.encryptText(msg);
        System.out.println("Your encrypted message is:");
        System.out.println(ciphertext);
    }

    public void decrypt() {


    }

    public void viewKeys() {
        System.out.println(cipherObj.getPublicKey());
        System.out.println(cipherObj.getPrivateKey());
    }

}












