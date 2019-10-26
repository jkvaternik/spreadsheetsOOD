package edu.cs3500.spreadsheets.model.cell.formula.function;

import java.util.List;

import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.value.Value;

/**
 * Represents the capitalize function.
 */
public class CapitalizeFunction extends Function {

  /**
   * Constructs an instance of the ProductFunction given a list of one or more arguments.
   * @param args list of arguments
   */
  public CapitalizeFunction(List<Formula> args) {
    super(args);
  }

  @Override
  public Value evaluate() {
    return null;
  }
}
