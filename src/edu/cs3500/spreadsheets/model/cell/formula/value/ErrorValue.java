package edu.cs3500.spreadsheets.model.cell.formula.value;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import java.util.Hashtable;

/**
 * Represents an error value in a spreadsheet cell.
 */
public class ErrorValue implements Value {
  Exception value;
  /**
   * Creates an instance of an error value.
   */
  public ErrorValue(Exception value) {
    this.value = value;
  }

  @Override
  public Value evaluate(Hashtable<Coord, Cell> spreadsheet) {
    return this;
  }
}
