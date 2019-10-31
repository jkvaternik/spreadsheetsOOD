package edu.cs3500.spreadsheets.model.cell.formula.function;

import edu.cs3500.spreadsheets.model.IFunc;
import edu.cs3500.spreadsheets.model.cell.formula.CellReference;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.formula.value.BooleanValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.DoubleValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.ErrorValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.StringValue;

public interface FormulaVisitor<R> extends IFunc<Formula, R> {
  public R visitDoubleValue(DoubleValue val) throws IllegalStateException;
  public R visitBooleanValue(BooleanValue val) throws IllegalStateException;
  public R visitStringValue(StringValue val) throws IllegalStateException;
  public R visitErrorValue(ErrorValue val) throws IllegalStateException;
  public R visitCellReference(CellReference ref) throws IllegalStateException;
  public R visitFunction(Function func) throws IllegalStateException;
}
