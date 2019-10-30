package edu.cs3500.spreadsheets.model.cell.formula.value;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.formula.function.FormulaVisitor;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Objects;

/**
 * Represents a boolean value in a spreadsheet cell.
 */
public class BooleanValue implements Value {
  private boolean value;
  /**
   * Creates an instance of a boolean value.
   */
  public BooleanValue(boolean value) {
    this.value = value;
  }

  @Override
  public Value evaluate(Hashtable<Coord, Cell> spreadsheet, Hashtable<Formula, Value> values) {
    return this;
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitBooleanValue(this);
  }

  @Override
  public boolean containsCyclicalReference(HashSet<Coord> visitedCoords,
      Hashtable<Coord, Cell> cells, HashSet<Coord> coordsNoCycle) {
    return false;
  }

  @Override
  public Boolean getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return Boolean.toString(this.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.value);
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof BooleanValue) {
      return ((BooleanValue) other).getValue() == this.getValue();
    }
    return false;
  }
}
