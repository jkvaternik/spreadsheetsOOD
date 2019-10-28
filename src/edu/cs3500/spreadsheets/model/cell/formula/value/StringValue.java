package edu.cs3500.spreadsheets.model.cell.formula.value;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import java.util.Hashtable;

/**
 * Represents a string value in a spreadsheet cell.
 */
public class StringValue implements Value {
  String value;
  /**
   * Creates an instance of a string value.
   */
  public StringValue(String value) {
    this.value = value;
  }

  @Override
  public Value evaluate(Hashtable<Coord, Cell> spreadsheet) {
    return this;
  }
}
