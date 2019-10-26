package edu.cs3500.spreadsheets.model.cell.formula.function;

import java.util.List;

import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.value.Value;

/**
 * Represents the less than function.
 */
public class LessThanFunction extends Function {

  /**
   * Constructs an instance of the LessThanFunction given a list of one or more arguments.
   * @param args list of arguments
   */
  public LessThanFunction(List<Formula> args) {
    super(args);
  }

  @Override
  public Value evaluate() {
    return null;
  }
}
