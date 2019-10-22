package edu.cs3500.spreadsheets.model.cell;

import edu.cs3500.spreadsheets.model.formula.Formula;
import edu.cs3500.spreadsheets.model.value.Value;



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
  public void evaluate() {

  }

  @Override
  public String getUserString() {
    return null;
  }

  @Override
  public Value getValue() {
    return null;
  }
}
