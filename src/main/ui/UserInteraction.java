package ui;


import exceptions.InvalidKeyPairException;
import exceptions.PrivateKeyException;
import exceptions.PublicKeyException;
import model.Account;
import model.CipherObj;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
    private LoginWindow loginWindow;
    private UserPanel userPanel;
    private UserKeysPanel userKeysPanel;


    //EFFECTS: Starts the sequence of user interactions
    public UserInteraction() throws Exception {

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        loginWindow = new LoginWindow();
        while (true) {
            Thread.sleep(2500);
            if (loginWindow.requestLogin()) {
                login();
                openUserPanel();
            } else if (loginWindow.requestNewAccount()) {
                System.out.println("Creating New Account...");
                genKeyOption();
                createAccount();
                openUserPanel();
            }
        }
    }

    //EFFECTS: create a new userpanel and then displays it
    public void openUserPanel() throws NoSuchPaddingException, NoSuchAlgorithmException, InterruptedException {
        System.out.println("User Page displayed");
        userPanel = new UserPanel();
        userPanel.getContentPane();
        userActions();
    }

    //EFFECTS: returns a useraction based of GUI input
    public void userActions() throws NoSuchAlgorithmException, NoSuchPaddingException, InterruptedException {

        while (true) {
            Thread.sleep(2500);
            if (userPanel.getAction("displayKeys")) {
                System.out.println("Displaying keys....");
                userKeysPanel = new UserKeysPanel(account);
                break;
            } else if (userPanel.getAction("setKey")) {
                System.out.println("Setting Key.....");
                setCipher();
                break;
            } else if (userPanel.getAction("addNewKey")) {
                System.out.println("Adding New Key...");
                addCipher();
                openUserPanel();
                break;
            } else if (userPanel.getAction("saveProfile")) {
                System.out.println("Saving Account...");
                saveAccount();
                break;
            }
        }
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
    public void genKeyOption() throws NoSuchPaddingException, NoSuchAlgorithmException {
        cipherObj = new CipherObj();
        cipherObj.genKeyPair("RSA");
        System.out.println("New Public/Private keypair generated");

    }

    //MODIFIES: account
    //EFFECTS:  logs into stored account
    public void login() {
        try {
            jsonReader.read();
            account = jsonReader.read();
            account.useCipher(1);
            System.out.println("Loaded " + account.getId() + " from " + JSON_STORE);

        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } catch (Exception e) {
            //
        }
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
        try {
            System.out.println("Please enter public modulus for encryption");
            String pubKey = consoleScanner.nextLine();
            System.out.println("Plese Enter message to encrypt:");
            String plainText = consoleScanner.nextLine();
            cipherObj.createPublicKey(pubKey);
            cipherObj.encryptText(plainText);
            keyOptions();
        } catch (PublicKeyException e) {
            System.out.println("Invalid public key, Please create a valid one");
            keyOptions();
        }
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
                addCipher();
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

        try {

            System.out.println("Please enter Message you would like to encrypt:");
            String msg = consoleScanner.nextLine();
            if (account == null) {
                cipherObj.encryptText(msg);
            } else {
                account.getAccountCipher().encryptText(msg);
            }
            keyOptions();
        } catch (PublicKeyException e) {
            System.out.println(e.getMessage());
            keyOptions();
        }
    }

    //EFFECTS: Decrypts a sealed object, if no existing private key, prompts user to enter a key for decryption
    public void decrypt() {
        try {
            if (account == null) {
                for (SealedObject sobj : cipherObj.getEncryptedMsgs()) {
                    System.out.println(cipherObj.decryptText(sobj));
                }
            } else {
                List<SealedObject> msgs = account.getAccountCipher().getEncryptedMsgs();

                for (SealedObject obj : msgs) {
                    System.out.println(account.getAccountCipher().decryptText(obj));
                }
            }
            keyOptions();
        } catch (PrivateKeyException e) {
            System.out.println(e.getMessage());
            keyOptions();
        }
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
        try {
            if (cipherObj.validPair(cipherObj.getPublicKey(), cipherObj.getPrivateKey())) {
                System.out.println("Please enter username");
                String user = consoleScanner.nextLine();
                account = new Account(user);
                account.newCipher(cipherObj);
                System.out.println("Account created succesfully");
                System.out.println();
            }
        } catch (InvalidKeyPairException e) {
            System.out.println(e.getMessage());
        }
    }


    //EFFECTS: Adds the current cipher object to an account, if no existing account warns user and returns to options
    public void addCipher() {
        try {
            if (account == null) {
                System.out.println("No account, Please create an account");
                keyOptions();
            } else {
                CipherObj nm = new CipherObj();
                nm.genKeyPair("RSA");
                account.newCipher(nm);
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
            keyOptions();
        }

    }

    //MODIFEIS: data file
    //EFFECTS: outputs current account data to file
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

    // Sets which CipherObj to use

    //EFFECTS Sets the current Cipher
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













