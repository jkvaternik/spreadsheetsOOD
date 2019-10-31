package edu.cs3500.spreadsheets.model.cell.formula.value;

import edu.cs3500.spreadsheets.model.cell.formula.Formula;


/**
 * Represents one of the allowable value types of a cell in the spreadsheet.
 */
public interface Value extends Formula {

  /**
   * Gets the internal value of this Value.
   *
   * @param <T> The type of primitive value
   * @return The primitive value
   */
  <T> T getValue();

  /**
   * Returns the result of applying the given visitor to this Value.
   *
   * @param visitor the given ValueVisitor<R>
   * @param <R>     The value type returned by the visitor (not Value)
   * @return the result of applying the given visitor to this Value
   */
  <R> R accept(ValueVisitor<R> visitor);
}
