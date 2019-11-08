package edu.cs3500.spreadsheets.model.cell;

import edu.cs3500.spreadsheets.model.Coord;
import org.junit.Test;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.cell.formula.value.StringValue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link BlankCell}.
 */
public class BlankCellTest {
  BlankCell bc = new BlankCell();

  @Test
  public void testEvaluate() {
    assertEquals(new StringValue(""), bc.getValue());
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
  public void testReferencesCell() {
    assertFalse(bc.referencesCell(new Coord(1, 1)));
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
