package com.jds.fpe.alphabet;

import com.idealista.fpe.config.Alphabet;

public class CompleteAlphabet implements Alphabet {

  private static final char[] ALL_CASE_CHARS = new char[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
          'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 
          'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 
          'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',};

  @Override
  public char[] availableCharacters() {
      return ALL_CASE_CHARS;
  }

  @Override
  public Integer radix() {
      return ALL_CASE_CHARS.length;
  }
}