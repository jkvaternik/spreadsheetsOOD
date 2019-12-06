package edu.cs3500.spreadsheets.model.cell.formula.function;

import edu.cs3500.spreadsheets.model.IFunc;
import edu.cs3500.spreadsheets.model.cell.formula.CellReference;
import edu.cs3500.spreadsheets.model.cell.formula.Formula;
import edu.cs3500.spreadsheets.model.cell.formula.value.BooleanValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.DoubleValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.ErrorValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.StringValue;

/**
 * Represents visitor pattern for Formula.
 */
public interface FormulaVisitor<R> extends IFunc<Formula, R> {

  /**
   * Visits the Double values.
   * @param val the value
   * @return Whatever type the specific visitor returns
   * @throws IllegalStateException If the argument type is invalid for the specific visitor
   */
  R visitDoubleValue(DoubleValue val) throws IllegalStateException;

  /**
   * Visits the Boolean values.
   * @param val the value
   * @return Whatever type the specific visitor returns
   * @throws IllegalStateException If the argument type is invalid for the specific visitor
   */
  R visitBooleanValue(BooleanValue val) throws IllegalStateException;


  /**
   * Visits the String values.
   * @param val the value
   * @return Whatever type the specific visitor returns
   * @throws IllegalStateException If the argument type is invalid for the specific visitor
   */
  R visitStringValue(StringValue val) throws IllegalStateException;

  /**
   * Visits the Error values.
   * @param val the value
   * @return Whatever type the specific visitor returns
   * @throws IllegalStateException If the argument type is invalid for the specific visitor
   */
  R visitErrorValue(ErrorValue val) throws IllegalStateException;

  /**
   * Visits the Cell reference.
   * @param ref the reference
   * @return Whatever type the specific visitor returns
   * @throws IllegalStateException If the argument type is invalid for the specific visitor
   */
  R visitCellReference(CellReference ref) throws IllegalStateException;

  /**
   * Visits the function.
   * @param func the function
   * @return Whatever type the specific visitor returns
   * @throws IllegalStateException If the argument type is invalid for the specific visitor
   */
  R visitFunction(Function func) throws IllegalStateException;
}
