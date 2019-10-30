package edu.cs3500.spreadsheets.model.cell.formula.value;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import edu.cs3500.spreadsheets.model.cell.formula.function.CapitalizeFunction;
import edu.cs3500.spreadsheets.model.cell.formula.function.LessThanFunction;
import edu.cs3500.spreadsheets.model.cell.formula.function.SumFunction;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Objects;
import org.junit.Test;

/**
 * Tests for {@link DoubleValue}.
 */
public class DoubleValueTest {
  DoubleValue d1 = new DoubleValue(0.0);
  DoubleValue d2 = new DoubleValue(5.6);
  DoubleValue d3 = new DoubleValue(-5.6);

  @Test
  public void testToString() {
    assertEquals("0.000000", d1.toString());
    assertEquals("5.600000", d2.toString());
    assertEquals("-5.600000", d3.toString());
  }

  @Test
  public void testEquals() {
    assertTrue(d1.equals(d1));
    assertTrue(d2.equals(new DoubleValue(5.6)));
    assertFalse(d2.equals(d3));
    assertFalse(d3.equals(-5.6));
  }

  @Test
  public void testHashcode() {
    assertEquals(d1.hashCode(), Objects.hash(0.0));
    assertEquals(d3.hashCode(), Objects.hash(-5.6));
  }

  @Test
  public void testEvaluate() {
    assertEquals(d1.evaluate(new Hashtable<>(), new Hashtable<>()), d1);
  }

  @Test
  public void testContainsCycle() {
    assertEquals(d3.containsCyclicalReference(new HashSet<>(), new Hashtable<>(), new HashSet<>()),
        false);
  }

  @Test
  public void testGetValue() {
    assertEquals(5.6, d2.getValue(), 0.0001);
    assertEquals(-5.6, d3.getValue(), 0.0001);
  }

  @Test
  public void testAccept() {
    assertEquals(d3.getValue(), d3.accept(new LessThanFunction(new Hashtable<>(),
        new Hashtable<>())));
    assertEquals(d2.getValue(),
        d2.accept(new SumFunction(new Hashtable<>(), new Hashtable<>())));
  }

  @Test (expected = IllegalStateException.class)
  public void testAccept_InvalidFunctionArg() {
    d1.accept(new CapitalizeFunction(new Hashtable<>(), new Hashtable<>()));
  }
}
