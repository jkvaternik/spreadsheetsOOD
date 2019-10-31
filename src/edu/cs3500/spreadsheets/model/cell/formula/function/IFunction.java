package edu.cs3500.spreadsheets.model.cell.formula.function;

import java.util.List;

import edu.cs3500.spreadsheets.model.cell.formula.Formula;

public interface IFunction extends Formula {

  /**
   * Adds an argument to the function.
   *
   * @param arg The argument to add
   */
  void addArg(Formula arg);
}
