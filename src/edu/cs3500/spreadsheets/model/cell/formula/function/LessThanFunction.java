package edu.cs3500.spreadsheets.model.cell.formula.function;

import java.util.Hashtable;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.value.Value;

/**
 * Represents the less than function.
 */
public class LessThanFunction extends AFunction {

  /**
   * Constructs an instance of the LessThanFunction given a list of one or more arguments.
   * @param args list of arguments
   */
  public LessThanFunction(List<Formula> args) {
    super(args);
  }

  @Override
  public Value evaluate(Hashtable<Coord, Cell> cells) {
    return null;
  }
}
