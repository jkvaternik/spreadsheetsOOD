package edu.cs3500.spreadsheets.sexp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ParserTest {
  Parser parser = new Parser();

  @Test
  public void testParseProduct() {
    assertEquals(parser.parse("(PRODUCT (SUB D1 B1) (SUB D1 B1))"),
        new SList(
            new SSymbol("PRODUCT"),
            new SList(new SSymbol("SUB"), new SSymbol("D1"),
                new SSymbol("B1")),
            new SList(new SSymbol("SUB"), new SSymbol("D1"),
                new SSymbol("B1"))));
  }
}