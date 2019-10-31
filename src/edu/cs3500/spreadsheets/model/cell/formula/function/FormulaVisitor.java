package edu.cs3500.spreadsheets.model.cell.formula.function;

import edu.cs3500.spreadsheets.model.IFunc;
import edu.cs3500.spreadsheets.model.cell.formula.CellReference;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.formula.value.BooleanValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.DoubleValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.ErrorValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.StringValue;

public interface FormulaVisitor<R> extends IFunc<Formula, R> {
  R visitDoubleValue(DoubleValue val) throws IllegalStateException;
  R visitBooleanValue(BooleanValue val) throws IllegalStateException;
  R visitStringValue(StringValue val) throws IllegalStateException;
  R visitErrorValue(ErrorValue val) throws IllegalStateException;
  R visitCellReference(CellReference ref) throws IllegalStateException;
  R visitFunction(Function func) throws IllegalStateException;
}
