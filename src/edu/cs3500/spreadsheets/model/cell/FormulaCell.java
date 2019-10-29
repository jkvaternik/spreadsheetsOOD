package edu.cs3500.spreadsheets.model.cell;

import java.util.Hashtable;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.formula.value.Value;
import java.util.List;


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
  public Value evaluate(Hashtable<Coord, Cell> cells) {
    return this.formula.evaluate(cells);
  }

  @Override
  public String getRawContents() {
    return this.rawContents;
  }

  @Override
  public boolean containsCyclicalReference(List<Coord> visitedCoords,
      Hashtable<Coord, Cell> cells) {
    return this.formula.containsCyclicalReference(visitedCoords, cells);
  }
}
