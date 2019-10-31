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
 * Represents the less than function.
 */
public class LessThanFunction implements FormulaVisitor<Double> {
  private Hashtable<Coord, Cell> cells;
  private Hashtable<Formula, Value> values;

  /**
   * Constructs an instance of the LessThanFunction given a list of one or more arguments.
   */
  public LessThanFunction(Hashtable<Coord, Cell> cells, Hashtable<Formula, Value> values) {
    this.cells = cells;
    this.values = values;
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
      return this.apply(references.get(0).evaluate(this.cells, values));
    } else {
      throw new IllegalStateException("Invalid argument to <.");
    }
  }

  @Override
  public Double visitFunction(Function func) throws IllegalStateException {
    return this.apply(func.evaluate(this.cells, values));
  }

  @Override
  public Double apply(Formula formula) throws IllegalStateException {
    return formula.accept(this);
  }
}
