package edu.cs3500.spreadsheets.model.cell.formula.function;

import edu.cs3500.spreadsheets.model.cell.formula.Formula;

/**
 * Represents a Function.
 */
public interface IFunction extends Formula {

  /**
   * Adds an argument to the function.
   *
   * @param arg The argument to add
   */
  void addArg(Formula arg);
}
