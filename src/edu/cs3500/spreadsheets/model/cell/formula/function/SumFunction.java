package edu.cs3500.spreadsheets.model.cell.formula.function;

import java.util.Hashtable;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.formula.CellReference;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.formula.value.BooleanValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.DoubleValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.ErrorValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.StringValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.Value;

/**
 * Represents the sum function.
 */
public class SumFunction implements FormulaVisitor<Double> {
  private List<Formula> args;
  /**
   * Constructs an instance of the SumFunction given a list of one or more arguments.
   * @param args list of arguments
   */
  public SumFunction(List<Formula> args) {
    this.args = args;
  }

  @Override
  public Double visitDoubleValue(DoubleValue val) {
    return val.getValue();
  }

  @Override
  public Double visitBooleanValue(BooleanValue val) {
    return 0.0;
  }

  @Override
  public Double visitStringValue(StringValue val) {
    return 0.0;
  }

  @Override
  public Double visitCellReference(CellReference ref) {
    return 0.0;
  }

  @Override
  public Double visitFunction(Function func) {
    return null;
  }

  @Override
  public List<Formula> getArgs() {
    return null;
  }

  @Override
  public void addArg(Formula arg) {

  }

  @Override
  public Double apply(Formula formula, Double result) {
    return formula.accept(this);
  }
}
