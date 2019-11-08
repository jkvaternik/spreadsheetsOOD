package edu.cs3500.spreadsheets.model.cell.formula.value;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.formula.function.FormulaVisitor;

/**
 * Represents a double value in a spreadsheet cell.
 */
public class DoubleValue implements Value {
  private double value;

  /**
   * Creates an instance of a double value.
   */
  public DoubleValue(double value) {
    this.value = value;
  }

  @Override
  public Value evaluate(Hashtable<Coord, Cell> spreadsheet, Hashtable<Formula, Value> values) {
    return this;
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitDoubleValue(this);
  }

  @Override
  public <R> R accept(ValueVisitor<R> visitor) {
    return visitor.visitDouble(this);
  }

  @Override
  public boolean containsCyclicalReference(HashSet<Coord> visitedCoords,
                                           Hashtable<Coord, Cell> cells,
                                           HashSet<Coord> coordsNoCycle) {
    return false;
  }

  @Override
  public boolean referencesCell(Coord coord) {
    return false;
  }

  @Override
  public Double getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return String.format("%f", this.value).stripTrailing();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.value);
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof DoubleValue) {
      return ((DoubleValue) other).getValue().equals(this.getValue());
    }
    return false;
  }
}
