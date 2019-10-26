package edu.cs3500.spreadsheets.model.cell.formula;

import edu.cs3500.spreadsheets.model.cell.value.Value;

/**
 * Represents a cell formula. A formula can be a value, a function, or a reference to one or more
 * cells.
 * TODO: Determine what methods should be in this interface.
 */
public interface Formula {
  /**
   * Evaluates the formula.
   * @return the evaluated value
   */
  Value evaluate();

}
