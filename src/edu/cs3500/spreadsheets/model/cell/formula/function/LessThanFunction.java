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

/**
 * Represents the less than function.
 */
public class LessThanFunction implements FormulaVisitor<Double> {
  private Hashtable<Coord, Cell> cells;

  /**
   * Constructs an instance of the LessThanFunction given a list of one or more arguments.
   */
  public LessThanFunction(Hashtable<Coord, Cell> cells) {
    this.cells = cells;
  }

  @Override
  public Double visitDoubleValue(DoubleValue val) throws IllegalStateException {
    return val.getValue();
  }

  @Override
  public Double visitBooleanValue(BooleanValue val) throws IllegalStateException {
    throw new IllegalStateException("Invalid argument to <.");
  }

  @Override
  public Double visitStringValue(StringValue val) throws IllegalStateException {
    throw new IllegalStateException("Invalid argument to <.");
  }

  @Override
  public Double visitErrorValue(ErrorValue val) throws IllegalStateException {
    throw new IllegalStateException("Invalid argument to <.");
  }

  @Override
  public Double visitCellReference(CellReference ref) throws IllegalStateException {
    List<Cell> references = ref.getAllCells(this.cells);
    if (references.size() == 1) {
      return this.apply(references.get(0).evaluate(this.cells));
    } else {
      throw new IllegalStateException("Invalid argument to <.");
    }
  }

  @Override
  public Double visitFunction(Function func) throws IllegalStateException {
    return this.apply(func.evaluate(this.cells));
  }

  @Override
  public Double apply(Formula arg1) throws IllegalStateException {
    return arg1.accept(this);
  }
}
