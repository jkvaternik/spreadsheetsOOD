package edu.cs3500.spreadsheets.model.cell.formula.value;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import edu.cs3500.spreadsheets.model.cell.formula.function.CapitalizeFunction;
import edu.cs3500.spreadsheets.model.cell.formula.function.SumFunction;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Objects;
import org.junit.Test;

/**
 * Tests for {@link StringValue}.
 */
public class StringValueTest {
  StringValue s1 = new StringValue("Jack says \"Hi\". Jill has one backslash \\ here.");
  StringValue s2 = new StringValue("\\hello");
  @Test
  public void testToString() {
    assertEquals("\"Jack says \\\"Hi\\\". Jill has one backslash \\\\ here.\"",
        s1.toString());
    assertEquals("\"\\\\hello\"", s2.toString());
  }

  @Test
  public void testEquals() {
    assertTrue(s1.equals(s1));
    assertTrue(s1.equals(new StringValue("Jack says \"Hi\". Jill has one backslash \\ here.")));
    assertFalse(s1.equals(s2));
    assertFalse(s1.equals(new HashSet<Integer>()));
    assertFalse(s2.equals("\\hello"));
  }

  @Test
  public void testHashcode() {
    assertEquals(s1.hashCode(), Objects.hash(s1.getValue()));
    assertNotEquals(s1.hashCode(), Objects.hash(s2.getValue()));
  }

  @Test
  public void testEvaluate() {
    assertEquals(s1.evaluate(new Hashtable<>(), new Hashtable<>()), s1);
  }

  @Test
  public void testContainsCycle() {
    assertFalse(s2.containsCyclicalReference(new HashSet<>(), new Hashtable<>(), new HashSet<>()));
  }

  @Test
  public void testGetValue() {
    assertEquals("Jack says \"Hi\". Jill has one backslash \\ here.", s1.getValue());
    assertEquals("\\hello", s2.getValue());
  }

  @Test
  public void testAccept() {
    assertEquals("\\hello", s2.accept(new CapitalizeFunction(new Hashtable<>(),
        new Hashtable<>())));
    assertEquals(new DoubleValue(0.0).getValue(),
        s1.accept(new SumFunction(new Hashtable<>(), new Hashtable<>())));
  }
}