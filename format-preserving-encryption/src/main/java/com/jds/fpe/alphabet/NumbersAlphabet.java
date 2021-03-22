package com.jds.fpe.alphabet;

import com.idealista.fpe.config.Alphabet;

public class NumbersAlphabet implements Alphabet{

  private static final char[] NUMBERS_CHARS = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

  @Override
  public char[] availableCharacters() {
      return NUMBERS_CHARS;
  }

  @Override
  public Integer radix() {
      return NUMBERS_CHARS.length;
  }
}