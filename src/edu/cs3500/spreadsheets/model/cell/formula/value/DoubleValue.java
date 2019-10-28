package edu.cs3500.spreadsheets.model.cell.formula.value;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import java.util.Hashtable;

/**
 * Represents a double value in a spreadsheet cell.
 */
public class DoubleValue implements Value {
  double value;
  /**
   * Creates an instance of a double value.
   */
  public DoubleValue(double value) {
    this.value = value;
  }

  @Override
  public Value evaluate(Hashtable<Coord, Cell> spreadsheet) {
    return this;
  }
}
