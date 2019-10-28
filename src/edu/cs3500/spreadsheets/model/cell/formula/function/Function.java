package edu.cs3500.spreadsheets.model.cell.formula.function;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.value.Value;

public abstract class Function implements Formula {
  private List<Formula> args;

  public Function(List<Formula> args) {
    if (args == null) {
      this.args = new ArrayList<>();
    }
    else {
      this.args = args;
    }
  }

  public abstract Value evaluate(Hashtable<Coord, Cell> cells);

  public List<Formula> getArgs() {
    return this.args;
  }

  /**
   * Adds an argument to this formula.
   * @param arg The argument to add
   */
  private void addArg(Formula arg) {
    this.args.add(arg);
  }
}
