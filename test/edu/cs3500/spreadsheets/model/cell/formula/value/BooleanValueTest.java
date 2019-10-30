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
 * Tests for {@link BooleanValue}.
 */
public class BooleanValueTest {
  BooleanValue b1 = new BooleanValue(true);
  BooleanValue b2 = new BooleanValue(false);
  BooleanValue b3 = new BooleanValue(true);

  @Test
  public void testToString() {
    assertEquals("true", b1.toString());
    assertEquals("false", b2.toString());
  }

  @Test
  public void testEquals() {
    assertTrue(b1.equals(b1));
    assertTrue(b1.equals(b3));
    assertFalse(b1.equals(b2));
    assertFalse(b1.equals(true));
  }

  @Test
  public void testHashcode() {
    assertEquals(b1.hashCode(), Objects.hash(true));
    assertEquals(b2.hashCode(), Objects.hash(false));
  }

  @Test
  public void testEvaluate() {
    assertEquals(b1, b1.evaluate(new Hashtable<>(), new Hashtable<>()));
  }

  @Test
  public void testContainsCycle() {
    assertFalse(b3.containsCyclicalReference(new HashSet<>(), new Hashtable<>(), new HashSet<>()));
  }

  @Test
  public void testGetValue() {
    assertEquals(true, b1.getValue());
    assertEquals(false, b2.getValue());
  }

  @Test
  public void testAccept() {
    assertEquals(0.0, b3.accept(new SumFunction(new Hashtable<>(), new Hashtable<>())), 0.001);
  }

  @Test (expected = IllegalStateException.class)
  public void testAccept_InvalidCapitalizeFunctionArg() {
    b2.accept(new CapitalizeFunction(new Hashtable<>(), new Hashtable<>()));
  }

  @Test (expected = IllegalStateException.class)
  public void testAccept_InvalidLessThanFunctionArg() {
    b1.accept(new LessThanFunction(new Hashtable<>(), new Hashtable<>()));
  }
}
