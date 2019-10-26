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
  private String cellString;
  private Value value;

  /**
   * Creates an instance of the formula cell based on the given formula, string, and value.
   * @param formula The formula
   * @param cellString The string
   * @param value The value
   */
  public FormulaCell(Formula formula, String cellString, Value value) {
    this.formula = formula;
    this.cellString = cellString;
    this.value = value;
  }

  @Override
  public void evaluate(Hashtable<Coord, Cell> spreadsheet) {
    this.value = this.formula.evaluate(spreadsheet);
  }

  @Override
  public String getUserString() {
    return this.cellString;
  }

  @Override
  public Value getValue() {
    return this.value;
  }
}
