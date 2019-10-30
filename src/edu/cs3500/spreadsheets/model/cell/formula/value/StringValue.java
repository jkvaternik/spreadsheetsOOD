package edu.cs3500.spreadsheets.model.cell.formula.value;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.formula.function.FormulaVisitor;

import java.util.HashSet;
import java.util.Hashtable;
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
  public Value evaluate(Hashtable<Coord, Cell> spreadsheet, Hashtable<Formula, Value> values) {
    return this;
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitStringValue(this);
  }

  @Override
  public boolean containsCyclicalReference(HashSet<Coord> visitedCoords,
      Hashtable<Coord, Cell> cells, HashSet<Coord> coordsNoCycle) {
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
