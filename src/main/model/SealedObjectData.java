package model;

import javax.crypto.SealedObject;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class SealedObjectData {
    CipherObj cipherObj;


    public SealedObjectData() throws Exception {
        cipherObj = new CipherObj();
        cipherObj.genKeyPair();
    }

    public void inputSealedObject() {

    }

    //REQUIRES: Non Null input
    //MODIFIES: sealedobj.txt file
    //EFFECTS: writes sealedobject list to output file
    public void outputSealedObject(List<SealedObject> sealedObjects) throws IOException {
        FileOutputStream fos = new FileOutputStream("sealedobject.txt");
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(sealedObjects);
        os.close();
    }
}
