package ui;


import model.Account;
import model.CipherObj;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Controls all console user interaction
public class UserInteraction {
    private Scanner consoleScanner = new Scanner(System.in);
    private static final String JSON_STORE = "./data/storeuser.json";
    private CipherObj cipherObj;
    private List<SealedObject> sealedObjectList = new ArrayList<>();
    private Account account;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    //EFFECTS: Starts the sequence of user interactions
    public UserInteraction() throws Exception {
        jsonWriter = new JsonWriter(JSON_STORE);
        //jsonReader = new JsonReader(JSON_STORE);
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
        System.out.println("2: Login to existing account");
        System.out.println("3: Enter existing keypair");
        System.out.println("4: Encrypt with public key");
        System.out.println();
        System.out.println("Select an option 1-4:");
    }

    //EFFECTS: inistantiates cipher object and
    public void initiateCipherObject() throws Exception {
        //inititate cipher object for this session
        cipherObj = new CipherObj();
        // Takes user input
        int num = consoleScanner.nextInt();
        consoleScanner.nextLine();

        if (num == 1) {
            genKeyOption();
        } else if (num == 2) {
            login();
        } else if (num == 3) {
            existingKeypair();
        } else if (num == 4) {
            encryptWithPublic();
        }
    }

    //EFFECTS: Generates a keypair
    public void genKeyOption() {
        cipherObj.genKeyPair("RSA");
        System.out.println("New Public/Private keypair generated");
        keyOptions();

    }

    public void login() {

    }

    //EFFECTS: Takes user input keypair, returns second options if valid, returns to first options if not valid
    public void existingKeypair() throws Exception {
        System.out.println("Enter public key Modulus:");
        String pubKey = consoleScanner.nextLine();
        System.out.println("Enter Private Key modulus:");
        String privMod = consoleScanner.nextLine();
        System.out.println("Enter private key exponent:");
        String privExp = consoleScanner.nextLine();
        cipherObj.createPublicKey(pubKey);
        cipherObj.createPrivateKey(privMod, privExp);
        if (cipherObj.validPair(cipherObj.getPublicKey(), cipherObj.getPrivateKey())) {
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

    //EFFECTS: prompts user to enter public key then message, encrypts message and displays it
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


    //EFFECTS: prompts user to enter option and then runs related option method
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
                setCipher();
            } else if (choice == 6) {
                System.out.println("Input cipher name: ");
                addCipher(consoleScanner.nextLine());
            } else if (choice == 7) {
                saveAccount();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //EFFECTS: Prints options to console
    public void optionsText() {
        System.out.println("Options: ");
        System.out.println("1: Encrypt Message");
        System.out.println("2: Decrypt stored Messages");
        System.out.println("3: View your Keys");
        System.out.println("4: Create Account");
        System.out.println("5: Set to specific cipher");
        System.out.println("6: Create and add new cipher to account");
        System.out.println("7: Save Account");
        System.out.println();
        System.out.println("Please select 1-7, or 8 to exit program :");
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
        if (account == null) {
            cipherObj.encryptText(msg);
        } else {
            account.getAccountCipher().encryptText(msg);
        }
        keyOptions();
    }

    //EFFECTS: Decrypts a sealed object, if no existing private key, prompts user to enter a key for decryption
    public void decrypt() {
        if (account == null) {
            System.out.println("null option");
            for (SealedObject sobj : cipherObj.getEncryptedMsgs()) {
                System.out.println(cipherObj.decryptText(sobj));
            }
        } else {
            System.out.println(account.getAccountCipher().getEncryptedMsgs());
            List<SealedObject> msgs = account.getAccountCipher().getEncryptedMsgs();

            for (SealedObject obj : msgs) {
                System.out.println(account.getAccountCipher().decryptText(obj));
            }
        }
        keyOptions();
    }

    //EFFECTS: Displays current keypair to console, null if no keypair
    public void viewKeys() {
        if (account == null) {
            System.out.println(cipherObj.getPublicKey());
            System.out.println(cipherObj.getPrivateKey());
        } else {
            System.out.println(account.getAccountCipher().getPublicKey());
            System.out.println(account.getAccountCipher().getPrivateKey());
        }
        keyOptions();
    }


    //EFFECTS: creates an account for the user with the current cipherObj,encrypted msgs list and a username.
    // If invalid keypair it prints error msg.Both options return to key menu
    public void createAccount() {
        if (cipherObj.validPair(cipherObj.getPublicKey(), cipherObj.getPrivateKey())) {
            System.out.println("Please enter username");
            String user = consoleScanner.nextLine();
            account = new Account(cipherObj, user);
            System.out.println("Account created succesfully");
            System.out.println();
            keyOptions();
        } else {
            System.out.println("Invalid key pair,please generate key pair");
            keyOptions();
        }
    }

    //EFFECTS: Adds the current cipher object to an account, if no existing account warns user and returns to options
    public void addCipher(String name) throws NoSuchPaddingException, NoSuchAlgorithmException {
        if (account == null) {
            System.out.println("No account, Please create an account");
            keyOptions();
        } else {
            CipherObj nm = new CipherObj();
            nm.genKeyPair("RSA");
            account.newCipher(nm, sealedObjectList);
            int size = account.getCipherSize();
            account.useCipher(size);
            System.out.println(account.getAccountCipher());
            System.out.println("Your cipher object was insert as cipher # " + size);
            System.out.println("To retrieve this cipher, remember its #");
            account.useCipher(size);
            account.getAccountCipher();
            System.out.println("Now set to new cipher");
            keyOptions();

        }

    }

    public void saveAccount() {
        try {
            jsonWriter.open();
            jsonWriter.write(account);
            jsonWriter.close();
            System.out.println("Saved " + account.getId() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }

    }

    public void setCipher() {
        System.out.println("Please enter id of cipher you would like to use");
        int id = consoleScanner.nextInt();
        try {
            account.useCipher(id);
            System.out.println("Cipher #" + id + " set");
            keyOptions();
        } catch (Exception e) {
            System.out.println("Not a valid Cipher, please try again");
            e.printStackTrace();
        }
    }

}













