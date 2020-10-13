package model;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SealedObject;
import java.io.*;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class SealedObjectData {
    CipherObj cipherObj;


    public SealedObjectData() throws Exception {
        cipherObj = new CipherObj();
        cipherObj.genKeyPair();
    }

    public void inputSealedObject(PrivateKey privateKey) throws Exception {
        FileInputStream fis = new FileInputStream("sealedobject");
        ObjectInputStream ois = new ObjectInputStream(fis);
        ArrayList<SealedObject> obj = (ArrayList<SealedObject>) ois.readObject();
        for (SealedObject o : obj) {
            System.out.println(o.getObject(privateKey));
        }
        //Object data = obj.getObject(cipherObj.getPrivateKey());
        ois.close();
        // System.out.println(data);
    }

    //REQUIRES: Non Null input
    //MODIFIES: sealedobj.txt file
    //EFFECTS: writes sealedobject list to output file
    public void outputSealedObject(List<SealedObject> sealedObjects) throws IOException {
        FileOutputStream fos = new FileOutputStream("sealedobject");
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(sealedObjects);
        os.close();
    }

    public void base64SealedObject(PrivateKey privateKey,SealedObject sobj,Cipher ecipher,Cipher dcipher) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            CipherOutputStream cipherOutputStream = new CipherOutputStream(
                    outputStream,ecipher);

            ObjectOutputStream oos = new ObjectOutputStream(cipherOutputStream);
            oos.writeObject(sobj);
            cipherOutputStream.close();

            byte[] values = outputStream.toByteArray();

            String base64encoded = Base64.getEncoder().encodeToString(values);
            System.out.println(base64encoded);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


