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
 * Represents the product function.
 */
public class ProductFunction implements FormulaVisitor<Double> {
  int doubleValueCount;
  private Hashtable<Coord, Cell> cells;

  /**
   * Constructs an instance of the ProductFunction given a list of one or more arguments.
   */
  public ProductFunction(Hashtable<Coord, Cell> cells) {
    this.cells = cells;
    this.doubleValueCount = 0;
  }

  @Override
  public Double visitDoubleValue(DoubleValue val) throws IllegalStateException {
    doubleValueCount++;
    return val.getValue();
  }

  @Override
  public Double visitBooleanValue(BooleanValue val) throws IllegalStateException {
    return 1.0;
  }

  @Override
  public Double visitStringValue(StringValue val) throws IllegalStateException {
    return 1.0;
  }

  @Override
  public Double visitErrorValue(ErrorValue val) throws IllegalStateException {
    throw new IllegalStateException("One of the arguments is erroring");
  }

  @Override
  public Double visitCellReference(CellReference ref) throws IllegalStateException {
    List<Cell> references = ref.getAllCells(this.cells);
    double result = 1.0;
    for (Cell c : references) {
      result = result * this.apply(c.evaluate(this.cells));
    }
    return result;
  }

  @Override
  public Double visitFunction(Function func) throws IllegalStateException {
    return this.apply(func.evaluate(this.cells));
  }

  @Override
  public Double apply(Formula arg1) {
    return arg1.accept(this);
  }

  /**
   * Gets the count of double values that this function has visited.
   *
   * @return the count
   */
  int getDoubleValueCount() {
    return doubleValueCount;
  }
}
