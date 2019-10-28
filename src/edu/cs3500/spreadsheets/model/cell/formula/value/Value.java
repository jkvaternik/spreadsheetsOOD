package edu.cs3500.spreadsheets.model.cell.formula.value;

import java.util.Hashtable;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;

/**
 * Represents one of the allowable value types of a cell in the spreadsheet.
 */
public interface Value extends Formula {

  /**
   * Gets the internal value of this Value.
   * @param <T> The type of primitive value
   * @return The primitive value
   */
  <T> T getValue();
}
