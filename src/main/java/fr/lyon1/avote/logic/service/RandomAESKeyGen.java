package fr.lyon1.avote.logic.service;


import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RandomAESKeyGen {

    public static String generate(final int keyLen) throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[keyLen / 8];
        random.nextBytes(bytes);
        return DatatypeConverter.printHexBinary(bytes).toLowerCase();
    }

}
