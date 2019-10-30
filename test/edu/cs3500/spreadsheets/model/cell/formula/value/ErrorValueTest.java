package edu.cs3500.spreadsheets.model.cell.formula.value;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import edu.cs3500.spreadsheets.model.cell.formula.function.CapitalizeFunction;
import edu.cs3500.spreadsheets.model.cell.formula.function.LessThanFunction;
import edu.cs3500.spreadsheets.model.cell.formula.function.ProductFunction;
import edu.cs3500.spreadsheets.model.cell.formula.function.SumFunction;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Objects;
import org.junit.Test;

/**
 * Tests for {@link ErrorValueTest}.
 */
public class ErrorValueTest {
  ErrorValue e1 = new ErrorValue(new IllegalArgumentException("Function could not be evaluated."));
  ErrorValue e2 = new ErrorValue(new IllegalStateException("Cell cannot reference itself."));

  @Test
  public void testToString() {
    assertEquals("Function could not be evaluated.", e1.toString());
    assertEquals("Cell cannot reference itself.", e2.toString());
  }

  @Test
  public void testEquals() {
    assertTrue(e1.equals(e1));
    assertFalse(e1.equals(e2));
    assertFalse(e2.equals(new IllegalStateException("Cell cannot reference itself.")));
    assertFalse(e2.equals(
        new ErrorValue(new IllegalArgumentException("Cell cannot reference itself."))));
  }

  @Test
  public void testHashcode() {
    assertEquals(e1.hashCode(), Objects.hash(e1.getValue()));
  }

  @Test
  public void testEvaluate() {
    assertEquals(e1, e1.evaluate(new Hashtable<>(), new Hashtable<>()));
  }

  @Test
  public void testContainsCycle() {
    assertFalse(e2.containsCyclicalReference(new HashSet<>(), new Hashtable<>(), new HashSet<>()));
  }

  @Test
  public void testGetValue() {
    assertEquals("Function could not be evaluated.", e1.getValue().getMessage());
  }

  @Test (expected = IllegalStateException.class)
  public void testAccept_InvalidCapitalizeFunctionArg() {
    e2.accept(new CapitalizeFunction(new Hashtable<>(), new Hashtable<>()));
  }

  @Test (expected = IllegalStateException.class)
  public void testAccept_InvalidSumFunctionArg() {
    e1.accept(new SumFunction(new Hashtable<>(), new Hashtable<>()));
  }

  @Test (expected = IllegalStateException.class)
  public void testAccept_InvalidProductFunctionArg() {
    e2.accept(new ProductFunction(new Hashtable<>(), new Hashtable<>()));
  }

  @Test (expected = IllegalStateException.class)
  public void testAccept_InvalidLessThanFunctionArg() {
    e1.accept(new LessThanFunction(new Hashtable<>(), new Hashtable<>()));
  }
}
