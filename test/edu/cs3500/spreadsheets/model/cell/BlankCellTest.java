package edu.cs3500.spreadsheets.model.cell;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import edu.cs3500.spreadsheets.model.cell.formula.value.StringValue;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Objects;
import org.junit.Test;

/**
 * Tests for {@link BlankCell}.
 */
public class BlankCellTest {
  BlankCell bc = new BlankCell();

  @Test
  public void testEvaluate() {
    assertEquals(new StringValue(""), bc.evaluate(new Hashtable<>(), new Hashtable<>()));
  }

  @Test
  public void testGetRawContents() {
    assertNull(bc.getRawContents());
  }

  @Test
  public void testContainsCycle() {
    assertFalse(bc.containsCyclicalReference(new HashSet<>(), new Hashtable<>(), new HashSet<>()));
  }

  @Test
  public void testToString() {
    assertEquals("", bc.toString());
  }

  @Test
  public void testEquals() {
    assertTrue(bc.equals(bc));
    assertTrue(bc.equals(new BlankCell()));
    assertFalse(bc.equals(new ValueCell("hi", new StringValue("hi"))));
  }

  @Test
  public void testHashcode() {
    assertEquals(bc.hashCode(), Objects.hash("blank cell"));
  }
}
