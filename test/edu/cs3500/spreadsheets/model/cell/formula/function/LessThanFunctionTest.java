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
 * Tests for {@link LessThanFunction}.
 */
public class LessThanFunctionTest {

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

  double tolerance;


  @Before
  public void init() {
    cellA1 = new FormulaCell(new DoubleValue(3.0), "3.0");
    cellB1 = new FormulaCell(new DoubleValue(4.0), "4.0");
    cellC1 = new FormulaCell(new StringValue("hello"), "hello");
    cellD1 = new FormulaCell(new CellReference(new Coord(1, 1), new Coord(1, 1)), "= A1");
    cellE1 = new FormulaCell(new CellReference(new Coord(2, 1), new Coord(2, 1)), "= B1");
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

    tolerance = 0.00000001;
  }

  @Test
  public void testVisitDoubleValue() {
    LessThanFunction func = new LessThanFunction(cells, evaluated);
    assertEquals(new Double(3.0), func.visitDoubleValue(new DoubleValue(3.0)));
    assertEquals(new Double(0.0), func.visitDoubleValue(new DoubleValue(0.0)));
    assertEquals(new Double(3.0123), func.visitDoubleValue(new DoubleValue(3.0123)));
  }

  @Test(expected = IllegalStateException.class)
  public void testVisitEmptyStringValue() {
    LessThanFunction func = new LessThanFunction(cells, evaluated);
    func.visitStringValue(new StringValue(""));
  }

  @Test(expected = IllegalStateException.class)
  public void testVisitStringValue() {
    LessThanFunction func = new LessThanFunction(cells, evaluated);
    func.visitStringValue(new StringValue(":-)"));
  }

  @Test(expected = IllegalStateException.class)
  public void testVisitBoolValueOne() {
    LessThanFunction func = new LessThanFunction(cells, evaluated);
    func.visitBooleanValue(new BooleanValue(true));
  }

  @Test(expected = IllegalStateException.class)
  public void testVisitBoolValueTwo() {
    LessThanFunction func = new LessThanFunction(cells, evaluated);
    func.visitBooleanValue(new BooleanValue(false));
  }

  @Test(expected = IllegalStateException.class)
  public void testVisitErrorValue() {
    LessThanFunction func = new LessThanFunction(cells, evaluated);
    func.visitErrorValue(new ErrorValue(new Exception("Invalid function")));
  }

  @Test
  public void testVisitCellReference() {
    LessThanFunction func = new LessThanFunction(cells, evaluated);
    assertEquals(3.0, func.visitCellReference(
            new CellReference(new Coord(1, 1), new Coord(1, 1))), tolerance);
    assertEquals(4.0, func.visitCellReference(
            new CellReference(new Coord(2, 1), new Coord(2, 1))), tolerance);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidVisitCellReference() {
    LessThanFunction func = new LessThanFunction(cells, evaluated);
    func.visitCellReference(new CellReference(new Coord(1, 1),
            new Coord(5, 1)));
  }

  @Test
  public void testVisitFunction() {
    LessThanFunction func = new LessThanFunction(cells, evaluated);
    assertEquals(7.0, func.visitFunction(new Function(EFunctions.SUM,
            new ArrayList<>(Arrays.asList(valueDoubleOne, valueDoubleTwo)))), tolerance);
    assertEquals(14.0, func.visitFunction(new Function(EFunctions.SUM,
            new ArrayList<>(Arrays.asList(valueDoubleOne, valueDoubleTwo, new Function(
                    EFunctions.SUM,
                    new ArrayList<>(Arrays.asList(new CellReference(new Coord(1, 1),
                            new Coord(2, 1))))))))), tolerance);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidVisitFunctionOne() {
    LessThanFunction func = new LessThanFunction(cells, evaluated);
    List<Formula> args = new ArrayList<>(Arrays.asList(valueStringOne));
    func.visitFunction(new Function(EFunctions.LESSTHAN, args));
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidVisitFunctionTwo() {
    LessThanFunction func = new LessThanFunction(cells, evaluated);
    List<Formula> args = new ArrayList<>(Arrays.asList(valueStringOne));
    func.visitFunction(new Function(EFunctions.CAPITALIZE, args));
  }

  @Test
  public void testApply() {
    LessThanFunction func = new LessThanFunction(cells, evaluated);
    assertEquals(3.0, func.apply(valueDoubleOne), tolerance);
    assertEquals(4.0, func.apply(valueDoubleTwo), tolerance);
    assertEquals(3.0, func.apply(new CellReference(new Coord(1, 1),
            new Coord(1, 1))), tolerance);
    assertEquals(4.0, func.apply(new CellReference(new Coord(2, 1),
            new Coord(2, 1))), tolerance);
    List<Formula> args = new ArrayList<>(Arrays.asList(valueDoubleOne, valueDoubleTwo));
    assertEquals(7.0, func.apply(new Function(EFunctions.SUM, args)), tolerance);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidApplyString() {
    LessThanFunction func = new LessThanFunction(cells, evaluated);
    func.apply(valueStringOne);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidApplyEmptyString() {
    LessThanFunction func = new LessThanFunction(cells, evaluated);
    func.apply(new StringValue(""));
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidBoolApplyOne() {
    LessThanFunction func = new LessThanFunction(cells, evaluated);
    func.apply(new BooleanValue(true));
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidBoolApplyTwo() {
    LessThanFunction func = new LessThanFunction(cells, evaluated);
    func.apply(new BooleanValue(false));
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidCellReference() {
    LessThanFunction func = new LessThanFunction(cells, evaluated);
    func.apply(new CellReference(new Coord(3, 1), new Coord(3, 1)));
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidApplyFunction() {
    LessThanFunction func = new LessThanFunction(cells, evaluated);
    func.apply(new Function(EFunctions.CAPITALIZE, new ArrayList<>(
            Arrays.asList(valueStringOne))));
  }
}
