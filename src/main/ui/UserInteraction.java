package ui;



import model.Account;
import model.CipherObj;
import javax.crypto.SealedObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInteraction {
    private Scanner consoleScanner = new Scanner(System.in);
    private CipherObj cipherObj;
    private List<SealedObject> sealedObjectList = new ArrayList<>();
    private Account account;



    public void consoleInput() throws Exception {
        welcomeArt();
        initiateCipherObject();

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
        System.out.println("Options:");
        System.out.println("1: Generate keypair (New Account)");
        System.out.println("2: Enter existing keypair");
        System.out.println("3: Encrypt with public key");
        System.out.println();
        System.out.println("Select an option 1-3:");
    }

    //REQUIRES:
    public void initiateCipherObject() throws Exception {
        //inititate cipher object for this session
        cipherObj = new CipherObj();
        // Takes user input
        int num = consoleScanner.nextInt();
        consoleScanner.nextLine();

        if (num == 1) {
            genKeyOption();
        } else if (num == 2) {
            existingKeypair();
        } else if (num == 3) {
            encryptWithPublic();
        }
    }

    public void genKeyOption() {
        if (account != null) {
            System.out.println("Already contains valid keypair");
            keyOptions();
        } else {

            cipherObj.genKeyPair("RSA");
            System.out.println("New Public/Private keypair generated");
            keyOptions();
        }
    }

    public void existingKeypair() throws Exception {
        System.out.println("Enter public key Modulus:");
        String pubKey = consoleScanner.nextLine();
        System.out.println("Enter Private Key modulus:");
        String privMod = consoleScanner.nextLine();
        System.out.println("Enter private key exponent:");
        String privExp = consoleScanner.nextLine();
        cipherObj.createPublicKey(pubKey);
        cipherObj.createPrivateKey(privMod,privExp);
        if (cipherObj.validPair(cipherObj.getPublicKey(),cipherObj.getPrivateKey())) {
            System.out.println("Keypair succesfully added");
            keyOptions();
        } else {
            System.out.println("ERROR: Invalid keypair");
            initiateCipherObject();
        }
    }

    //REQUIRES: Valid RSA public key modulus (617 char hexstring)
    //MODIFIES: creates and inserts public key into cipherObj
    //EFFECTS:

    public void encryptWithPublic() {
        System.out.println("Please enter public modulus for encryption");
        String pubKey = consoleScanner.nextLine();
        System.out.println("Plese Enter message to encrypt:");
        String plainText = consoleScanner.nextLine();
        cipherObj.createPublicKey(pubKey);
        cipherObj.encryptText(plainText);
        keyOptions();
    }

    // Code is part of P2!!
    //public void decryptWithPrivate() {
    // System.out.println("Enter private key modulus:");
    // String privMod = consoleScanner.nextLine();
    // System.out.println("Enter private key exponent:");
    // String privExp = consoleScanner.nextLine();
    // System.out.println("Enter encrypted message");
    // byte[] cipherText = consoleScanner.nextLine().getBytes();
    // keyOptions();
    //}


    public void keyOptions() {
        try {
            optionsText();
            int choice = consoleScanner.nextInt();
            consoleScanner.nextLine();

            if (choice == 1) {
                encrypt();
            } else if (choice == 2) {
                decrypt();
            } else if (choice == 3) {
                viewKeys();
            } else if (choice == 4) {
                createAccount();
            } else if (choice == 5) {
                genKeyOption();
            } else {
                System.out.println("Invalid program");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void optionsText() {
        System.out.println();
        System.out.println("Options: ");
        System.out.println("1: Encrypt Message");
        System.out.println("2: Decrypt stored Messages");
        System.out.println("3: View your Keys");
        System.out.println("4: Create Account");
        System.out.println("5: Generate Keypair");
        System.out.println();
        System.out.println("Please select a number :");
    }

    //MODIFIES: Assigns encrypted value to cipherObj instance
    //EFFECTS:  Takes user message, encrypts it with copherObj instance and displays
    //          encrypted message in console
    public void encrypt() {
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

    public void decrypt() {
        if (cipherObj.getPrivateKey() == null) {
            System.out.println("It appears you dont have a private key stored.");
            System.out.println("Please enter private key modulus:");
            String modulus = consoleScanner.nextLine();
            System.out.println("Please enter private key exponent");
            String exponent = consoleScanner.nextLine();
            // creates a public key
            cipherObj.createPrivateKey(modulus, exponent);
        }
        System.out.println();
        for (SealedObject sobj : sealedObjectList) {
            System.out.println(cipherObj.decryptText(sobj));
        }
        keyOptions();


    }

    public void viewKeys() {
        System.out.println(cipherObj.getPublicKey());
        System.out.println(cipherObj.getPrivateKey());
        keyOptions();
    }


    //EFFECTS: creates an account for the user with the current cipherObj,encrypted msgs list and a username.
    // If invalid keypair it prints error msg.Both options return to key menu
    public void createAccount() {
        if (cipherObj.validPair(cipherObj.getPublicKey(), cipherObj.getPrivateKey())) {
            System.out.println("Please enter username");
            String user = consoleScanner.nextLine();
            account = new Account(cipherObj, sealedObjectList, user);
            System.out.println("Account created succesfully");
            System.out.println();
            keyOptions();
        } else {
            System.out.println("Invalid key pair,please generate key pair");
            keyOptions();
        }
    }


}













