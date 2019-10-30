package edu.cs3500.spreadsheets.model.cell.formula.function;

import org.junit.*;

import java.util.Hashtable;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.FormulaCell;
import edu.cs3500.spreadsheets.model.cell.ValueCell;
import edu.cs3500.spreadsheets.model.cell.formula.CellReference;
import edu.cs3500.spreadsheets.model.cell.formula.value.DoubleValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.StringValue;

/**
 * Tests for {@link CapitalizeFunction}.
 */
public class CapitalizeFunctionTest {

  @Before
  public void init() {
    FormulaCell cellA1 = new FormulaCell(new DoubleValue(3.0), "3.0");
    FormulaCell cellB1 = new FormulaCell(new DoubleValue(4.0), "4.0");
    FormulaCell cellC1 = new FormulaCell(new StringValue("hello"), "hello");
    FormulaCell cellD1 = new FormulaCell(new CellReference(new Coord(3, 1), new Coord(3, 1)), "= C1");
    Hashtable<Coord, Cell> spreadsheet = new Hashtable<Coord, Cell>();
  }

  @Test
  public void testVisitDoubleValue() {


    //CapitalizeFunction func1 = new CapitalizeFunction();

  }

  @Test
  public void testVisitStringValue() {

  }

  @Test
  public void testVisitErrorValue() {

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
