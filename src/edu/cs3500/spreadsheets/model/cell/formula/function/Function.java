package edu.cs3500.spreadsheets.model.cell.formula.function;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.formula.value.ErrorValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.Value;

public class Function implements IFunction {
  private List<Formula> args;
  private EFunctions func;

  /**
   * Creates a function with the given arguments.
   * @param args The arguments
   */
  public Function(EFunctions func, List<Formula> args) {
    if (args == null) {
      this.args = new ArrayList<>();
      this.func = func;
    }
    else {
      this.args = args;
      this.func = func;
    }
  }

  @Override
  public Value evaluate(Hashtable<Coord, Cell> cells) {
    switch (func) {
      case SUM:
        break;
      case PRODUCT:
        break;
      case LESSTHAN:
        break;
      case CAPITALIZE:
        break;
    }
    return new ErrorValue(new IllegalArgumentException("Function could not be evaluated."));
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitFunction(this);
  }

  @Override
  public List<Formula> getArgs() {
    return this.args;
  }

  @Override
  public void addArg(Formula arg) {
    this.args.add(arg);
  }
}
