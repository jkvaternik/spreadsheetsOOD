package edu.cs3500.spreadsheets.model.cell.formula.function;

import java.util.Hashtable;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.value.DoubleValue;
import edu.cs3500.spreadsheets.model.cell.value.ErrorValue;
import edu.cs3500.spreadsheets.model.cell.value.Value;

/**
 * Represents the sum function.
 */
public class SumFunction extends AFunction {
  /**
   * Constructs an instance of the SumFunction given a list of one or more arguments.
   * @param args list of arguments
   */
  public SumFunction(List<Formula> args) {
    super(args);
  }

  @Override
  public Value evaluate(Hashtable<Coord, Cell> cells) {
    if (super.getArgs().isEmpty()) {
      throw new IllegalArgumentException("List of arguments cannot be empty.");
    }

    double result = 0.0;

    for (Formula arg : super.getArgs()) {
      try {
        Value<Double> val = arg.evaluate(cells);
        result += val.getValue();
      }
      catch (IllegalArgumentException e) {
        return new ErrorValue(new IllegalArgumentException("Function could not be evaluated."));
      }
    }
    return new DoubleValue(result);
  }
}
