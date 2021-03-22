package com.jds.fpe;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import com.idealista.fpe.FormatPreservingEncryption;
import com.idealista.fpe.builder.FormatPreservingEncryptionBuilder;
import com.idealista.fpe.config.Domain;
import com.idealista.fpe.config.GenericDomain;
import com.idealista.fpe.config.GenericTransformations;
import com.idealista.fpe.transformer.IntToTextTransformer;
import com.idealista.fpe.transformer.TextToIntTransformer;
import com.jds.fpe.alphabet.CapitalAlphabet;
import com.jds.fpe.alphabet.CompleteAlphabet;
import com.jds.fpe.alphabet.NumbersAlphabet;

public class Encryption {

	public static final NumbersAlphabet NUMBERS_ALPHABET = new NumbersAlphabet();
	public static final CompleteAlphabet ALL_LETTERS_ALPHABET = new CompleteAlphabet();
	public static final CapitalAlphabet CAP_LETTERS_ALPHABET = new CapitalAlphabet();
    static final TextToIntTransformer TEXT_TO_INT_TRANSFORMER = new GenericTransformations(NUMBERS_ALPHABET.availableCharacters());
    static final IntToTextTransformer INT_TO_TEXT_TRANSFORMER = new GenericTransformations(NUMBERS_ALPHABET.availableCharacters());
    static final TextToIntTransformer ALL_TEXT_TO_INT_TRANSFORMER = new GenericTransformations(ALL_LETTERS_ALPHABET.availableCharacters());
    static final IntToTextTransformer INT_TO_ALL_TEXT_TRANSFORMER = new GenericTransformations(ALL_LETTERS_ALPHABET.availableCharacters());
    static final TextToIntTransformer CAP_TEXT_TO_INT_TRANSFORMER = new GenericTransformations(CAP_LETTERS_ALPHABET.availableCharacters());
    static final IntToTextTransformer INT_TO_CAP_TEXT_TRANSFORMER = new GenericTransformations(CAP_LETTERS_ALPHABET.availableCharacters());
    static final Domain NUMBERS_DOMAIN = new GenericDomain(new NumbersAlphabet(), TEXT_TO_INT_TRANSFORMER, INT_TO_TEXT_TRANSFORMER);
    static final Domain ALL_LETTERS_DOMAIN = new GenericDomain(new CapitalAlphabet(), ALL_TEXT_TO_INT_TRANSFORMER, INT_TO_ALL_TEXT_TRANSFORMER);
    static final Domain CAP_LETTERS_DOMAIN = new GenericDomain(new CapitalAlphabet(), CAP_TEXT_TO_INT_TRANSFORMER, INT_TO_CAP_TEXT_TRANSFORMER);
	
    static byte[] keyByteArrray = new byte[]{
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x01,
            (byte) 0x02, (byte) 0x02, (byte) 0x02, (byte) 0x02,
            (byte) 0x03, (byte) 0x03, (byte) 0x03, (byte) 0x03
    }; 
    
    static byte[] anyTweak = new byte[]{
            (byte) 0x01, (byte) 0x03, (byte) 0x02, (byte) 0x04
    };
    
    public static Integer encryptInt(Integer input) {
    	
        FormatPreservingEncryption numbersFormatPreservingEncryption = FormatPreservingEncryptionBuilder
                .ff1Implementation()
                .withDomain(NUMBERS_DOMAIN)
                .withDefaultPseudoRandomFunction(keyByteArrray)
                .withDefaultLengthRange()
                .build();
        
        // Min lenght is 2 characters
        if (input < 10) {
        	input = input + 10;
        }
        
        String cipherText = numbersFormatPreservingEncryption.encrypt(input.toString(), anyTweak);
        
		return Integer.parseInt(cipherText);
    	    	
    }

    public static String encryptStr(String input) {
    	
        FormatPreservingEncryption defaultFormatPreservingEncryption1 = FormatPreservingEncryptionBuilder.ff1Implementation()
                .withDefaultDomain()
                .withDefaultPseudoRandomFunction(keyByteArrray)
                .withDefaultLengthRange()
                .build();
        
        FormatPreservingEncryption numbersFormatPreservingEncryption = FormatPreservingEncryptionBuilder
                .ff1Implementation()
                .withDomain(NUMBERS_DOMAIN)
                .withDefaultPseudoRandomFunction(keyByteArrray)
                .withDefaultLengthRange()
                .build();
        
        FormatPreservingEncryption allLettersFormatPreservingEncryption = FormatPreservingEncryptionBuilder
                .ff1Implementation()
                .withDomain(ALL_LETTERS_DOMAIN)
                .withDefaultPseudoRandomFunction(keyByteArrray)
                .withDefaultLengthRange()
                .build();
        
        FormatPreservingEncryption capLettersFormatPreservingEncryption = FormatPreservingEncryptionBuilder
                .ff1Implementation()
                .withDomain(CAP_LETTERS_DOMAIN)
                .withDefaultPseudoRandomFunction(keyByteArrray)
                .withDefaultLengthRange()
                .build();
    	
        HashMap<Integer, Character> othersMap = new HashMap<Integer, Character>(); 
        StringBuilder numbers = new StringBuilder();
        Queue<Character> queueOthers = new LinkedList<Character>();
        StringBuilder originalLetters = new StringBuilder();
        StringBuilder lowerLetters = new StringBuilder();
        StringBuilder capLetters = new StringBuilder();
        
        for (int i = 0; i < input.length(); i++){
        	Character currChar = input.charAt(i);
            if ((Character.isLetter(currChar)) && (Character.isLowerCase(currChar))) {
            	lowerLetters.append(currChar);
            	originalLetters.append(currChar);
            } else if ((Character.isLetter(currChar)) && (!Character.isLowerCase(currChar))) {
            	capLetters.append(currChar);
            	originalLetters.append(currChar);
            } else if (Character.isDigit(currChar)) {
            	numbers.append(currChar);
            } else {
            	othersMap.put(i, currChar);
            	queueOthers.add(currChar);
            }
        }
        
        System.out.println("Original Numbers: " + numbers);
        System.out.println("Original Letters: " + originalLetters);
        System.out.println("Original Cap Letters: " + capLetters);
        System.out.println("Original Lower Letters: " + lowerLetters);
        
        String numbersEnc;
        if (numbers.length() > 1) {
        	numbersEnc = numbersFormatPreservingEncryption.encrypt(numbers.toString(), anyTweak);
        } else {
        	numbersEnc = numbers.toString();
        }
        
        String lowerLettersEnc;
        if (lowerLetters.length() > 1) {
        	lowerLettersEnc = defaultFormatPreservingEncryption1.encrypt(lowerLetters.toString(), anyTweak);
        } else {
        	lowerLettersEnc = lowerLetters.toString();
        }
        
        String capLettersEnc;
        if (capLetters.length() > 1) {
        	capLettersEnc = capLettersFormatPreservingEncryption.encrypt(capLetters.toString(), anyTweak);
        } else {
        	capLettersEnc = capLetters.toString();
        }
        
        System.out.println("Numbers Encoded: " + numbersEnc);
        System.out.println("Lower Letters Encoded: " + lowerLettersEnc);
        System.out.println("Cap Letters Encoded: " + capLettersEnc);
        
        Character[] charNumbers = numbersEnc.chars().mapToObj(ch -> (char) ch).toArray(Character[]::new);
        Queue<Character> queueNumbers = new LinkedList<Character>(Arrays.asList(charNumbers));
        
        Character[] charLowerLetters = lowerLettersEnc.chars().mapToObj(ch -> (char) ch).toArray(Character[]::new);
        Queue<Character> lowerQueueLetters = new LinkedList<Character>(Arrays.asList(charLowerLetters));
        
        Character[] charCapLetters = capLettersEnc.chars().mapToObj(ch -> (char) ch).toArray(Character[]::new);
        Queue<Character> capQueueLetters = new LinkedList<Character>(Arrays.asList(charCapLetters));
        
        StringBuilder fpeFinal = new StringBuilder(input);
        
        for (int i = 0; i < input.length(); i++){
        	Character currChar = input.charAt(i);
            if ((Character.isLetter(currChar)) && (Character.isLowerCase(currChar))) {
            	fpeFinal.setCharAt(i, lowerQueueLetters.poll());
            } else if ((Character.isLetter(currChar)) && (!Character.isLowerCase(currChar))) {
                fpeFinal.setCharAt(i, capQueueLetters.poll());
            } else if (Character.isDigit(currChar)) {
            	fpeFinal.setCharAt(i, queueNumbers.poll());
            } else {
            	fpeFinal.setCharAt(i, queueOthers.poll());
            }
        }
        
        System.out.println();
        System.out.println("FPE Final Text Encoded: " + fpeFinal);
        
        return fpeFinal.toString();
    }

}
