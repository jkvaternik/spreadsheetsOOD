package edu.cs3500.spreadsheets.model.cell.formula.value;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import java.util.Hashtable;
import java.util.Objects;

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

  @Override
  public Double getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return Double.toString(this.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.value);
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof DoubleValue) {
      return ((DoubleValue) other).getValue() == this.getValue();
    }
    return false;
  }
}
