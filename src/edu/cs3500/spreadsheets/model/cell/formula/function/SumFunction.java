package edu.cs3500.spreadsheets.model.cell.formula.function;

import java.util.List;

import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.value.Value;

/**
 * Represents the sum function.
 */
public class SumFunction implements Function {
  private List<Formula> args;

  public SumFunction(List<Formula> args) {
    if (args == null) {
      throw new IllegalArgumentException("#N/A");
    }


  }

  @Override
  public Value evaluate() {
    return null;
  }
}
