package edu.cs3500.spreadsheets.model.cell;

import static org.junit.Assert.assertEquals;


import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.formula.CellReference;
import edu.cs3500.spreadsheets.model.cell.formula.function.EFunctions;
import edu.cs3500.spreadsheets.model.cell.formula.function.Function;
import edu.cs3500.spreadsheets.model.cell.formula.value.BooleanValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.DoubleValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.ErrorValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.StringValue;
import org.junit.Test;

/**
 * Tests for {@link SexpVisitorFormula}.
 */
public class SexpVisitorFormulaTest {
  SexpVisitorFormula visitor = new SexpVisitorFormula();

  @Test
  public void visitBoolean() {
    assertEquals(new BooleanValue(true), visitor.visitBoolean(true));
    assertEquals(new BooleanValue(false), visitor.visitBoolean(false));
  }

  @Test
  public void visitNumber() {
    assertEquals(new DoubleValue(5.6), visitor.visitNumber(5.6));
  }

  @Test
  public void visitString() {
    assertEquals(new StringValue("hi mom"), visitor.visitString("hi mom"));
  }

  @Test
  public void visitSList() {

  }

  @Test
  public void visitSymbol() {
    assertEquals(new Function(EFunctions.PRODUCT, null), visitor.visitSymbol("PRODUCT"));
    assertEquals(new Function(EFunctions.SUM, null), visitor.visitSymbol("SUM"));
    assertEquals(new Function(EFunctions.LESSTHAN, null), visitor.visitSymbol("<"));
    assertEquals(new Function(EFunctions.CAPITALIZE, null), visitor.visitSymbol("CAPITALIZE"));

    assertEquals(new CellReference(new Coord(1, 1), new Coord(1, 1)),
        visitor.visitSymbol("A1"));
    assertEquals(new CellReference(new Coord(1, 1), new Coord(3, 3)),
        visitor.visitSymbol("A1:C3"));

    // Now show that all illegal cell references will lead to the creation of an error value
    assertEquals(new ErrorValue(new IllegalArgumentException("Invalid cell reference")),
        visitor.visitSymbol("1A"));
    assertEquals(new ErrorValue(new IllegalArgumentException("Invalid cell reference")),
        visitor.visitSymbol("A12B"));
    assertEquals(new ErrorValue(new IllegalArgumentException("Invalid cell reference")),
        visitor.visitSymbol("A1!"));
    assertEquals(new ErrorValue(new IllegalArgumentException("Invalid cell reference")),
        visitor.visitSymbol("A"));
    assertEquals(new ErrorValue(new IllegalArgumentException("Invalid cell reference")),
        visitor.visitSymbol("B1:A1"));
    assertEquals(new ErrorValue(new IllegalArgumentException("Invalid cell reference")),
        visitor.visitSymbol("C:3A3"));
    assertEquals(new ErrorValue(new IllegalArgumentException("Invalid cell reference")),
        visitor.visitSymbol("C4:"));
  }
}
