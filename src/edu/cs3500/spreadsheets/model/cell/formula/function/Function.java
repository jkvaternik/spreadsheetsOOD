package edu.cs3500.spreadsheets.model.cell.formula.function;


import java.util.ArrayList;
import java.util.List;

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

  public abstract Value evaluate();

  public List<Formula> getArgs() {
    return this.args;
  }
}
