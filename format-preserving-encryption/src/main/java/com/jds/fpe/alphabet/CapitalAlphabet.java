package com.jds.fpe.alphabet;

import com.idealista.fpe.config.Alphabet;

public class CapitalAlphabet implements Alphabet {

  private static final char[] CAP_CASE_CHARS = new char[] {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 
          'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',};

  @Override
  public char[] availableCharacters() {
      return CAP_CASE_CHARS;
  }

  @Override
  public Integer radix() {
      return CAP_CASE_CHARS.length;
  }
}