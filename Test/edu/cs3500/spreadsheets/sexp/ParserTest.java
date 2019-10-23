package edu.cs3500.spreadsheets.sexp;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParserTest {
  Parser parser = new Parser();

  @Test
  public void testParse() {
    assertEquals(parser.parse("(PRODUCT (SUB D1 B1) (SUB D1 B1))")), null);
  }
}