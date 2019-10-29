package edu.cs3500.spreadsheets.model.cell.formula.value;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.formula.function.FormulaVisitor;

import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

/**
 * Represents a string value in a spreadsheet cell.
 */
public class StringValue implements Value {
  private String value;
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

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitStringValue(this);
  }

  @Override
  public boolean containsCyclicalReference(List<Coord> visitedCoords,
      Hashtable<Coord, Cell> cells) {
    return false;
  }

  @Override
  public String getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return this.value.replace("\"", "\\\"");
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.value);
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof StringValue) {
      return ((StringValue) other).getValue().equals(this.getValue());
    }
    return false;
  }
}
