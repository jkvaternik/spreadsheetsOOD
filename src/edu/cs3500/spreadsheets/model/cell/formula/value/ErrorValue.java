package edu.cs3500.spreadsheets.model.cell.formula.value;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import java.util.Hashtable;
import java.util.Objects;

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

  @Override
  public Exception getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return value.toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.value);
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof ErrorValue) {
      return ((ErrorValue) other).getValue().equals(this.getValue());
    }
    return false;
  }
}
