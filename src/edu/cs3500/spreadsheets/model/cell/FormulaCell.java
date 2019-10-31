package edu.cs3500.spreadsheets.model.cell;

import java.util.HashSet;
import java.util.Hashtable;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.formula.value.Value;
import java.util.Objects;


/**
 * Represents a cell which contains a formula.
 */
public class FormulaCell implements Cell {
  private Formula formula;
  private String rawContents;

  /**
   * Creates an instance of the formula cell based on the given formula, string, and value.
   * @param formula The formula
   * @param rawContents The string
   */
  public FormulaCell(Formula formula, String rawContents) {
    this.formula = formula;
    this.rawContents = rawContents;
  }

  @Override
  // TODO: Add a Cell->Value map so we only evaluate each cell once (memoize)
  public Value evaluate(Hashtable<Coord, Cell> cells, Hashtable<Formula, Value> values) {
      return this.formula.evaluate(cells, values);
  }

  @Override
  public String getRawContents() {
    return this.rawContents;
  }

  @Override
  public boolean containsCyclicalReference(HashSet<Coord> visitedCoords,
      Hashtable<Coord, Cell> cells, HashSet<Coord> coordsNoCycle) {
    return this.formula.containsCyclicalReference(visitedCoords, cells, coordsNoCycle);
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof FormulaCell) {
      return this.rawContents.equals(((FormulaCell) other).rawContents)
          && this.formula.equals(((FormulaCell) other).formula);
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.rawContents, this.formula.hashCode());
  }

  @Override
  public String toString() {
    return this.rawContents;
  }
}
