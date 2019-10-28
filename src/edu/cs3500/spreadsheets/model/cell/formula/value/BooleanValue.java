package edu.cs3500.spreadsheets.model.cell.formula.value;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import java.util.Hashtable;

/**
 * Represents a boolean value in a spreadsheet cell.
 */
public class BooleanValue implements Value {
  boolean value;
  /**
   * Creates an instance of a boolean value.
   */
  public BooleanValue(Boolean value) {
    this.value = value;
  }

  @Override
  public Value evaluate(Hashtable<Coord, Cell> spreadsheet) {
    return this;
  }
}
