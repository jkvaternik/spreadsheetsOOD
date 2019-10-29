package edu.cs3500.spreadsheets.model.cell.formula.function;

import java.util.Hashtable;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.formula.CellReference;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.formula.value.BooleanValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.DoubleValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.ErrorValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.StringValue;

/**
 * Represents the capitalize function.
 */
public class CapitalizeFunction implements FormulaVisitor<String> {
  private Hashtable<Coord, Cell> cells;

  /**
   * Constructs an instance of the ProductFunction given a list of one or more arguments.
   */
  public CapitalizeFunction(Hashtable<Coord, Cell> cells) {
    this.cells = cells;
  }


  @Override
  public String visitDoubleValue(DoubleValue val) throws IllegalStateException {
    throw new IllegalStateException("Invalid argument to CAPITALIZE");
  }

  @Override
  public String visitBooleanValue(BooleanValue val) throws IllegalStateException {
    throw new IllegalStateException("Invalid argument to CAPITALIZE");
  }

  @Override
  public String visitStringValue(StringValue val) throws IllegalStateException {
    return val.getValue();
  }

  @Override
  public String visitErrorValue(ErrorValue val) throws IllegalStateException {
    throw new IllegalStateException("Invalid argument to CAPITALIZE");
  }

  @Override
  public String visitCellReference(CellReference ref) throws IllegalStateException {
    throw new IllegalStateException("Invalid argument to CAPITALIZE");
  }

  @Override
  public String visitFunction(Function func) throws IllegalStateException {
    return this.apply(func.evaluate(this.cells));
  }

  @Override
  public String apply(Formula arg1) {
    return arg1.accept(this);
  }
}
