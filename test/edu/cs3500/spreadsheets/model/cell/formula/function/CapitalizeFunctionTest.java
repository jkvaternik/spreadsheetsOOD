package edu.cs3500.spreadsheets.model.cell.formula.function;

import org.junit.*;

import java.util.Hashtable;

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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link CapitalizeFunction}.
 */
public class CapitalizeFunctionTest {
  private FormulaCell cellA1;
  private FormulaCell cellB1;
  private FormulaCell cellC1;
  private FormulaCell cellD1;
  private FormulaCell cellE1;
  private FormulaCell cellA2;

  private Hashtable<Coord, Cell> spreadsheet;
  private Hashtable<Formula, Value> evaluated;

  private Value valueDoubleOne;
  private Value valueDoubleTwo;
  private Value valueStringOne;


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

    spreadsheet = new Hashtable<>();
    spreadsheet.put(new Coord(1, 1), cellA1);
    spreadsheet.put(new Coord(2, 1), cellB1);
    spreadsheet.put(new Coord(3, 1), cellC1);
    spreadsheet.put(new Coord(4, 1), cellD1);
    spreadsheet.put(new Coord(5, 1), cellE1);
    spreadsheet.put(new Coord(6, 1), cellA2);
  }

  @Test(expected = IllegalStateException.class)
  public void testVisitDoubleValueOne() {
    init();
    CapitalizeFunction func = new CapitalizeFunction(spreadsheet, evaluated);
    func.visitDoubleValue(new DoubleValue(3.0));
  }

  @Test(expected = IllegalStateException.class)
  public void testVisitDoubleValueTwo() {
    init();
    CapitalizeFunction func = new CapitalizeFunction(spreadsheet, evaluated);
    func.visitDoubleValue(new DoubleValue(0.0));
  }

  @Test
  public void testVisitStringValue() {
    init();
    CapitalizeFunction func = new CapitalizeFunction(spreadsheet, evaluated);
    assertEquals("", func.visitStringValue(new StringValue("")));
    assertEquals("23", func.visitStringValue(new StringValue("23")));
    assertEquals("hello", func.visitStringValue(new StringValue("hello")));
  }

  @Test(expected = IllegalStateException.class)
  public void testVisitBoolValueOne() {
    init();
    CapitalizeFunction func = new CapitalizeFunction(spreadsheet, evaluated);
    func.visitBooleanValue(new BooleanValue(true));
  }

  @Test(expected = IllegalStateException.class)
  public void testVisitBoolValueTwo() {
    init();
    CapitalizeFunction func = new CapitalizeFunction(spreadsheet, evaluated);
    func.visitBooleanValue(new BooleanValue(false));
  }

  @Test(expected = IllegalStateException.class)
  public void testVisitErrorValue() {
    init();
    CapitalizeFunction func = new CapitalizeFunction(spreadsheet, evaluated);
    func.visitErrorValue(new ErrorValue(new IllegalArgumentException("Invalid function")));
  }

  @Test
  public void testVisitSingleCellReference() {
    init();
    CapitalizeFunction func = new CapitalizeFunction(spreadsheet, evaluated);
    assertEquals("hello", func.visitCellReference
            (new CellReference(new Coord(3, 1), new Coord(3, 1))));
  }

  @Test
  public void testVisitDoubleCellReference() {
    init();
    CapitalizeFunction func = new CapitalizeFunction(spreadsheet, evaluated);
    assertEquals("hello", func.visitCellReference
            (new CellReference(new Coord(4, 1), new Coord(4, 1))));
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidVisitCellReference() {
    init();
    CapitalizeFunction func = new CapitalizeFunction(spreadsheet, evaluated);
    func.visitCellReference(new CellReference(new Coord(1, 1), new Coord(4, 1)));
  }

  @Test
  public void testVisitFunction() {

  }

  @Test
  public void testApply() {

  }
}
