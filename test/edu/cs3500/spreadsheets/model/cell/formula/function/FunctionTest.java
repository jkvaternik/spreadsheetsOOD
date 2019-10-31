package edu.cs3500.spreadsheets.model.cell.formula.function;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.FormulaCell;
import edu.cs3500.spreadsheets.model.cell.ValueCell;
import edu.cs3500.spreadsheets.model.cell.formula.CellReference;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.formula.value.BooleanValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.DoubleValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.ErrorValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.StringValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.Value;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link Function}.
 */
public class FunctionTest {

  Function sumFunctionOne;
  Function productFunctionOne;
  Function lessThanFunction;
  Function capitalizeFunction;

  Function cyclicFunctionSumOne;
  Function cyclicFunctionSumTwo;
  Function cyclicFunctionProduct;
  Function cyclicFunctionLessThanOne;
  Function cyclicFunctionLessThanTwo;

  Cell cellA1;
  Cell cellB1;
  Cell cellC1;
  Cell cellD1;
  Cell cellA2;
  Cell cellB2;
  Cell cellC2;
  Cell cellD2;
  Cell cellA3;
  Cell cellB3;

  Hashtable<Coord, Cell> cells;
  Hashtable<Formula, Value> evaluated;

  double tolerance;

  @Before
  public void init() {
    cellA1 = new ValueCell("6.0", new DoubleValue(6.0));
    cellB1 = new ValueCell("3.0", new DoubleValue(3.0));
    cellC1 = new ValueCell("hello world", new StringValue("hello world"));
    cellD1 = new ValueCell("true", new BooleanValue(true));
    cellA2 = new FormulaCell(new CellReference(new Coord(1, 1), new Coord(1, 1)),
            "= A1");
    cellB2 = new FormulaCell(new CellReference(new Coord(1, 2),
            new Coord(1, 2)), "= A2");
    cellC2 = new FormulaCell(new CellReference(new Coord(3, 1),
            new Coord(3, 1)), "= C1");
    cellD2 = new FormulaCell(new CellReference(new Coord(1, 1), new Coord(3, 2)),
            "= A1:C3");
    cellA3 = new FormulaCell(new CellReference(new Coord(2, 3), new Coord(2, 3)),
            "= B3");
    cellB3 = new FormulaCell(new CellReference(new Coord(1, 3), new Coord(1, 3)),
            "= A3");

    cells = new Hashtable<>();
    evaluated = new Hashtable<>();

    cells.put(new Coord(1, 1), cellA1);
    cells.put(new Coord(2, 1), cellB1);
    cells.put(new Coord(3, 1), cellC1);
    cells.put(new Coord(4, 1), cellD1);
    cells.put(new Coord(1, 2), cellA2);
    cells.put(new Coord(2, 2), cellB2);
    cells.put(new Coord(3, 2), cellC2);
    cells.put(new Coord(4, 2), cellD2);
    cells.put(new Coord(1, 3), cellA3);
    cells.put(new Coord(2, 3), cellB3);

    evaluated.put(new DoubleValue(6.0), new DoubleValue(6.0));
    evaluated.put(new DoubleValue(3.0), new DoubleValue(3.0));
    evaluated.put(new StringValue("hello world"), new StringValue("hello world"));
    evaluated.put(new BooleanValue(true), new BooleanValue(true));
    evaluated.put(new CellReference(new Coord(1, 1), new Coord(1, 1)),
            new DoubleValue(1.0));
    evaluated.put(new CellReference(new Coord(1, 2), new Coord(1, 2)),
            new DoubleValue(1.0));
    evaluated.put(new CellReference(new Coord(3, 1), new Coord(3, 1)),
            new StringValue("hello world"));
    evaluated.put(new CellReference(new Coord(1, 1), new Coord(3, 2)),
            new ErrorValue(new IllegalArgumentException("Can't evaluate a multi-reference.")));

    sumFunctionOne = new Function(EFunctions.SUM, new ArrayList<>(Arrays.asList(
            cellA1.evaluate(cells, evaluated), cellB1.evaluate(cells, evaluated))));
    productFunctionOne = new Function(EFunctions.PRODUCT, new ArrayList<>(Arrays.asList(
            cellA1.evaluate(cells, evaluated), cellB1.evaluate(cells, evaluated))));
    lessThanFunction = new Function(EFunctions.LESSTHAN, new ArrayList<>(Arrays.asList(
            cellA1.evaluate(cells, evaluated), cellB1.evaluate(cells, evaluated))));
    capitalizeFunction = new Function(EFunctions.CAPITALIZE, new ArrayList<>(Arrays.asList(
            cellC1.evaluate(cells, evaluated))));

    cyclicFunctionSumOne = new Function(EFunctions.SUM, new ArrayList<>(Arrays.asList(
            new CellReference(new Coord(1, 3), new Coord(1, 3)),
            new CellReference(new Coord(2, 3), new Coord(2, 3)))));
    cyclicFunctionSumTwo = new Function(EFunctions.SUM, new ArrayList<>(Arrays.asList(
            new CellReference(new Coord(1, 3), new Coord(1, 3)),
            new CellReference(new Coord(2, 3), new Coord(2, 3)),
            new DoubleValue(1.0))));
    cyclicFunctionProduct = new Function(EFunctions.PRODUCT, new ArrayList<>(Arrays.asList(
            new CellReference(new Coord(1, 3), new Coord(1, 3)),
            new CellReference(new Coord(2, 3), new Coord(2, 3)))));
    cyclicFunctionLessThanOne = new Function(EFunctions.LESSTHAN, new ArrayList<>(Arrays.asList(
            new CellReference(new Coord(1, 3), new Coord(1, 3)),
            cellA1.evaluate(cells, evaluated))));
    cyclicFunctionLessThanTwo = new Function(EFunctions.LESSTHAN, new ArrayList<>(Arrays.asList(
            new CellReference(new Coord(2, 3), new Coord(2, 3)),
            cellA1.evaluate(cells, evaluated))));

    tolerance = 0.00000001;
  }

  @Test
  public void testEvaluate() {
    assertEquals(9.0, sumFunctionOne.evaluate(cells, evaluated).getValue(), tolerance);
    assertEquals(18.0, productFunctionOne.evaluate(cells, evaluated).getValue(), tolerance);
    assertEquals(false, lessThanFunction.evaluate(cells, evaluated).getValue());
    assertEquals("HELLO WORLD", capitalizeFunction.evaluate(cells, evaluated).getValue());
  }

  @Test
  public void testContainsCycle() {
    assertTrue(cyclicFunctionLessThanOne.containsCyclicalReference(new HashSet<>(),
            cells, new HashSet<>()));
    assertTrue(cyclicFunctionLessThanTwo.containsCyclicalReference(new HashSet<>(),
            cells, new HashSet<>()));
    assertTrue(cyclicFunctionProduct.containsCyclicalReference(new HashSet<>(),
            cells, new HashSet<>()));
    assertTrue(cyclicFunctionSumOne.containsCyclicalReference(new HashSet<>(),
            cells, new HashSet<>()));
    assertTrue(cyclicFunctionSumTwo.containsCyclicalReference(new HashSet<>(),
            cells, new HashSet<>()));
    assertFalse(sumFunctionOne.containsCyclicalReference(new HashSet<>(),
            cells, new HashSet<>()));
    assertFalse(productFunctionOne.containsCyclicalReference(new HashSet<>(),
            cells, new HashSet<>()));
    assertFalse(lessThanFunction.containsCyclicalReference(new HashSet<>(),
            cells, new HashSet<>()));
  }

  @Test
  public void testAccept() {
    assertEquals(new Double(9.0), sumFunctionOne.accept(new SumFunction(cells, evaluated)));
    assertEquals(new Double(18.0), productFunctionOne.accept(
            new ProductFunction(cells, evaluated)));
    //TODO: ... accept should return Double but evaluates to false.... add cases for intertwined functions?
//    assertEquals(false, lessThanFunction.accept(new LessThanFunction(cells, evaluated)));
    assertEquals("HELLO WORLD", capitalizeFunction.accept(
            new CapitalizeFunction(cells, evaluated)));
  }

  @Test
  public void testGetArgs() {
    assertEquals(new ArrayList<>(Arrays.asList(cellA1.evaluate(cells, evaluated),
            cellB1.evaluate(cells, evaluated))), sumFunctionOne.getArgs());
    assertEquals(new ArrayList<>(Arrays.asList(cellA1.evaluate(cells, evaluated),
            cellB1.evaluate(cells, evaluated))), productFunctionOne.getArgs());
    assertEquals(new ArrayList<>(Arrays.asList(cellA1.evaluate(cells, evaluated),
            cellB1.evaluate(cells, evaluated))), lessThanFunction.getArgs());
    assertEquals(new ArrayList<>(Arrays.asList(cellC1.evaluate(cells, evaluated))),
            capitalizeFunction.getArgs());
  }

  @Test
  public void testAddArgs() {
    Function sum = new Function(EFunctions.SUM, null);
    Function product = new Function(EFunctions.PRODUCT, null);
    Function lessThan = new Function(EFunctions.LESSTHAN, null);
    Function toCap = new Function(EFunctions.CAPITALIZE, null);

    sum.addArg(new DoubleValue(3.0));
    product.addArg(new DoubleValue(34.2));
    lessThan.addArg(new DoubleValue(2.0));
    toCap.addArg(new StringValue("oof"));
  }

  @Test
  public void testEquals() {
    assertTrue(sumFunctionOne.equals(sumFunctionOne));
    assertFalse(productFunctionOne.equals(sumFunctionOne));
    assertFalse(sumFunctionOne.equals(productFunctionOne));

    assertTrue(productFunctionOne.equals(productFunctionOne));
    assertTrue(lessThanFunction.equals(lessThanFunction));
    assertTrue(capitalizeFunction.equals(capitalizeFunction));
  }

  @Test
  public void testHashcode() {
    assertEquals(-1274899087, sumFunctionOne.hashCode());
    assertEquals(-2003654199, productFunctionOne.hashCode());
    assertEquals(801431534, lessThanFunction.hashCode());
    assertEquals(-1479408768, capitalizeFunction.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("SUM, [6.000000, 3.000000]", sumFunctionOne.toString());
    assertEquals("PRODUCT, [6.000000, 3.000000]", productFunctionOne.toString());
    assertEquals("LESSTHAN, [6.000000, 3.000000]", lessThanFunction.toString());
    assertEquals("CAPITALIZE, [\"hello world\"]", capitalizeFunction.toString());
  }
}
