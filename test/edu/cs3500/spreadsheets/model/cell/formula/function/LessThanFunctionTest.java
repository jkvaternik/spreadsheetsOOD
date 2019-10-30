package edu.cs3500.spreadsheets.model.cell.formula.function;

import org.junit.Before;
import org.junit.Test;

import java.util.Hashtable;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.FormulaCell;
import edu.cs3500.spreadsheets.model.cell.formula.CellReference;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.formula.value.DoubleValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.ErrorValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.StringValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.Value;

/**
 * Tests for {@link LessThanFunction}.
 */
public class LessThanFunctionTest {

  private FormulaCell cellA1;
  private FormulaCell cellB1;
  private FormulaCell cellC1;
  private FormulaCell cellD1;

  private Hashtable<Coord, Cell> spreadsheet;
  private Hashtable<Formula, Value> evaluated;

  private Value valueA1;
  private Value valueB1;
  private Value valueC1;
  private Value valueD1;


  @Before
  public void init() {
    cellA1 = new FormulaCell(new DoubleValue(3.0), "3.0");
    cellB1 = new FormulaCell(new DoubleValue(4.0), "4.0");
    cellC1 = new FormulaCell(new StringValue("hello"), "hello");
    cellD1 = new FormulaCell(new CellReference(new Coord(3, 1), new Coord(3, 1)), "= C1");

    valueA1 = new DoubleValue(3.0);
    valueB1 = new DoubleValue(4.0);
    valueC1 = new StringValue("hello");
    valueD1 = new StringValue("hello");

    evaluated = new Hashtable<>();
    evaluated.put(new DoubleValue(3.0), new DoubleValue(3.0));
    evaluated.put(new DoubleValue(4.0), new DoubleValue(4.0));
    evaluated.put(new StringValue("hello"), new StringValue("hello"));
    evaluated.put(new StringValue("hello"), new StringValue("hello"));

    spreadsheet = new Hashtable<>();
    spreadsheet.put(new Coord(1, 1), cellA1);
    spreadsheet.put(new Coord(2, 1), cellB1);
    spreadsheet.put(new Coord(3, 1), cellC1);
    spreadsheet.put(new Coord(4, 1), cellD1);
  }

  @Test
  public void testVisitDoubleValue() {

  }

  @Test
  public void testVisitStringValue() {

  }

  @Test(expected = IllegalStateException.class)
  public void testVisitErrorValue() {
    init();
    LessThanFunction func = new LessThanFunction(spreadsheet, evaluated);
    func.visitErrorValue(new ErrorValue(new Exception("Invalid function")));
  }

  @Test
  public void testVisitCellReference() {

  }

  @Test
  public void testVisitFunction() {

  }

  @Test
  public void testApply() {

  }
}
