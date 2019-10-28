package edu.cs3500.spreadsheets.model.cell;

import java.util.Hashtable;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.value.Value;



/**
 * Represents a cell which contains a formula.
 */
public class FormulaCell implements Cell {
  private Formula formula;
  private String rawContents;
  private Value value;

  /**
   * Creates an instance of the formula cell based on the given formula, string, and value.
   * @param formula The formula
   * @param rawContents The string
   * @param value The value
   */
  public FormulaCell(Formula formula, String rawContents, Value value) {
    this.formula = formula;
    this.rawContents = rawContents;
    this.value = value;
  }

  @Override
  public void evaluate(Hashtable<Coord, Cell> cells) {
    this.value = this.formula.evaluate(cells);
  }

  @Override
  public String getRawContents() {
    return this.rawContents;
  }

  @Override
  public Value getValue() {
    return this.value;
  }
}
