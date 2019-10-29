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
public class SumFunction implements FormulaVisitor<Double>, IFunction {
  private List<Formula> args;
  /**
   * Constructs an instance of the SumFunction given a list of one or more arguments.
   * @param args list of arguments
   */
  public SumFunction(List<Formula> args) {
    this.args = args;
  }

  @Override
  public Value evaluate(Hashtable<Coord, Cell> cells) {
    if (this.getArgs().isEmpty()) {
      throw new IllegalArgumentException("List of arguments cannot be empty.");
    }

    double result = 0.0;

    for (Formula arg : this.getArgs()) {
      result += this.apply(arg);

    }
    return new DoubleValue(result);
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitSumFunction(this);
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
  public Double visitSumFunction(SumFunction func) {
    return 0.0;
  }

  @Override
  public Double visitProductFunction(ProductFunction func) {
    return 0.0;
  }

  @Override
  public Double visitLessThanFunction(LessThanFunction func) {
    return 0.0;
  }

  @Override
  public Double visitCapitalizeFunction(CapitalizeFunction func) {
    return 0.0;
  }

  @Override
  public List<Formula> getArgs() {
    return null;
  }

  @Override
  public void addArg(Formula arg) {

  }

  @Override
  public Double apply(Formula arg1) {
    return null;
  }
}
