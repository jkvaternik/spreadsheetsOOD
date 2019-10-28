package edu.cs3500.spreadsheets.model.cell.formula;

import java.util.Hashtable;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.value.Value;

/**
 * Represents a cell formula. A formula can be a value, a function, or a reference to one or more
 * cells.
 * TODO: Determine what methods should be in this interface.
 */
public interface Formula {
  /**
   * Evaluates the formula.
   * @return the evaluated value
   */
  Value evaluate(Hashtable<Coord, Cell> spreadsheet);

  /**
   * Determines if this Formula contains a cyclic reference.
   * A cyclic reference is when this formula refers to a cell who refers back to this cell.
   * @param spreadsheet The coordinate to cell mapping
   * @return Whether or not there exists a cycle
   */
  boolean containsCyclicReference(Hashtable<Coord, Cell> spreadsheet);
}
