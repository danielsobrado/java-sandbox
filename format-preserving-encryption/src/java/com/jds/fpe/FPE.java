package com.jds.fpe;

import com.idealista.fpe.FormatPreservingEncryption;
import com.idealista.fpe.builder.FormatPreservingEncryptionBuilder;
import com.idealista.fpe.config.Domain;
import com.idealista.fpe.config.GenericDomain;
import com.idealista.fpe.config.GenericTransformations;
import com.idealista.fpe.transformer.IntToTextTransformer;
import com.idealista.fpe.transformer.TextToIntTransformer;

public class FPE {
	
	public static final NumbersAlphabet ALPHABET = new NumbersAlphabet();
    static final TextToIntTransformer TEXT_TO_INT_TRANSFORMER = new GenericTransformations(ALPHABET.availableCharacters());
    static final IntToTextTransformer INT_TO_TEXT_TRANSFORMER = new GenericTransformations(ALPHABET.availableCharacters());
    static final Domain NUMBERS_DOMAIN = new GenericDomain(new NumbersAlphabet(), TEXT_TO_INT_TRANSFORMER, INT_TO_TEXT_TRANSFORMER);

    public static void main(String[] args) {
        System.out.println("Start testing format preserving encryption.");
        
        
        byte[] keyByteArrray = new byte[]{
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x01,
                (byte) 0x02, (byte) 0x02, (byte) 0x02, (byte) 0x02,
                (byte) 0x03, (byte) 0x03, (byte) 0x03, (byte) 0x03
        };        
        
        FormatPreservingEncryption defaultFormatPreservingEncryption1 = FormatPreservingEncryptionBuilder.ff1Implementation()
                    .withDefaultDomain()
                    .withDefaultPseudoRandomFunction(keyByteArrray)
                    .withDefaultLengthRange()
                    .build();
        
        String testString1 = "danielsobrado";
        
        byte[] anyTweak = new byte[]{
                (byte) 0x01, (byte) 0x03, (byte) 0x02, (byte) 0x04
        };
        
        // Usage
        String cipherText1 = defaultFormatPreservingEncryption1.encrypt(testString1, anyTweak);
        String plainText1 = defaultFormatPreservingEncryption1.decrypt(testString1, anyTweak);
        
        System.out.println("Plain text: " + plainText1);
        System.out.println("Ciphered text: " + cipherText1);
                
        FormatPreservingEncryption formatPreservingEncryption = FormatPreservingEncryptionBuilder
                .ff1Implementation()
                .withDomain(NUMBERS_DOMAIN)
                .withDefaultPseudoRandomFunction(keyByteArrray)
                .withDefaultLengthRange()
                .build();
        
        String testString2 = "12345";
        
        // Usage
        String cipherText2 = formatPreservingEncryption.encrypt(testString2, anyTweak);
        String plainText2 = formatPreservingEncryption.decrypt(testString2, anyTweak);
        
        System.out.println("Plain text: " + plainText2);
        System.out.println("Ciphered text: " + cipherText2);

    }    

}