package edu.cs3500.spreadsheets.model.cell.formula;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Hashtable;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.FormulaCell;
import edu.cs3500.spreadsheets.model.cell.formula.function.SumFunction;
import edu.cs3500.spreadsheets.model.cell.formula.value.DoubleValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.ErrorValue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link CellReference}.
 */
public class CellReferenceTest {

  FormulaCell cellA1;
  FormulaCell cellB1;
  FormulaCell cellC1;
  FormulaCell cellA2;
  FormulaCell cellB2;
  FormulaCell cellC2;

  CellReference refSingle;
  CellReference refMultiRow;
  CellReference refMultiRegion;
  CellReference refDirectCycle;
  CellReference refIndirectCycleA;
  CellReference refIndirectCycleB;

  Hashtable<Coord, Cell> cells;

  double tolerance;

  @Before
  public void init() {
    cellA1 = new FormulaCell(new DoubleValue(12.0), "12.0");
    cellB1 = new FormulaCell(new DoubleValue(35.0), "35.0");
    cellC1 = new FormulaCell(new DoubleValue(1.0), "1.0");
    cellA2 = new FormulaCell(new DoubleValue(3.14), "3.14");
    cellB2 = new FormulaCell(new DoubleValue(6.66), "6.66");
    cellC2 = new FormulaCell(new DoubleValue(69.69), "69.69");

    cells = new Hashtable<>();

    cells.put(new Coord(1, 1), cellA1);
    cells.put(new Coord(2, 1), cellB1);
    cells.put(new Coord(3, 1), cellC1);
    cells.put(new Coord(1, 2), cellA2);
    cells.put(new Coord(2, 2), cellB2);
    cells.put(new Coord(3, 2), cellC2);

    refSingle = new CellReference(new Coord(1, 1), new Coord(1, 1));
    refMultiRow = new CellReference(new Coord(1, 1), new Coord(3, 1));
    refMultiRegion = new CellReference(new Coord(1, 1), new Coord(3, 2));

    cells.put(new Coord(1, 3), new FormulaCell(refSingle, "=A1"));
    cells.put(new Coord(2, 3), new FormulaCell(refMultiRow, "=A1:C1"));
    cells.put(new Coord(3, 3), new FormulaCell(refMultiRegion, "=A1:C2"));

    refDirectCycle = new CellReference(new Coord(1, 4), new Coord(1, 4));
    refIndirectCycleA = new CellReference(new Coord(3, 4), new Coord(3, 4));
    refIndirectCycleB = new CellReference(new Coord(2, 4), new Coord(2, 4));

    cells.put(new Coord(1, 4), new FormulaCell(refDirectCycle, "=A4"));
    cells.put(new Coord(2, 4), new FormulaCell(refIndirectCycleA, "=C4"));
    cells.put(new Coord(3, 4), new FormulaCell(refIndirectCycleB, "=B4"));

    tolerance = 0.00000001;
  }

  @Test
  public void testEvaluate() {
    assertEquals(cellA1.getValue(),
            refSingle.evaluate(cells, new Hashtable<>()));
    assertEquals(new ErrorValue(new IllegalArgumentException("Can't evaluate a multi-reference.")),
            refMultiRow.evaluate(cells, new Hashtable<>()));
    assertEquals(new ErrorValue(new IllegalArgumentException("Can't evaluate a multi-reference.")),
            refMultiRegion.evaluate(cells, new Hashtable<>()));
  }

  @Test
  public void testContainsCycle() {
    assertTrue(refDirectCycle.containsCyclicalReference(new HashSet<>(), cells, new HashSet<>()));
    assertTrue(refIndirectCycleA.containsCyclicalReference(new HashSet<>(), cells,
            new HashSet<>()));
    assertTrue(refIndirectCycleB.containsCyclicalReference(new HashSet<>(), cells,
            new HashSet<>()));
    assertFalse(refSingle.containsCyclicalReference(new HashSet<>(), cells, new HashSet<>()));
    assertFalse(refMultiRow.containsCyclicalReference(new HashSet<>(), cells, new HashSet<>()));
    assertFalse(refMultiRegion.containsCyclicalReference(new HashSet<>(), cells, new HashSet<>()));
  }

  @Test
  public void testAccept() {
    assertEquals(12.0, refSingle.accept(new SumFunction(cells,
            new Hashtable<>())), tolerance);
    assertEquals(48.0, refMultiRow.accept(new SumFunction(cells,
            new Hashtable<>())), tolerance);
    assertEquals(127.49, refMultiRegion.accept(new SumFunction(cells,
            new Hashtable<>())), tolerance);
  }
}
