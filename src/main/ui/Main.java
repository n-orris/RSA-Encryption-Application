package ui;

import java.security.Security;
import model.CipherObj;

public class Main {
    public static void main(String[] args) throws Exception {



        CipherObj cipherObj = new CipherObj();

        byte[] test = cipherObj.encryptText("text test");


        System.out.println(cipherObj.decryptText(test));









    }
}
