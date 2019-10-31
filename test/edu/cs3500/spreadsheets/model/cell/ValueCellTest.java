package edu.cs3500.spreadsheets.model.cell;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import edu.cs3500.spreadsheets.model.cell.formula.value.BooleanValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.DoubleValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.ErrorValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.StringValue;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Objects;
import org.junit.Test;

/**
 * Tests for {@link ValueCell}.
 */
public class ValueCellTest {
  ValueCell bv = new ValueCell("true", new BooleanValue(true));
  ValueCell dv = new ValueCell("6.0", new DoubleValue(6.0));
  ValueCell sv = new ValueCell("hi", new StringValue("hi"));
  ValueCell ev = new ValueCell("#ERR",
      new ErrorValue(new IllegalArgumentException("Cell cannot reference itself.")));


  @Test
  public void testEvaluate() {
    assertEquals(new BooleanValue(true), bv.evaluate(new Hashtable<>(), new Hashtable<>()));
    assertEquals(new StringValue("hi"), sv.evaluate(new Hashtable<>(), new Hashtable<>()));
    assertEquals(new DoubleValue(6.0), dv.evaluate(new Hashtable<>(), new Hashtable<>()));
    assertEquals(new ErrorValue(new IllegalArgumentException("Cell cannot reference itself.")),
        ev.evaluate(new Hashtable<>(), new Hashtable<>()));
  }

  @Test
  public void testGetRawContents() {
    assertEquals("hi", sv.getRawContents());
    assertEquals("true", bv.getRawContents());
    assertEquals("6.0", dv.getRawContents());
    assertEquals("#ERR", ev.getRawContents());
  }

  @Test
  public void testContainsCycle() {
    assertFalse(bv.containsCyclicalReference(new HashSet<>(), new Hashtable<>(), new HashSet<>()));
    assertFalse(sv.containsCyclicalReference(new HashSet<>(), new Hashtable<>(), new HashSet<>()));
    assertFalse(dv.containsCyclicalReference(new HashSet<>(), new Hashtable<>(), new HashSet<>()));
    assertFalse(ev.containsCyclicalReference(new HashSet<>(), new Hashtable<>(), new HashSet<>()));
  }

  @Test
  public void testToString() {
    assertEquals("hi", sv.toString());
    assertEquals("true", bv.toString());
    assertEquals("6.0", dv.toString());
    assertEquals("#ERR", ev.toString());
  }

  @Test
  public void testEquals() {
    assertTrue(bv.equals(bv));
    assertTrue(bv.equals(new ValueCell("true", new BooleanValue(true))));
    assertFalse(bv.equals(new ValueCell("TrUe", new BooleanValue(true))));
    assertFalse(dv.equals(ev));
    assertFalse(dv.equals(new ValueCell("5.6", new DoubleValue(5.6))));
  }

  @Test
  public void testHashcode() {
    assertEquals(bv.hashCode(), Objects.hash("true", new BooleanValue(true)));
    assertEquals(dv.hashCode(), Objects.hash("6.0", new DoubleValue(6.0)));
    assertEquals(sv.hashCode(), Objects.hash("hi", new StringValue("hi")));
    assertEquals(ev.hashCode(), Objects.hash("#ERR",
        new ErrorValue(new IllegalArgumentException("Cell cannot reference itself."))));
  }
}
