package edu.cs3500.spreadsheets.model.cell.formula.function;

import edu.cs3500.spreadsheets.model.cell.formula.CellReference;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.formula.value.BooleanValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.DoubleValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.StringValue;

public interface FormulaVisitor<R> extends IFunc<Formula, R> {
  public R visitDoubleValue(DoubleValue val);
  public R visitBooleanValue(BooleanValue val);
  public R visitStringValue(StringValue val);
  public R visitCellReference(CellReference ref);
  public R visitSumFunction(SumFunction func);
  public R visitProductFunction(ProductFunction func);
  public R visitLessThanFunction(LessThanFunction func);
  public R visitCapitalizeFunction(CapitalizeFunction func);
}