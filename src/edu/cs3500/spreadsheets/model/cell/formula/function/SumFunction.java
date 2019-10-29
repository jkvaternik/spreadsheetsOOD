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
  private Hashtable<Coord, Cell> cells;
  /**
   * Constructs an instance of the SumFunction function object
   */
  public SumFunction(Hashtable<Coord, Cell> cells) {
    this.cells = cells;
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
  public Double visitErrorValue(ErrorValue val) {
    return 0.0;
  }

  @Override
  public Double visitCellReference(CellReference ref) {
    return 0.0;
  }

  @Override
  public Double visitFunction(Function func) {
    return this.apply(func.evaluate(this.cells));
  }

  @Override
  public Double apply(Formula formula) {
    return formula.accept(this);
  }
}
