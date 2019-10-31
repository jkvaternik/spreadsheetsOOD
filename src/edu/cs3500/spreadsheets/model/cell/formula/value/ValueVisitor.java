package edu.cs3500.spreadsheets.model.cell.formula.value;

import edu.cs3500.spreadsheets.model.IFunc;

public interface ValueVisitor<R> extends IFunc<Value, R> {
  public R visitBoolean(BooleanValue val);

  public R visitString(StringValue val);

  public R visitDouble(DoubleValue val);

  public R visitError(ErrorValue val);
}
