package edu.cs3500.spreadsheets.model.cell.formula.value;


/**
 * Represents a function object that determines whether or not a value is an ErrorValue.
 */
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
  public Boolean apply(Value val) throws IllegalStateException {
    return val.accept(this);
  }
}
