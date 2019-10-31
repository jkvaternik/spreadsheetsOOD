package edu.cs3500.spreadsheets.model.cell.formula.value;

public class IsErrorFunction implements ValueVisitor<Boolean> {
  @Override
  public Boolean visitBoolean(BooleanValue val) {
    return false;
  }

  @Override
  public Boolean visitString(StringValue val) {
    return false;
  }

  @Override
  public Boolean visitDouble(DoubleValue val) {
    return false;
  }

  @Override
  public Boolean visitError(ErrorValue val) {
    return true;
  }


  @Override
  public Boolean apply(Value arg1) throws IllegalStateException {
    return arg1.accept(this);
  }
}
