package com.jds.fpe;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

@SpringBootApplication
public class FPEApplication implements CommandLineRunner {
	
	static Boolean isParquet = false;
    static String[] columns;
//    static Path path = Paths.get("file:\\C:\\myfile.snappy.parquet");
	
    private static final Logger logger = LoggerFactory.getLogger(FPEApplication.class);
	
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

    public static void main(String[] args) {
    	logger.info("STARTING THE FPE APPLICATION");
        SpringApplication.run(FPEApplication.class, args);
        logger.info("FPE APPLICATION FINISHED");
    }
    
	public void run(String[] args) throws Exception {
		
        String filename = "";
        
        Options options = new Options();
        options.addOption(Option.builder("f")
            .longOpt("filename")
            .hasArg(true)
            .desc("File to be encrypted.")
            .required(true)
            .build());
        options.addOption(Option.builder("p")
            .longOpt("parquet")
            .hasArg(false)
            .desc("Parquet file or directory to be encrypted.)")
            .required(false)
            .build());
        options.addOption(Option.builder("c")
            .longOpt("columns")
            .hasArg(true)
            .desc("Columns to encrypted, by default all.")
            .numberOfArgs(Option.UNLIMITED_VALUES)
            .required(false)
            .build());
  			        
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);

            if (cmd.hasOption("c")) {
                String[] columns = cmd.getOptionValues("c");
                System.out.println("We have --columns option = " + columns);
                System.out.println("Number of columns: " + columns.length);
                System.out.println("Column(s): " + String.join(", ", Arrays.asList(columns)));
            } 
            if (cmd.hasOption("p")) {
                isParquet = true;
                System.out.println("We have --parquet to read a parquet file.");
            } 
            if (cmd.hasOption("f")) {
                String file = cmd.getOptionValue("f");
                if (isParquet) {
                	System.out.println("Parquet Filename: " + file);
                } else {
                	System.out.println("CSV Filename: " + file);
                }
            } else {
            	System.out.println("Filename required!");
            }
        } catch (ParseException pe) {
            System.out.println("Error parsing command-line arguments!");
            System.out.println("Please, follow the instructions below:");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "Format Preserving Encryption: ", options );
            System.exit(1);
        }
                
        System.out.println("Start format preserving encryption for file: .");
        
        
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
        
        // Usage
        System.out.println("Initial: "+testString1);
        String cipherText1 = defaultFormatPreservingEncryption1.encrypt(testString1, anyTweak);
        String plainText1 = defaultFormatPreservingEncryption1.decrypt(testString1, anyTweak);
        
        System.out.println("Plain text: " + plainText1);
        System.out.println("Ciphered text: " + cipherText1);
                
        FormatPreservingEncryption numbersFormatPreservingEncryption = FormatPreservingEncryptionBuilder
                .ff1Implementation()
                .withDomain(NUMBERS_DOMAIN)
                .withDefaultPseudoRandomFunction(keyByteArrray)
                .withDefaultLengthRange()
                .build();
        
        String testString2 = "12345";
        
        // Usage
        String cipherText2 = numbersFormatPreservingEncryption.encrypt(testString2, anyTweak);
        String plainText2 = numbersFormatPreservingEncryption.decrypt(testString2, anyTweak);
        
        System.out.println("Initial: "+testString2);
        System.out.println("Plain text: " + plainText2);
        System.out.println("Ciphered text: " + cipherText2);
        
        
        String testString3 = "123 456_78";
        testString3 = testString3.replaceAll("[^a-zA-Z0-9]", "");
        
        // Usage
        System.out.println("Initial: "+testString3);
        String cipherText3 = numbersFormatPreservingEncryption.encrypt(testString3, anyTweak);
        String plainText3 = numbersFormatPreservingEncryption.decrypt(testString3, anyTweak);
        
        System.out.println("Plain text: " + plainText3);
        System.out.println("Ciphered text: " + cipherText3);
        System.out.println("");
        
        System.out.println("Format Preserving Encryption: ");
        String fpeSample1 = "Al Reem Island, Addax Tower 55th Floor";
        System.out.println("Original String: " + fpeSample1);
        System.out.println();
        HashMap<Integer, Character> othersMap = new HashMap<Integer, Character>(); 
        StringBuilder numbers = new StringBuilder();
        Queue<Character> queueOthers = new LinkedList<Character>();
        StringBuilder originalLetters = new StringBuilder();
        StringBuilder lowerLetters = new StringBuilder();
        StringBuilder capLetters = new StringBuilder();
        
        for (int i = 0; i < fpeSample1.length(); i++){
        	Character currChar = fpeSample1.charAt(i);
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
        
        StringBuilder fpeFinal = new StringBuilder(fpeSample1);
        
        for (int i = 0; i < fpeSample1.length(); i++){
        	Character currChar = fpeSample1.charAt(i);
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
	}    

}