package edu.cs3500.spreadsheets.model.cell.formula.function;

import java.util.List;

import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.value.DoubleValue;
import edu.cs3500.spreadsheets.model.cell.value.Value;

/**
 * Represents the sum function.
 */
public class SumFunction extends Function {
  /**
   * Constructs an instance of the SumFunction given a list of one or more arguments.
   * @param args list of arguments
   */
  public SumFunction(List<Formula> args) {
    super(args);
  }

  @Override
  public Value evaluate() {
    if (super.getArgs().isEmpty()) {
      throw new IllegalArgumentException("List of arguments cannot be empty.");
    }

    double result = 0.0;
    // if arg doesn't evaluate to a double value, then error, if it doesn't evaluate, error (within individual evaluate)
    for (Formula arg : super.getArgs()) {
      Value<Double> val = arg.evaluate();
      if (val.getValue() instanceof Double) {
        result += val.getValue();
      }
    }
    return new DoubleValue<>(result);
  }
}
