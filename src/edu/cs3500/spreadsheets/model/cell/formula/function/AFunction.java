package edu.cs3500.spreadsheets.model.cell.formula.function;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.value.Value;

public abstract class AFunction implements IFunction {
  private List<Formula> args;

  /**
   * Creates a function with the given arguments.
   * @param args The arguments
   */
  public AFunction(List<Formula> args) {
    if (args == null) {
      this.args = new ArrayList<>();
    }
    else {
      this.args = args;
    }
  }

  @Override
  public abstract Value evaluate(Hashtable<Coord, Cell> cells);

  @Override
  public List<Formula> getArgs() {
    return this.args;
  }

  @Override
  public void addArg(Formula arg) {
    this.args.add(arg);
  }
}
