package edu.cs3500.spreadsheets.model.cell.formula.function;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.FormulaCell;
import edu.cs3500.spreadsheets.model.cell.formula.CellReference;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.formula.value.BooleanValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.DoubleValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.ErrorValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.StringValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.Value;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link CapitalizeFunction}.
 */
public class CapitalizeFunctionTest {
  FormulaCell cellA1;
  FormulaCell cellB1;
  FormulaCell cellC1;
  FormulaCell cellD1;
  FormulaCell cellE1;
  FormulaCell cellA2;

  Hashtable<Coord, Cell> cells;
  Hashtable<Formula, Value> evaluated;

  Value valueDoubleOne;
  Value valueDoubleTwo;
  Value valueStringOne;

  @Before
  public void init() {
    cellA1 = new FormulaCell(new DoubleValue(3.0), "3.0");
    cellB1 = new FormulaCell(new DoubleValue(4.0), "4.0");
    cellC1 = new FormulaCell(new StringValue("hello"), "hello");
    cellD1 = new FormulaCell(new CellReference(new Coord(3, 1), new Coord(3, 1)), "= C1");
    cellE1 = new FormulaCell(new CellReference(new Coord(4, 1), new Coord(4, 1)), "= D1");
    cellA2 = new FormulaCell(new CellReference(new Coord(1, 1), new Coord(5, 1)), "= A1:E1");

    valueDoubleOne = new DoubleValue(3.0);
    valueDoubleTwo = new DoubleValue(4.0);
    valueStringOne = new StringValue("hello");

    evaluated = new Hashtable<>();
    evaluated.put(valueDoubleOne, valueDoubleOne);
    evaluated.put(valueDoubleTwo, valueDoubleOne);
    evaluated.put(valueStringOne, valueStringOne);
    evaluated.put(new CellReference(new Coord(3, 1), new Coord(3, 1)),
            valueStringOne);
    evaluated.put(new CellReference(new Coord(4, 1), new Coord(3, 1)),
            valueStringOne);

    cells = new Hashtable<>();
    cells.put(new Coord(1, 1), cellA1);
    cells.put(new Coord(2, 1), cellB1);
    cells.put(new Coord(3, 1), cellC1);
    cells.put(new Coord(4, 1), cellD1);
    cells.put(new Coord(5, 1), cellE1);
    cells.put(new Coord(6, 1), cellA2);
  }

  @Test(expected = IllegalStateException.class)
  public void testVisitDoubleValueOne() {
    CapitalizeFunction func = new CapitalizeFunction(cells, evaluated);
    func.visitDoubleValue(new DoubleValue(3.0));
  }

  @Test(expected = IllegalStateException.class)
  public void testVisitDoubleValueTwo() {
    CapitalizeFunction func = new CapitalizeFunction(cells, evaluated);
    func.visitDoubleValue(new DoubleValue(0.0));
  }

  @Test
  public void testVisitStringValue() {
    CapitalizeFunction func = new CapitalizeFunction(cells, evaluated);
    assertEquals("", func.visitStringValue(new StringValue("")));
    assertEquals("23", func.visitStringValue(new StringValue("23")));
    assertEquals("hello", func.visitStringValue(new StringValue("hello")));
  }

  @Test(expected = IllegalStateException.class)
  public void testVisitBoolValueOne() {
    CapitalizeFunction func = new CapitalizeFunction(cells, evaluated);
    func.visitBooleanValue(new BooleanValue(true));
  }

  @Test(expected = IllegalStateException.class)
  public void testVisitBoolValueTwo() {
    CapitalizeFunction func = new CapitalizeFunction(cells, evaluated);
    func.visitBooleanValue(new BooleanValue(false));
  }

  @Test(expected = IllegalStateException.class)
  public void testVisitErrorValue() {
    CapitalizeFunction func = new CapitalizeFunction(cells, evaluated);
    func.visitErrorValue(new ErrorValue(new IllegalArgumentException("Invalid function")));
  }

  @Test
  public void testVisitSingleCellReference() {
    CapitalizeFunction func = new CapitalizeFunction(cells, evaluated);
    assertEquals("hello", func.visitCellReference(
            new CellReference(new Coord(3, 1), new Coord(3, 1))));
  }

  @Test
  public void testVisitDoubleCellReference() {
    CapitalizeFunction func = new CapitalizeFunction(cells, evaluated);
    assertEquals("hello", func.visitCellReference(
            new CellReference(new Coord(4, 1), new Coord(4, 1))));
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidVisitCellReference() {
    CapitalizeFunction func = new CapitalizeFunction(cells, evaluated);
    func.visitCellReference(new CellReference(new Coord(1, 1), new Coord(4, 1)));
  }

  @Test
  public void testVisitFunction() {
    CapitalizeFunction func = new CapitalizeFunction(cells, evaluated);
    List<Formula> args = new ArrayList<>(Arrays.asList(valueStringOne));
    assertEquals("HELLO", func.visitFunction(new Function(EFunctions.CAPITALIZE, args)));
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidVisitFunctionOne() {
    CapitalizeFunction func = new CapitalizeFunction(cells, evaluated);
    List<Formula> args = new ArrayList<>(Arrays.asList(valueDoubleOne));
    func.visitFunction(new Function(EFunctions.CAPITALIZE, args));
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidVisitFunctionTwo() {

    CapitalizeFunction func = new CapitalizeFunction(cells, evaluated);
    List<Formula> args = new ArrayList<>(Arrays.asList(valueDoubleOne, valueDoubleTwo));
    func.visitFunction(new Function(EFunctions.SUM, args));
  }

  @Test
  public void testApply() {
    CapitalizeFunction func = new CapitalizeFunction(cells, evaluated);
    assertEquals("hello", func.apply(valueStringOne));
    assertEquals("hello", func.apply(new CellReference(new Coord(3, 1),
            new Coord(3, 1))));
    assertEquals("hello", func.apply(new CellReference(new Coord(4, 1),
            new Coord(4, 1))));
    List<Formula> args = new ArrayList<>(Arrays.asList(valueStringOne));
    assertEquals("HELLO", func.apply(new Function(EFunctions.CAPITALIZE, args)));
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidApplyNumber() {
    CapitalizeFunction func = new CapitalizeFunction(cells, evaluated);
    func.apply(valueDoubleOne);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidApplyBoolean() {
    CapitalizeFunction func = new CapitalizeFunction(cells, evaluated);
    func.apply(new BooleanValue(true));
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidApplyError() {
    CapitalizeFunction func = new CapitalizeFunction(cells, evaluated);
    func.apply(new ErrorValue(new IllegalArgumentException("Invalid function.")));
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidApplyCellRef() {
    CapitalizeFunction func = new CapitalizeFunction(cells, evaluated);
    func.apply(new CellReference(new Coord(2, 1), new Coord(2, 1)));
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidApplyFunction() {
    CapitalizeFunction func = new CapitalizeFunction(cells, evaluated);
    func.apply(new Function(EFunctions.SUM, new ArrayList<>(
            Arrays.asList(valueDoubleTwo, valueDoubleOne))));
  }
}
