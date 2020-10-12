package ui;


import model.CipherObj;


import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInteraction {
    private Scanner consoleScanner = new Scanner(System.in);
    private CipherObj cipherObj;
    private CipherObj test2;
    private Cipher cipher;
    private List<SealedObject> sealedObjectList = new ArrayList<>();


    public void consoleInput() throws Exception {
        welcomeArt();
        keyGenPrompt();





    }






    //EFFECTS: prints out key image and welcome text to console
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
                cipherObj.genKeyPair();
                System.out.println("New Public/Private keypair generated");
                keyOptions();
                break;
            } else if (response.toUpperCase().equals("N") || response.toUpperCase().equals("NO")) {
                System.out.println();
                cipherObj = new CipherObj();
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
        if (cipherObj.getPublicKey() == null) {
            System.out.println("It appears you dont have a public key stored.");
            System.out.println("Please enter public key modulus:");
            String modulus = consoleScanner.nextLine();
            // creates a public key
            cipherObj.createPublicKey(modulus);
        }

        System.out.println("Please enter Message you would like to encrypt:");
        String msg = consoleScanner.nextLine();

        SealedObject ciphertext = cipherObj.encryptText(msg);
        sealedObjectList.add(ciphertext);
        System.out.println("Your encrypted message is:");
        System.out.println(ciphertext);
        keyOptions();
    }

    public void decrypt() throws Exception {
        if (cipherObj.getPrivateKey() == null) {
            System.out.println("It appears you dont have a private key stored.");
            System.out.println("Please enter private key modulus:");
            String modulus = consoleScanner.nextLine();
            System.out.println("Please enter private key exponent");
            String exponent = consoleScanner.nextLine();
            // creates a public key
            cipherObj.createPrivateKey(modulus,exponent);

            System.out.println("Please enter encrypted text");
            byte[] ciphermsg = consoleScanner.nextLine().getBytes();
            //System.out.println(cipherObj.decryptText(ciphermsg));
        }

        for (SealedObject sobj : sealedObjectList) {
            System.out.println(cipherObj.decryptText(sobj));
        }
        keyOptions();



    }

    public void viewKeys() throws Exception {
        System.out.println(cipherObj.getPublicKey());
        System.out.println(cipherObj.getPrivateKey());
        keyOptions();
    }

}












