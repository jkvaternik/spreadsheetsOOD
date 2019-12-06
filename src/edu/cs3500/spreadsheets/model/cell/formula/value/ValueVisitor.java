package edu.cs3500.spreadsheets.model.cell.formula.value;

import edu.cs3500.spreadsheets.model.IFunc;

/**
 * Represents visitor pattern for Value.
 */
public interface ValueVisitor<R> extends IFunc<Value, R> {

  /**
   * Visits the boolean value.
   * @param val the value
   * @return Whatever type the specific visitor returns
   */
  public R visitBoolean(BooleanValue val);

  /**
   * Visits the string value.
   * @param val the value
   * @return Whatever type the specific visitor returns
   */
  public R visitString(StringValue val);

  /**
   * Visits the double value.
   * @param val the value
   * @return Whatever type the specific visitor returns
   */
  public R visitDouble(DoubleValue val);

  /**
   * Visits the error value.
   * @param val the value
   * @return Whatever type the specific visitor returns
   */
  public R visitError(ErrorValue val);
}
