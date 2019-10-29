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
public class LessThanFunction implements FormulaVisitor<Boolean> {

  /**
   * Constructs an instance of the LessThanFunction given a list of one or more arguments.
   */
  public LessThanFunction() {

  }

  @Override
  public Boolean visitDoubleValue(DoubleValue val) {
    return null;
  }

  @Override
  public Boolean visitBooleanValue(BooleanValue val) {
    return null;
  }

  @Override
  public Boolean visitStringValue(StringValue val) {
    return null;
  }

  @Override
  public Boolean visitErrorValue(ErrorValue val) {
    return null;
  }

  @Override
  public Boolean visitCellReference(CellReference ref) {
    return null;
  }

  @Override
  public Boolean visitFunction(Function func) {
    return null;
  }

  @Override
  public Boolean apply(Formula arg1, Boolean arg2) {
    return null;
  }
}
