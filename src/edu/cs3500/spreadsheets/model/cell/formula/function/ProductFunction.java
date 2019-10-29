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
 * Represents the product function.
 */
public class ProductFunction implements FormulaVisitor<Double> {

  /**
   * Constructs an instance of the ProductFunction given a list of one or more arguments.
   */
  public ProductFunction() {

  }


  @Override
  public Double visitDoubleValue(DoubleValue val) {
    return null;
  }

  @Override
  public Double visitBooleanValue(BooleanValue val) {
    return null;
  }

  @Override
  public Double visitStringValue(StringValue val) {
    return null;
  }

  @Override
  public Double visitErrorValue(ErrorValue val) {
    return null;
  }

  @Override
  public Double visitCellReference(CellReference ref) {
    return null;
  }

  @Override
  public Double visitFunction(Function func) {
    return null;
  }

  @Override
  public Double apply(Formula arg1, Double arg2) {
    return null;
  }
}
