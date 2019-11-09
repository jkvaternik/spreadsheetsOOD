package edu.cs3500.spreadsheets.model.cell;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.formula.CellReference;
import edu.cs3500.spreadsheets.model.cell.formula.function.EFunctions;
import edu.cs3500.spreadsheets.model.cell.formula.function.Function;
import edu.cs3500.spreadsheets.model.cell.formula.value.BooleanValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.DoubleValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.StringValue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link FormulaCell}.
 */
public class FormulaCellTest {

  FormulaCell doubleOne = new FormulaCell(new DoubleValue(3.0), "3.0");
  FormulaCell doubleTwo = new FormulaCell(new DoubleValue(8.0), "8.0");
  FormulaCell stringEmpty = new FormulaCell(new StringValue(""), "");
  FormulaCell string = new FormulaCell(new StringValue(":-3"), ":-3");
  FormulaCell boolTrue = new FormulaCell(new BooleanValue(true), "true");
  FormulaCell cellSingleRef = new FormulaCell(new CellReference(
          new Coord(1, 1), new Coord(1, 1)), "=A1");
  FormulaCell cellFunction = new FormulaCell(new Function(EFunctions.SUM, new ArrayList<>(
          Arrays.asList(new CellReference(new Coord(1, 1), new Coord(1, 2))))),
          "=SUM(A1:A2)");
  FormulaCell cellSelfReferenceCycleOne = new FormulaCell(new Function(EFunctions.SUM,
          new ArrayList<>(Arrays.asList(new CellReference(new Coord(2, 1),
                  new Coord(2, 1)), new Function(EFunctions.SUM,
                  new ArrayList<>(Arrays.asList(new CellReference(new Coord(2, 1),
                          new Coord(2, 1)), new DoubleValue(1.0))))))),
          "=(SUM B1 (SUM B1 1))");
  FormulaCell cellSelfReferenceCycleTwo = new FormulaCell(new CellReference(
          new Coord(2, 2), new Coord(2, 2)), "=B2");

  FormulaCell doubleOneCopy = new FormulaCell(new DoubleValue(3.0), "3.0");
  FormulaCell stringCopy = new FormulaCell(new StringValue(":-3"), ":-3");

  Hashtable<Coord, Cell> cells;

  @Before
  public void init() {
    cells = new Hashtable<>();
    cells.put(new Coord(1, 1), doubleOne);
    cells.put(new Coord(1, 2), doubleTwo);
    cells.put(new Coord(1, 3), stringEmpty);
    cells.put(new Coord(1, 4), string);
    cells.put(new Coord(1, 5), boolTrue);
    cells.put(new Coord(1, 6), cellFunction);

    for (Cell c : cells.values()) {
      c.evaluate(cells, new Hashtable<>());
    }

    cells.put(new Coord(2, 1), cellSelfReferenceCycleOne);
    cells.put(new Coord(2, 2), cellSelfReferenceCycleTwo);
  }

  @Test
  public void testEvaluate() {
    assertEquals(new DoubleValue(3.0), doubleOne.getValue());
    assertEquals(new DoubleValue(8.0), doubleTwo.getValue());
    assertEquals(new StringValue(""), stringEmpty.getValue());
    assertEquals(new StringValue(":-3"), string.getValue());
    assertEquals(new BooleanValue(true), boolTrue.getValue());
    assertEquals(new DoubleValue(11.0), cellFunction.getValue());
  }

  @Test
  public void testGetRawContents() {
    assertEquals("3.0", doubleOne.getRawContents());
    assertEquals("8.0", doubleTwo.getRawContents());
    assertEquals("", stringEmpty.getRawContents());
    assertEquals(":-3", string.getRawContents());
    assertEquals("true", boolTrue.getRawContents());
    assertEquals("=A1", cellSingleRef.getRawContents());
    assertEquals("=SUM(A1:A2)", cellFunction.getRawContents());
  }

  @Test
  public void testContainsNoCycle() {
    assertFalse(doubleOne.containsCyclicalReference(new HashSet<>(), new Hashtable<>(),
            new HashSet<>()));
    assertFalse(string.containsCyclicalReference(new HashSet<>(), new Hashtable<>(),
            new HashSet<>()));
    assertFalse(boolTrue.containsCyclicalReference(new HashSet<>(), new Hashtable<>(),
            new HashSet<>()));
    assertFalse(cellSingleRef.containsCyclicalReference(new HashSet<>(), new Hashtable<>(),
            new HashSet<>()));
    assertFalse(cellFunction.containsCyclicalReference(new HashSet<>(), new Hashtable<>(),
            new HashSet<>()));
  }

  @Test
  public void testContainsDirectCycle() {
    assertTrue(cellSelfReferenceCycleOne.containsCyclicalReference(new HashSet<>(), cells,
            new HashSet<>()));
    assertTrue(cellSelfReferenceCycleTwo.containsCyclicalReference(new HashSet<>(), cells,
            new HashSet<>()));
  }

  @Test
  public void testReferencesCell() {
    assertFalse(doubleOne.referencesCell(new Coord(1, 1)));
    assertTrue(cellSingleRef.referencesCell(new Coord(1, 1)));
  }

  @Test
  public void testToString() {
    assertEquals("3.0", doubleOne.toString());
    assertEquals("8.0", doubleTwo.toString());
    assertEquals("", stringEmpty.toString());
    assertEquals(":-3", string.toString());
    assertEquals("true", boolTrue.toString());
    assertEquals("=A1", cellSingleRef.toString());
    assertEquals("=SUM(A1:A2)", cellFunction.toString());
  }

  @Test
  public void testEquals() {
    assertTrue(doubleOne.equals(doubleOne));
    assertTrue(doubleOne.equals(doubleOneCopy));
    assertFalse(doubleTwo.equals(doubleOne));
    assertFalse(doubleOne.equals(doubleTwo));

    assertTrue(string.equals(string));
    assertTrue(string.equals(stringCopy));
    assertFalse(string.equals(stringEmpty));
    assertFalse(stringEmpty.equals(string));

    assertFalse(doubleTwo.equals(cellFunction));
    assertFalse(cellSingleRef.equals(boolTrue));
  }

  @Test
  public void testHashcode() {
    assertTrue(doubleOne.hashCode() == doubleOne.hashCode());
    assertTrue(doubleTwo.hashCode() == doubleTwo.hashCode());
    assertTrue(string.hashCode() == string.hashCode());
    assertTrue(stringEmpty.hashCode() == stringEmpty.hashCode());
    assertTrue(boolTrue.hashCode() == boolTrue.hashCode());
    assertTrue(cellSingleRef.hashCode() == cellSingleRef.hashCode());
    assertTrue(cellFunction.hashCode() == cellFunction.hashCode());
  }
}
