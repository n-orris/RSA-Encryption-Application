package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;
import persistence.Writable;

//
import javax.crypto.NoSuchPaddingException;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class JsonWriterTests {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination = "./data/writertest.json";
    private String badDestination = ".ta/write";
    private CipherObj cipherObj;
    private Reader reader;

    @BeforeEach
    void setup() throws NoSuchPaddingException, NoSuchAlgorithmException {
        cipherObj = new CipherObj();
        this.destination = destination;
        this.badDestination = badDestination;
    }

    @Test
    void open() {
        try {
            writer = new PrintWriter(new File(destination));
            assertTrue(writer != null);
        } catch (FileNotFoundException e) {
            fail("valid file");
        }
        String t = "Sun RSA private key, 2048 bits\n  params: null\n  modulus: ";
        System.out.println(t.length());

        try {
            writer = new PrintWriter(new File(badDestination));
            fail("invalid file");
        } catch (FileNotFoundException e) {
            //expected result
        }

    }

    @Test
    void writerEmptyFile() {
        try {
            cipherObj.genKeyPair("RSA");
            Account ac = new Account("user");
            ac.newCipher(cipherObj);
            JsonWriter writer = new JsonWriter("./data/writertest.jso");
            writer.open();
            writer.write(ac);
            writer.close();

            JsonReader reader = new JsonReader("./data/writertest.json");
            ac = reader.read();
            assertEquals("user", ac.getId());
            assertEquals(2, ac.getCipherSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testWriterExistingFile() {
        try {
            cipherObj.genKeyPair("RSA");
            Account ac = new Account("user");
            ac.newCipher(cipherObj);
            CipherObj test1 = new CipherObj();
            CipherObj test2 = new CipherObj();
            test1.genKeyPair("RSA");
            test2.genKeyPair("RSA");
            ac.newCipher(test1);
            ac.newCipher(test2);
            JsonWriter writer = new JsonWriter("./data/testWriterAccount.json");
            writer.open();
            writer.write(ac);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterAccount.json");
            ac = reader.read();
            assertEquals("user", ac.getId());
            assertEquals(4, ac.getCipherSize());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
