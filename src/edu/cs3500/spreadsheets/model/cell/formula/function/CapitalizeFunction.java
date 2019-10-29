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
  public String visitDoubleValue(DoubleValue val) {
    return null;
  }

  @Override
  public String visitBooleanValue(BooleanValue val) {
    return null;
  }

  @Override
  public String visitStringValue(StringValue val) {
    return null;
  }

  @Override
  public String visitErrorValue(ErrorValue val) {
    return null;
  }

  @Override
  public String visitCellReference(CellReference ref) {
    return null;
  }

  @Override
  public String visitFunction(Function func) {
    return null;
  }

  @Override
  public String apply(Formula arg1) {
    return null;
  }
}
