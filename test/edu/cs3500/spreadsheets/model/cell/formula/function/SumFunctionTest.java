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
 * Tests for {@link SumFunction}.
 */
public class SumFunctionTest {
  FormulaCell cellA1;
  FormulaCell cellB1;
  FormulaCell cellC1;
  FormulaCell cellD1;
  FormulaCell cellE1;
  FormulaCell cellA2;
  FormulaCell cellB2;

  Hashtable<Coord, Cell> cells;
  Hashtable<Formula, Value> evaluated;

  Value valueDoubleOne;
  Value valueDoubleTwo;
  Value valueStringOne;


  @Before
  public void init() {
    cellA1 = new FormulaCell(new DoubleValue(2.0), "3.0");
    cellB1 = new FormulaCell(new DoubleValue(5.0), "4.0");
    cellC1 = new FormulaCell(new StringValue("hello"), "hello");
    cellD1 = new FormulaCell(new CellReference(new Coord(1, 1), new Coord(1, 1)), "= A1");
    cellE1 = new FormulaCell(new CellReference(new Coord(2, 1), new Coord(2, 1)), "= B1");
    cellA2 = new FormulaCell(new CellReference(new Coord(1, 1), new Coord(5, 1)), "= A1:E1");
    cellB2 = new FormulaCell(new CellReference(new Coord(4, 1), new Coord(4, 1)), "= D1");

    valueDoubleOne = new DoubleValue(2.0);
    valueDoubleTwo = new DoubleValue(5.0);
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

  @Test
  public void testVisitDoubleValue() {
    SumFunction func = new SumFunction(cells, evaluated);
    assertEquals(new Double(14.0), func.visitDoubleValue(new DoubleValue(14.0)));
    assertEquals(new Double(0.0), func.visitDoubleValue(new DoubleValue(0.0)));
    assertEquals(new Double(1.2323), func.visitDoubleValue(new DoubleValue(1.2323)));
  }

  @Test
  public void testVisitStringValue() {
    SumFunction func = new SumFunction(cells, evaluated);
    assertEquals(new Double(0.0), func.visitStringValue(new StringValue(":~)")));
    assertEquals(new Double(0.0), func.visitStringValue(new StringValue("")));
  }

  @Test
  public void testVisitBoolValue() {
    SumFunction func = new SumFunction(cells, evaluated);
    assertEquals(new Double(0.0), func.visitBooleanValue(new BooleanValue(true)));
    assertEquals(new Double(0.0), func.visitBooleanValue(new BooleanValue(false)));
  }

  @Test(expected = IllegalStateException.class)
  public void testVisitErrorValue() {
    SumFunction func = new SumFunction(cells, evaluated);
    func.visitErrorValue(new ErrorValue(new Exception("Invalid function")));
  }

  @Test
  public void testVisitCellReference() {
    SumFunction func = new SumFunction(cells, evaluated);
    assertEquals(new Double(2.0), func.visitCellReference(
            new CellReference(new Coord(1, 1), new Coord(1, 1))));
    assertEquals(new Double(5.0), func.visitCellReference(
            new CellReference(new Coord(2, 1), new Coord(2, 1))));
    assertEquals(new Double(2.0), func.visitCellReference(
            new CellReference(new Coord(4, 1), new Coord(4, 1))));
    assertEquals(new Double(14.0), func.visitCellReference(new CellReference(
            new Coord(1, 1), new Coord(5, 1))));
  }

  @Test
  public void testVisitFunction() {
    SumFunction func = new SumFunction(cells, evaluated);
    assertEquals(new Double(7.0), func.visitFunction(new Function(EFunctions.SUM,
            new ArrayList<>(Arrays.asList(valueDoubleOne, valueDoubleTwo)))));
    assertEquals(new Double(16.0), func.visitFunction(new Function(EFunctions.SUM,
            new ArrayList<>(Arrays.asList(valueDoubleOne, valueDoubleTwo, new Function(
                    EFunctions.SUM,
                    new ArrayList<>(Arrays.asList(new CellReference(new Coord(1, 1),
                            new Coord(2, 1)), valueDoubleOne))))))));
    assertEquals(new Double(0.0), func.visitFunction(new Function(EFunctions.SUM,
            new ArrayList<>(Arrays.asList(valueStringOne)))));
    assertEquals(new Double(0.0), func.visitFunction(new Function(EFunctions.CAPITALIZE,
            new ArrayList<>(Arrays.asList(valueStringOne)))));
    assertEquals(new Double(2.0), func.visitFunction(new Function(EFunctions.SUM,
            new ArrayList<>(Arrays.asList(valueStringOne, valueDoubleOne)))));
  }

  @Test
  public void testApply() {
    SumFunction func = new SumFunction(cells, evaluated);
    assertEquals(new Double(2.0), func.apply(valueDoubleOne));
    assertEquals(new Double(5.0), func.apply(valueDoubleTwo));
    assertEquals(new Double(2.0), func.apply(new CellReference(new Coord(1, 1),
            new Coord(1, 1))));
    assertEquals(new Double(5.0), func.apply(new CellReference(new Coord(2, 1),
            new Coord(2, 1))));
    assertEquals(new Double(2.0), func.apply(new CellReference(new Coord(4, 1),
            new Coord(4, 1))));
    List<Formula> args = new ArrayList<>(Arrays.asList(valueDoubleOne, valueDoubleTwo));
    assertEquals(new Double(7.0), func.apply(new Function(EFunctions.SUM, args)));
  }

  @Test
  public void testInvalidApplyString() {
    SumFunction func = new SumFunction(cells, evaluated);
    assertEquals(new Double(0.0), func.apply(valueStringOne));
  }

  @Test
  public void testInvalidApplyEmptyString() {
    SumFunction func = new SumFunction(cells, evaluated);
    assertEquals(new Double(0.0), func.apply(new StringValue("")));
  }

  @Test
  public void testInvalidBoolApplyOne() {
    SumFunction func = new SumFunction(cells, evaluated);
    assertEquals(new Double(0.0), func.apply(new BooleanValue(true)));
    assertEquals(new Double(0.0), func.apply(new BooleanValue(false)));
  }

  @Test
  public void testInvalidCellReference() {
    SumFunction func = new SumFunction(cells, evaluated);
    assertEquals(new Double(0.0), func.apply(new CellReference(new Coord(3, 1),
            new Coord(3, 1))));
  }

  @Test
  public void testInvalidApplyFunction() {
    SumFunction func = new SumFunction(cells, evaluated);
    assertEquals(new Double(0.0), func.apply(new Function(EFunctions.CAPITALIZE,
            new ArrayList<>(Arrays.asList(valueStringOne)))));
  }
}
